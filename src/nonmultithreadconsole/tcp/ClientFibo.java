package nonmultithreadconsole.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientFibo {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		try {
			Socket socket = new Socket("localhost", 1998);

			DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
			DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

			while (true) {
				System.out.print("Nhap so/exit: ");
				String str = scanner.nextLine();
				if (str.equals("exit")) {
					dataOutputStream.writeUTF(str);
					break;
				}
				Integer num = Integer.parseInt(str);
				dataOutputStream.writeUTF(num + "");
				
				String result = dataInputStream.readUTF();
				if (result.equals("-1")) {
					System.out.println(str + " khong phai la so fibo");
				} else {
					System.out.println(str + " la so fibo thu " + result);
					break;
				}
			}
			
			dataOutputStream.close();
			dataInputStream.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		scanner.close();
	}
}
