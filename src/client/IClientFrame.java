package client;

public interface IClientFrame {
	public void buildFrame();

	public void addMessage(String message);

	public void addUser(String user);

	public void removeUser(String user);
}
