package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import projectdb.dao.INVENTORYDao;

public class Butcher_Calculate extends JFrame {
	private String[] calName = { "일일정산: ", "주간정산: ", "월별정산: " };
	private JLabel[] namela = new JLabel[calName.length];
	private JLabel[] valuela = new JLabel[calName.length];
	private JLabel[] lossla = new JLabel[calName.length];
	private JLabel titlela;
	private JPanel titlepanel, gridpanel;
	private JPanel[] valpanel = new JPanel[calName.length];
	private String daycale;
	private INVENTORYDao idao = new INVENTORYDao();

	public Butcher_Calculate(int n) {
		// TODO 자동 생성된 생성자 스텁
		setLayout(new BorderLayout());
		titlepanel = new JPanel();
		titlela = new JLabel();

		SimpleDateFormat dats = new SimpleDateFormat("yyyy년 MM월 dd일");
		String todays = dats.format(new Date());
		titlela.setText(todays + " 정산");
		titlela.setFont(new Font("Serif", Font.BOLD, 25));
		titlepanel.add(titlela);
		gridpanel = new JPanel(new GridLayout(3, 1));
		for (int i = 0; i < calName.length; i++) {
			daycale = idao.cale(i);
			namela[i] = new JLabel(calName[i]);
			namela[i].setFont(new Font("Serif", Font.BOLD, 15));
			valuela[i] = new JLabel(daycale + "원");
			valuela[i].setFont(new Font("Serif", Font.BOLD, 15));
			if (Long.parseLong(daycale.replace(",", "").trim()) >= 0) {
				lossla[i] = new JLabel("흑자");
				lossla[i].setFont(new Font("Serif", Font.BOLD, 15));
				lossla[i].setForeground(Color.BLACK);
			} else {
				lossla[i] = new JLabel("적자");
				lossla[i].setFont(new Font("Serif", Font.BOLD, 15));
				lossla[i].setForeground(Color.RED);
			}
			valpanel[i] = new JPanel(new GridLayout(1, 3));
			valpanel[i].add(namela[i]);
			valpanel[i].add(valuela[i]);
			valpanel[i].add(lossla[i]);
			gridpanel.add(valpanel[i]);
		}
		refresh();

		add(titlepanel, BorderLayout.NORTH);
		add(gridpanel, BorderLayout.CENTER);
		setSize(350, 300);
		setTitle("재고 추가");
		setResizable(false);
		setLocationRelativeTo(null);
		if (n == 1) {
			setVisible(false);
		} else {
			setVisible(true);
		}
	}

	public void refresh() {
		for (int i = 0; i < calName.length; i++) {
			daycale = idao.cale(i);
			valuela[i].setText(daycale + "원");
			if (Long.parseLong(daycale.replace(",", "").trim()) >= 0) {
				lossla[i].setText("흑자");
				lossla[i].setForeground(Color.BLACK);
			} else {
				lossla[i].setText("적자");
				lossla[i].setForeground(Color.RED);
			}
		}
	}
}
