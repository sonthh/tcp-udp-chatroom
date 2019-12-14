package nonmultithreadconsole.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import util.Util;

public class ServerFibo {
	public static void main(String[] args) {
		try {
			DatagramSocket datagramSocket = new DatagramSocket(1998);

			while (true) {
				byte[] receiveData = new byte[1024];
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				datagramSocket.receive(receivePacket);
				String str = new String(receivePacket.getData()).trim();
				if (str.equals("exit")) {
					break;
				}

				InetAddress ipAddress = receivePacket.getAddress();
				int port = receivePacket.getPort();
				Integer num = Integer.parseInt(str);
				byte[] sendData = (Util.kiemTra(num) + "").getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
				datagramSocket.send(sendPacket);
			}
			datagramSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
