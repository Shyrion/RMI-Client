package client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote{

	public String getName() throws RemoteException;
	public void setName(String name) throws RemoteException;
	public void notify(String string) throws RemoteException;
	public void notifyConnect(String string) throws RemoteException;
	public void notifyDisconnect(String message) throws RemoteException;

}
