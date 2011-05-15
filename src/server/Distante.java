package server;
import java.rmi.Remote;
import java.rmi.RemoteException;

import client.IClient;


public interface Distante extends Remote{

	public String echo() throws RemoteException;
	public boolean login(String login, String password, IClient client) throws RemoteException;
	public boolean logout(String login) throws RemoteException;
}