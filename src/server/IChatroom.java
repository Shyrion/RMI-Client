package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import client.SPClient;

public interface IChatroom extends Remote {
	public boolean login(SPClient client, String pwd) throws RemoteException;

	public boolean logout(SPClient client) throws RemoteException;

	public boolean broadCastMessage(String sender, String message) throws RemoteException;

	public SPClient getClient(String name) throws RemoteException;
}