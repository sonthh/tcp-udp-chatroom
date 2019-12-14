package nonmultithreadconsole.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ClientFibo {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		try {
			DatagramSocket clientSocket = new DatagramSocket();
			InetAddress iPAddress = InetAddress.getByName("localhost");

			while (true) {
				System.out.println("Nhập số/exit: ");
				String str = scanner.nextLine();
				
				byte[] sendData = str.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, iPAddress, 1998);
				clientSocket.send(sendPacket);
				
				if (str.equals("exit")) {
					break;
				}

				byte[] receiveData = new byte[1024];
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				clientSocket.receive(receivePacket);
				String result = new String(receivePacket.getData()).trim();
				if (result.equals("-1")) {
					System.out.println(str + " khong phai la so fibo");
				} else {
					System.out.println(str + " la so fibo thu " + result);
					break;
				}
			}
			clientSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		scanner.close();
	}
}
