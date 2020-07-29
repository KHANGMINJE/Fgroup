package server;

import java.io.*;
import java.net.*;
import java.util.*;
import transData.*;

public class Server {
    // room init
    static Map<Integer, Integer> room_info = new HashMap<Integer, Integer>() {
        {
            put(1, 0);
            put(2, 0);
            put(3, 0);
            put(4, 0);
        }
    };

    static String name_p1, name_p2, name_p3, name_p4, name_p5, name_p6, name_p7, name_p8;

    static String name_r1_1, name_r1_2, name_r2_1, name_r2_2, name_r3_1, name_r3_2, name_r4_1, name_r4_2;

    static int d_p_1 = 10510, d_p_2 = 10520, d_p_3 = 10530, d_p_4 = 10540, d_p_5 = 10550, d_p_6 = 10560, d_p_7 = 10570,
            d_p_8 = 10580;

    public static void connect() {
        int first_port = 10000, port_1 = 10100, port_2 = 10200, port_3 = 10300, port_4 = 10400, port_5 = 10500,
                port_6 = 10600, port_7 = 10700, port_8 = 10800;

        ConnectSocket cs = new ConnectSocket(first_port);
        Socket_thread t1 = new Socket_thread(port_1, 1);
        Socket_thread t2 = new Socket_thread(port_2, 2);
        Socket_thread t3 = new Socket_thread(port_3, 3);
        Socket_thread t4 = new Socket_thread(port_4, 4);
        Socket_thread t5 = new Socket_thread(port_5, 5);
        Socket_thread t6 = new Socket_thread(port_6, 6);
        Socket_thread t7 = new Socket_thread(port_7, 7);
        Socket_thread t8 = new Socket_thread(port_8, 8);

        cs.start();
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();
    }

    public static void room() {
        Room_server room_1 = new Room_server(10011, 10012, 1);
        Room_server room_2 = new Room_server(10021, 10022, 2);
        Room_server room_3 = new Room_server(10031, 10032, 3);
        Room_server room_4 = new Room_server(10041, 10042, 4);

        room_1.start();
        room_2.start();
        room_3.start();
        room_4.start();
    }

