package projectdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import DB.JDBCUtil;

public class INVENTORYDao {
	private static String[] colName = null;
	private String colIn = null;

	public ArrayList<HashMap<String, Object>> inventoryList() {
		// TODO 자동 생성된 메소드 스텁
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "SELECT I.WCODE,M.MEATTYPE,M.MEATAREA,M.GRADE,M.ORIGIN,TO_CHAR(W.EXPIRATION_DATE,'YYYY/MM/DD') AS EXPIRATION_DATE,TO_CHAR(I.REMAININGAMOUNT,'999,999') AS REMAININGAMOUNT "
					+ "FROM WARECHOUSING W,MEAT M,INVENTORY I WHERE W.MEATCODE=M.MEATCODE AND W.WCODE=I.WCODE";
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

	public int rema(String wcode) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "SELECT REMAININGAMOUNT FROM INVENTORY WHERE WCODE=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, wcode);
			rs = pstmt.executeQuery();
			rs.next();
			int rema = rs.getInt("REMAININGAMOUNT");
			return rema;
		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return -1;
		} finally {
			// TODO: handle finally clause
			JDBCUtil.close(con, pstmt, rs);
		}
	}

	public String cale(int i) {
		Connection con = null;
		PreparedStatement pstmt1 = null, pstmt2 = null;
		ResultSet rs1 = null, rs2 = null;
		try {
			con = JDBCUtil.getConn();
			if (i == 0) {
				String sql1 = "SELECT SUM(NVL(SPRICE,0)) AS DEF FROM SALE GROUP BY TO_CHAR(SALEDATE,'YY/MM/DD') HAVING TO_CHAR(SALEDATE,'YY/MM/DD')=TO_CHAR(SYSDATE,'YY/MM/DD')";
				String sql2 = "SELECT SUM(NVL(PRICE,0)) AS DEF FROM WARECHOUSING GROUP BY TO_CHAR(ARRIVAL_TIME,'YY/MM/DD') HAVING TO_CHAR(ARRIVAL_TIME,'YY/MM/DD')=TO_CHAR(SYSDATE,'YY/MM/DD')";
				pstmt1 = con.prepareStatement(sql1);
				pstmt2 = con.prepareStatement(sql2);
			} else if (i == 1) {
				String sql1 = "SELECT SUM(SPRICE) AS DEF FROM SALE GROUP BY TO_CHAR(SALEDATE,'YY/MM/DD') HAVING TO_CHAR(SALEDATE,'YY/MM/DD')=TO_CHAR(SYSDATE,'YY/MM/DD')";
				String sql2 = "SELECT SUM(PRICE) AS DEF FROM WARECHOUSING GROUP BY TO_CHAR(ARRIVAL_TIME,'YY/MM/DD') HAVING TO_CHAR(ARRIVAL_TIME,'YY/MM/DD')=TO_CHAR(SYSDATE,'YY/MM/DD')";
				pstmt1 = con.prepareStatement(sql1);
				pstmt2 = con.prepareStatement(sql2);
			} else if (i == 2) {
				String sql1 = "SELECT SUM(SPRICE) AS DEF FROM SALE GROUP BY TO_CHAR(SALEDATE,'YY/MM') HAVING TO_CHAR(SALEDATE,'YY/MM')=TO_CHAR(SYSDATE,'YY/MM')";
				String sql2 = "SELECT SUM(PRICE) AS DEF FROM WARECHOUSING GROUP BY TO_CHAR(ARRIVAL_TIME,'YY/MM') HAVING TO_CHAR(ARRIVAL_TIME,'YY/MM')=TO_CHAR(SYSDATE,'YY/MM')";
				pstmt1 = con.prepareStatement(sql1);
				pstmt2 = con.prepareStatement(sql2);
			}

			rs1 = pstmt1.executeQuery();
			rs2 = pstmt2.executeQuery();
			int cale1 = 0, cale2 = 0;
			if (rs1.next()) {
				cale1 = rs1.getInt("DEF");
			}
			if (rs2.next()) {
				cale2 = rs2.getInt("DEF");
			}

			DecimalFormat format = new DecimalFormat("###,###");
			String cale = String.valueOf(format.format(cale1 - cale2));

			return cale;
		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return null;
		} finally {
			// TODO: handle finally clause
			JDBCUtil.close(rs1);
			JDBCUtil.close(rs2);
			JDBCUtil.close(pstmt1);
			JDBCUtil.close(pstmt2);
			JDBCUtil.close(con);
			// JDBCUtil.close(con, pstmt2, rs);
		}
	}

	public static String[] getColName() {
		// TODO 자동 생성된 메소드 스텁
		return colName;
	}

}
