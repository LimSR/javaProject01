package project;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class Butcher_Stock_Check extends JFrame implements ActionListener {

	private JPanel sb_p;
	private JButton ck_b, search_b, sale_b;

	private JMenu m = new JMenu("����");
	private JMenuItem addm = new JMenuItem("�߰�");
	private JMenuItem inventory = new JMenuItem("����");
	private JMenuItem calculate = new JMenuItem("����");
	private JMenuBar mb;

	private String[] colName = { "��ȣ", "��� ����", "����", "���", "������", "�԰�����", "�԰�� ��(g/����)", "�������", "���� ��(g/����)",
			"����(100g/����)" };
	private JTable table;
	private DefaultTableModel tablemodel;
	private JScrollPane jsp;

	private String[] comna = { "��ü", "����", "�������", "�߰��" };

	private JComboBox<String> comb;
	private JTextField stf;
	private Butcher_Stock_Check_Table ch_table = new Butcher_Stock_Check_Table();
	private ButcherAddInventory bad;
	private ButcherInventory bi;
	private Butcher_Cell bc;
	private Butcher_Cellcheck bcc;
	private DefaultTableCellRenderer celAlignRight;
	private Butcher_Calculate cale;

	public Butcher_Stock_Check() {
		// TODO �ڵ� ������ ������ ����
		setSize(900, 500);
		setTitle("Fresh&M ���  Ȯ��");
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		m.add(addm);
		m.add(inventory);
		m.add(calculate);
		mb = new JMenuBar();
		mb.add(m);
		setJMenuBar(mb);

		sb_p = new JPanel();
		comb = new JComboBox<String>(comna);
		sb_p.add(comb);
		stf = new JTextField(30);
		sb_p.add(stf);
		search_b = new JButton("�˻�");
		sb_p.add(search_b);
		ck_b = new JButton("Ȯ��");
		sb_p.add(ck_b);
		sale_b = new JButton("�Ǹ�");
		sb_p.add(sale_b);
		add(sb_p, BorderLayout.SOUTH);

		tablemodel = new DefaultTableModel(colName, 0) {
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
		table.getColumnModel().getColumn(5).setPreferredWidth(120);
		table.getColumnModel().getColumn(6).setPreferredWidth(100);
		table.getColumnModel().getColumn(7).setPreferredWidth(90);
		table.getColumnModel().getColumn(8).setPreferredWidth(90);
		table.getColumnModel().getColumn(9).setPreferredWidth(110);
		table.getTableHeader().setReorderingAllowed(false);

		table.getColumnModel().getColumn(0).setCellRenderer(celAlignRight);
		table.getColumnModel().getColumn(1).setCellRenderer(celAlignRight);
		table.getColumnModel().getColumn(2).setCellRenderer(celAlignRight);
		table.getColumnModel().getColumn(3).setCellRenderer(celAlignRight);
		table.getColumnModel().getColumn(4).setCellRenderer(celAlignRight);
		table.getColumnModel().getColumn(5).setCellRenderer(celAlignRight);
		table.getColumnModel().getColumn(6).setCellRenderer(celAlignRight);
		table.getColumnModel().getColumn(7).setCellRenderer(celAlignRight);
		table.getColumnModel().getColumn(8).setCellRenderer(celAlignRight);
		table.getColumnModel().getColumn(9).setCellRenderer(celAlignRight);

		table.setAutoCreateRowSorter(true);
		TableRowSorter ts = new TableRowSorter(table.getModel());
		table.setRowSorter(ts);

		addm.addActionListener(this);
		inventory.addActionListener(this);
		calculate.addActionListener(this);
		ck_b.addActionListener(this);
		search_b.addActionListener(this);
		sale_b.addActionListener(this);
		bi = new ButcherInventory(1);
		bc = new Butcher_Cell(1);
		cale = new Butcher_Calculate(1);
		setVisible(true);
	}

	public void refresh() {
		ch_table.userSelectAll(tablemodel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO �ڵ� ������ �޼ҵ� ����
		if (e.getSource() == addm) {
			bad = new ButcherAddInventory(this, bi, cale);
		} else if (e.getSource() == inventory) {
			bi.setVisible(true);
		} else if (e.getSource() == calculate) {
			cale.setVisible(true);
		} else if (e.getSource() == ck_b) {
			bc.setVisible(true);
		} else if (e.getSource() == search_b) {
			String fieldName = comb.getSelectedItem().toString();
			if (fieldName.trim().equals(comna[0]) && stf.getText().equals("")) {
				refresh();
				if (tablemodel.getRowCount() > 0)
					table.setRowSelectionInterval(0, 0);
			} else if (fieldName.trim().equals(comna[0]) && !stf.getText().equals("")) {
				ch_table.getUserSearch(tablemodel, null, stf.getText());
			} else {
				if (stf.getText().trim().equals("")) {
					ch_table.getUserSearch(tablemodel, fieldName, "");
					if (tablemodel.getRowCount() > 0)
						table.setRowSelectionInterval(0, 0);
				} else {
					ch_table.getUserSearch(tablemodel, fieldName, stf.getText());
					if (tablemodel.getRowCount() > 0)
						table.setRowSelectionInterval(0, 0);
				}
			}
		} else if (e.getSource() == sale_b) {
			int row = table.getSelectedRow();
			if (row == -1) {
				JOptionPane.showMessageDialog(null, "�Ǹ��� ��ǰ�� ������ �ּ���.", "��ǰ �̼���", JOptionPane.ERROR_MESSAGE);
				return;
			}
			String wcode = (String) table.getValueAt(row, 0);
			bcc = new Butcher_Cellcheck(this, wcode, bc, bi, cale);

		}
	}
}