package client;
import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import transData.*;
public class Explain extends JFrame implements ActionListener{
	static final long serialVersionUID = 1;
	JTextArea txt = new JTextArea("《オセロのゲーム説明》\r\n" +
						"1.制限時間を合意して設定する。\r\n" +
						"2.各手番は制限時間内に行う必要がある。\r\n" +
						"3.制限時間を超えると、負けになる。\r\n" +
						"4.黒の先手で始まる。\r\n" +
						"5.基本的に一回ずつ着手し、手番を転換する。\r\n" +
						"6.縦、横、斜めの一直線上で新たに置く石とすでにある自身の石の間にある相手の石を自身の石にひっくり返す。\r\n" +
						"7.置ける場所とは、新たに石を置いた時にひっくり返す相手の石がある場所を言う。\r\n" +
						"8.置ける場所がある場合は必ずおく必要がある。\r\n" +
						"9.置ける場所がない場合パスになり、相手の手番になる。\r\n" +
						"10.双方が置ける場所がない場合(通常は盤面が全て埋まったら)、ゲーム終了である。\r\n" +
						"11.石の多い方が勝者となる。");
	JScrollPane scr = new JScrollPane(txt);
	JButton btn = new JButton("戻る");
	Client client;
	public Explain(String title,Client client){
		super(title);
		this.client = client;
		JPanel p = (JPanel)getContentPane();
		p.setLayout(null);

		txt.setEditable(false);
		txt.setLineWrap(true);

		scr.setBounds(15, 10, 200, 150);
		btn.setBounds(90, 180, 60, 20);
		btn.addActionListener(this);

		p.add(scr);
		p.add(btn);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(250, 250);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		//onclick
		if(e.getSource()==btn){
			new Login_display("Login",client);
			this.dispose();
		}
	}
}
