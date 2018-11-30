package connection;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionProvider {

	public static Connection getConnection() {
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/TodoApp?autoReconnect=true&useSSL=false";
		String user = "root";
		String password = "root";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

}
