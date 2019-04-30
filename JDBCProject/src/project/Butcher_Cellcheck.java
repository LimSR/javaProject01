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
		// TODO 자동 생성된 생성자 스텁
		this.bsc = bsc;
		this.wcode = wcode;
		this.bc = bc;
		this.bi = bi;
		this.cale = cale;

		setSize(300, 200);
		setTitle("판매");
		setResizable(false);
		setLocationRelativeTo(null);

		t_p = new JPanel();
		cell_p = new JPanel();
		cell_p.setLayout(null);
		btn_p = new JPanel();

		name_la = new JLabel("판매 창");
		name_la.setFont(new Font("Serif", Font.BOLD, 20));
		t_p.add(name_la);

		c_la1 = new JLabel("판매량");
		c_la1.setBounds(20, 50, 50, 30);
		order_t = new JFormattedTextField(new NumberFormatter());
		order_t.setBounds(80, 50, 200, 30);
		cell_p.add(c_la1);
		cell_p.add(order_t);

		String item = maindao.saleMeat(wcode);
		meattype = item.startsWith("닭고기");
		c_la2 = new JLabel(item);
		c_la2.setBounds(50, 0, 200, 30);
		cell_p.add(c_la2);

		ck_b = new JButton("판매");
		ck_b.addActionListener(this);
		cancel_b = new JButton("취소");
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
		// TODO 자동 생성된 메소드 스텁
		int rema = idao.rema(wcode);
		if (e.getSource() == ck_b) {
			String amount = order_t.getText().replace(",", "");
			if (amount.equals("")) {
				JOptionPane.showMessageDialog(null, "주문량을 입력하지 않았습니다.", "주문량 미입력", JOptionPane.ERROR_MESSAGE);
			} else if (Integer.parseInt(amount) > rema) {
				JOptionPane.showMessageDialog(null, "남이있는 재고량보다 많은 양을 입력하셨습니다!", "많은 양 입력!", JOptionPane.ERROR_MESSAGE);
			} else {
				int n = saledao.addSale(wcode, Integer.parseInt(amount), meattype);
				if (n > 0) {
					JOptionPane.showMessageDialog(null, "판매가 완료되었습니다.", "판매완료!", JOptionPane.INFORMATION_MESSAGE);
					if (bc != null)
						bc.refresh();
					if (bi != null)
						bi.refresh();
					if (cale != null)
						cale.refresh();
					bsc.refresh();
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "처리 중 오류가 발생했습니다.!", "오류발생!", JOptionPane.ERROR_MESSAGE);
				}
			}

		} else if (e.getSource() == cancel_b) {
			order_t.setText("");
		}

	}
}
