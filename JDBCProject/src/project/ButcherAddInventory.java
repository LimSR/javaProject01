package project;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.NumberFormatter;

import projectdb.dao.DELIVERYDao;
import projectdb.dao.MEATDao;
import projectdb.dao.WARECHOUSINGDao;
import projectdb.dto.DELIVERYDto;

public class ButcherAddInventory extends JFrame implements ActionListener {

	private String[] la_m = { "고기 종류", "고기 부위", "원산지", "등급", "입고된 양", "구입가격", "납품회사" };
	private String[] m_type = { "선택하세요", "쇠고기", "돼지고기", "닭고기" };
	private ArrayList<String> meat_area;
	private ArrayList<String> originlist;
	private ArrayList<String> gradelist;
	private ArrayList<DELIVERYDto> deliname;
	private JPanel t_p, addla_p, b_p;
	private JComboBox<String> meatcom, areacom, origincom, gradecom, delcom;
	private JLabel m_la1, m_la2, m_la3, m_la4, m_la5, m_la6, m_la7;
	private JFormattedTextField amount_tf, price_tf;
	private JButton add_b, cancel_b;

	private Butcher_Stock_Check sc;
	private MEATDao meatdao = new MEATDao();
	private DELIVERYDao deldao = new DELIVERYDao();
	private WARECHOUSINGDao wdao = new WARECHOUSINGDao();
	private ButcherInventory bi;
	private Butcher_Calculate cale;

