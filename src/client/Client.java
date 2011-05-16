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

	String name;
	
	protected Client() throws RemoteException {
		super();
		this.name = "";
	}
	
	protected Client(String name) throws RemoteException {
		super();
		this.name = name;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		try {
			//System.setSecurityManager(new java.rmi.RMISecurityManager());
			Distante obj = (Distante) Naming.lookup("cr1");
			IClient client = new Client();
			Console console = System.console();
			
			if(console == null){
				System.err.println("Error : No console ! Please run it with command line :).");
			}
			
			String login = console.readLine("Please enter your login : ");
			String password = console.readLine("Please enter your password : ");
			
			// ArgumentMismatchException... WTF ?
			if(obj.login(login, password, client)){
				// switch sur la commande
				//boucle qui le tient en vie, tu peux envoyer un message
			}

			return;
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void notify(String message) throws RemoteException{
		System.out.println(message);
	}
	
	public void notifyConnect(String message) throws RemoteException{
		notify(message + " has joined the channel.");
		// set la liste des gens connectés
	}
	
	public void notifyDisconnect(String message) throws RemoteException{
		notify(message + " has left the channel.");
		// set la liste des gens connectés
	}
	
}
