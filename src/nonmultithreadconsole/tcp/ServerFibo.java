package nonmultithreadconsole.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import util.Util;

public class ServerFibo {
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(1998);
			Socket socket = serverSocket.accept();

			DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
			DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

			while (true) {
				String str = dataInputStream.readUTF();
				if (str.equals("exit")) {
					break;
				}
				Integer num = Integer.parseInt(str);
				
				int check = Util.kiemTra(num);
				dataOutputStream.writeUTF(check + "");
				if (check != -1) {
					break;
				}
			}

			dataInputStream.close();
			dataInputStream.close();
			socket.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
