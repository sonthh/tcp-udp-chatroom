package multithreadconsole.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ChatClient {
	Scanner scanner = new Scanner(System.in);
	DatagramSocket clientSocket;
	DatagramPacket sendPacket;
	DatagramPacket receivePacket;
	
	public void chat() {

		try {
			clientSocket = new DatagramSocket();
			
			InetAddress iPAddress = InetAddress.getByName("localhost");
			byte[] sendData = new byte[1024];
			byte[] receiveData = new byte[1024];
			
			System.out.println("Mời bạn nhập tên: ");
			String data = scanner.nextLine().trim();
			sendData = data.getBytes();
			sendPacket = new DatagramPacket(sendData, sendData.length, iPAddress, 3000);
			
			clientSocket.send(sendPacket); // GỞI VỀ SERVER

			// LẮNG NGHE TỪ SERVER

			Thread clientThread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						while (true) {
							// ĐỌC TIN NHẮN CHAT CỦA NGƯỜI KHÁC
							receivePacket = new DatagramPacket(receiveData, receiveData.length);
							clientSocket.receive(receivePacket);
							String str = new String(receivePacket.getData());
							System.out.println(str);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			clientThread.start();

			// NHẬP TIN NHẮN CHAT
			while (true) {
				String msg = scanner.nextLine();
				sendData = msg.getBytes();
				sendPacket = new DatagramPacket(sendData, sendData.length, iPAddress, 3000);
				clientSocket.send(sendPacket); 
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new ChatClient().chat();
	}
}
