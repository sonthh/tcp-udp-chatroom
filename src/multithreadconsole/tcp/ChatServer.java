package multithreadconsole.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
	
	List<Socket> sockets = new ArrayList<Socket>();
	
	public void chat() {
		ServerSocket serverSocket = null;
		try {		
			serverSocket = new ServerSocket(2000);
			while (true) {
				Socket socket = serverSocket.accept();  //DỪNG LẠI CHỜ 1 CLIENT GỞI ĐẾN
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
			System.out.println("Số client v2: " + sockets.size());
		}

		@Override
		public void run() {
			try {
				DataInputStream inStream = new DataInputStream(socket.getInputStream());
				//DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
				String user = inStream.readUTF();
				this.sendToAll(user + " vừa vào phòng");

				// ĐỌC TIN NHẮN CỦA CLIENT NÀY: CLIENT LÀ BIẾN SOCKET, MỖI CLIENT VÀO LÀ CÓ MỘT
				// SOCKET MỚI
				while (true) {
					String data = inStream.readUTF();
					this.sendToAll(user + ": " + data);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void sendToAll(String message) {
			System.out.println("Nhận từ client rồi gởi all client: " + message);
			synchronized (sockets) {
				for (Socket socket : sockets) {
					try {
						DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
						outStream.writeUTF(message);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		new ChatServer().chat();
	}

}