    public static boolean login(transData obj, ObjectOutputStream oos) {
        // ファイル読み込みで使用する３つのクラス
        FileInputStream fi = null;
        InputStreamReader is = null;
        BufferedReader br = null;

        String ans = null;

        try {
            // 読み込みファイルのインスタンス生成
            // ファイル名を指定する
            fi = new FileInputStream("csv/login.csv");
            is = new InputStreamReader(fi);
            br = new BufferedReader(is);

            // 読み込み行
            String line;

            // 列名を管理する為の配列
            String[] arr = null;

            // 1行ずつ読み込みを行う
            // 1行目から検索して名前が一致したらパスを確認
            // 一致したら "true" 違えば "false password" を返す
            int i = 0;
            while ((line = br.readLine()) != null) {
                if (i == 0) {
                    // System.out.println("line 1");
                } else {
                    arr = line.split(",");
                    // System.out.println(arr[0]);

                    if (arr[0].equals(obj.get_login_name())) {
                        if (arr[1].equals(obj.get_login_pass())) {
                            ans = "login succeed";
                            break;

                        } else {
                            ans = "login failed : false password";
                            break;
                        }
                    }
                }
                i++;
            }
            if (ans == null) {
                ans = "login failed : this name does not exist";
            }

            // 送信用インスタンス作成
            transData login_ans = new transData(11);
            login_ans.set_login_answer(ans);

            // send
            // ObjectOutputStream os = new ObjectOutputStream (s.getOutputStream ());
            oos.writeObject(login_ans);
            // os.flush();

            System.out.println(login_ans.get_login_answer());
            System.out.println("login_ans sent");

            // if(ans.equals("login succeed")){
            // return true;
            // }else{
            // return false;
            // }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (ans.equals("login succeed")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean change_pass(transData obj, ObjectOutputStream oos) {
        // ファイル読み込みで使用する３つのクラス
        FileInputStream fi = null;
        InputStreamReader is = null;
        BufferedReader br = null;

        FileInputStream fi_2 = null;
        InputStreamReader is_2 = null;
        BufferedReader br_2 = null;

        String ans = null;

        try {
            // 読み込みファイルのインスタンス生成
            // ファイル名を指定する
            fi = new FileInputStream("csv/login.csv");
            is = new InputStreamReader(fi);
            br = new BufferedReader(is);

            FileWriter fw = new FileWriter("csv/login_tmp.csv");
            PrintWriter pw = new PrintWriter(new BufferedWriter(fw));

            // 読み込み行
            String line;

            // 列名を管理する為の配列
            String[] arr = null;

            // 1行ずつ読み込みを行う
            // 1行目から検索して名前が一致したらパスを確認
            // 一致したら "true" 違えば "false password" を返す
            int i = 0;
            while ((line = br.readLine()) != null) {
                if (i == 0) {
                    pw.println(line);
                    // System.out.println("line 1");
                } else {
                    arr = line.split(",");
                    // System.out.println(arr[0]);

                    if (arr[0].equals(obj.get_change_name())) {
                        if (arr[2].equals(obj.get_change_secret_question())) {
                            // todo 認証成功 パスワード変更
                            ans = "change succeed";

                            pw.print(arr[0] + ",");
                            pw.print(obj.get_change_pass() + ",");
                            pw.print(arr[2] + ",");
                            pw.println();

                            // break;
                        } else {
                            ans = "change failed : false question answer";
                            pw.println(line);
                            // break;
                        }
                    } else {
                        pw.println(line);
                    }
                }
                i++;
            }
            if (ans == null) {
                ans = "change failed : this name does not exist";
            }

            pw.close();
            br.close();

            // 書き換えたテンプファイルで上書き
            fi_2 = new FileInputStream("csv/login_tmp.csv");
            is_2 = new InputStreamReader(fi_2);
            br_2 = new BufferedReader(is_2);

            FileWriter fw_2 = new FileWriter("csv/login.csv");
            PrintWriter pw_2 = new PrintWriter(new BufferedWriter(fw_2));

            String line_2;
            while ((line_2 = br_2.readLine()) != null) {
                pw_2.println(line_2);
            }
            pw_2.close();
            br_2.close();

            // 送信用インスタンス作成
            transData change_ans = new transData(11);
            change_ans.set_login_answer(ans);

            // send
            oos.writeObject(change_ans);
            // oos.flush();

            System.out.println(change_ans.get_login_answer());
            System.out.println("change done");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

        if (ans.equals("change succeed")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean register(transData obj, ObjectOutputStream oos) {
        FileInputStream fi = null;
        InputStreamReader is = null;
        BufferedReader br = null;

        FileInputStream fi_2 = null;
        InputStreamReader is_2 = null;
        BufferedReader br_2 = null;

        String ans = null;
        try {

            // 同じ名前のユーザがいないか判定
            boolean ok_flag = true;
            fi = new FileInputStream("csv/login.csv");
            is = new InputStreamReader(fi);
            br = new BufferedReader(is);
            String line;
            String[] arr = null;
            int i = 0;
            while ((line = br.readLine()) != null) {
                if (i == 0) {
                    // System.out.println("line 1");
                } else {
                    arr = line.split(",");
                    // System.out.println(arr[0]);

                    if (arr[0].equals(obj.get_register_name())) {
                        ok_flag = false;
                    }
                }
                i++;
            }

            if (ok_flag == true) {
                FileWriter f = new FileWriter("csv/login.csv", true);
                PrintWriter p = new PrintWriter(new BufferedWriter(f));

                p.print(obj.get_register_name());
                p.print(",");
                p.print(obj.get_register_pass());
                p.print(",");
                p.print(obj.get_register_secret_question());
                p.println();

                p.close();

                // record
                FileWriter f_2 = new FileWriter("csv/record.csv", true);
                PrintWriter p_2 = new PrintWriter(new BufferedWriter(f_2));

                p_2.print(obj.get_register_name());
                p_2.print(",0,0,0,0");

                p_2.println();

                p_2.close();

                ans = "register succeed";
                System.out.println(ans);

            } else {

                ans = "Register failed : this name is already used";
                System.out.println(ans);
            }

            // 送信用インスタンス作成
            transData register_ans = new transData(11);
            register_ans.set_login_answer(ans);

            // send
            oos.writeObject(register_ans);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (ans.equals("register succeed")) {
                return true;
            } else {
                return false;
            }
        } // updata 7.20 コンパイラが”｝”をつける方がいいと提示した、一応つけた。
    }

    public static void manage() {
        Manage m = new Manage();

        m.start();

    }

    public static void dummy() {
        Timer time_1 = new Timer();
        Timer time_2 = new Timer();
        Timer time_3 = new Timer();
        Timer time_4 = new Timer();
        Timer time_5 = new Timer();
        Timer time_6 = new Timer();
        Timer time_7 = new Timer();
        Timer time_8 = new Timer();

        time_1.scheduleAtFixedRate(new Dummy(d_p_1, 1), 100, 1000);
        time_2.scheduleAtFixedRate(new Dummy(d_p_2, 2), 100, 1000);
        time_3.scheduleAtFixedRate(new Dummy(d_p_3, 3), 100, 1000);
        time_4.scheduleAtFixedRate(new Dummy(d_p_4, 4), 100, 1000);
        time_5.scheduleAtFixedRate(new Dummy(d_p_5, 5), 100, 1000);
        time_6.scheduleAtFixedRate(new Dummy(d_p_6, 6), 100, 1000);
        time_7.scheduleAtFixedRate(new Dummy(d_p_7, 7), 100, 1000);
        time_8.scheduleAtFixedRate(new Dummy(d_p_8, 8), 100, 1000);

    }

    public static void update_record(transData obj) {
        FileInputStream fi = null;
        InputStreamReader is = null;
        BufferedReader br = null;

        FileInputStream fi_2 = null;
        InputStreamReader is_2 = null;
        BufferedReader br_2 = null;

        try {
            fi = new FileInputStream("csv/record.csv");
            is = new InputStreamReader(fi);
            br = new BufferedReader(is);

            FileWriter fw = new FileWriter("csv/record_tmp.csv");
            PrintWriter pw = new PrintWriter(new BufferedWriter(fw));

            if (obj.get_draw_flag()) {
                // todo when draw
                String line;
                String[] arr = null;
                int i = 0;
                while ((line = br.readLine()) != null) {
                    if (i == 0) {
                        pw.println(line);
                        // System.out.println("line 1");
                    } else {
                        arr = line.split(",");
                        // System.out.println(arr[0]);

                        if (arr[0].equals(obj.get_winner()) || arr[0].equals(obj.get_loser())) {
                            int draw = Integer.parseInt(arr[3]) + 1;

                            pw.print(arr[0] + ",");
                            pw.print(arr[1] + ",");
                            pw.print(arr[2] + ",");
                            pw.print(String.valueOf(draw) + ",");
                            pw.print(arr[4]);
                            pw.println();
                        } else {
                            pw.println(line);
                        }
                    }
                    i++;
                }
            } else {
                // todo when not draw

                String line;
                String[] arr = null;
                int i = 0;
                while ((line = br.readLine()) != null) {
                    if (i == 0) {
                        pw.println(line);
                        // System.out.println("line 1");
                    } else {
                        arr = line.split(",");
                        // System.out.println(arr[0]);

                        if (arr[0].equals(obj.get_winner())) {
                            // winner
                            int win = Integer.parseInt(arr[1]) + 1;
                            int rate = win * 10 - Integer.parseInt(arr[2]) * 5;
                            if (rate < 0)
                                rate = 0;

                            pw.print(arr[0] + ",");
                            pw.print(String.valueOf(win) + ",");
                            pw.print(arr[2] + ",");
                            pw.print(arr[3] + ",");
                            pw.print(String.valueOf(rate) + ",");
                            pw.println();
                        } else if (arr[0].equals(obj.get_loser())) {
                            // loser
                            int lose = Integer.parseInt(arr[2]) + 1;
                            int rate = Integer.parseInt(arr[1]) * 10 - lose * 5;
                            if (rate < 0)
                                rate = 0;

                            pw.print(arr[0] + ",");
                            pw.print(arr[1] + ",");
                            pw.print(String.valueOf(lose) + ",");
                            pw.print(arr[3] + ",");
                            pw.print(String.valueOf(rate));
                            pw.println();
                        } else {
                            pw.println(line);
                        }
                    }
                    i++;
                }
            }

            pw.close();
            br.close();

            // 書き換えたテンプファイルで上書き
            fi_2 = new FileInputStream("csv/record_tmp.csv");
            is_2 = new InputStreamReader(fi_2);
            br_2 = new BufferedReader(is_2);

            FileWriter fw_2 = new FileWriter("csv/record.csv");
            PrintWriter pw_2 = new PrintWriter(new BufferedWriter(fw_2));

            String line_2;
            while ((line_2 = br_2.readLine()) != null) {
                pw_2.println(line_2);
            }
            pw_2.close();
            br_2.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public static List get_record() {
        // ファイル読み込みで使用する３つのクラス
        FileInputStream fi = null;
        InputStreamReader is = null;
        BufferedReader br = null;

        List<String> strList = new ArrayList<String>();

        String rec_11 = "", rec_12 = "", rec_21 = "", rec_22 = "", rec_31 = "", rec_32 = "", rec_41 = "", rec_42 = "";

        if (name_r1_1 == null) {
            rec_11 = "room 1-1 : empty";
        }
        if (name_r1_2 == null) {
            rec_12 = "room 1-2 : empty";
        }
        if (name_r2_1 == null) {
            rec_21 = "room 2-1 : empty";
        }
        if (name_r2_2 == null) {
            rec_22 = "room 2-2 : empty";
        }
        if (name_r3_1 == null) {
            rec_31 = "room 3-1 : empty";
        }
        if (name_r3_2 == null) {
            rec_32 = "room 3-2 : empty";
        }
        if (name_r4_1 == null) {
            rec_41 = "room 4-1 : empty";
        }
        if (name_r4_2 == null) {
            rec_42 = "room 4-2 : empty";
        }

        try {
            // 読み込みファイルのインスタンス生成
            // ファイル名を指定する
            fi = new FileInputStream("csv/record.csv");
            is = new InputStreamReader(fi);
            br = new BufferedReader(is);

            // 読み込み行
            String line;

            // 列名を管理する為の配列
            String[] arr = null;

            // 1行ずつ読み込みを行う
            // 1行目から検索して名前が一致したらパスを確認
            // 一致したら "true" 違えば "false password" を返す
            int i = 0;
            while ((line = br.readLine()) != null) {
                if (i == 0) {
                    // System.out.println("line 1");
                } else {
                    arr = line.split(",");
                    // System.out.println(arr[0]);

                    if (name_r1_1 != null && arr[0].equals(name_r1_1)) {
                        rec_11 = "room 1-1 : name:" + name_r1_1 + ", win:" + arr[1] + ", lose:" + arr[2] + ", draw:"
                                + arr[3] + ", rate:" + arr[4];
                    } else if (name_r1_2 != null && arr[0].equals(name_r1_2)) {
                        rec_12 = "room 1-2 : name:" + name_r1_2 + ", win:" + arr[1] + ", lose:" + arr[2] + ", draw:"
                                + arr[3] + ", rate:" + arr[4];
                    } else if (name_r2_1 != null && arr[0].equals(name_r2_1)) {
                        rec_21 = "room 2-1 : name:" + name_r2_1 + ", win:" + arr[1] + ", lose:" + arr[2] + ", draw:"
                                + arr[3] + ", rate:" + arr[4];
                    } else if (name_r2_2 != null && arr[0].equals(name_r2_2)) {
                        rec_22 = "room 2-2 : name:" + name_r2_2 + ", win:" + arr[1] + ", lose:" + arr[2] + ", draw:"
                                + arr[3] + ", rate:" + arr[4];
                    } else if (name_r3_1 != null && arr[0].equals(name_r3_1)) {
                        rec_31 = "room 3-1 : name:" + name_r3_1 + ", win:" + arr[1] + ", lose:" + arr[2] + ", draw:"
                                + arr[3] + ", rate:" + arr[4];
                    } else if (name_r3_2 != null && arr[0].equals(name_r3_2)) {
                        rec_32 = "room 3-2 : name:" + name_r3_2 + ", win:" + arr[1] + ", lose:" + arr[2] + ", draw:"
                                + arr[3] + ", rate:" + arr[4];
                    } else if (name_r4_1 != null && arr[0].equals(name_r4_1)) {
                        rec_41 = "room 4-1 : name:" + name_r4_1 + ", win:" + arr[1] + ", lose:" + arr[2] + ", draw:"
                                + arr[3] + ", rate:" + arr[4];
                    } else if (name_r4_2 != null && arr[0].equals(name_r4_2)) {
                        rec_42 = "room 4-2 : name:" + name_r4_2 + ", win:" + arr[1] + ", lose:" + arr[2] + ", draw:"
                                + arr[3] + ", rate:" + arr[4];
                    }
                }
                i++;
            }

            strList.add(rec_11);
            strList.add(rec_12);
            strList.add(rec_21);
            strList.add(rec_22);
            strList.add(rec_31);
            strList.add(rec_32);
            strList.add(rec_41);
            strList.add(rec_42);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return strList;
    }

    public static void main(String[] args) {
        System.out.println("main start--------------------");

        connect();

        // dummy();

        room();

        manage();

        System.out.println("--------------------main close");
    }
}
