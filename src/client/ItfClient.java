package client;

import java.rmi.RemoteException;

import server.IChatroom;

public interface ItfClient {

	public IChatroom getChatroom() throws RemoteException;

	public void setChatroom(IChatroom d) throws RemoteException;

	public String getName() throws RemoteException;

	public void setName(String name) throws RemoteException;
	
	public boolean isConnected() throws RemoteException ;

	public void setConnected(boolean connected) throws RemoteException ;

	public IClientFrame getFrame() throws RemoteException;

	public void setFrame(IClientFrame frame) throws RemoteException;

	public void notifyConnect(SPClient user) throws RemoteException;

	public void notifyDisconnect(String message) throws RemoteException;

	public void notifyMessage(String sender, String message) throws RemoteException;

	public void notifyPrivateMessage(SPClient sender, String message) throws RemoteException;
}
