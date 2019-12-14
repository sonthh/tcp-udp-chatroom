package nonmultithreadconsole.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ClientString {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		try {
			DatagramSocket clientSocket = new DatagramSocket();
			InetAddress iPAddress = InetAddress.getByName("localhost");

			byte[] sendData = new byte[1024];
			System.out.print("Nhập chuỗi: ");
			sendData = scanner.nextLine().getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, iPAddress, 1998);
			clientSocket.send(sendPacket);

			
			byte[] receiveData = null;
			DatagramPacket receivePacket = null;
			//nhan chu thuong
			receiveData = new byte[1024];
			receivePacket = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(receivePacket);
			System.out.println("Chu thuong: " + new String(receivePacket.getData()).trim());
			//nhan chu hoa
			receiveData = new byte[1024];
			receivePacket = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(receivePacket);
			System.out.println("Chu hoa: " + new String(receivePacket.getData()).trim());
			//so tu
			receiveData = new byte[1024];
			receivePacket = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(receivePacket);
			System.out.println("So tu: " + new String(receivePacket.getData()).trim());

			clientSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		scanner.close();
	}
}
