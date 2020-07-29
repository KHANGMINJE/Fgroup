package client;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JRadioButton;

import transData.transData;

public class Oserov4 /* extends JFrame implements ActionListener */ {
	/**
	 *
	 */

	private static final long serialVersionUID = 1L;

	Container c;
	JLabel l1 = new JLabel("対戦相手" + " vs " + "相手の成績");
	JLabel l2 = new JLabel("残り時間");
	JLabel l3 = new JLabel("");
	JLabel l4 = new JLabel("黒の数：白の数");
	JLabel l5 = new JLabel("あなたの番");
	JButton b1 = new JButton("設定");
	JLabel l6 = new JLabel("置ける場所表現 : ");
	JRadioButton r1 = new JRadioButton("0n", true);
    	JRadioButton r2 = new JRadioButton("Off", false);

	JButton[] A = new JButton[10];
	JButton[] B = new JButton[10];
	JButton[] C = new JButton[10];
	JButton[] D = new JButton[10];
	JButton[] E = new JButton[10];
	JButton[] F = new JButton[10];
	JButton[] G = new JButton[10];
	JButton[] H = new JButton[10];

	JLabel chessboard;
	JLabel chess;
	double x = 0;
	double y = 0;
	boolean interuput = false;
	boolean G_switch = true;
	Osero_setting osero_setting;
	ImageIcon iconB = new ImageIcon(getClass().getResource("00Black.jpg"));
	ImageIcon iconW = new ImageIcon(getClass().getResource("00White.jpg"));
	ImageIcon iconG = new ImageIcon(getClass().getResource("green.jpg"));
	// static int turn = 1;//初始化先手 黑色为0 白色为1
	// static int your_turn = 0; //自分のターン1, 相手のターン0
	Client client = null;
	int count = 0;
	int result = 5;
	// protocol番号3でオセロの駒の位置情報をs_dataでサーバに送る。
	transData s_data = new transData(3);
	transData r_data = null;
	transData battle_start = null;
	Ban map = new Ban();
	HashMap<Integer, transData> hash = new HashMap<Integer, transData>();

	Reciever rec = null;
	int time_limit = 30;
	int my_turn = -1;
	Timer_count_down time_count_down;
	JFrame j = new JFrame();
	Room.Display4 room;

	public Oserov4(Client client, ObjectOutputStream oos, ObjectInputStream ois, int time) {
		this.client = client;
		// this.oos = oos;
		// this.ois = ois;
		// client.oos = oos;
		// client.ois = ois;
		// this.room = room;
		this.time_limit = time;
		new Display();
		// System.out.println("Battlereceiver");
		// client.BattleReceiver(map);
	}

	public class Display extends JFrame implements ActionListener {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		public Display() {
			c = j.getContentPane();

			// j.setSize(800,600);
			j.setBounds(0, 0, 800, 600);
			j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			time_count_down = new Timer_count_down(time_limit, this);
			JLayeredPane lp = new JLayeredPane();

			ImageIcon img = new ImageIcon(getClass().getResource("frame.jpg"));
			chessboard = new JLabel();
			chessboard.setIcon(img);

			img = new ImageIcon(getClass().getResource("00Black.jpg"));
			chess = new JLabel();
			chess.setIcon(img);

			l1.setBounds(300, 10, 300, 20);
			chessboard.setBounds(10, 40, 420, 420);
			chess.setBounds(28, 57, 43, 43);
			l2.setBounds(600, 50, 200, 30);
			l3.setBounds(600, 150, 200, 30);
			l4.setBounds(600, 250, 200, 30);
			l5.setBounds(50, 500, 100, 30);
			b1.setBounds(200, 500, 100, 30);
			b1.addActionListener(this);

			l6.setBounds(350, 500, 100, 30);
        		ButtonGroup group = new ButtonGroup();
       			group.add(r1);
        		group.add(r2);
        		r1.setBounds(450, 500, 50, 30);
        		r2.setBounds(500, 500, 50, 30);

		        r1.addActionListener(this);
		        r2.addActionListener(this);

			int buttonSize = 46, i = 0;

