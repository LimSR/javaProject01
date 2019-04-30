package project;

import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import DB.JDBCUtil;

public class Butcher_First_Frame extends JFrame {
	private JPanel p;
	private JLabel p_name_la, id_la, pw_la;
	private JTextField id_tf;
	private JPasswordField pw_tf;
	private JButton login_b;
	private boolean blogch;
	private Butcher_Main bm;
	Butcher_Stock_Check bsc;

	public Butcher_First_Frame() {
		// TODO 자동 생성된 생성자 스텁
		setSize(300, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("재고  관리");
		setResizable(false);
		setLocationRelativeTo(null);

		p = new JPanel();
		p.setLayout(null);
		p_name_la = new JLabel("정육점 재고 관리 프로그램");
		p_name_la.setFont(new Font("Serif", Font.BOLD, 16));
		p_name_la.setBounds(10, 10, 270, 40);
		p.add(p_name_la);
		id_la = new JLabel("ID");
		id_la.setFont(new Font("Serif", Font.BOLD, 12));
		id_la.setBounds(15, 50, 80, 25);
		p.add(id_la);
		id_tf = new JTextField();
		id_tf.setBounds(70, 50, 170, 25);
		p.add(id_tf);
		pw_la = new JLabel("PW");
		pw_la.setFont(new Font("Serif", Font.BOLD, 12));
		pw_la.setBounds(15, 85, 80, 25);
		p.add(pw_la);
		pw_tf = new JPasswordField();
		pw_tf.setBounds(70, 85, 170, 25);
		p.add(pw_tf);
		login_b = new JButton("Login");
		login_b.setBounds(100, 125, 80, 25);
		login_b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 자동 생성된 메소드 스텁
				if (e.getSource() == login_b) {
					try {
						JDBCUtil.setUser(id_tf.getText());
						JDBCUtil.setPassword(pw_tf.getPassword());
						if (JDBCUtil.getConn() != null) {
							JOptionPane.showMessageDialog(null, "로그인 되었습니다.");
							blogch = true;

							if (isLogin()) {
								dispose();
								//bsc = new Butcher_Stock_Check();
//								DBConnectTest test=new DBConnectTest();
//								test.test();
							}
						}
					} catch (HeadlessException e1) {
						// TODO 자동 생성된 catch 블록
						JOptionPane.showMessageDialog(null, "프로그램 오류!", "프로그램 오류!", JOptionPane.ERROR_MESSAGE);
						System.exit(0);
					} catch (SQLException e1) {
						// TODO 자동 생성된 catch 블록
						JOptionPane.showMessageDialog(null, "ID/PW가 틀렸습니다.", "로그인 오류", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		pw_tf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO 자동 생성된 메소드 스텁
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						JDBCUtil.setUser(id_tf.getText());
						JDBCUtil.setPassword(pw_tf.getPassword());
						if (JDBCUtil.getConn() != null) {
							JOptionPane.showMessageDialog(null, "로그인 되었습니다.");
							blogch = true;

							if (isLogin()) {
								dispose();
								bsc = new Butcher_Stock_Check();
//								DBConnectTest test=new DBConnectTest();
//								test.test();
							}
						}
					} catch (HeadlessException e1) {
						// TODO 자동 생성된 catch 블록
						JOptionPane.showMessageDialog(null, "프로그램 오류!", "프로그램 오류!", JOptionPane.ERROR_MESSAGE);
						System.exit(0);
					} catch (SQLException e1) {
						// TODO 자동 생성된 catch 블록
						JOptionPane.showMessageDialog(null, "ID/PW가 틀렸습니다.", "로그인 오류", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		p.add(login_b);

		add(p);
		setVisible(true);
	}

	public void setMain(Butcher_Main bm) {
		// TODO 자동 생성된 메소드 스텁
		this.bm = bm;
	}

	public boolean isLogin() {
		return blogch;
	}
}
