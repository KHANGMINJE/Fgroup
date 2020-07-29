{package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import transData.transData;

import java.util.*;
//import client.;

public class Room {

	private PrintWriter out;
	static String str;
	static Map<Integer, Integer> room_info;
	static ObjectOutputStream oos = null;
	static ObjectInputStream ois = null;
	Client client = null;
	private static int room_port = 0;
	private static int room_num = -1;
	static String final_time; 

	public Room(Client client, Map<Integer, Integer> room_info, ObjectOutputStream oos, ObjectInputStream ois) {
		this.client=client;
		this.room_info = room_info;
		this.oos = oos;
		this.ois = ois;
		client.oos = oos;
		client.ois = ois;
		new Display4();
		
	}

	// use function -- client.send_room_num(int room_number); to send information of
	// room_number

	public int getRoomPort() {
		return room_port;
	}

	public class Display4 extends JFrame implements ActionListener {
		JLabel label1 = new JLabel("ルーム1");
		JLabel label2 = new JLabel("ルーム2");
		JLabel label3 = new JLabel("ルーム3");
		JLabel label4 = new JLabel("ルーム4");
		//M:空き情報などのラベルの格納
		String[] room_label = new String[8];
		//M:room内のプレイヤー管理(いるなら1, いないなら0)
		int[] room_player= new int[8];
		
		JButton b11 = null;
		JButton b12 = null;
		JButton b21 = null;
		JButton b22 = null;
		JButton b31 = null;
		JButton b32 = null; 
		JButton b41 = null;
		JButton b42 = null;
		JButton CPU_match = null;

		public Display4()	{
			System.out.println("Display4 method");
			for(int i=1, j=0;i<5;i++, j+=2) {
				if(room_info.get(i)==0) {
					room_label[j] = "空き";
					room_label[j+1] = "空き";
					room_player[j] = 0;
					room_player[j+1] = 0;
				}else if(room_info.get(i)==1){
					//M:プレイヤー情報の格納
					room_label[j] = "Player1";
					room_label[j+1] = "空き";
					room_player[j] = 1;
					room_player[j+1] = 0;
				}else if(room_info.get(i)==2){
					//M:プレイヤー情報の格納
					room_label[j] = "Player1";
					room_label[j+1] = "Player2";
					room_player[j] = 1;
					room_player[j+1] = 1;
				}else {
					System.out.println("Room.Display4:room_info error");
				}
			}
			b11 = new JButton(room_label[0]);
			b12 = new JButton(room_label[1]);
			b21 = new JButton(room_label[2]);
			b22 = new JButton(room_label[3]);
			b31 = new JButton(room_label[4]);
			b32 = new JButton(room_label[5]);
			b41 = new JButton(room_label[6]);
			b42 = new JButton(room_label[7]);
			setLayout(new BorderLayout());
			JPanel w = new JPanel();
			JButton b = new JButton("ログアウト");
			CPU_match = new JButton("CPU対戦");
			CPU_match.addActionListener(this);
			b.addActionListener(this);
			w.add(b);
			w.add(CPU_match);
			add(w, "West");
			JPanel m = new JPanel();
			m.setLayout(new BorderLayout());
			JPanel x = new JPanel();
			x.setPreferredSize(new Dimension(350, 15));
			m.add(x, "North");
			JPanel p = new JPanel();
			p.setLayout(new GridLayout(2, 2));
			JPanel p1 = new JPanel();
			JPanel p11 = new JPanel();
			p11.setBackground(Color.BLUE);
			JPanel p111 = new JPanel();
			p111.setPreferredSize(new Dimension(300, 250));
			p111.setLayout(new BorderLayout());
			JPanel p1111 = new JPanel();
			p1111.setPreferredSize(new Dimension(300, 70));
			p1111.add(label1);
			p111.add(p1111, "North");
			JPanel p1112 = new JPanel();
			p1112.setPreferredSize(new Dimension(300, 90));
			b11.addActionListener(this);
			if (room_player[0] == 0) {
				b11.setFont(b11.getFont().deriveFont(40.0f));
				p1112.add(b11);
			} else {
				JLabel playera = new JLabel("<html>プレイヤ名A<br/>（成績）</html>");
				p1112.add(playera);
				playera.setFont(playera.getFont().deriveFont(20.0f));
			}
			p111.add(p1112, "Center");
			JPanel p1113 = new JPanel();
			p1113.setPreferredSize(new Dimension(300, 90));
			b12.addActionListener(this);
			if (room_player[1] == 0) {
				b12.setFont(b12.getFont().deriveFont(40.0f));
				p1113.add(b12);
			} else {
				JLabel playerb = new JLabel("<html>プレイヤ名B<br/>（成績）</html>");
				p1113.add(playerb);
				playerb.setFont(playerb.getFont().deriveFont(20.0f));
			}
			p111.add(p1113, "South");
			p11.add(p111);
			label1.setFont(label1.getFont().deriveFont(44.0f));
			p1.add(p11);
			p.add(p1);
			JPanel p2 = new JPanel();
			JPanel p22 = new JPanel();
			p22.setBackground(Color.BLUE);
			JPanel p222 = new JPanel();
			p222.setPreferredSize(new Dimension(300, 250));
			p222.setLayout(new BorderLayout());
			JPanel p2221 = new JPanel();
			p2221.setPreferredSize(new Dimension(300, 70));
			p2221.add(label2);
			p222.add(p2221, "North");
			JPanel p2222 = new JPanel();
			p2222.setPreferredSize(new Dimension(300, 90));
			b21.addActionListener(this);
			if (room_player[2] == 0) {
				b21.setFont(b21.getFont().deriveFont(40.0f));
				p2222.add(b21);
			} else {
				JLabel playerc = new JLabel("<html>プレイヤ名C<br/>（成績）</html>");
				p2222.add(playerc);
				playerc.setFont(playerc.getFont().deriveFont(20.0f));
			}
			p222.add(p2222, "Center");
			JPanel p2223 = new JPanel();
			p2223.setPreferredSize(new Dimension(300, 90));
			b22.addActionListener(this);
			if (room_player[3] == 0) {
				b22.setFont(b22.getFont().deriveFont(40.0f));
				p2223.add(b22);
			} else {
				JLabel playerd = new JLabel("<html>プレイヤ名D<br/>（成績）</html>");
				p2223.add(playerd);
				playerd.setFont(playerd.getFont().deriveFont(20.0f));
			}
			p222.add(p2223, "South");
			p22.add(p222);
			label2.setFont(label2.getFont().deriveFont(44.0f));
			p2.add(p22);
			p.add(p2);
			JPanel p3 = new JPanel();
			JPanel p33 = new JPanel();
			p33.setBackground(Color.BLUE);
			JPanel p333 = new JPanel();
			p333.setPreferredSize(new Dimension(300, 250));
			p333.setLayout(new BorderLayout());
			JPanel p3331 = new JPanel();
			p3331.setPreferredSize(new Dimension(300, 70));
			p3331.add(label3);
			p333.add(p3331, "North");
			JPanel p3332 = new JPanel();
			p3332.setPreferredSize(new Dimension(300, 90));
			b31.addActionListener(this);
			if (room_player[4] == 0) {
				b31.setFont(b31.getFont().deriveFont(40.0f));
				p3332.add(b31);
			} else {
				JLabel playere = new JLabel("<html>プレイヤ名E<br/>（成績）</html>");
				p3332.add(playere);
				playere.setFont(playere.getFont().deriveFont(20.0f));
			}
			p333.add(p3332, "Center");
			JPanel p3333 = new JPanel();
			p3333.setPreferredSize(new Dimension(300, 90));
			b32.addActionListener(this);
			if (room_player[5] == 0) {
				b32.setFont(b32.getFont().deriveFont(40.0f));
				p3333.add(b32);
			} else {
				JLabel playerf = new JLabel("<html>プレイヤ名F<br/>（成績）</html>");
				p3333.add(playerf);
				playerf.setFont(playerf.getFont().deriveFont(20.0f));
			}
			p333.add(p3333, "South");
			p33.add(p333);
			label3.setFont(label3.getFont().deriveFont(44.0f));
			p3.add(p33);
			p.add(p3);
			JPanel p4 = new JPanel();
			JPanel p44 = new JPanel();
			p44.setBackground(Color.BLUE);
			JPanel p444 = new JPanel();
			p444.setPreferredSize(new Dimension(300, 250));
			p444.setLayout(new BorderLayout());
			JPanel p4441 = new JPanel();
			p4441.setPreferredSize(new Dimension(300, 70));
			p4441.add(label4);
			p444.add(p4441, "North");
			JPanel p4442 = new JPanel();
			p4442.setPreferredSize(new Dimension(300, 90));
			b41.addActionListener(this);
			if (room_player[6] == 0) {
				b41.setFont(b41.getFont().deriveFont(40.0f));
				p4442.add(b41);
			} else {
				JLabel playerg = new JLabel("<html>プレイヤ名G<br/>（成績）</html>");
				p4442.add(playerg);
				playerg.setFont(playerg.getFont().deriveFont(20.0f));
			}
			p444.add(p4442, "Center");
			JPanel p4443 = new JPanel();
			p4443.setPreferredSize(new Dimension(300, 90));
			b42.addActionListener(this);
			if (room_player[7] == 0) {
				b42.setFont(b42.getFont().deriveFont(40.0f));
				p4443.add(b42);
			} else {
				JLabel playerh = new JLabel("<html>プレイヤ名H<br/>（成績）</html>");
				p4443.add(playerh);
				playerh.setFont(playerh.getFont().deriveFont(20.0f));
			}
			p444.add(p4443, "South");
			p44.add(p444);
			label4.setFont(label4.getFont().deriveFont(44.0f));
			p4.add(p44);
			p.add(p4);
			m.add(p, "Center");
			add(m, "Center");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(1000, 600);
			setTitle("ルーム選択画面");
			setVisible(true);
			
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("ログアウト")) {
				setVisible(false);

			} else {
				if (e.getSource() == b11) {
					room_player[0] = 1;
					room_num = 1;
				setVisible(false);
				if (room_player[1] == 1) {
					new Oserov4_cpu(client,client.ois,client.oos);
				}
				
				} else if (e.getSource() == b12) {
					room_player[1] = 1;
					room_num = 1;
					setVisible(false);
					if (room_player[0] == 1) {
						new Oserov4_cpu(client,client.ois,client.oos);
					}
				} else if (e.getSource() == b21) {
					room_player[2] = 1;
					room_num = 2;
					setVisible(false);
					if (room_player[3] == 1) {
						new Oserov4_cpu(client,client.ois,client.oos);
					}
				} else if (e.getSource() == b22) {
					room_player[3] = 1;
					room_num = 2;
					setVisible(false);
					if (room_player[2] == 1) {
						new Oserov4_cpu(client,client.ois,client.oos);
					}
				} else if (e.getSource() == b31) {
					room_player[4] = 1;
					room_num = 3;
					setVisible(false);
					if (room_player[5] == 1) {
						new Oserov4_cpu(client,client.ois,client.oos);
					}
				} else if (e.getSource() == b32) {
					room_player[5] = 1;
					room_num = 3;
					setVisible(false);
					if (room_player[4] == 1) {
						new Oserov4_cpu(client,client.ois,client.oos);
					}
				} else if (e.getSource() == b41) {
					room_player[6] = 1;
					room_num = 4;
					setVisible(false);
					if (room_player[7] == 1) {
						new Oserov4_cpu(client,client.ois,client.oos);
					}
				} else if (e.getSource() == b42) {
					room_player[7] = 1;
					room_num = 4;
					setVisible(false);
					if (room_player[6] == 1) {
						new Oserov4_cpu(client,client.ois,client.oos);
					}
				}else if(e.getSource()==CPU_match){
					new Oserov4_cpu(client,client.ois,client.oos);
				}
				transData room_choice = new transData(13);
	            room_choice.set_room_num(room_num);
	            System.out.println("room choice="+room_choice.get_room_num());
	            try {
					oos.writeObject(room_choice);
					System.out.println("sent room choice");
					transData return_port = (transData)ois.readObject();
					System.out.println("receive return port");
		            client.room_port = return_port.get_port();
		            System.out.println("Room:Action, room_port="+client.room_port);
		            ObjectOutputStream oos_room = null;
			        ObjectInputStream ois_room = null;
			        try{
			            Socket s_room = new Socket(client.ServerAddress,client.room_port);
			            OutputStream os_room = s_room.getOutputStream();
			            oos_room = new ObjectOutputStream(os_room);

			            InputStream is_room = s_room.getInputStream();
			            ois_room = new ObjectInputStream(is_room);
			            System.out.println("start oserov4");
			        }catch (Exception e1){
			            //e.printStackTrace();
			        	
			        }finally {

			        }
				} catch (IOException | ClassNotFoundException e1) {
					// TODO 自動生成された catch ブロック
					e1.printStackTrace();
				}finally {
					
				}
	            
	            /*ObjectOutputStream oos_room = null;
	            ObjectInputStream ois_room = null;
	            try{
	                Socket s_room = new Socket(,room_port);
	                //s.close(); // close unnecessary socket

	                OutputStream os_room = s_room.getOutputStream();
	                oos_room = new ObjectOutputStream(os_room);

	                InputStream is_room = s_room.getInputStream();
	                ois_room = new ObjectInputStream(is_room);

	            }catch (Exception er){
	                //er.printStackTrace();
	            }finally {
	            	new Oserov4(oos_room, ois_room);
	            }*/
			}
		}

	}

	public static class Display6 extends JFrame implements ActionListener {

		JLabel label = new JLabel("<html>対戦相手の希望時間は、<br/>" + str + "です。<br/>合意しますか？</html>");
		Client client = null;
		Display6(Client client) {
			this.client = client;
			setLayout(new FlowLayout());
			JPanel p = new JPanel();
			p.setLayout(new BorderLayout());
			JPanel x = new JPanel();
			x.setPreferredSize(new Dimension(350, 15));
			p.add(x, "North");
			JPanel pn = new JPanel();
			pn.setPreferredSize(new Dimension(350, 70));
			pn.setBackground(Color.GREEN);
			JPanel pnc = new JPanel();
			pnc.setPreferredSize(new Dimension(340, 60));
			pnc.add(label);
			pn.add(pnc);
			p.add(pn, "Center");

			JPanel pc = new JPanel();
			pc.setLayout(new FlowLayout());
			JButton b1 = new JButton("合意する");
			b1.setPreferredSize(new Dimension(120, 30));
			b1.addActionListener(this);
			pc.add(b1);
			JButton b2 = new JButton("合意しない");
			b2.setPreferredSize(new Dimension(120, 30));
			b2.addActionListener(this);
			pc.add(b2);
			p.add(pc, "South");
			add(p, "Center");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(400, 200);
			setTitle("持ち時間合意画面");
			setVisible(true);
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("合意する")) {
				new Oserov4(this.client,client.oos,client.ois,final_time);
			} else if (e.getActionCommand().equals("合意しない")) {
				setVisible(false);
				new Display5(client);
				this.dispose();
			}
		}

	}

	public static class Display5 extends JFrame implements ActionListener {

		JLabel label = new JLabel("<html>自分の持ち時間の希望を<br/>対戦相手に送信します。<br/>以下から選んでください。</html>");
		Client client = null;
		Display5(Client client) {
			setLayout(new FlowLayout());
			JPanel p = new JPanel();
			p.setLayout(new BorderLayout());
			JPanel x = new JPanel();
			x.setPreferredSize(new Dimension(350, 15));
			p.add(x, "North");
			JPanel pn = new JPanel();
			pn.setPreferredSize(new Dimension(350, 70));
			pn.setBackground(Color.GREEN);
			JPanel pnc = new JPanel();
			pnc.setPreferredSize(new Dimension(340, 60));
			pnc.add(label);
			pn.add(pnc);
			p.add(pn, "North");
			JPanel pc = new JPanel();
			pc.setLayout(new FlowLayout());
		
			JButton b2 = new JButton("10秒");
			b2.setPreferredSize(new Dimension(90, 30));
			b2.addActionListener(this);
			pc.add(b2);
			JButton b3 = new JButton("15秒");
			b3.setPreferredSize(new Dimension(90, 30));
			b3.addActionListener(this);
			pc.add(b3);
			p.add(pc, "Center");
			JPanel ps = new JPanel();
			ps.setLayout(new FlowLayout());
			JButton b4 = new JButton("30秒");
			b4.setPreferredSize(new Dimension(90, 30));
			b4.addActionListener(this);
			ps.add(b4);
			p.add(ps, "South");
			add(p, "Center");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(400, 250);
			setTitle("持ち時間選択画面");
			setVisible(true);
		}

		public void actionPerformed(ActionEvent e) {
			int time ;
			
			boolean flag = false;
			if (e.getActionCommand().equals("10秒")) {
				str = "10秒";
				time =10;
				final_time=client.send_wait_time(time,client.ois,client.oos);
				new Display6(this.client);
				this.dispose();
			} else if (e.getActionCommand().equals("15秒")) {
				str = "15秒";
				time=15;
				final_time=client.send_wait_time(time,client.ois,client.oos);
				new Display6(this.client);
				this.dispose();
			} else if (e.getActionCommand().equals("30秒")) {
				str = "30秒";
				time=30;
				final_time=client.send_wait_time(time,client.ois,client.oos);
				new Display6(this.client);
				this.dispose();
			} 
			if(flag){
				System.out.println("send waiting time successed");


			}else{
				System.out.println("send waiting time fail");
			}
		}

	}

	public static void main(String[] args) {
		//System.out.println("Room:main");
		//new Room();
	}

}
