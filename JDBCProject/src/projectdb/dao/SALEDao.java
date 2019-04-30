package projectdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import DB.JDBCUtil;

public class SALEDao {
	private static String[] colName = null;
	private String colIn = null;

	public ArrayList<HashMap<String, Object>> saleList() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "SELECT S.SCODE,M.MEATTYPE,M.MEATAREA,M.GRADE,M.ORIGIN,"
					+ "TO_CHAR(S.SALEMOUNT,'999,999') AS \"SALEMOUNT\",TO_CHAR(S.SPRICE,'999,999') AS \"SPRICE\","
					+ "TO_CHAR(S.SALEDATE,'YYYY/MM/DD HH24:MI:SS') AS \"SALEDATE\""
					+ "FROM SALE S,MEAT M,WARECHOUSING W WHERE S.WCODE=W.WCODE AND W.MEATCODE=M.MEATCODE ORDER BY SCODE";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				colName = new String[rsmd.getColumnCount()];
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					colName[i - 1] = rsmd.getColumnName(i);
					colIn = rs.getString(colName[i - 1]);
					map.put(colName[i - 1], colIn);
				}
				list.add(map);
			}
			return list;
		} catch (SQLException e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
			return null;
		} finally {
			JDBCUtil.close(con, pstmt, rs);
		}
	}

	public int addSale(String wcode, int amount, boolean meattype) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "INSERT INTO SALE VALUES("
					+ "'S'||TO_CHAR(SYSDATE,'YYMMDD')|| LPAD(?, 3, 0),?,?,((SELECT M.PRICE FROM MEAT M,WARECHOUSING W WHERE W.MEATCODE=M.MEATCODE AND W.WCODE=?)*?),SYSDATE)";
			pstmt = con.prepareStatement(sql);
			if (checkScode() == null) {
				pstmt.setString(1, "001");
			} else {
				String n = Integer.toString(Integer.parseInt(checkScode()) + 1);
				pstmt.setString(1, n);
			}
			pstmt.setString(2, wcode);
			pstmt.setInt(3, amount);
			pstmt.setString(4, wcode);
			if (meattype) {
				pstmt.setInt(5, amount);
			} else {
				pstmt.setInt(5, amount / 100);
			}
			int n = pstmt.executeUpdate();

			con.commit();
			return n;
		} catch (SQLException e) {
			// TODO: handle exception
			try {
				System.out.println(e.getMessage());
				con.rollback();
			} catch (SQLException e1) {
				// TODO: handle exception
				System.out.println(e1.getMessage());
			}
			return -1;
		} finally {
			JDBCUtil.close(con, pstmt, null);
		}
	}

	public String checkScode() throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "SELECT MAX(SCODE) FROM SALE";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			String scode = rs.getString("MAX(SCODE)");
			SimpleDateFormat todays = new SimpleDateFormat("yyMMdd");

			if (scode == null || !todays.format(new Date()).equals(scode.substring(1, 7))) {
				return null;
			} else {
				return scode.substring(7, 10);
			}
		} finally {
			JDBCUtil.close(con, pstmt, rs);
		}
	}

	public String todayTurnover() {
		// TODO 자동 생성된 메소드 스텁
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "SELECT TO_CHAR(SUM(SPRICE),'999,999,999') AS \"ALLPRICE\" FROM SALE GROUP BY TO_CHAR(SALEDATE,'YY/MM/DD') HAVING TO_CHAR(SALEDATE,'YY/MM/DD')=TO_CHAR(SYSDATE,'YY/MM/DD')";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			String to = null;
			if (!rs.next()) {
				to = "0";
			} else {
				to = rs.getString("ALLPRICE").trim();
			}
			return to;

		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return null;
		} finally {
			// TODO: handle finally clause
			JDBCUtil.close(con, pstmt, rs);
		}
	}

	public static String[] getColName() {
		return colName;
	}
}
