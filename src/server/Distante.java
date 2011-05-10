package server;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface Distante extends Remote{

	public String echo() throws RemoteException;
	public boolean login(String nom) throws RemoteException;
	public boolean logout(String nom) throws RemoteException;
}