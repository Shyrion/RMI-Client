package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.DefaultListModel;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

public class ClientFrame extends JFrame implements IClientFrame {
	private static final long serialVersionUID = 1L;
	private static final String TEXT_SUBMIT = "text-submit";
	private static final String INSERT_BREAK = "insert-break";

	private JPanel mainPanel;
	private JSplitPane horizontalSplitPane;
	private JSplitPane verticalSplitPane;

	private JTabbedPane messageTabbedPane;
	private ArrayList<ITab> tabs;

	private JScrollPane usersPane;
	private JList usersJList;
	private DefaultListModel usersModel;

	private JPanel inputPanel;
	private JTextArea inputArea;
	private JButton sendButton;

	private SPClient client;

	private final String chatroomName = "Chatroom";

	public ClientFrame(SPClient client) {
		this.client = client;
		buildFrame();
	}

	public ArrayList<ITab> getTabs() {
		return tabs;
	}

	public String getChatroomName() {
		return chatroomName;
	}
	
	public void visible(boolean b){
		this.setVisible(b);
	}

	public void buildFrame() {
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					SPClient spClient = client;
					client.getChatroom().logout(spClient);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});

		mainPanel = new JPanel(new BorderLayout());

		usersModel = new DefaultListModel();
		usersJList = new JList(usersModel);
		usersJList.setPreferredSize(new Dimension(140, 400));
		usersJList.setMinimumSize(new Dimension(140, 400));
		usersJList.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					try {
						String name = client.getName();
						String target = (String) usersJList.getSelectedValue();
						if (!target.equals(name)) {
							int find = findOpenedTab(target);
							if (find == -1) {
								SPClient c = client.getChatroom().getClient(
								          (String) usersJList.getSelectedValue());
								createTab(c);
							} else {
								messageTabbedPane.setSelectedIndex(find);
							}
						}
					} catch (RemoteException e) {
						System.err.println(e);
					}
				}
			}

			public void mouseReleased(MouseEvent event) {
			}

			public void mousePressed(MouseEvent event) {
			}

			public void mouseExited(MouseEvent event) {
			}

			public void mouseEntered(MouseEvent event) {
			}
		});
		usersPane = new JScrollPane(usersJList);

		// Création du panneau onglets et création de l'onglet chatroom
		tabs = new ArrayList<ITab>();
		messageTabbedPane = new JTabbedPane();

		horizontalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, messageTabbedPane,
		          usersPane);
		horizontalSplitPane.setDividerLocation(600);
		horizontalSplitPane.setEnabled(false);
		inputPanel = new JPanel();
		inputPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		inputArea = new JTextArea(6, 60);
		inputPanel.add(inputArea);
		sendButton = new JButton("Send");
		inputPanel.add(sendButton);

		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendMessage();
			}
		});

		InputMap input = inputArea.getInputMap();
		KeyStroke enter = KeyStroke.getKeyStroke("ENTER");
		KeyStroke shiftEnter = KeyStroke.getKeyStroke("shift ENTER");
		input.put(shiftEnter, INSERT_BREAK);
		input.put(enter, TEXT_SUBMIT);

		ActionMap actions = inputArea.getActionMap();
		actions.put(TEXT_SUBMIT, new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				sendMessage();
			}
		});

		verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, horizontalSplitPane,
		          inputPanel);
		verticalSplitPane.setDividerLocation(450);
		verticalSplitPane.setEnabled(false);

		mainPanel.add(verticalSplitPane);

		this.setMinimumSize(new Dimension(800, 600));
		this.setSize(800, 600);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			this.setTitle("ChatRoom - " + client.getName());
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		this.setContentPane(mainPanel);
		this.setVisible(false);
	}

	public void sendMessage() {
		if (inputArea.getText() != "") {
			try {
				if (messageTabbedPane.getTitleAt(messageTabbedPane.getSelectedIndex()).equals(
				          chatroomName)) {
					client.getChatroom().broadCastMessage(client.getName(),
					          inputArea.getText());
				} else {
					String destName = messageTabbedPane.getTitleAt(messageTabbedPane
					          .getSelectedIndex());
					if (destName != null) {
						ITab t = findTab(destName);
						t.addMessage(client.getName() + " : " + inputArea.getText());
						t.getClient().notifyPrivateMessage(client, inputArea.getText());
					}
				}
				inputArea.setText("");
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void addUser(SPClient user) {
		try {
			usersModel.addElement(user.getName());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void removeUser(String user) {
		usersModel.removeElement(user);
	}

	public void print(String tabName, String message) {
		ITab t = findTab(tabName);
		if (t == null) {
			t = createTab(tabName);
		}
		t.addMessage(message);
	}

	public void print(SPClient user, String message) {
		ITab t;
		try {
			t = findTab(user.getName());
			if (t == null) {
				t = createTab(user);
			}
			t.addMessage(user.getName() + " : " + message);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void printMessage(String sender, String message) {
		print(chatroomName, sender + " : " + message);
	}

	public void printConnect(String user) {
		print(chatroomName, user + " has joined the channel.");
	}

	public void printDisconnect(String user) {
		print(chatroomName, user + " has left the channel.");
	}

	public Tab createTab(String name) {
		Tab newTab = new Tab(name, messageTabbedPane);
		tabs.add(newTab);
		messageTabbedPane.setSelectedIndex(messageTabbedPane.getTabCount() - 1);
		return newTab;
	}

	public Tab createTab(SPClient user) {
		Tab newTab = new Tab(user, messageTabbedPane);
		tabs.add(newTab);
		messageTabbedPane.setSelectedIndex(messageTabbedPane.getTabCount() - 1);
		return newTab;
	}

	public ITab findTab(String name) {
		for (int i = 0; i < tabs.size(); i++) {
			if (tabs.get(i).getName().equals(name)) {
				return tabs.get(i);
			}
		}
		return null;
	}

	public int findOpenedTab(String name) {
		for (int i = 0; i < messageTabbedPane.getTabCount(); i++) {
			if (messageTabbedPane.getTitleAt(i).equals(name)) {
				return i;
			}
		}
		return -1;
	}
}
