package client;

import java.util.ArrayList;

public interface IClientFrame {
	public void buildFrame();
	
	public void sendMessage();

	public void addUser(SPClient user);

	public void removeUser(String user);
	
	public String getChatroomName();
	
	public ArrayList<ITab> getTabs();
	
	public void print(String tabName, String message);

	public void print(SPClient sender, String message);

	public void printConnect(String name);

	public void printDisconnect(String user);

	public void printMessage(String sender, String message);
	
	public ITab createTab(String name);
	
	public ITab findTab(String name);
	
	public int findOpenedTab(String name);
	
	public void visible(boolean visible);
}
