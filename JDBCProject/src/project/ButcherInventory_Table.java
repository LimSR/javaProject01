package project;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.table.DefaultTableModel;

import projectdb.dao.MEATDao;

public class ButcherInventory_Table {
	public void userSelectAll(DefaultTableModel t_model) {
		int cnt = t_model.getRowCount();
		for (int i = 0; i < cnt;i++) {
			t_model.removeRow(0);
		}

		MEATDao meatdoa = new MEATDao();

		ArrayList<HashMap<String, Object>> list = meatdoa.inventoryList();
	
		String[] colName = MEATDao.getColName();
		for (HashMap<String, Object> map : list) {
			Object[] rowData = new Object[colName.length];
			for (int i = 0; i < colName.length; i++) {
				rowData[i] = map.get(colName[i]);
			}
			t_model.addRow(rowData);
		}
	}
}