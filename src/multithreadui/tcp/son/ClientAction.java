package multithreadui.tcp.son;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

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

	@SuppressWarnings("unused")
	private Socket socket;
	@SuppressWarnings("unused")
	private String username;
	private DataInputStream inStream;
	private DataOutputStream outStream;

	public ClientAction(Socket socket, String username) {
		this.socket = socket;
		this.username = username;
		try {
			inStream = new DataInputStream(socket.getInputStream());
			outStream = new DataOutputStream(socket.getOutputStream());

			outStream.writeUTF(username);
		} catch (IOException e) {
			e.printStackTrace();
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
					try {
						outStream.writeUTF(message);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					txtMsg.setText("");
				}
			}
		});
	}

	@Override
	public void run() {
		try {
			while (true) {
				// ĐỌC TIN NHẮN CHAT CỦA NGƯỜI KHÁC
				String message = inStream.readUTF();
				txtConversation.append(message + "\n");
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

}