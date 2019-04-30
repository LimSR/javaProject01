package project;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.table.DefaultTableModel;

import projectdb.dao.SALEDao;

public class Butcher_Cell_Table {
	public void userSelectAll(DefaultTableModel t_model) {
		for (int i = 0; i < t_model.getRowCount();) {
			t_model.removeRow(0);
		}

		SALEDao saledao = new SALEDao();
		ArrayList<HashMap<String, Object>> list = saledao.saleList();
		String[] colName = SALEDao.getColName();
		for (HashMap<String, Object> map : list) {
			Object[] rowData = new Object[colName.length];
			for (int i = 0; i < colName.length; i++) {
				rowData[i] = map.get(colName[i]);
			}
			t_model.addRow(rowData);

		}
	}
}
