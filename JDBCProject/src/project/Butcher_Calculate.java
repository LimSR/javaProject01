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
	private String[] calName = { "��������: ", "�ְ�����: ", "��������: " };
	private JLabel[] namela = new JLabel[calName.length];
	private JLabel[] valuela = new JLabel[calName.length];
	private JLabel[] lossla = new JLabel[calName.length];
	private JLabel titlela;
	private JPanel titlepanel, gridpanel;
	private JPanel[] valpanel = new JPanel[calName.length];
	private String daycale;
	private INVENTORYDao idao = new INVENTORYDao();

	public Butcher_Calculate(int n) {
		// TODO �ڵ� ������ ������ ����
		setLayout(new BorderLayout());
		titlepanel = new JPanel();
		titlela = new JLabel();

		SimpleDateFormat dats = new SimpleDateFormat("yyyy�� MM�� dd��");
		String todays = dats.format(new Date());
		titlela.setText(todays + " ����");
		titlela.setFont(new Font("Serif", Font.BOLD, 25));
		titlepanel.add(titlela);
		gridpanel = new JPanel(new GridLayout(3, 1));
		for (int i = 0; i < calName.length; i++) {
			daycale = idao.cale(i);
			namela[i] = new JLabel(calName[i]);
			namela[i].setFont(new Font("Serif", Font.BOLD, 15));
			valuela[i] = new JLabel(daycale + "��");
			valuela[i].setFont(new Font("Serif", Font.BOLD, 15));
			if (Long.parseLong(daycale.replace(",", "").trim()) >= 0) {
				lossla[i] = new JLabel("����");
				lossla[i].setFont(new Font("Serif", Font.BOLD, 15));
				lossla[i].setForeground(Color.BLACK);
			} else {
				lossla[i] = new JLabel("����");
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
		setTitle("��� �߰�");
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
			valuela[i].setText(daycale + "��");
			if (Long.parseLong(daycale.replace(",", "").trim()) >= 0) {
				lossla[i].setText("����");
				lossla[i].setForeground(Color.BLACK);
			} else {
				lossla[i].setText("����");
				lossla[i].setForeground(Color.RED);
			}
		}
	}
}
