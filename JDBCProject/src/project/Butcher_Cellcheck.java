package project;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.NumberFormatter;

import projectdb.dao.INVENTORYDao;
import projectdb.dao.MainDao;
import projectdb.dao.SALEDao;

public class Butcher_Cellcheck extends JFrame implements ActionListener {
	private JPanel t_p, cell_p, btn_p;
	private JLabel c_la1, c_la2, name_la;
	private JFormattedTextField order_t;
	private JButton ck_b, cancel_b;
	private Butcher_Cell bc;
	private Butcher_Stock_Check bsc;
	private ButcherInventory bi;
	private MainDao maindao = new MainDao();
	private SALEDao saledao = new SALEDao();
	private INVENTORYDao idao = new INVENTORYDao();
	private String wcode;
	private boolean meattype;
	private Butcher_Calculate cale;

	public Butcher_Cellcheck(Butcher_Stock_Check bsc, String wcode, Butcher_Cell bc, ButcherInventory bi,
			Butcher_Calculate cale) {
		// TODO �ڵ� ������ ������ ����
		this.bsc = bsc;
		this.wcode = wcode;
		this.bc = bc;
		this.bi = bi;
		this.cale = cale;

		setSize(300, 200);
		setTitle("�Ǹ�");
		setResizable(false);
		setLocationRelativeTo(null);

		t_p = new JPanel();
		cell_p = new JPanel();
		cell_p.setLayout(null);
		btn_p = new JPanel();

		name_la = new JLabel("�Ǹ� â");
		name_la.setFont(new Font("Serif", Font.BOLD, 20));
		t_p.add(name_la);

		c_la1 = new JLabel("�Ǹŷ�");
		c_la1.setBounds(20, 50, 50, 30);
		order_t = new JFormattedTextField(new NumberFormatter());
		order_t.setBounds(80, 50, 200, 30);
		cell_p.add(c_la1);
		cell_p.add(order_t);

		String item = maindao.saleMeat(wcode);
		meattype = item.startsWith("�߰��");
		c_la2 = new JLabel(item);
		c_la2.setBounds(50, 0, 200, 30);
		cell_p.add(c_la2);

		ck_b = new JButton("�Ǹ�");
		ck_b.addActionListener(this);
		cancel_b = new JButton("���");
		cancel_b.addActionListener(this);
		btn_p.add(ck_b);
		btn_p.add(cancel_b);

		add(t_p, BorderLayout.NORTH);
		add(cell_p, BorderLayout.CENTER);
		add(btn_p, BorderLayout.SOUTH);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO �ڵ� ������ �޼ҵ� ����
		int rema = idao.rema(wcode);
		if (e.getSource() == ck_b) {
			String amount = order_t.getText().replace(",", "");
			if (amount.equals("")) {
				JOptionPane.showMessageDialog(null, "�ֹ����� �Է����� �ʾҽ��ϴ�.", "�ֹ��� ���Է�", JOptionPane.ERROR_MESSAGE);
			} else if (Integer.parseInt(amount) > rema) {
				JOptionPane.showMessageDialog(null, "�����ִ� ������� ���� ���� �Է��ϼ̽��ϴ�!", "���� �� �Է�!", JOptionPane.ERROR_MESSAGE);
			} else {
				int n = saledao.addSale(wcode, Integer.parseInt(amount), meattype);
				if (n > 0) {
					JOptionPane.showMessageDialog(null, "�ǸŰ� �Ϸ�Ǿ����ϴ�.", "�ǸſϷ�!", JOptionPane.INFORMATION_MESSAGE);
					if (bc != null)
						bc.refresh();
					if (bi != null)
						bi.refresh();
					if (cale != null)
						cale.refresh();
					bsc.refresh();
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "ó�� �� ������ �߻��߽��ϴ�.!", "�����߻�!", JOptionPane.ERROR_MESSAGE);
				}
			}

		} else if (e.getSource() == cancel_b) {
			order_t.setText("");
		}

	}
}
