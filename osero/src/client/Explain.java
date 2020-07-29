package client;
import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import transData.*;
public class Explain extends JFrame implements ActionListener{
	static final long serialVersionUID = 1;
	JTextArea txt = new JTextArea("オセロのゲーム説明。123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
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
