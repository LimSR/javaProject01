package projectdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import DB.JDBCUtil;

public class MainDao {
	private static String[] colName = null;
	private String colIn = null;

	public ArrayList<HashMap<String, Object>> mainList() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "SELECT * FROM MAINVIEW ORDER BY WCODE";
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

	public ArrayList<HashMap<String, Object>> searchList(String meattype, String meatarea) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		try {
			con = JDBCUtil.getConn();
			if (meatarea.equals("")) {
				String sql = "SELECT * FROM MAINVIEW WHERE MEATTYPE=? ORDER BY WCODE";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, meattype);
			} else if (meattype == null) {
				String sql = "SELECT * FROM MAINVIEW WHERE MEATAREA=? ORDER BY WCODE";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, meatarea);
			} else {
				String sql = "SELECT * FROM MAINVIEW WHERE MEATTYPE=? AND MEATAREA=? ORDER BY WCODE";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, meattype);
				pstmt.setString(2, meatarea);
			}
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

	public String saleMeat(String wcode) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "SELECT M.MEATTYPE,M.MEATAREA,M.GRADE,M.ORIGIN FROM MEAT M,WARECHOUSING W WHERE M.MEATCODE=W.MEATCODE AND W.WCODE=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, wcode);
			rs = pstmt.executeQuery();
			String[] tmp = new String[4];
			rs.next();
			tmp[0] = rs.getString("MEATTYPE");
			tmp[1] = rs.getString("MEATAREA");
			tmp[2] = rs.getString("GRADE");
			tmp[3] = rs.getString("ORIGIN");
			result = "";
			for (int i = 0; i < tmp.length; i++) {
				result = result.concat(tmp[i] + ", ");
			}
			return result;

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
