package server;
import java.rmi.Remote;
import java.rmi.RemoteException;

import client.IClient;


public interface Distante extends Remote{
	public String echo() throws RemoteException;
	public boolean login(String login, String password, IClient client) throws RemoteException;
	public boolean logout(IClient client) throws RemoteException;
	
	public boolean sendPrivateMessage(IClient sender, IClient receiver, String message)  throws RemoteException;
	public boolean broadCastMessage(IClient sender, String message)  throws RemoteException;
}