package nonmultithreadconsole.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import util.Util;


public class ServerString {
	public static void main(String[] args) {
		try {
			DatagramSocket datagramSocket = new DatagramSocket(1998);

			byte[] receiveData = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			datagramSocket.receive(receivePacket);
			String str = new String(receivePacket.getData());

			InetAddress ipAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();
			
			byte[] sendData = null;
			DatagramPacket sendPacket= null;
			//chu thuong
			sendData = Util.lowerCase(str).getBytes();
			sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
			datagramSocket.send(sendPacket);
			//chu hoa
			sendData = Util.upperCase(str).getBytes();
			sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
			datagramSocket.send(sendPacket);
			//so tu
			sendData = (Util.count(str) + "").getBytes();
			sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
			datagramSocket.send(sendPacket);

			datagramSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
