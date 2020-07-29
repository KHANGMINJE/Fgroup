package server;
import java.io.*;
import java.net.*;
import java.util.*;
import transData.*;
public class Room_server extends Thread{
    private boolean running = true;
    private int port_1, port_2, room_num;

    private String name_p1, name_p2;

    private int time;

    public Room_server(int port_1, int port_2, int room_num) {

        this.port_1 = port_1;
        this.port_2 = port_2;
        this.room_num = room_num;
    }
    public void run_inside(){

    }
    public void run() {
        System.out.println("Room [ " + String.valueOf(room_num) + " ] is running");
        try{
            ServerSocket ss_1 = new ServerSocket(port_1);
            ServerSocket ss_2 = new ServerSocket(port_2);

            Socket s_1 = ss_1.accept();
            System.out.println("Room [ " + String.valueOf(room_num) + " ] : socket [1] accept");

            //  入出力ストリーム 1
            ObjectOutputStream os_1 = new ObjectOutputStream(s_1.getOutputStream());
            InputStream is_1 = s_1.getInputStream();
            ObjectInputStream ois_1 = new ObjectInputStream(is_1);

            Socket s_2 = ss_2.accept();
            System.out.println("Room [ " + String.valueOf(room_num) + " ] : socket [2] accept");

            //  入出力ストリーム 2
            ObjectOutputStream os_2 = new ObjectOutputStream(s_2.getOutputStream());
            InputStream is_2 = s_2.getInputStream();
            ObjectInputStream ois_2 = new ObjectInputStream(is_2);

            // 対局スタート通知
            transData battle_start = new transData(80);
            os_1.writeObject(battle_start);
            os_2.writeObject(battle_start);

            int first_turn = 1;// 1 or 2
            boolean battle_end = true;
            if (first_turn==1){
                // 1 が先手の場合

                transData start = new transData(1000);
                os_1.writeObject(start);
                System.out.println("send --start-- to player1");
                transData koukou = new transData(1200);
                os_2.writeObject(koukou);
                os_2.flush();
                System.out.println("send --turn-- to player2");

                while (battle_end) {

                    Timer_manage_server timer_1 = new Timer_manage_server(time, os_1);
                    timer_1.start();

                    transData data_1 = (transData) ois_1.readObject();

                    timer_1.stop();

                    System.out.println("read Object");
                    System.out.println(data_1.get_protocol());
                    if (data_1 instanceof transData) {
                        if (data_1.get_protocol() == 3 || data_1.get_protocol() == 3000) {

                            os_2.writeObject(data_1);
														System.out.println("send Object to 2");
                        }else if(data_1.get_protocol()==50){
                            battle_end = data_1.get_battle_end();
                           // Server.update_record();
                        }
                    }


                    if (!battle_end) {
                        break;
                    }

                    // os_2.writeObject(start);
                    // System.out.println("send --start-- to player2");

                    Timer_manage_server timer_2 = new Timer_manage_server(time, os_2);
                    timer_2.start();

                    transData data_2 = (transData) ois_2.readObject();

                    timer_2.stop();

                    if (data_2 instanceof transData) {
                        if (data_2.get_protocol() == 3 || data_1.get_protocol() == 3000) {

                            os_1.writeObject(data_2);
														System.out.println("send Object to 1");
                        }else if(data_2.get_protocol()==50){
                            battle_end = data_2.get_battle_end();
                          //  Server.update_record();
                        }
                    }
                }
            }else{
                // 2 が先手の場合

                while (battle_end) {

                    Timer_manage_server timer_2 = new Timer_manage_server(time, os_2);
                    timer_2.start();

                    transData data_2 = (transData) ois_2.readObject();

                    timer_2.stop();

                    if (data_2 instanceof transData) {
                        if (data_2.get_protocol() == 3) {

                            os_1.writeObject(data_2);
                        }else if(data_2.get_protocol()==50){
                            battle_end = data_2.get_battle_end();
                           // Server.update_record();
                        }
                    }


                    if (battle_end)
                        break;

                    Timer_manage_server timer_1 = new Timer_manage_server(time, os_1);
                    timer_1.start();

                    transData data_1 = (transData) ois_1.readObject();

                    timer_1.stop();

                    if (data_1 instanceof transData) {
                        if (data_1.get_protocol() == 3) {

                            os_1.writeObject(data_1);
                        }else if(data_1.get_protocol()==50){
                            battle_end = data_1.get_battle_end();
                           // Server.update_record();
                        }
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
        }
//        while (running){
//            run_inside();
//        }
    }
}
