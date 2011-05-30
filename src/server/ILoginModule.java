package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import client.SPClient;

public interface ILoginModule extends Remote {
	
	public IChatroom login(SPClient client, String pwd) throws RemoteException;

}
