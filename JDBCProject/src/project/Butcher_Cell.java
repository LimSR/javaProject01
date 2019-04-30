package project;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class Butcher_Cell extends JFrame {
	private String[] adm = { "판매번호", "고기 종류", "부위", "등급", "원산지", "판매량(g/마리)", "가격(원)", "판매 일자 및 시간" };
	private JTable table;
	private DefaultTableModel tablemodel;
	private JScrollPane jsp;

	private Butcher_Cell_Table cell_Table = new Butcher_Cell_Table();
	private DefaultTableCellRenderer celAlignRight;

	public Butcher_Cell(int n) {
		// TODO Auto-generated constructor stub
		setSize(750, 400);
		setTitle("판매 내역");
		setResizable(false);
		setLocationRelativeTo(null);

		tablemodel = new DefaultTableModel(adm, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO 자동 생성된 메소드 스텁
				if (column >= 0) {
					return false;
				} else {
					return true;
				}
			}
		};
		table = new JTable(tablemodel);
		jsp = new JScrollPane(table);
		celAlignRight = new DefaultTableCellRenderer();
		celAlignRight.setHorizontalAlignment(JLabel.RIGHT);
		if (tablemodel.getRowCount() > 0)
			table.setRowSelectionInterval(0, 0);
		add(jsp, BorderLayout.CENTER);
		refresh();
		table.getColumnModel().getColumn(0).setPreferredWidth(80);
		table.getColumnModel().getColumn(1).setPreferredWidth(70);
		table.getColumnModel().getColumn(2).setPreferredWidth(60);
		table.getColumnModel().getColumn(3).setPreferredWidth(70);
		table.getColumnModel().getColumn(4).setPreferredWidth(70);
		table.getColumnModel().getColumn(5).setPreferredWidth(110);
		table.getColumnModel().getColumn(6).setPreferredWidth(90);
		table.getColumnModel().getColumn(7).setPreferredWidth(150);
		table.getTableHeader().setReorderingAllowed(false);

		table.getColumnModel().getColumn(0).setCellRenderer(celAlignRight);
		table.getColumnModel().getColumn(1).setCellRenderer(celAlignRight);
		table.getColumnModel().getColumn(2).setCellRenderer(celAlignRight);
		table.getColumnModel().getColumn(3).setCellRenderer(celAlignRight);
		table.getColumnModel().getColumn(4).setCellRenderer(celAlignRight);
		table.getColumnModel().getColumn(5).setCellRenderer(celAlignRight);
		table.getColumnModel().getColumn(6).setCellRenderer(celAlignRight);
		table.getColumnModel().getColumn(7).setCellRenderer(celAlignRight);

		table.setAutoCreateRowSorter(true);
		TableRowSorter ts = new TableRowSorter(table.getModel());
		table.setRowSorter(ts);

		if (n == 1) {
			setVisible(false);
		} else {
			setVisible(true);
		}
	}

	public void refresh() {
		// TODO 자동 생성된 메소드 스텁
		cell_Table.userSelectAll(tablemodel);
	}
}
