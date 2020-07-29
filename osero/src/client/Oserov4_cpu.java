package client;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
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

public class Oserov4_cpu extends JFrame implements ActionListener {
    /**
     * 
     */

    private static final long serialVersionUID = 1L;

    Container c;
    JLabel l1 = new JLabel("対戦相手" + " vs " + "相手の成績");
    JLabel l2 = new JLabel("残り時間");

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
    int time_limit = 30;
    boolean interuput = false;
    int CPU_switch = 1;
    boolean cpu_switch = true;
    
    boolean G_switch = true;

    Osero_setting_cpu osero_setting;
    ImageIcon iconB = new ImageIcon(getClass().getResource("00Black.jpg"));
    ImageIcon iconW = new ImageIcon(getClass().getResource("00White.jpg"));
    ImageIcon iconG = new ImageIcon(getClass().getResource("green.jpg"));
    static int turn = 0;// 初始化先手 黑色为0 白色为1
    Map map = new Map();
    HashMap<Integer, transData> hash = new HashMap<Integer, transData>();
    JFrame j = new JFrame();
   // Timer_count_down time_count_down = new Timer_count_down(time_limit, l2);

    public Oserov4_cpu(ObjectInputStream ois_room,ObjectOutputStream oos_room,int time) {
        this.time_limit = time;
        c = j.getContentPane();

        // j.setSize(800,600);
        j.setBounds(0, 0, 800, 600);
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
    
        int buttonSize = 46;
       // time_count_down.start();
        // Timer

        Timer chess_number_count = new Timer();
        chess_number_count.schedule(new TimerTask() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                l4.setText("Black:" + String.valueOf(map.count_black_chess()) + "vs" + "White:"
                        + String.valueOf(map.count_white_chess()));
            }
        }, 100, 100);

        // Timer finish
        for (int i = 0; i < 8; i++) {
            A[i] = new JButton();

            A[i].setBounds(28, 57 + i * 48, buttonSize, buttonSize);
            A[i].addActionListener(this);
            A[i].setOpaque(false);
            A[i].setContentAreaFilled(false);
        }
        for (int i = 0; i < 8; i++) {
            B[i] = new JButton();

            B[i].setBounds(77, 57 + i * 48, buttonSize, buttonSize);
            B[i].addActionListener(this);
            B[i].setOpaque(false);
            B[i].setContentAreaFilled(false);
        }

        for (int i = 0; i < 8; i++) {
            C[i] = new JButton();

            C[i].setBounds(126, 57 + i * 48, buttonSize, buttonSize);
            C[i].addActionListener(this);
            C[i].setOpaque(false);
            C[i].setContentAreaFilled(false);
        }
        for (int i = 0; i < 8; i++) {
            D[i] = new JButton();

            D[i].setBounds(172, 57 + i * 48, buttonSize, buttonSize);
            D[i].addActionListener(this);
            D[i].setOpaque(false);
            D[i].setContentAreaFilled(false);
        }
        for (int i = 0; i < 8; i++) {
            E[i] = new JButton();

            E[i].setBounds(220, 57 + i * 48, buttonSize, buttonSize);
            E[i].addActionListener(this);
            E[i].setOpaque(false);
            E[i].setContentAreaFilled(false);
        }
        for (int i = 0; i < 8; i++) {
            F[i] = new JButton();

            F[i].setBounds(268, 57 + i * 48, buttonSize, buttonSize);
            F[i].addActionListener(this);
            F[i].setOpaque(false);
            F[i].setContentAreaFilled(false);
        }
        for (int i = 0; i < 8; i++) {
            G[i] = new JButton();

            G[i].setBounds(314, 57 + i * 48, buttonSize, buttonSize);
            G[i].addActionListener(this);
            G[i].setOpaque(false);
            G[i].setContentAreaFilled(false);
        }
        for (int i = 0; i < 8; i++) {
            H[i] = new JButton();

            H[i].setBounds(360, 57 + i * 48, buttonSize, buttonSize);
            H[i].addActionListener(this);
            H[i].setOpaque(false);
            H[i].setContentAreaFilled(false);
        }

        c.add(l1);
        c.add(l2);

        c.add(l4);
        c.add(l5);
        c.add(b1);
        
        c.add(l6);
        c.add(r1);
        c.add(r2);
       
        j.getLayeredPane().add(chessboard, 100);

        for (int i = 0; i < 8; i++) {
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
        map.castToBoard(this);
        Data_reciever data_reciever = new Data_reciever(ois_room,cpu_switch);
        data_reciever.start();
        if(this.cpu_switch){
            this.dispose();
        }
    }   

    @Override
    public void actionPerformed(ActionEvent e) {
    	
    	boolean status1 = r1.isSelected();
        boolean status2 = r2.isSelected();
        
        
        if (e.getSource() == r1 || e.getSource() == r2) {
        	if(status1 == true) {
            	G_switch = true;
            }else if(status2 == true) {
            	G_switch = false;
            }
        	map.castToBoard(this);

        }
        
        /*
         * if(e.getSource()==A[0]) { System.out.println("A[0]"); try {
         * 
         * Socket s = new Socket("localhost",10301);
         * 
         * OutputStream os = s.getOutputStream(); ObjectOutputStream oos = new
         * ObjectOutputStream(os);
         * 
         * transData data =new transData(0,0); //hash.put(3,data);
         * oos.writeObject(data); oos.close(); s.close();
         * 
         * }catch(Exception erro) {erro.printStackTrace();} }
         */
        if (e.getSource() == b1) {
            System.out.println("open new window");
            osero_setting = new Osero_setting_cpu(this);

        }

        try {
            for (int i = 0; i < 8; i++) {
                if (e.getSource() == A[i]&&map.getMapValue(i, 0) == 2) {
                    map.updateMap(i, 0, turn);
                    
                    map.castToBoard(this);
                    turn = 1 - turn;
                    map.checkMap(turn);
                    matchCPU(turn);
                    turn = 1 - turn;
                    map.checkMap(0);
                    map.castToBoard(this);
                    //time_count_down.reset();

                } else if (e.getSource() == B[i]&&map.getMapValue(i, 1) == 2) {
                    map.updateMap(i, 1, turn);
                
                    map.castToBoard(this);
                    turn = 1 - turn;
                    map.checkMap(turn);
                    matchCPU(turn);
                    turn = 1 - turn;
                    map.checkMap(0);
                    map.castToBoard(this);
                    //time_count_down.reset();

                } else if (e.getSource() == C[i]&&map.getMapValue(i, 2) == 2) {
                    map.updateMap(i, 2, turn);
                  
                    map.castToBoard(this);
                    turn = 1 - turn;
                    map.checkMap(turn);
                    matchCPU(turn);
                    turn = 1 - turn;
                    map.checkMap(0);
                    map.castToBoard(this);
                    //time_count_down.reset();

                } else if (e.getSource() == D[i]&&map.getMapValue(i, 3) == 2) {
                    map.updateMap(i, 3, turn);
                   
                    map.castToBoard(this);
                    turn = 1 - turn;
                    map.checkMap(turn);
                    matchCPU(turn);
                    turn = 1 - turn;
                    map.checkMap(0);
                    map.castToBoard(this);
                   // time_count_down.reset();

                } else if (e.getSource() == E[i]&&map.getMapValue(i, 4) == 2) {
                    map.updateMap(i, 4, turn);
                   
                    map.castToBoard(this);
                    turn = 1 - turn;
                    map.checkMap(turn);
                    matchCPU(turn);
                    turn = 1 - turn;
                    map.checkMap(0);
                    map.castToBoard(this);
                   // time_count_down.reset();

                } else if (e.getSource() == F[i]&&map.getMapValue(i, 5) == 2) {
                    map.updateMap(i, 5, turn);
              
                    map.castToBoard(this);
                    turn = 1 - turn;
                    map.checkMap(turn);
                    matchCPU(turn);
                    turn = 1 - turn;
                    map.checkMap(0);
                    map.castToBoard(this);
                   // time_count_down.reset();

                } else if (e.getSource() == G[i]&&map.getMapValue(i, 6) == 2) {
                    map.updateMap(i, 6, turn);
                   
                    map.castToBoard(this);
                    turn = 1 - turn;
                    map.checkMap(turn);
                    matchCPU(turn);
                    turn = 1 - turn;
                    map.checkMap(0);
                    map.castToBoard(this);
                   // time_count_down.reset();

                } else if (e.getSource() == H[i]&&map.getMapValue(i, 7) == 2) {
                    map.updateMap(i, 7, turn);
                
                    map.castToBoard(this);
                    turn = 1 - turn;
                    map.checkMap(turn);
                    matchCPU(turn);
                    turn = 1 - turn;
                    map.checkMap(0);
                    map.castToBoard(this);
                   // time_count_down.reset();

                }

                if (isGameFinish() != 3) {
                    // 勝敗を表すディスプレイ//
                    System.out.println("Gameover");
                }
            }

        } catch (ArithmeticException pe) {
            System.out.println("you can't place here");
        } catch (Exception erro) {
            erro.printStackTrace();
        }

    }
  
    public int isGameFinish() {// ゲームが終了した:0or1or2[0=黒勝,1=白勝,2=引き分け],終了しない:3
        // 両者置ける場所が無くなったときにゲーム終了
        int b, b2, w, w2;
        map.checkMap(0);
        b = map.countNumber(0);// 黒石の個数
        b2 = map.countNumber(2);// 黒の置ける場所の数
        map.printMap();

        map.checkMap(1);
        w = map.countNumber(1);// 白石の個数
        w2 = map.countNumber(2);// 白の置ける場所の数
        map.printMap();

        map.checkMap(turn);// map[][]を元に戻しておく

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

    public void matchCPU(int t) {// t:CPUの手番[0=CPUが黒,1=CPUが白,2=CPUstop]
        // CPUとの対戦
        if (t == 0 || t == 1) {
            map.checkMap(t);
            int c = map.countNumber(2);// CPUが置ける場所の個数

            if (c > 0) {
                Random r = new Random();
                int p = r.nextInt(c);// 乱数で置く場所を一つ選ぶ
                int i, j, d = 0;
                for (i = 0; i < 8; i++) {
                    for (j = 0; j < 8; j++) {
                        if (map.map[i][j] == 2) {
                            if (p == d) {
                                System.out.println("CPU" + t + " put " + i + " " + j);
                                map.updateMap(i, j, t);// i,jに置く
                            }
                            d++;
                        }
                    }
                }
            } else {
                // パスする
                System.out.println("CPU pass");
            }
        } else {
        }

    }

    public class Map {
        int r;
        int l;
        int whiteChessNum;
        int blackChessNum;
        int[][] map = new int[8][8];

        public Map() {
            this.r = 8;
            this.l = 8;
        }
        public int getMapValue(int r, int l) {
			return map[r][l];
		}
        int count_white_chess() {
            this.whiteChessNum = 0;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (map[i][j] == 1) {
                        whiteChessNum = whiteChessNum + 1;
                    }
                }
            }
            return whiteChessNum;
        }

        int count_black_chess() {
            this.blackChessNum = 0;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (map[i][j] == 0) {
                        blackChessNum = blackChessNum + 1;
                    }
                }
            }
            return blackChessNum;
        }

        public void initMap() {
            for (int i = 0; i < 8; i++) {
                Arrays.fill(map[i], 3);
            }
            map[3][3] = 1;
            map[3][4] = 0;
            map[4][3] = 0;
            map[4][4] = 1;

            checkMap(0);
            printMap();
        }

        public void printMap() {
            System.out.println("====================");
            System.out.println(" | 0 1 2 3 4 5 6 7");
            System.out.println("-+------------------");
            for (int i = 0; i < 8; i++) {
                System.out.print(String.valueOf(i) + "| ");
                for (int j = 0; j < 8; j++) {
                    System.out.print(map[i][j]);
                    System.out.print(' ');
                }
                System.out.println();
            }
            System.out.println("====================");
        }

        public void updateMap(int r, int l, int p) throws ArithmeticException {

            if (map[r][l] != 2) {
                System.out.println("this place has been occupied");
                throw new ArithmeticException();
            } else if (p != 0 && p != 1) {
                System.out.println("invalid value");
            } else {
                map[r][l] = p;

                int target = 1 - p;

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
                    if (i >= 0) {
                        // System.out.println(i);
                        boolean turnFlag = false;
                        int tr, tl;
                        for (tr = r + dir[i][0], tl = l + dir[i][1]; (0 <= tr && tr < 8 && 0 <= tl
                                && tl < 8); tr += dir[i][0], tl += dir[i][1]) {// 琛屽垪銇�0锝�7銇瘎鍥层倰瓒呫亪銇亜銈堛亞銇�
                            // System.out.println("tr:"+tr+" tl:"+tl);
                            if (map[tr][tl] == target) {

                                turnFlag = true;
                            } else if (turnFlag) {

                                if (map[tr][tl] == p) {

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

                                break;
                            }
                        }
                    }
                }
            }

            checkMap(p);
            printMap();
        }

        public void checkMap(int t) {
            int j, k;
            for (j = 0; j < 8; j++) {
                for (k = 0; k < 8; k++) {

                    if (map[j][k] == 0 || map[j][k] == 1) {

                        continue;
                    } else {

                        map[j][k] = 3;
                    }
                    int target = 1 - t;
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
                        if (i >= 0) {
                            boolean turnFlag = false;
                            int tr, tl;
                            for (tr = j + dir[i][0], tl = k + dir[i][1]; (0 <= tr && tr < 8 && 0 <= tl
                                    && tl < 8); tr += dir[i][0], tl += dir[i][1]) {
                                if (map[tr][tl] == target) {

                                    turnFlag = true;
                                } else if (turnFlag) {

                                    if (map[tr][tl] == t) {
                                        map[j][k] = 2;
                                        break;
                                    } else {
                                        break;
                                    }
                                } else {

                                    break;
                                }
                            }

                        }
                    }
                }
            }
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

        public void castToBoard(Oserov4_cpu osero) {

            for (int i = 0; i < 8; i++) {
                if (i == 0) {
                    for (int j = 0; j < 8; j++) {
                        if (map[j][i] == 1) {
                            osero.A[j].setIcon(iconW);
                            osero.A[j].setOpaque(true);
                        } else if (map[j][i] == 0) {
                            osero.A[j].setIcon(iconB);
                            osero.A[j].setOpaque(true);
                        }
                        else if(map[j][i]==2 && G_switch == true){
							A[j].setIcon(iconG);
						}else{
							A[j].setIcon(null);
						}
                    }
                } else if (i == 1) {
                    for (int j = 0; j < 8; j++) {
                        if (map[j][i] == 1) {
                            osero.B[j].setIcon(iconW);
                            osero.B[j].setOpaque(true);
                        } else if (map[j][i] == 0) {
                            osero.B[j].setIcon(iconB);
                            osero.B[j].setOpaque(true);
                        }
                        else if(map[j][i]==2 && G_switch == true){
							B[j].setIcon(iconG);
						}else{
							B[j].setIcon(null);
						}
                    }
                } else if (i == 2) {
                    for (int j = 0; j < 8; j++) {
                        if (map[j][i] == 1) {
                            osero.C[j].setIcon(iconW);
                            osero.C[j].setOpaque(true);
                        } else if (map[j][i] == 0) {
                            osero.C[j].setIcon(iconB);
                            osero.C[j].setOpaque(true);
                        }
                        else if(map[j][i]==2 && G_switch == true){
							C[j].setIcon(iconG);
						}else{
							C[j].setIcon(null);
						}
                    }
                } else if (i == 3) {
                    for (int j = 0; j < 8; j++) {
                        if (map[j][i] == 1) {
                            osero.D[j].setIcon(iconW);
                            osero.D[j].setOpaque(true);
                        } else if (map[j][i] == 0) {
                            osero.D[j].setIcon(iconB);
                            osero.D[j].setOpaque(true);
                        }
                        else if(map[j][i]==2 && G_switch == true){
							D[j].setIcon(iconG);
						}else{
							D[j].setIcon(null);
						}
                    }
                } else if (i == 4) {
                    for (int j = 0; j < 8; j++) {
                        if (map[j][i] == 1) {
                            osero.E[j].setIcon(iconW);
                            osero.E[j].setOpaque(true);
                        } else if (map[j][i] == 0) {
                            osero.E[j].setIcon(iconB);
                            osero.E[j].setOpaque(true);
                        }
                        else if(map[j][i]==2 && G_switch == true){
							E[j].setIcon(iconG);
						}else{
							E[j].setIcon(null);
						}
                    }
                } else if (i == 5) {
                    for (int j = 0; j < 8; j++) {
                        if (map[j][i] == 1) {
                            osero.F[j].setIcon(iconW);
                            osero.F[j].setOpaque(true);
                        } else if (map[j][i] == 0) {
                            osero.F[j].setIcon(iconB);
                            osero.F[j].setOpaque(true);
                        }
                        else if(map[j][i]==2 && G_switch == true){
							F[j].setIcon(iconG);
						}else{
							F[j].setIcon(null);
						}
                    }
                } else if (i == 6) {
                    for (int j = 0; j < 8; j++) {
                        if (map[j][i] == 1) {
                            osero.G[j].setIcon(iconW);
                            osero.G[j].setOpaque(true);
                        } else if (map[j][i] == 0) {
                            osero.G[j].setIcon(iconB);
                            osero.G[j].setOpaque(true);
                        }
                        else if(map[j][i]==2 && G_switch == true){
							G[j].setIcon(iconG);
						}else{
							G[j].setIcon(null);
						}
                    }
                } else if (i == 7) {
                    for (int j = 0; j < 8; j++) {
                        if (map[j][i] == 1) {
                            osero.H[j].setIcon(iconW);
                            osero.H[j].setOpaque(true);
                        } else if (map[j][i] == 0) {
                            osero.H[j].setIcon(iconB);
                            osero.H[j].setOpaque(true);
                        }
                        else if(map[j][i]==2 && G_switch == true){
							H[j].setIcon(iconG);
						}else{
							H[j].setIcon(null);
						}
                    }
                }
            } // for

        }// castToBoard 将棋子投射到棋盘上
    }
    /*
     * class Iner_Thread extends Thread { Oserov4 osero;
     * 
     * Iner_Thread(Oserov4 osero) { this.osero = osero; }
     * 
     * @Override public void run() { new Osero_setting(osero); } }
     */

}

