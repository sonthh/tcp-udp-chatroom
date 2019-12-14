package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost/dulieu?useUnicode=true&characterEncoding=UTF-8";
		String user = "root";
		String password = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection conn = DriverManager.getConnection(url, user, password);
			System.out.println(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
