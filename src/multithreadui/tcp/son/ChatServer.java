package multithreadui.tcp.son;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class ChatServer {

	private List<Socket> sockets = new ArrayList<Socket>();
	@SuppressWarnings("unused")
	private int port;

	public ChatServer(int port) {
		this.port = port;

		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			while (true) {
				Socket socket = serverSocket.accept(); // DỪNG LẠI CHỜ 1 CLIENT GỞI ĐẾN
				if (socket.isConnected()) {
					HandleForClientThread connectionThread = new HandleForClientThread(socket);
					connectionThread.start();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	class HandleForClientThread extends Thread {
		Socket socket;

		public HandleForClientThread(Socket socket) {
			this.socket = socket;
			sockets.add(socket);
			// System.out.println("Số lượng client: " + sockets.size());
		}

		@Override
		public void run() {
			try {
				DataInputStream inStream = new DataInputStream(socket.getInputStream());
				// DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
				String user = inStream.readUTF();
				this.sendToAll(user + " vừa vào phòng");

				// ĐỌC TIN NHẮN CỦA CLIENT NÀY: CLIENT LÀ BIẾN SOCKET, MỖI CLIENT VÀO LÀ CÓ MỘT
				// SOCKET MỚI
				while (true) {
					String data = inStream.readUTF();
					this.sendToAll(user + ": " + data);
				}
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}

		public void sendToAll(String message) {
			// System.out.println("Nhận từ client rồi gởi all client: " + message);
			synchronized (sockets) {
				for (Socket socket : sockets) {
					try {
						if (socket.isConnected()) {
							DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
							outStream.writeUTF(message);
						}
					} catch (Exception e) {
						//e.printStackTrace();
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		int port = Integer.parseInt(JOptionPane.showInputDialog("Nhập cổng: ", "1998"));
		new ChatServer(port);
	}

}
