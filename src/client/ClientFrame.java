package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

public class ClientFrame extends JFrame implements IClientFrame {
	private JPanel mainPanel;
	private JSplitPane horizontalSplitPane;
	private JSplitPane verticalSplitPane;

	private JPanel messagePanel;
	private JScrollPane messageScrollPane;
	private JTextArea messageArea;

	private JScrollPane usersPane;
	private JList usersJList;
	private DefaultListModel usersModel;

	private JPanel inputPanel;
	private JTextArea inputArea;
	private JButton sendButton;

	private IClient client;

	public ClientFrame(IClient client) {
		this.client = client;
		buildFrame();
	}

	public void buildFrame() {
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
	                    client.getChatroom().logout(client);
                    } catch (RemoteException e1) {
	                    e1.printStackTrace();
                    }
			}
		});
		
		mainPanel = new JPanel(new BorderLayout());

		usersModel = new DefaultListModel();
		usersJList = new JList(usersModel);
		usersPane = new JScrollPane(usersJList);

		messagePanel = new JPanel();
		messagePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		messageArea = new JTextArea(29, 47);
		messageArea.setEditable(false);
		Font font = new Font("Verdana", Font.BOLD, 12);
		messageArea.setFont(font);
		messageArea.setForeground(Color.BLACK);
		messageScrollPane = new JScrollPane(messageArea,
		          JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
		          JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		messagePanel.add(messageScrollPane);

		horizontalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
		          messagePanel, usersPane);
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
				try {
					client.getChatroom().broadCastMessage(client,
					          inputArea.getText());
					inputArea.setText("");
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});

		verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
		          horizontalSplitPane, inputPanel);
		verticalSplitPane.setDividerLocation(450);
		verticalSplitPane.setEnabled(false);

		mainPanel.add(verticalSplitPane);

		this.setMinimumSize(new Dimension(800, 600));
		this.setSize(800, 600);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("ChatRoom");
		this.setContentPane(mainPanel);
		this.setVisible(true);
	}

	public void addMessage(String message) {
		messageArea.setText(messageArea.getText() + "\n" + message);
		int x;
		messageArea.selectAll();
		messageArea.setSelectedTextColor(Color.black);
		x = messageArea.getSelectionEnd();
		messageArea.select(x,x);
		messageArea.repaint();
	}
}
