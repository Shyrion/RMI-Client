package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

public class ClientFrame extends JFrame {
	private JPanel mainPanel;
     private JSplitPane horizontalSplitPane;
     private JSplitPane verticalSplitPane;
     private JScrollPane messagePane;
     private JPanel inputPanel;
     
     private JScrollPane usersPane;
     private JList usersJList;
     private DefaultListModel usersModel;

     private JTextArea inputArea;
     private JButton sendButton;
     
     private IClient client;
     
     public ClientFrame(IClient client){
     	this.client = client;
     	buildFrame();
     }
	
     private void buildFrame() {
     	mainPanel = new JPanel(new BorderLayout());
     	
     	usersModel = new DefaultListModel();
     	usersJList = new JList(usersModel);
     	usersPane = new JScrollPane(usersJList);
     
     	messagePane = new JScrollPane();
     	
     	horizontalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, messagePane, usersPane);
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
	                    client.getChatroom().broadCastMessage(client, inputArea.getText());
                    } catch (RemoteException e1) {
	                    e1.printStackTrace();
                    }
			}
		});
     	
     	verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, horizontalSplitPane, inputPanel);
     	verticalSplitPane.setDividerLocation(450);
     	verticalSplitPane.setEnabled(false);
     	
     	mainPanel.add(verticalSplitPane);
     	
          this.setMinimumSize(new Dimension(800, 600));
          this.setSize(600, 500);
          this.setResizable(false);
          this.setLocationRelativeTo(null);
          this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          this.setTitle("ChatRoom");
          this.setContentPane(mainPanel);
          this.setVisible(true);
     }
}
