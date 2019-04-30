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
	private String[] adm = { "�ǸŹ�ȣ", "��� ����", "����", "���", "������", "�Ǹŷ�(g/����)", "����(��)", "�Ǹ� ���� �� �ð�" };
	private JTable table;
	private DefaultTableModel tablemodel;
	private JScrollPane jsp;

	private Butcher_Cell_Table cell_Table = new Butcher_Cell_Table();
	private DefaultTableCellRenderer celAlignRight;

	public Butcher_Cell(int n) {
		// TODO Auto-generated constructor stub
		setSize(750, 400);
		setTitle("�Ǹ� ����");
		setResizable(false);
		setLocationRelativeTo(null);

		tablemodel = new DefaultTableModel(adm, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO �ڵ� ������ �޼ҵ� ����
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
		// TODO �ڵ� ������ �޼ҵ� ����
		cell_Table.userSelectAll(tablemodel);
	}
}
