package multithreadui.udp.son;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class ClientAction extends JFrame implements Runnable {
	private static final long serialVersionUID = 1L;

	private JTextField txtMsg;
	private JTextArea txtConversation;
	private JButton btnSend;
	private JLabel lblTitle;

	private String username;
	private int port;
	private InetAddress ipAddress;
	private DatagramSocket clientSocket;

	public ClientAction(DatagramSocket clientSocket, String username, InetAddress ipAddress, int port) {
		this.port = port;
		this.ipAddress = ipAddress;
		this.clientSocket = clientSocket;
		this.username = username;

		//gởi lên server username
		byte[] sendData = username.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
		try {
			clientSocket.send(sendPacket);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		addControls();
		addEvents();
	}

	public void addControls() {
		Container container = getContentPane();
		container.setLayout(new BorderLayout(0, 0));

		JPanel pnTitle = new JPanel();
		container.add(pnTitle, BorderLayout.NORTH);

		lblTitle = new JLabel();
		lblTitle.setText("Tôi là " + username);
		pnTitle.add(lblTitle);

		JPanel pnMsg = new JPanel();
		container.add(pnMsg, BorderLayout.CENTER);
		pnMsg.setLayout(new BorderLayout(0, 0));

		txtConversation = new JTextArea();
		pnMsg.add(txtConversation, BorderLayout.CENTER);

		JPanel pnSend = new JPanel();
		container.add(pnSend, BorderLayout.SOUTH);
		pnSend.setLayout(new BorderLayout(0, 0));

		txtMsg = new JTextField();
		pnSend.add(txtMsg, BorderLayout.CENTER);
		txtMsg.setColumns(10);

		btnSend = new JButton("Send");
		pnSend.add(btnSend, BorderLayout.EAST);

		setSize(400, 300);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void addEvents() {
		btnSend.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String message = txtMsg.getText().trim();
				if (!message.equals("")) {
					byte[] sendData = message.getBytes();
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
					try {
						clientSocket.send(sendPacket);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				txtMsg.setText("");
			}
		});
	}

	@Override
	public void run() {
		try {
			while (true) {
				// ĐỌC TIN NHẮN CHAT CỦA NGƯỜI KHÁC
				byte[] receiveData = new byte[1024];
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				clientSocket.receive(receivePacket);
				String message = new String(receivePacket.getData()).trim();
				txtConversation.append(message + "\n");
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

}