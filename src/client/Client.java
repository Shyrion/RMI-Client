package client;

import java.io.Console;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import server.Distante;

public class Client extends UnicastRemoteObject implements IClient {
	private static final long serialVersionUID = 1L;
	private String name;
	private Distante chatroom;
	private IClientFrame frame;

	protected Client() throws RemoteException {
		super();
		this.name = "";
	}

	protected Client(String name) throws RemoteException {
		super();
		this.name = name;
	}

	public Distante getChatroom() throws RemoteException {
		return chatroom;
	}

	public void setChatroom(Distante chatroom) throws RemoteException {
		this.chatroom = chatroom;
	}

	public IClientFrame getFrame() {
		return frame;
	}

	public void setFrame(IClientFrame frame) {
		this.frame = frame;
	}

	public static void main(String[] args) {
		try {
			// System.setSecurityManager(new java.rmi.RMISecurityManager());
			Distante cr1 = (Distante) Naming.lookup("cr1");
			IClient client = new Client();
			client.setChatroom(cr1);
			Console console = System.console();

			if (console == null) {
				System.err.println("Error : No console ! Please run it with command line :).");
			}

			String login = console.readLine("Please enter your login : ");
			String password = console.readLine("Please enter your password : ");

			client.setFrame(new ClientFrame(client));
			final IClient c2 = client;
			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					try {
						c2.getChatroom().logout(c2);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
			});
			if (client.getChatroom().login(login, password, client)) {
				
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void print(String message) throws RemoteException {
		frame.addMessage(message);
	}

	public void notifyConnect(String message) throws RemoteException {
		print(message + " has joined the channel.");
		frame.addUser(message);
	}

	public void notifyDisconnect(String message) throws RemoteException {
		print(message + " has left the channel.");
		frame.removeUser(message);
	}

	public void notifyMessage(String sender, String message) throws RemoteException {
		print(sender + " : " + message);
	}

	public void notifyLogout() throws RemoteException {
		print("Good bye !");
	}
}
