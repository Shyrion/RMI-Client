package client;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public interface ITab {
	public String getName();

	public void setName(String name);
	
	public SPClient getClient();
	
	public void setClient(SPClient client);

	public JTextArea getMessageArea();

	public void setMessageArea(JTextArea messageArea);

	public JScrollPane getScrollPane();

	public void setScrollPane(JScrollPane scrollPane);

	public void addMessage(String message);
}
