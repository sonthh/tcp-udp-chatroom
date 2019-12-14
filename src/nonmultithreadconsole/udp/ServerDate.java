package nonmultithreadconsole.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

public class ServerDate {
	public static void main(String[] args) {
		try {
			DatagramSocket datagramSocket = new DatagramSocket(1998);

			byte[] receiveData = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			datagramSocket.receive(receivePacket);
			System.out.println(new String(receivePacket.getData()));

			byte[] sendData = new byte[1024];
			InetAddress ipAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();
			sendData = new Date().toString().getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
			datagramSocket.send(sendPacket);

			datagramSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