	public ButcherAddInventory(Butcher_Stock_Check sc, ButcherInventory bi, Butcher_Calculate cale) {
		// TODO 자동 생성된 생성자 스텁
		this.sc = sc;
		this.bi = bi;
		this.cale = cale;
		setSize(250, 400);
		setTitle("재고 추가");
		setResizable(false);
		setLocationRelativeTo(null);

		t_p = new JPanel();
		addla_p = new JPanel(new GridLayout(7, 2, 0, 10));
		b_p = new JPanel();
		JLabel named = new JLabel("재고 추가");
		named.setFont(new Font("Serif", Font.BOLD, 25));
		t_p.add(named);

		m_la1 = new JLabel(la_m[0]);
		m_la1.setFont(new Font("Serif", Font.BOLD, 16));
		// m_la1.setBounds(25, 20, 80, 30);
		addla_p.add(m_la1);
		meatcom = new JComboBox<String>(m_type);
		// meatcom.setBounds(130, 30, 90, 20);
		meatcom.addActionListener(this);
		addla_p.add(meatcom);

		m_la2 = new JLabel(la_m[1]);
		// m_la2.setBounds(25, 60, 80, 30);
		m_la2.setFont(new Font("Serif", Font.BOLD, 16));
		addla_p.add(m_la2);
		areacom = new JComboBox<String>();
		// areacom.setBounds(130, 70, 90, 20);
		// addla_p.add(meatcom);
		areacom.addActionListener(this);
		addla_p.add(areacom);

		m_la3 = new JLabel(la_m[2]);
		m_la3.setFont(new Font("Serif", Font.BOLD, 16));
		addla_p.add(m_la3);
		origincom = new JComboBox<String>();
		addla_p.add(origincom);

		m_la4 = new JLabel(la_m[3]);
		m_la4.setFont(new Font("Serif", Font.BOLD, 16));
		addla_p.add(m_la4);
		gradecom = new JComboBox<String>();
		addla_p.add(gradecom);

		m_la5 = new JLabel(la_m[4]);
		// m_la5.setBounds(25, 100, 80, 30);
		m_la5.setFont(new Font("Serif", Font.BOLD, 16));
		addla_p.add(m_la5);
		amount_tf = new JFormattedTextField(new NumberFormatter());
		// m_tf1.setBounds(130, 110, 80, 20);
//		JLabel gla = new JLabel("g");
//		gla.setBounds(215, 110, 10, 20);
		addla_p.add(amount_tf);
//		addla_p.add(gla);

		m_la6 = new JLabel(la_m[5]);
		m_la6.setFont(new Font("Serif", Font.BOLD, 16));
		addla_p.add(m_la6);
		price_tf = new JFormattedTextField(new NumberFormatter());
		addla_p.add(price_tf);

		m_la7 = new JLabel(la_m[6]);
		m_la7.setFont(new Font("Serif", Font.BOLD, 16));
		addla_p.add(m_la7);
		delcom = new JComboBox<String>();
		addla_p.add(delcom);

		add_b = new JButton("추가");
		add_b.addActionListener(this);
		cancel_b = new JButton("취소");
		cancel_b.addActionListener(this);
		b_p.add(add_b);
		b_p.add(cancel_b);
		add(t_p, BorderLayout.NORTH);
		add(addla_p, BorderLayout.CENTER);
		add(b_p, BorderLayout.SOUTH);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 자동 생성된 메소드 스텁
		String meattype = null;
		String meatarea = null;
		String origin = null;
		String grade = null;
		if (e.getSource() == meatcom) {
			meattype = meatcom.getSelectedItem().toString();
			if (meattype.equals(m_type[0])) {
				origincom.removeAllItems();
				gradecom.removeAllItems();
				areacom.removeAllItems();
				delcom.removeAllItems();
				amount_tf.setText("");
				price_tf.setText("");
			} else {
				origincom.removeAllItems();
				gradecom.removeAllItems();
				areacom.removeAllItems();
				delcom.removeAllItems();
				meat_area = meatdao.meatAreaList(meattype);
				for (int i = 0; i < meat_area.size(); i++) {
					areacom.addItem(meat_area.get(i));
				}
				deliname = deldao.deliveryList();
				for (DELIVERYDto deldto : deliname) {
					String dcode = Integer.toString(deldto.getDcode());
					String delname = deldto.getDcompanyname();
					delcom.addItem(dcode.concat("," + delname));
				}

			}
		}

		else if (e.getSource() == areacom) {
			meattype = meatcom.getSelectedItem().toString();
			origincom.removeAllItems();
			gradecom.removeAllItems();
			if (meatdao.meatAreaList(meattype).size() != 0) {
				meat_area = meatdao.meatAreaList(meattype);
				areacom.addItem(meat_area.get(0));
				meatarea = areacom.getSelectedItem().toString();
				areacom.removeItem(meat_area.get(0));
				originlist = meatdao.meatOriginList(meattype, meatarea);
				for (int i = 0; i < originlist.size(); i++) {
					origincom.addItem(originlist.get(i));
				}
				gradelist = meatdao.meatGradeList(meattype, meatarea);
				for (int i = 0; i < gradelist.size(); i++) {
					gradecom.addItem(gradelist.get(i));
				}
			} else {
				meatarea = "";
			}

		}

		else if (e.getSource() == add_b) {
			meattype = meatcom.getSelectedItem().toString();
			meatarea = areacom.getSelectedItem().toString();
			origin = origincom.getSelectedItem().toString();
			grade = gradecom.getSelectedItem().toString();
			String amount = amount_tf.getText().replace(",", "");
			String price = price_tf.getText().replace(",", "");
			String[] dcodes = delcom.getSelectedItem().toString().split(",");
			int dcode = Integer.parseInt(dcodes[0]);
			int cost = meatdao.price(meattype, meatarea, origin, grade, Integer.parseInt(amount));

			if (meattype.equals("선택하세요")) {
				JOptionPane.showMessageDialog(null, "고기를 선택하시지 않았습니다.", "고기 미선택", JOptionPane.ERROR_MESSAGE);
			} else if (amount.equals("")) {
				JOptionPane.showMessageDialog(null, "입고량을 입력하지 않았습니다.", "입고량 미입력", JOptionPane.ERROR_MESSAGE);
			} else if (price.equals("")) {
				JOptionPane.showMessageDialog(null, "가격을 입력하지 않았습니다.", "가격 미입력", JOptionPane.ERROR_MESSAGE);
			} else if (Integer.parseInt(price) >= cost) {
				JOptionPane.showMessageDialog(null, "판매가격보다 높은 가격을 입력하셨습니다.", "적자!!", JOptionPane.ERROR_MESSAGE);
			} else {
				int n = wdao.addWare(meattype, meatarea, origin, grade, Integer.parseInt(amount),
						Integer.parseInt(price), dcode);
				if (n > 0) {
					JOptionPane.showMessageDialog(null, "성공적으로 등록되었습니다.", "저장 성공!", JOptionPane.INFORMATION_MESSAGE);
					sc.refresh();
					if (bi != null)
						bi.refresh();
					if (cale != null)
						cale.refresh();
				} else {
					JOptionPane.showMessageDialog(null, "저장 중 오류가 발생했습니다.", "저장 실패!", JOptionPane.ERROR_MESSAGE);
				}

			}
		} else if (e.getSource() == cancel_b) {
			amount_tf.setText("");
			price_tf.setText("");
			meatcom.setSelectedIndex(0);
		}
	}
}
