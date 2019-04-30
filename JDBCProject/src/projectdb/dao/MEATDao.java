package projectdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import DB.JDBCUtil;

public class MEATDao {
	private static String[] colName;
	private String colIn;

	public ArrayList<String> meatAreaList(String meattype) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "SELECT DISTINCT MEATAREA FROM MEAT WHERE MEATTYPE=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, meattype);
			rs = pstmt.executeQuery();

			ArrayList<String> meatarea = new ArrayList<String>();
			while (rs.next()) {
				meatarea.add(rs.getString("MEATAREA"));
			}
			return meatarea;
		} catch (SQLException e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
			return null;
		} finally {
			JDBCUtil.close(con, pstmt, rs);
		}
	}

	public ArrayList<String> meatOriginList(String meattype, String meatarea) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "SELECT DISTINCT ORIGIN FROM MEAT WHERE MEATTYPE=? AND MEATAREA=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, meattype);
			pstmt.setString(2, meatarea);
			rs = pstmt.executeQuery();

			ArrayList<String> originlist = new ArrayList<String>();
			while (rs.next()) {
				originlist.add(rs.getString("ORIGIN"));
			}
			return originlist;
		} catch (SQLException e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
			return null;
		} finally {
			JDBCUtil.close(con, pstmt, rs);
		}
	}

	public ArrayList<String> meatGradeList(String meattype, String meatarea) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "SELECT DISTINCT GRADE FROM MEAT WHERE MEATTYPE=? AND MEATAREA=? ORDER BY GRADE";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, meattype);
			pstmt.setString(2, meatarea);
			rs = pstmt.executeQuery();

			ArrayList<String> gradelist = new ArrayList<String>();
			while (rs.next()) {
				gradelist.add(rs.getString("GRADE"));
			}
			return gradelist;
		} catch (SQLException e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
			return null;
		} finally {
			JDBCUtil.close(con, pstmt, rs);
		}
	}

	public ArrayList<HashMap<String, Object>> inventoryList() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "SELECT MEATTYPE,MEATAREA,GRADE,ORIGIN,TO_CHAR(TOTAL_STOCK,'999,999,999') AS TOTAL_STOCK ,TO_CHAR(PRICE,'999,999,999') FROM MEAT ORDER BY TOTAL_STOCK DESC,MEATTYPE,GRADE,MEATCODE";
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
			System.out.println(e.getMessage());
			return null;
		} finally {
			JDBCUtil.close(con, pstmt, rs);
		}
	}

	public int price(String meattype, String meatarea, String origin, String grade, int amount) {
		// TODO 자동 생성된 메소드 스텁
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "SELECT PRICE*? AS COST FROM MEAT WHERE MEATTYPE=? AND MEATAREA=? AND ORIGIN=? AND GRADE=?";
			pstmt = con.prepareStatement(sql);
			if (meattype.equals("닭고기")) {
				pstmt.setInt(1, amount);
			} else {
				pstmt.setInt(1, (amount / 100));
			}
			pstmt.setString(2, meattype);
			pstmt.setString(3, meatarea);
			pstmt.setString(4, origin);
			pstmt.setString(5, grade);
			rs = pstmt.executeQuery();
			rs.next();

			int cost = rs.getInt("COST");
			return cost;
		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return -1;
		} finally {
			JDBCUtil.close(con, pstmt, rs);
		}
	}

	public static String[] getColName() {
		return colName;
	}

}
