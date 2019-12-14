package multithreadconsole.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
	List<DatagramPacket> datagramPackets = new ArrayList<>(); // list client
	DatagramSocket server;

	public void chat() {
		DatagramPacket receivePacket;
		byte[] receiveData;

		try {
			server = new DatagramSocket(3000);
			while (true) {
				receiveData = new byte[1024];
				receivePacket = new DatagramPacket(receiveData, receiveData.length);
				server.receive(receivePacket); // chờ client
				HandleForClientThread cc = new HandleForClientThread(receivePacket);
				
				boolean check = true;
				for (DatagramPacket packet : datagramPackets) {
					
					if (packet.getPort() == receivePacket.getPort()) {
						check = false;
					}
				}
				if (check == true) {
					datagramPackets.add(receivePacket);
				}
				cc.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
	}

	public static void main(String[] args) {
		new ChatServer().chat();
	}

	class HandleForClientThread extends Thread {
		DatagramPacket receivePacket;

		public HandleForClientThread(DatagramPacket receivePacket) {
			this.receivePacket = receivePacket;
			
		}

		public void run() {
			try {
				byte[] receiveData = new byte[1024];
				//receivePacket = new DatagramPacket(receiveData, receiveData.length);
				receiveData = receivePacket.getData();
				String user = new String(receiveData).trim();
				sendToAll(user);

				while (true) {
					receivePacket = new DatagramPacket(receiveData, receiveData.length);
					server.receive(receivePacket);
					receiveData = receivePacket.getData();
					String data = new String(receiveData).trim();
					this.sendToAll(data);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void sendToAll(String message) {
			synchronized (datagramPackets) {
				for (DatagramPacket packet : datagramPackets) {
					byte[] sendData = new byte[1024];
					sendData = (message).getBytes();

					InetAddress address = packet.getAddress();
					int port = packet.getPort();
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port);

					try {
						server.send(sendPacket);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
