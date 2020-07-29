import java.io.*;
import java.net.*;
import java.util.*;

public class Timer_manage_server extends Thread{

    int time;

    ObjectOutputStream os = null;

    Timer_manage_server(int time, ObjectOutputStream os){
        this.time = time;
        this.os = os;
    }


    public void run(){
        System.out.println("timer start");

        try {
            Thread.sleep(1000*time); // time[sec]
        } catch (InterruptedException e) {

        }

        transData time_over = new transData(2000);
        try{
            os.writeObject(time_over);

        }catch (Exception e){
            e.printStackTrace();
        }


        System.out.println("timer finish");
    }
}
