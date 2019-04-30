package projectdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import DB.JDBCUtil;

public class WARECHOUSINGDao {
	public int addWare(String meattype, String meatarea, String origin, String grade, int amount, int price,
			int dcode) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "INSERT INTO WARECHOUSING VALUES('W'||TO_CHAR(SYSDATE,'YYMMDD')|| LPAD(?, 3, 0),(SELECT MEATCODE FROM MEAT WHERE MEATTYPE=? AND MEATAREA=? AND ORIGIN=? AND GRADE=?),?,SYSDATE,SYSDATE+30,?,?)";
			pstmt = con.prepareStatement(sql);
			if (checkWcode() == null) {
				pstmt.setString(1, "001");
			} else {
				String n = Integer.toString(Integer.parseInt(checkWcode()) + 1);
				pstmt.setString(1, n);
			}
			pstmt.setString(2, meattype);
			pstmt.setString(3, meatarea);
			pstmt.setString(4, origin);
			pstmt.setString(5, grade);
			pstmt.setInt(6, amount);
			pstmt.setInt(7, price);
			pstmt.setInt(8, dcode);
			int n = pstmt.executeUpdate();

			con.commit();
			return n;
		} catch (SQLException e) {
			// TODO 자동 생성된 catch 블록
			try {
				System.out.println(e.getMessage());
				con.rollback();
			} catch (SQLException e1) {
				// TODO: handle exception
				System.out.println(e1.getMessage());
			}
			return -1;
		} finally {
			// TODO: handle finally clause
			JDBCUtil.close(con, pstmt, null);
		}
	}

	public String checkWcode() throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "SELECT MAX(WCODE) FROM WARECHOUSING";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			String wcode = rs.getString("MAX(WCODE)");
			SimpleDateFormat todays = new SimpleDateFormat("yyMMdd");

			if (wcode == null || !todays.format(new Date()).equals(wcode.substring(1, 7))) {
				return null;
			} else {
				return wcode.substring(7, 10);
			}
		} finally {
			JDBCUtil.close(con, pstmt, rs);
		}
	}
}
