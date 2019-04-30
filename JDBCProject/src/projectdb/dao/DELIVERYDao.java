package projectdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DB.JDBCUtil;
import projectdb.dto.DELIVERYDto;

public class DELIVERYDao {
	public ArrayList<DELIVERYDto> deliveryList() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "SELECT DCODE,DCOMPANYNAME FROM DELIVERY";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			ArrayList<DELIVERYDto> deliverylist = new ArrayList<DELIVERYDto>();
			while (rs.next()) {
				int dcode = rs.getInt("DCODE");
				String dcompanyname = rs.getString("DCOMPANYNAME");
				DELIVERYDto ddto = new DELIVERYDto(dcode, dcompanyname);
				deliverylist.add(ddto);
			}
			return deliverylist;
		} catch (SQLException e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
			return null;
		} finally {
			JDBCUtil.close(con, pstmt, rs);
		}
	}
}
