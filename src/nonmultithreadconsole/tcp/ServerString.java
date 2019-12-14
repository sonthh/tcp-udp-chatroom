package nonmultithreadconsole.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import util.Util;

public class ServerString {
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(1998);
			Socket socket = serverSocket.accept();

			DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
			DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

			String str = dataInputStream.readUTF();
			dataOutputStream.writeUTF(Util.lowerCase(str));
			dataOutputStream.writeUTF(Util.upperCase(str));
			dataOutputStream.writeUTF(Util.count(str) + "");

			dataInputStream.close();
			dataInputStream.close();
			socket.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
