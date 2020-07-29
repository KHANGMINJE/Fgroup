package client;

import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.net.*;
import transData.*;

public class Login_display extends JFrame implements ActionListener {
	static final long serialVersionUID = 1;
	JLabel label[] = new JLabel[2];
	JTextField txt = new JTextField();
	JPasswordField pwd = new JPasswordField();
	JButton btn[] = new JButton[4];
	Client client = null;
	Socket s = null;
	ObjectOutputStream oos = null;
	ObjectInputStream ois = null;
	JLabel label1[] = new JLabel[4];
	JTextArea txt1[] = new JTextArea[3];
	JButton btn1 = new JButton("送信する");
	public Login_display(String title, Client client) {
		super(title);
		this.client = client;
		JPanel p = (JPanel) getContentPane();
		p.setLayout(null);
		label[0] = new JLabel("プレイヤ名");
		label[1] = new JLabel("パスワード");
		btn[0] = new JButton("新規登録");
		btn[1] = new JButton("ログイン");
		btn[2] = new JButton("パスワード");
		btn[3] = new JButton("ルール説明");

		for (int i = 0; i < 4; i++) {
			btn[i].addActionListener(this);
		}

		label[0].setBounds(10, 10, 75, 20);
		label[1].setBounds(10, 30, 75, 20);
		txt.setBounds(90, 10, 120, 20);
		pwd.setBounds(90, 30, 120, 20);
		btn[0].setBounds(15, 60, 100, 20);
		btn[1].setBounds(125, 60, 100, 20);
		btn[2].setBounds(40, 90, 150, 20);
		btn[3].setBounds(40, 120, 150, 20);

		p.add(label[0]);
		p.add(label[1]);
		p.add(txt);
		p.add(pwd);
		p.add(btn[0]);
		p.add(btn[1]);
		p.add(btn[2]);
		p.add(btn[3]);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(250, 250);
		setVisible(true);
		setResizable(false);

		try {
			s = new Socket(client.ServerAddress, client.getLoginPort());
			OutputStream os = s.getOutputStream();
			oos = new ObjectOutputStream(os);
			InputStream is = s.getInputStream();
			ois = new ObjectInputStream(is);
			client.ois = ois;
			client.oos = oos;
		} catch (Exception se) {
			System.out.println("Error(Login_display):Socket error");
			se.printStackTrace();
		} finally {

		}
	}

	

	public void actionPerformed(ActionEvent e) {
		// onclick
		
		// button 新規登録
		if (e.getSource() == btn[0]) {
			try {
				new Register_display("Register",this.client);
				this.dispose();
			} catch (Exception e1) {
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
			}
			
			
			//this.setVisible(false);
		}

		// button ログイン
		if (e.getSource() == btn[1]) {
			String username = txt.getText();
			// String username = "usr_1";
			char[] password = pwd.getPassword();
			String passwordstr = new String(password);
			// String passwordstr = "pass_1";
			System.out.println("username=" + username + ",password=" + passwordstr);
			boolean flag = client.send_login_info(username, passwordstr, ois, oos);
			if (flag) {
				// login success
				client.choose_room(oos, ois);
				this.dispose();
				/*
				 * try { System.out.println("Login_display:socket close"); //s.close(); } catch
				 * (IOException e1) { e1.printStackTrace(); }finally {
				 */
				/*
				 * ObjectOutputStream oos_room = null; ObjectInputStream ois_room = null; try{
				 * Socket s_room = new Socket(client.ServerAddress,client.room_port);
				 * OutputStream os_room = s_room.getOutputStream(); oos_room = new
				 * ObjectOutputStream(os_room);
				 * 
				 * InputStream is_room = s_room.getInputStream(); ois_room = new
				 * ObjectInputStream(is_room); System.out.println("start oserov4"); new
				 * Oserov4(client, oos_room, ois_room); }catch (Exception e1){
				 * //e.printStackTrace(); }finally {
				 * 
				 * }
				 */ // ->Display4 Action
				// }
			} else {
				// login failed
				// TODO show "login failed"
				System.out.println("Login Failed");
			}
		}
		
		// osero 対局の終了

		// button ルール説明
		if (e.getSource() == btn[3]) {
			new Explain("Explaination", client);
			this.dispose();
		}
	}
}
