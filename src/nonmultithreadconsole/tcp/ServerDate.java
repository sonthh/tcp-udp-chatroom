package nonmultithreadconsole.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class ServerDate {
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(1998);
			Socket socket = serverSocket.accept();

			DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
			DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

			dataOutputStream.writeUTF(new Date().toString());

			dataInputStream.close();
			dataInputStream.close();
			socket.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
