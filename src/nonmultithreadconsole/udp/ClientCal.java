package nonmultithreadconsole.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ClientCal {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		try {
			DatagramSocket clientSocket = new DatagramSocket();
			InetAddress iPAddress = InetAddress.getByName("localhost");

			System.out.println("Nhập phép tính: ");
			byte[] sendData = scanner.nextLine().getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, iPAddress, 1998);
			clientSocket.send(sendPacket);

			byte[] receiveData = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(receivePacket);
			System.out.println("Ket qua phep tinh: " + new String(receivePacket.getData()).trim());

			clientSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		scanner.close();
	}
}
