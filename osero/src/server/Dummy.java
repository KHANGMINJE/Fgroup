package server;
import java.io.*;
import java.net.*;
import java.util.*;
import transData.*;
public class Dummy extends TimerTask{

    private int port, num;

    public Dummy(int port, int num){
        this.port = port;
        this.num = num;
    }

    static int i = 1; // 実行回数
    static ServerSocket ss = null;
    static Socket s = null;
    static ObjectOutputStream os = null;
    static InputStream is = null;
    static ObjectInputStream ois = null;
    static transData dummy = new transData(35);

    public void connect(){
        System.out.println("Dummy [ " + String.valueOf(num) + " ] is running");


        try{
            ss = new ServerSocket(port);
            s = ss.accept();
            System.out.println("Dummy [ " + String.valueOf(num) + " ] accept");

            //  入出力ストリーム
            os = new ObjectOutputStream(s.getOutputStream());
            is = s.getInputStream();
            ois = new ObjectInputStream(is);




            i++;
        }catch (Exception e) {
            System.out.println("ERROR at : Dummy [ " + String.valueOf(num) + " ]");
            e.printStackTrace();

        }finally {

        }
    }


    public void run_inside(){
        try{

            os.writeObject(dummy);

            transData dummy_read =(transData)ois.readObject();

            i++;
        }catch (Exception e) {
            System.out.println("ERROR at : Dummy [ " + String.valueOf(num) + " ]");
            e.printStackTrace();
        }
    }

    public void run(){
        if (i==1){
            connect();
        }

        run_inside();
    }
}
