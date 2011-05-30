package client;

import java.awt.Color;
import java.awt.Font;
import java.rmi.RemoteException;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

public class Tab implements ITab {
	private String name;
	private SPClient client;
	private JTextArea messageArea;
	private JScrollPane scrollPane;
	private final Font font = new Font("Verdana", Font.BOLD, 12);
	
	public Tab(String name, JTabbedPane tabbedPane){
		this.name = name;
		this.messageArea = new JTextArea(29, 47);
		messageArea.setEditable(false);
		messageArea.setFont(font);
		messageArea.setForeground(Color.BLACK);
		scrollPane = new JScrollPane(messageArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
		          JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		tabbedPane.add(name, scrollPane);
	}
	
	public Tab(SPClient client, JTabbedPane tabbedPane){
		this.client = client;
		try {
	          this.name = client.getName();
          } catch (RemoteException e) {
	          e.printStackTrace();
          }
		this.messageArea = new JTextArea(29, 47);
		messageArea.setEditable(false);
		messageArea.setFont(font);
		messageArea.setForeground(Color.BLACK);
		scrollPane = new JScrollPane(messageArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
		          JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		tabbedPane.add(name, scrollPane);
	}
	
	public String getName() {
     	return name;
     }

	public void setName(String name) {
     	this.name = name;
     }

	public SPClient getClient() {
     	return client;
     }

	public void setClient(SPClient client) {
     	this.client = client;
     }

	public Font getFont() {
     	return font;
     }

	public JTextArea getMessageArea() {
     	return messageArea;
     }

	public void setMessageArea(JTextArea messageArea) {
     	this.messageArea = messageArea;
     }

	public JScrollPane getScrollPane() {
     	return scrollPane;
     }

	public void setScrollPane(JScrollPane scrollPane) {
     	this.scrollPane = scrollPane;
     }

	public void addMessage(String message) {
		messageArea.setText(messageArea.getText() + "\n" + message);
		int x;
		messageArea.selectAll();
		messageArea.setSelectedTextColor(Color.black);
		x = messageArea.getSelectionEnd();
		messageArea.select(x, x);
		messageArea.repaint();
	}
}
