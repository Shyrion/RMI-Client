package client;

import java.io.Serializable;
import java.rmi.RemoteException;

import server.IChatroom;

public class SPClient implements ItfClient, Serializable {
	private static final long serialVersionUID = 1L;
	ItfClient client = null;
	String name = null;

	public SPClient(IClient c) {
		this.client = c;
		try {
			this.name = new String(c.getName());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public IChatroom getChatroom() throws RemoteException {
		return client.getChatroom();
	}

	@Override
	public IClientFrame getFrame() throws RemoteException {
		return client.getFrame();
	}

	@Override
	public String getName() throws RemoteException {
		return name;
	}
	
	public boolean isConnected()  throws RemoteException {
		return client.isConnected();
	}

	public void setConnected(boolean connected)  throws RemoteException {
		client.setConnected(connected);
	}

	@Override
	public void notifyConnect(SPClient user) throws RemoteException {
		client.notifyConnect(user);
	}

	@Override
	public void notifyDisconnect(String message) throws RemoteException {
		client.notifyDisconnect(message);
	}

	@Override
	public void notifyMessage(String sender, String message) throws RemoteException {
		client.notifyMessage(sender, message);
	}

	@Override
	public void notifyPrivateMessage(SPClient sender, String message) throws RemoteException {
		client.notifyPrivateMessage(sender, message);
	}

	@Override
	public void setChatroom(IChatroom d) throws RemoteException {
		client.setChatroom(d);
	}

	@Override
	public void setFrame(IClientFrame frame) throws RemoteException {
		client.setFrame(frame);
	}

	@Override
	public void setName(String name) throws RemoteException {
		client.setName(name);
	}
}
