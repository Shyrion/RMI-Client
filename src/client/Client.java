package client;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import server.Distante;


public class Client {

	public static void main(String[] args) {
		try {
			//System.setSecurityManager(new java.rmi.RMISecurityManager());
			Distante obj = (Distante) Naming.lookup("cr1");
			obj.login("lol");
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
	
}