			Timer chess_number_count = new Timer();
			chess_number_count.schedule(new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					l4.setText("Black:" + String.valueOf(map.countNumber(0)) + "vs" + "White:"
							+ String.valueOf(map.countNumber(1)));
					if (client.your_turn == 1) {
						l5.setText("あなたの番");
					} else {
						l5.setText("相手の番");
					}
				}
			}, 100, 100);

			for (i = 0; i < 8; i++) {
				A[i] = new JButton();

				A[i].setBounds(28, 57 + i * 48, buttonSize, buttonSize);
				A[i].addActionListener(this);
				A[i].setOpaque(false);
				A[i].setContentAreaFilled(false);
			}
			for (i = 0; i < 8; i++) {
				B[i] = new JButton();

				B[i].setBounds(77, 57 + i * 48, buttonSize, buttonSize);
				B[i].addActionListener(this);
				B[i].setOpaque(false);
				B[i].setContentAreaFilled(false);
			}

			for (i = 0; i < 8; i++) {
				C[i] = new JButton();

				C[i].setBounds(126, 57 + i * 48, buttonSize, buttonSize);
				C[i].addActionListener(this);
				C[i].setOpaque(false);
				C[i].setContentAreaFilled(false);
			}
			for (i = 0; i < 8; i++) {
				D[i] = new JButton();

				D[i].setBounds(172, 57 + i * 48, buttonSize, buttonSize);
				D[i].addActionListener(this);
				D[i].setOpaque(false);
				D[i].setContentAreaFilled(false);
			}
			for (i = 0; i < 8; i++) {
				E[i] = new JButton();

				E[i].setBounds(220, 57 + i * 48, buttonSize, buttonSize);
				E[i].addActionListener(this);
				E[i].setOpaque(false);
				E[i].setContentAreaFilled(false);
			}
			for (i = 0; i < 8; i++) {
				F[i] = new JButton();

				F[i].setBounds(268, 57 + i * 48, buttonSize, buttonSize);
				F[i].addActionListener(this);
				F[i].setOpaque(false);
				F[i].setContentAreaFilled(false);
			}
			for (i = 0; i < 8; i++) {
				G[i] = new JButton();

				G[i].setBounds(314, 57 + i * 48, buttonSize, buttonSize);
				G[i].addActionListener(this);
				G[i].setOpaque(false);
				G[i].setContentAreaFilled(false);
			}
			for (i = 0; i < 8; i++) {
				H[i] = new JButton();

				H[i].setBounds(360, 57 + i * 48, buttonSize, buttonSize);
				H[i].addActionListener(this);
				H[i].setOpaque(false);
				H[i].setContentAreaFilled(false);
			}

			c.add(l1);
			c.add(l2);
			c.add(l3);
			c.add(l4);
			c.add(l5);
			c.add(b1);
			c.add(l6);
		        c.add(r1);
        		c.add(r2);

			j.getLayeredPane().add(chessboard, 100);

			for (i = 0; i < 8; i++) {
				j.getLayeredPane().add(A[i], 0);
				j.getLayeredPane().add(B[i], 0);
				j.getLayeredPane().add(C[i], 0);
				j.getLayeredPane().add(D[i], 0);
				j.getLayeredPane().add(E[i], 0);
				j.getLayeredPane().add(F[i], 0);
				j.getLayeredPane().add(G[i], 0);
				j.getLayeredPane().add(H[i], 0);
			}
			c.setLayout(null);

			j.setVisible(true);
			j.setResizable(false);
			map.initMap();
			map.castToBoard();
			time_count_down.start();
			// data exchange

			// waiting for server send battle start info
			try {
				battle_start = (transData) client.ois.readObject();
				if (battle_start.get_protocol() == 80) {
					System.out.println("Recieve game start information");
				} else {
					//
				}
			} catch (Exception erro) {
				erro.printStackTrace();
			}

			// 次以降の時のため

