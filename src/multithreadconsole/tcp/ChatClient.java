package multithreadconsole.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
	Socket socket = null;
	DataInputStream inStream = null;
	DataOutputStream outStream = null;
	Scanner scanner = new Scanner(System.in);

	public void chat() {
		try {
			socket = new Socket("localhost", 2000);
			inStream = new DataInputStream(socket.getInputStream());
			outStream = new DataOutputStream(socket.getOutputStream());
			
			//NHẬP TÊN TRƯỚC KHI CHAT
			System.out.print("Nhập tên của bạn: ");
			outStream.writeUTF(scanner.nextLine()); //GỞI ĐẾN SERVER
			
			//LẮNG NGHE TỪ SERVER
			
			Thread clientThread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						while (true) {
							//ĐỌC TIN NHẮN CHAT CỦA NGƯỜI KHÁC
							String message = inStream.readUTF();
							System.out.println(message);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			clientThread.start();
		
			//NHẬP TIN NHẮN CHAT
			while (true) {
				String msg = scanner.nextLine();
				outStream.writeUTF(msg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new ChatClient().chat();
	}
}
