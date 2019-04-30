package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtil {
	private static String user;
	private static String password;

	public static Connection getConn() throws SQLException {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			String url = "jdbc:oracle:thin:@192.168.0.13:1521:xe";
			con = DriverManager.getConnection(url, user, password);
			con.setAutoCommit(false);
			return con;
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
			return null;
		}
	}

	public static void close(Connection con, Statement stmt, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
		}
	}

	public static void close(Connection con) {
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
		}
	}

	public static void close(Statement stmt) {
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
		}
	}

	public static void close(ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
		}
	}

	public static void setUser(String user) {
		JDBCUtil.user = user;
	}

	public static void setPassword(char[] pwdchar) {
		String password = new String(pwdchar, 0, pwdchar.length);
		JDBCUtil.password = password;
	}

}
