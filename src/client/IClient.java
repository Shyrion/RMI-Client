package client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote{

	public void notify(String string) throws RemoteException;

}
