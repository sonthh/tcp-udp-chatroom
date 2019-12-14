package multithreadui.udp.son;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChatClient extends JFrame {
	private static final long serialVersionUID = 1L;

	Scanner scanner = new Scanner(System.in);
	DatagramSocket clientSocket;
	DatagramPacket sendPacket;
	DatagramPacket receivePacket;
	
	private JTextField txtUsername;
	private JTextField txtPort;
	private JTextField txtServerName;
	private JButton btnLogin;

	public ChatClient() {
		addControls();
		addEvents();
	}

	public void addControls() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setTitle("Đăng nhập");
		Container container = getContentPane();
		container.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		JPanel pnUsername = new JPanel();
		container.add(pnUsername);

		JLabel lblUsername = new JLabel("Username");
		pnUsername.add(lblUsername);

		txtUsername = new JTextField();
		txtUsername.setColumns(10);
		pnUsername.add(txtUsername);

		JPanel pnPort = new JPanel();
		container.add(pnPort);

		JLabel lblPort = new JLabel("Port");
		pnPort.add(lblPort);

		txtPort = new JTextField();
		txtPort.setColumns(10);
		txtPort.setText("1998");
		pnPort.add(txtPort);

		JPanel pnServerName = new JPanel();
		container.add(pnServerName);

		JLabel lblServerName = new JLabel("Server name");
		pnServerName.add(lblServerName);

		txtServerName = new JTextField();
		txtServerName.setColumns(10);
		txtServerName.setText("localhost");
		pnServerName.add(txtServerName);

		JPanel pnButton = new JPanel();
		getContentPane().add(pnButton);

		btnLogin = new JButton("Login");
		pnButton.add(btnLogin);
		
		setSize(359, 199);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	public void addEvents() {
		btnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String serverName = txtServerName.getText();
				int port = Integer.parseInt(txtPort.getText());
				String username = txtUsername.getText();
				ChatClient.this.dispose();
				
				
				try {
					clientSocket = new DatagramSocket();
					
					InetAddress ipAdress = InetAddress.getByName(serverName);
					byte[] sendData = new byte[1024];
					
					sendData = username.getBytes();
					sendPacket = new DatagramPacket(sendData, sendData.length, ipAdress, port);
					clientSocket.send(sendPacket); // GỞI VỀ SERVER TÊN USER VỪA VÀO PHÒNG
					
					ClientAction clientAction = new ClientAction(clientSocket, username, ipAdress, port);
					Thread thread = new Thread(clientAction);
					thread.start();
					
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});
	}

	public static void main(String[] args) {
		new ChatClient();
	}
}
