package nonmultithreadconsole.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import util.Balan;

public class ServerCal {
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(1998);
			Socket socket = serverSocket.accept();

			DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
			DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

			String cal = dataInputStream.readUTF();
			try {
				dataOutputStream.writeUTF(Balan.calculate(cal));
			} catch(Exception e) {
				dataOutputStream.writeUTF("Vui long nhap chinh xac");
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