class Timer_count_down extends Thread {
    int time_limit;
    int time;
    JLabel l2;

    Timer_count_down(int time_limit, JLabel l2) {
        this.time_limit = time_limit;
        this.l2 = l2;
    }

    public void run() {
        time = time_limit;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                time = time - 1;
                l2.setText("Left time: " + String.valueOf(time));
                if (time_limit == 0) {
                    System.out.println("Time out");
                    cancel();
                }
            }
        }, 0, 1000);
    }

    public void reset() {
        time = time_limit;
    } 
} 
class Data_reciever extends Thread{
    ObjectInputStream ois_room =null;
    boolean cpu_switch;
     Data_reciever(ObjectInputStream ois_room,boolean cpu_switch){
        this.ois_room = ois_room;
        this.cpu_switch = cpu_switch;
    }
    public void run(){
        try{
            transData data = new transData(80);
            
            int f_time;
            data = (transData)ois_room.readObject();
            System.out.println("recieved battle start from server");
            if(data.get_protocol()==80){
               f_time = data.get_time();
               cpu_switch = true;
                new Room.Display6(f_time);
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
// Oserov4

// comment: 棋盘应该是横纵为偶数，并且初始化双方各两个子交叉排列在中心，只有旁边有棋子的时候才能下棋

/*
 * 映射机制：按钮映射到Map的实例上，Map的实例将按钮的状态（黑或者白）映射到棋盘上
 * 协议机制：Osero传送Object数据，根据Object的类型（isinstanceof()）来判断是什么数据然后读取数据
 */
// (GUI)button(send chess loction to the sever) - map -updata - map - GUI
// GUI(Login Room Osero) - client - sever - client - GUI(Login Room Osero)