			System.out.println("please wait for opponent");
			try {
				// 先攻、後攻の受け取り
				r_data = (transData) client.ois.readObject();

				if (r_data instanceof transData) {
					// 1000が送られてきた方が先攻
					if (r_data.get_protocol() == 1000) {
						System.out.println("You hava black!");
						client.turn = 0; // 先攻なので黒
						client.your_turn = 1;
						my_turn = 0;
					} else {
						System.out.println("You hava white!");
						client.turn = 1;
						client.your_turn = 0;
						my_turn = 1;
						// client.BattleReceiver(map);
						rec = new Reciever(client, map, client.ois);
						count++;
						System.out.println("re12");
						rec.start();
						System.out.println("toppa");
					}
					// client.ois.reset();
				}
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {

			}
			System.out.println("Waiting for timeout info");
			int timeout_protocol = 2000;
			try {
				r_data = (transData) client.ois.readObject();
				if (r_data.get_protocol() == timeout_protocol) {
					new Result(1 - my_turn, client, this);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		public void actionPerformed(ActionEvent e) {

			try {
				client.oos.reset();
			} catch (IOException e1) {
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
			}
			if (e.getSource() == b1) {
				System.out.println("open new window");
				new Osero_setting(chessboard);

			}
			boolean status1 = r1.isSelected();
       			boolean status2 = r2.isSelected();
			if (e.getSource() == r1 || e.getSource() == r2) {
        			if(status1 == true) {
            				G_switch = true;
            			}else if(status2 == true) {
            				G_switch = false;
            			}
        			map.castToBoard();
			}
			try {
				if (client.your_turn == 1) {

					for (int i = 0; i < 8; i++) {
						if (e.getSource() == A[i] && map.getMapValue(i, 0) == 2) {
							map.updateMap(i, 0, client.turn);
							map.castToBoard();
							s_data.set_line(0);
							s_data.set_row(i);

							map.checkMap(client.turn);
							time_count_down.reset();

						} else if (e.getSource() == B[i] && map.getMapValue(i, 1) == 2) {
							map.updateMap(i, 1, client.turn);
							map.castToBoard();
							s_data.set_line(1);
							s_data.set_row(i);

							map.checkMap(client.turn);
							time_count_down.reset();

						} else if (e.getSource() == C[i] && map.getMapValue(i, 2) == 2) {
							map.updateMap(i, 2, client.turn);
							map.castToBoard();
							s_data.set_line(2);
							s_data.set_row(i);

							map.checkMap(client.turn);
							time_count_down.reset();

						} else if (e.getSource() == D[i] && map.getMapValue(i, 3) == 2) {
							map.updateMap(i, 3, client.turn);
							map.castToBoard();
							s_data.set_line(3);
							s_data.set_row(i);

							map.checkMap(client.turn);
							time_count_down.reset();

						} else if (e.getSource() == E[i] && map.getMapValue(i, 4) == 2) {
							map.updateMap(i, 4, client.turn);
							map.castToBoard();
							s_data.set_line(4);
							s_data.set_row(i);

							map.checkMap(client.turn);
							time_count_down.reset();

						} else if (e.getSource() == F[i] && map.getMapValue(i, 5) == 2) {
							map.updateMap(i, 5, client.turn);
							map.castToBoard();
							s_data.set_line(5);
							s_data.set_row(i);

							map.checkMap(client.turn);
							time_count_down.reset();

						} else if (e.getSource() == G[i] && map.getMapValue(i, 6) == 2) {
							map.updateMap(i, 6, client.turn);
							map.castToBoard();
							s_data.set_line(6);
							s_data.set_row(i);

							map.checkMap(client.turn);
							time_count_down.reset();

						} else if (e.getSource() == H[i] && map.getMapValue(i, 7) == 2) {
							map.updateMap(i, 7, client.turn);
							map.castToBoard();
							s_data.set_line(7);
							s_data.set_row(i);

							map.checkMap(client.turn);
							time_count_down.reset();

						}
						result = map.isGameFinish();
						try {
							transData end = new transData(36);
							ObjectOutputStream oos = new ObjectOutputStream(client.s.getOutputStream());
							if (result == my_turn) {
								new Result(result, client, this);
								end.set_endinfo_draw();
								try {
									oos.writeObject(end);
									oos.reset();
								} catch (Exception erro) {
									erro.printStackTrace();
								}
							} else if (result == 1 - my_turn) {
								new Result(result, client, this);
								end.set_endinfo_draw();
								try {
									oos.writeObject(end);
									oos.reset();
								} catch (Exception erro) {
									erro.printStackTrace();
								}
							} else {
								new Result(result, client, this);
								end.set_endinfo_draw();
								try {
									oos.writeObject(end);
									oos.reset();
								} catch (Exception erro) {
									erro.printStackTrace();
								}
							}
						} catch (Exception erro) {
							erro.printStackTrace();
						}

					}
					// client.oos.flush();
					// client.oos.shutdown();
					System.out.println("s_data=" + s_data.get_row() + "," + s_data.get_line());
					client.oos.writeObject(s_data);
					System.out.println("send!!");
					client.your_turn = 0;
					// client.BattleReceiver(map);
					if (count == 0) {
						rec = new Reciever(client, map, client.ois);
						rec.start();
						count++;
						time_count_down.reset();
					} else {
						rec = new Reciever(client, map, client.ois);
						rec.start();
						time_count_down.reset();
					}

				}

			} catch (ArithmeticException pe) {
				System.out.println("you can't place here");
			} catch (Exception erro) {
				erro.printStackTrace();
			} finally {

			}
		}

		// battle start
		/*
		 * transData r_data = null; try { //receive battle start notice r_data =
		 * (transData)ois.readObject(); } catch (ClassNotFoundException e11) { // TODO
		 * 自動生成された catch ブロック e11.printStackTrace(); } catch (IOException e1) { // TODO
		 * 自動生成された catch ブロック e11.printStackTrace(); }finally {
		 *
		 * } if (r_data.get_protocol()==80){ System.out.println("Battle start!"); }else{
		 * System.out.println("cant receive start"); }
		 *
		 * while(true) { System.out.println("please wait for opponent"); try { r_data =
		 * (transData)ois.readObject();
		 *
		 * if(r_data instanceof transData) { if(r_data.get_protocol()==1000) { turn = 0;
		 * //先攻なので黒 } if (r_data.get_protocol()==3 || r_data.get_protocol()==1000){
		 * if(r_data.get_protocol()==3){
		 * System.out.println("opponent:row="+r_data.get_row()+",line="+r_data.get_line(
		 * )); map.updateMap(r_data.get_row(), r_data.get_line(), 1-turn); //redraw
		 * //map.castToBoard(osero); }else{ System.out.println("Start!!"); }
		 * System.out.println("Your turn!!"); your_turn = 1; //input from GUI }else{
		 * System.out.println("no data"); } } } catch (ClassNotFoundException e) { //
		 * TODO 自動生成された catch ブロック e.printStackTrace(); } catch (IOException e) { //
		 * TODO 自動生成された catch ブロック e.printStackTrace(); }
		 */

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Oserov4();
	}

	public class Ban {
		int r;
		int l;
		int[][] map = new int[8][8];// 黒0,白1,置ける場所2,空白3

		public Ban() {
			this.r = 8;
			this.l = 8;
		}

		public void timeupdater() {
			time_count_down.reset();
		}

		public void initMap() {
			for (int i = 0; i < 8; i++) {
				Arrays.fill(map[i], 3);// 空白で埋める
			}

			// 中央4マスの配置
			map[3][3] = 1;
			map[3][4] = 0;
			map[4][3] = 0;
			map[4][4] = 1;
			// 先手が黒なので黒に対する”置ける場所”を探索する
			checkMap(0);
			printMap(0);
		}

		public int getMapValue(int r, int l) {
			return map[r][l];
		}

		public void printSign(int p) {
			if (p == 0) {
				System.out.print("● ");
			} else if (p == 1) {
				System.out.print("○ ");
			} else if (p == 2) {
				System.out.print("* ");
			} else {
				System.out.print("　");
			}
		}

		public void printMap(int p) {
			System.out.println("====================");
			System.out.println(" | 0 1 2 3 4 5 6 7");
			System.out.println("-+------------------");
			checkMap(p);
			for (int i = 0; i < 8; i++) {
				System.out.print(String.valueOf(i) + "| ");
				for (int j = 0; j < 8; j++) {
					printSign(map[i][j]);
				}
				System.out.println();
			}
			System.out.println("====================");
		}

		public void updateMap(int r, int l, int p) throws ArithmeticException {
			// r:行, l:列, p:置かれた色
			/*
			 * if(map[r][l] != 2) {//置ける場所以外に置こうとしたとき,空白に置こうとしたときも含む
			 * System.out.println("this place has been occupied"); throw new
			 * ArithmeticException(); } else if(p != 0 && p != 1){//手番情報が黒(0),白(1)以外のとき
			 * System.out.println("invalid value"); throw new ArithmeticException(); } else
			 */ {
				map[r][l] = p;

				// 置いた場所の周囲8マスを探索
				// 0が置かれたら1を、1が置かれたら0を探索する
				int target = 1 - p;// 探索対象

				// 探索方向を指定してilistに格納=>有効な探索方向には値が残り無効な探索方向には-1が残る
				// 0:[-1,-1] 1:[-1, 0] 2:[-1, 1]
				// 3:[ 0,-1] [ r, l] 4:[ 0, 1]
				// 5:[ 1,-1] 6:[ 1, 0] 7:[ 1, 1]
				Integer dir[][] = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 },
						{ 1, 1 } };
				Integer ilist[] = { 0, 1, 2, 3, 4, 5, 6, 7 };
				if (r == 0) {
					ilist[0] = -1;
					ilist[1] = -1;
					ilist[2] = -1;
				} else if (r == 7) {
					ilist[5] = -1;
					ilist[6] = -1;
					ilist[7] = -1;
				}
				if (l == 0) {
					ilist[0] = -1;
					ilist[3] = -1;
					ilist[5] = -1;
				} else if (l == 7) {
					ilist[2] = -1;
					ilist[4] = -1;
					ilist[7] = -1;
				}
				// System.out.println(Arrays.asList(ilist));

				for (int i : ilist) {
					if (i >= 0) {// i=-1の場合を除く
						// System.out.println(i);
						boolean turnFlag = false;// 石をひっくり返すかどうか
						int tr, tl;// tr=行,tl=列
						for (tr = r + dir[i][0], tl = l + dir[i][1]; (0 <= tr && tr < 8 && 0 <= tl
								&& tl < 8); tr += dir[i][0], tl += dir[i][1]) {// 行列が0～7の範囲を超えないように
							// System.out.println("tr:"+tr+" tl:"+tl);
							if (map[tr][tl] == target) {
								// turnFlagがtrue->trueの場合は探索を続行
								// turnFlagがfalse->trueの場合は続行(初回のみ発生)
								turnFlag = true;
							} else if (turnFlag) {
								// turnFlagがtrue->falseと変化し、かつmap[tr][tl]が自分の手番情報である場合は実際にひっくり返す必要が生じる
								// ひっくり返す
								if (map[tr][tl] == p) {
									// System.out.println("turn");
									int ttr, ttl;
									for (ttr = r, ttl = l; (tr != ttr
											|| tl != ttl); ttr += dir[i][0], ttl += dir[i][1]) {
										map[ttr][ttl] = p;
									}
									break;
								} else {
									break;
								}
							} else {
								// turnFlagがfalse->falseの場合はこの探索方向での探索を終了(初回のみ発生)
								break;
							}
						}
						// 探索終了時にturnFlagがtrueの場合はひっくり返せない
					}
				}
			}

			// checkMap(p);
			// printMap();
		}

		public void checkMap(int t) {// t:自分の手番情報=0or1
			// 「自分の」置ける場所を調べ、そのマスを2に変える

			// マスを絞るのが面倒なので全マスに対して探索
			int j, k;// j=行,k=列
			for (j = 0; j < 8; j++) {
				for (k = 0; k < 8; k++) {
					// map[j][k]の周囲8マスを探索
					if (map[j][k] == 0 || map[j][k] == 1) {
						// map[j][k]に既に石が置かれていたら判別する必要がないのでcontinue
						continue;
					} else {
						// 置ける場所(2)の情報が残っていると面倒なので一旦3でリセット
						map[j][k] = 3;
					}

					// 1が置かれたら0を、0が置かれたら1を探索する
					int target = 1 - t;// 探索対象

					// 探索方向を指定してilistに格納=>有効な探索方向には値が残り無効な探索方向には-1が残る
					// 0:[-1,-1] 1:[-1, 0] 2:[-1, 1]
					// 3:[ 0,-1] [ r, l] 4:[ 0, 1]
					// 5:[ 1,-1] 6:[ 1, 0] 7:[ 1, 1]
					Integer dir[][] = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 },
							{ 1, 1 } };
					Integer ilist[] = { 0, 1, 2, 3, 4, 5, 6, 7 };
					if (j == 0) {
						ilist[0] = -1;
						ilist[1] = -1;
						ilist[2] = -1;
					} else if (j == 7) {
						ilist[5] = -1;
						ilist[6] = -1;
						ilist[7] = -1;
					}
					if (k == 0) {
						ilist[0] = -1;
						ilist[3] = -1;
						ilist[5] = -1;
					} else if (k == 7) {
						ilist[2] = -1;
						ilist[4] = -1;
						ilist[7] = -1;
					}
					// System.out.println(Arrays.asList(ilist));

					for (int i : ilist) {
						if (i >= 0) {// i=-1の場合を除く
							boolean turnFlag = false;// 石をひっくり返せるかどうか
							int tr, tl;// tr=行,tl=列
							for (tr = j + dir[i][0], tl = k + dir[i][1]; (0 <= tr && tr < 8 && 0 <= tl
									&& tl < 8); tr += dir[i][0], tl += dir[i][1]) {// 行列が0～7の範囲を超えないように
								if (map[tr][tl] == target) {
									// turnFlagがtrue->trueの場合は探索を続行
									// turnFlagがfalse->trueの場合は続行(初回のみ発生)
									turnFlag = true;
								} else if (turnFlag) {
									// turnFlagがtrue->falseと変化し、かつmap[tr][tl]が自分の手番情報である場合は実際にひっくり返せる
									if (map[tr][tl] == t) {
										map[j][k] = 2;
										break;// 1方向でもひっくり返せれば良いのでループを抜ける
									} else {
										break;
									}
								} else {
									// turnFlagがfalse->falseの場合はこの探索方向での探索を終了(初回のみ発生)
									break;
								}
							}
							// 探索終了時にturnFlagがtrueの場合はひっくり返せない
						}
					}
				}
			} // j,kによる全探索終了
		}

		public int countNumber(int n) {// n:数えたい数字(0～3)
			int i, j, k = 0;
			for (i = 0; i < 8; i++) {
				for (j = 0; j < 8; j++) {
					if (map[i][j] == n) {
						k++;
					}
				}
			}

			return k;
		}

		public int isGameFinish() {// ゲームが終了した:0or1or2[0=黒勝,1=白勝,2=引き分け],終了しない:3
			// 両者置ける場所が無くなったときにゲーム終了
			int b, b2, w, w2;
			checkMap(0);
			b = countNumber(0);// 黒石の個数
			b2 = countNumber(2);// 黒の置ける場所の数
			// printMap();

			checkMap(1);
			w = countNumber(1);// 白石の個数
			w2 = countNumber(2);// 白の置ける場所の数
			// printMap();

			// checkMap(turn);//map[][]を元に戻しておく

			if (b2 == 0 && w2 == 0) {
				// ゲーム終了確定
				if (b > w) {
					return 0;
				} else if (w > b) {
					return 1;
				} else {
					return 2;
				}
			} else {
				return 3;
			}
		}

		public void castToBoard() {

			for (int i = 0; i < 8; i++) {
				if (i == 0) {
					for (int j = 0; j < 8; j++) {
						if (map[j][i] == 1) {
							A[j].setIcon(iconW);
							A[j].setOpaque(true);
						} else if (map[j][i] == 0) {
							A[j].setIcon(iconB);
							A[j].setOpaque(true);
						} else if (map[j][i] == 2 && G_switch == true) {
							A[j].setIcon(iconG);
						}else{
							A[j].setIcon(null);
						}
					}
				} else if (i == 1) {
					for (int j = 0; j < 8; j++) {
						if (map[j][i] == 1) {
							B[j].setIcon(iconW);
							B[j].setOpaque(true);
						} else if (map[j][i] == 0) {
							B[j].setIcon(iconB);
							B[j].setOpaque(true);
						} else if (map[j][i] == 2 && G_switch == true) {
							B[j].setIcon(iconG);
						}else{
							B[j].setIcon(null);
						}
					}
				} else if (i == 2) {
					for (int j = 0; j < 8; j++) {
						if (map[j][i] == 1) {
							C[j].setIcon(iconW);
							C[j].setOpaque(true);
						} else if (map[j][i] == 0) {
							C[j].setIcon(iconB);
							C[j].setOpaque(true);
						} else if (map[j][i] == 2 && G_switch == true) {
							C[j].setIcon(iconG);
						}else{
							C[j].setIcon(null);
						}
					}
				} else if (i == 3) {
					for (int j = 0; j < 8; j++) {
						if (map[j][i] == 1) {
							D[j].setIcon(iconW);
							D[j].setOpaque(true);
						} else if (map[j][i] == 0) {
							D[j].setIcon(iconB);
							D[j].setOpaque(true);
						} else if (map[j][i] == 2 && G_switch == true) {
							D[j].setIcon(iconG);
						}else{
							D[j].setIcon(null);
						}
					}
				} else if (i == 4) {
					for (int j = 0; j < 8; j++) {
						if (map[j][i] == 1) {
							E[j].setIcon(iconW);
							E[j].setOpaque(true);
						} else if (map[j][i] == 0) {
							E[j].setIcon(iconB);
							E[j].setOpaque(true);
						} else if (map[j][i] == 2 && G_switch == true) {
							E[j].setIcon(iconG);
						}else{
							E[j].setIcon(null);
						}
					}
				} else if (i == 5) {
					for (int j = 0; j < 8; j++) {
						if (map[j][i] == 1) {
							F[j].setIcon(iconW);
							F[j].setOpaque(true);
						} else if (map[j][i] == 0) {
							F[j].setIcon(iconB);
							F[j].setOpaque(true);
						} else if (map[j][i] == 2 && G_switch == true) {
							F[j].setIcon(iconG);
						}else{
							F[j].setIcon(null);
						}
					}
				} else if (i == 6) {
					for (int j = 0; j < 8; j++) {
						if (map[j][i] == 1) {
							G[j].setIcon(iconW);
							G[j].setOpaque(true);
						} else if (map[j][i] == 0) {
							G[j].setIcon(iconB);
							G[j].setOpaque(true);
						} else if (map[j][i] == 2 && G_switch == true) {
							G[j].setIcon(iconG);
						}else{
							G[j].setIcon(null);
						}
					}
				} else if (i == 7) {
					for (int j = 0; j < 8; j++) {
						if (map[j][i] == 1) {
							H[j].setIcon(iconW);
							H[j].setOpaque(true);
						} else if (map[j][i] == 0) {
							H[j].setIcon(iconB);
							H[j].setOpaque(true);
						} else if (map[j][i] == 2 && G_switch == true) {
							H[j].setIcon(iconG);
						}else{
							H[j].setIcon(null);
						}
					}
				}
			} // for

		}// castToBoard 将棋子投射到棋盘上
	}

	class Timer_count_down extends Thread {
		int time_limit;
		int time;
		Display display;

		Timer_count_down(int time_limit, Display display) {
			this.time_limit = time_limit;
			this.display = display;
		}

		public void run() {
			time = time_limit;
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					time = time - 1;
					l2.setText("Left time: " + String.valueOf(time));
					// time out の処理
					if (time_limit == 0) {
						System.out.println("Time out");

						new Result(1 - my_turn, client, display);
						try {
							transData end = new transData(36);
							ObjectOutputStream oos = new ObjectOutputStream(client.s.getOutputStream());
							end.set_endinfo_draw();
							try {
								oos.writeObject(end);
								oos.reset();
							} catch (Exception erro) {
								erro.printStackTrace();
							}
						} catch (Exception erro) {
							erro.printStackTrace();
						}

						this.cancel();
					}
				}
			}, 0, 1000);
		}

		public void reset() {
			time = time_limit;
		}
	}
} // Oserov4

// comment: 棋盘应该是横纵为偶数，并且初始化双方各两个子交叉排列在中心，只有旁边有棋子的时候才能下棋

/*
 * 映射机制：按钮映射到Map的实例上，Map的实例将按钮的状态（黑或者白）映射到棋盘上
 * 协议机制：Osero传送Object数据，根据Object的类型（isinstanceof()）来判断是什么数据然后读取数据
 */
// (GUI)button(send chess loction to the sever) - map -updata - map - GUI
// GUI(Login Room Osero) - client - sever - client - GUI(Login Room Osero)
