package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import projectdb.dao.SALEDao;

public class ButcherInventory extends JFrame {
	private JPanel name_p, ch_p;
	private JScrollPane sc_ck;
	private JTable ta;
	private DefaultTableModel dtb;
	private String[] tana = { "고기종류", "고기부위", "등급", "원산지", "총량(g,마리)", "가격(100g,마리/원)" };
	private JLabel tula;
	private JTextField turnover;
	private ButcherInventory_Table bit = new ButcherInventory_Table();
	private DefaultTableCellRenderer celAlignRight;
	private SALEDao saledao = new SALEDao();

	public ButcherInventory(int n) {
		// TODO 자동 생성된 생성자 스텁
		setSize(500, 500);
		setTitle("재고 조사");
		setResizable(false);
		setLocationRelativeTo(null);

		name_p = new JPanel();
		JLabel na_la = new JLabel("재고 조사 및 확인");
		na_la.setFont(new Font("Serif", Font.BOLD, 20));
		name_p.add(na_la);
		ch_p = new JPanel();

		dtb = new DefaultTableModel(tana, 0);
		ta = new JTable(dtb);
		sc_ck = new JScrollPane(ta);

		celAlignRight = new DefaultTableCellRenderer();
		celAlignRight.setHorizontalAlignment(JLabel.RIGHT);
		if (dtb.getRowCount() > 0)
			ta.setRowSelectionInterval(0, 0);
		ta.getColumnModel().getColumn(0).setPreferredWidth(60);
		ta.getColumnModel().getColumn(1).setPreferredWidth(60);
		ta.getColumnModel().getColumn(2).setPreferredWidth(70);
		ta.getColumnModel().getColumn(3).setPreferredWidth(70);
		ta.getColumnModel().getColumn(4).setPreferredWidth(90);
		ta.getColumnModel().getColumn(5).setPreferredWidth(100);
		ta.getTableHeader().setReorderingAllowed(false);

		ta.getColumnModel().getColumn(0).setCellRenderer(celAlignRight);
		ta.getColumnModel().getColumn(1).setCellRenderer(celAlignRight);
		ta.getColumnModel().getColumn(2).setCellRenderer(celAlignRight);
		ta.getColumnModel().getColumn(3).setCellRenderer(celAlignRight);
		ta.getColumnModel().getColumn(4).setCellRenderer(celAlignRight);
		ta.getColumnModel().getColumn(5).setCellRenderer(celAlignRight);

		ta.setAutoCreateRowSorter(true);
		TableRowSorter ts = new TableRowSorter(ta.getModel());
		ta.setRowSorter(ts);

		add(sc_ck, BorderLayout.CENTER);

		ch_p = new JPanel();
		tula = new JLabel("오늘 총 매출액");
		turnover = new JTextField(20);
		turnover.setEnabled(false);
		turnover.setHorizontalAlignment(JTextField.RIGHT);

		JLabel wonla = new JLabel("원");
		wonla.setFont(new Font("Serif", Font.BOLD, 17));

		refresh();
		// ch_p.add(sc_ck);
		ch_p.add(tula);
		ch_p.add(turnover);
		ch_p.add(wonla);
		add(name_p, BorderLayout.NORTH);
		add(ch_p, BorderLayout.SOUTH);
		if (n == 1) {
			setVisible(false);
		} else {
			setVisible(true);
		}
	}

	public void refresh() {
		bit.userSelectAll(dtb);
		String daysturnover = saledao.todayTurnover();
		turnover.setText(daysturnover);
	}
}
