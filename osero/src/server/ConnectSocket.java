package server;
import java.io.*;
import java.net.*;
import java.util.*;
import transData.*;

public class ConnectSocket extends Thread{
    private boolean running = true;

    private int port;
    int port_send = 10100;

    public ConnectSocket(int port){
        this.port = port;
    }
    public int get_port(){
        return port;
    }

    public void run_inside(){
        System.out.println("Connect is running");

        ServerSocket ss = null;
        Socket s = null;

        try{
            ss = new ServerSocket(port);
            s = ss.accept();
            System.out.println("Connect socket accept");

            //  入出力ストリーム
            ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
            InputStream is = s.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);

            transData port_send_obj = new transData(85);
            port_send_obj.set_port_send(port_send);
            os.writeObject(port_send_obj);

        }catch(Exception e) {
            e.printStackTrace();

        }finally {
            try{
                if (s!=null){
                    s.close();
                }
                if (ss!=null){
                    ss.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            port_send = port_send + 100;
            if(port_send > 10800){
                port_send = 10100;
            }
            run_inside();
        }
    }


    public void run() {
//        while (running){
//            run_inside();
//        }
        run_inside();
    }
}
