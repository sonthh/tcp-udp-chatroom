package nonmultithreadconsole.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientDate {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		try {
			Socket socket = new Socket("localhost", 1998);

			DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
			DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

			System.out.println("Ng�y: " + dataInputStream.readUTF());
			
			dataOutputStream.close();
			dataInputStream.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		scanner.close();
	}
}
