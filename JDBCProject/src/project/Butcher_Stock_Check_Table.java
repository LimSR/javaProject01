package project;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.table.DefaultTableModel;

import projectdb.dao.MainDao;

public class Butcher_Stock_Check_Table {

	public void userSelectAll(DefaultTableModel t_model) {
		for (int i = 0; i < t_model.getRowCount();) {
			t_model.removeRow(0);
		}

		MainDao maindao = new MainDao();
		ArrayList<HashMap<String, Object>> list = maindao.mainList();
		String[] colName = MainDao.getColName();
		for (HashMap<String, Object> map : list) {
			Object[] rowData = new Object[colName.length];
			for (int i = 0; i < colName.length; i++) {
				rowData[i] = map.get(colName[i]);
			}
			t_model.addRow(rowData);
		}

	}

	public void getUserSearch(DefaultTableModel t_model, String fieldName, String word) {
		for (int i = 0; i < t_model.getRowCount();) {
			t_model.removeRow(0);
		}

		MainDao maindao = new MainDao();
		ArrayList<HashMap<String, Object>> list = maindao.searchList(fieldName, word);
		String[] colName = MainDao.getColName();
		for (HashMap<String, Object> map : list) {
			Object[] rowData = new Object[colName.length];
			for (int i = 0; i < colName.length; i++) {
				rowData[i] = map.get(colName[i]);
			}
			t_model.addRow(rowData);
		}
	}

}
