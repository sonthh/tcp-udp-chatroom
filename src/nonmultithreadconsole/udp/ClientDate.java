package nonmultithreadconsole.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientDate {
	public static void main(String[] args) {
		try {
			DatagramSocket clientSocket = new DatagramSocket();
			InetAddress iPAddress = InetAddress.getByName("localhost");

			byte[] sendData = new byte[1024];
			sendData = "get date".getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, iPAddress, 1998);
			clientSocket.send(sendPacket);

			byte[] receiveData = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(receivePacket);

			String str = new String(receivePacket.getData());
			System.out.println(str);

			clientSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
