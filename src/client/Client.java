package client;

import java.io.Console;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import server.*;

@SuppressWarnings("serial")
public class Client extends UnicastRemoteObject implements IClient {
	private String name;
	private IChatroom chatroom;
	private IClientFrame frame;
	private boolean connected = false;

	public Client() throws RemoteException {
		super();
		this.name = "";
	}

	public Client(String name) throws RemoteException {
		super();
		this.name = name;
	}

	public static void main(String[] args) {
		try {
			Console console = System.console();
			if (console == null) {
				System.err.println("Error : No console ! Please run it with command line :).");
				System.exit(1);
			}

			// System.setSecurityManager(new java.rmi.RMISecurityManager());

			ILoginModule lm = (ILoginModule) Naming.lookup("LoginModule");
			IClient client = new Client();

			String login = console.readLine("Please enter your login \t: ");
			String password = console.readLine("Please enter room's password \t: ");

			client.setName(login);
			SPClient spClient = new SPClient(client);

			final SPClient c2 = new SPClient(client);
			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					try {
						c2.getChatroom().logout(c2);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
			});

			client.setFrame(new ClientFrame(spClient));
			IChatroom cr = lm.login(spClient, password);
			if (cr != null) {
				client.setChatroom(cr);
				client.getFrame().visible(true);
				client.setConnected(true);
			}
			while (!client.isConnected()) {
				System.out
				          .println("Connection failed : incorrect password or login already in use.");
				login = console.readLine("Please enter your login \t: ");
				password = console.readLine("Please enter room's password \t: ");
				client.setName(login);
				spClient = new SPClient(client);
				cr = lm.login(spClient, password);
				if (cr != null) {
					client.setChatroom(cr);
					client.getFrame().visible(true);
					client.setConnected(true);
				}
			}
			return;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	public IChatroom getChatroom() throws RemoteException {
		return chatroom;
	}

	public void setChatroom(IChatroom chatroom) throws RemoteException {
		this.chatroom = chatroom;
	}

	public IClientFrame getFrame() throws RemoteException {
		return frame;
	}

	public void setFrame(IClientFrame frame) throws RemoteException {
		this.frame = frame;
	}

	public String getName() throws RemoteException {
		return name;
	}

	public void setName(String name) throws RemoteException {
		this.name = name;
	}

	public boolean isConnected() throws RemoteException {
		return connected;
	}

	public void setConnected(boolean connected) throws RemoteException {
		this.connected = connected;
	}

	public void notifyConnect(SPClient client) throws RemoteException {
		frame.addUser(client);
		frame.printConnect(client.getName());
	}

	public void notifyDisconnect(String user) throws RemoteException {
		frame.removeUser(user);
		frame.printDisconnect(user);
	}

	public void notifyMessage(String sender, String message) throws RemoteException {
		frame.printMessage(sender, message);
	}

	public void notifyPrivateMessage(SPClient sender, String message) throws RemoteException {
		frame.print(sender, message);
	}
}
