package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import client.Oserov4.Ban;
import transData.transData;

public class Reciever extends Thread{
	Client client =  null;
	Ban map = null;
	ObjectInputStream ois = null;
	
	public Reciever(Client client, Ban map, ObjectInputStream ois) {
		this.client = client;
		this.map = map;
		this.ois = ois;
	}
	@Override
	public void run (){
		transData r_data = null;
		try {
			System.out.println("receive start");
			r_data = (transData)ois.readObject();
			System.out.println("receive data");
		} catch (ClassNotFoundException | IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		if(r_data instanceof transData) {
				if (r_data.get_protocol()==3){
						System.out.println("opponent:row="+r_data.get_row()+",line="+r_data.get_line());
						//client.r_data = r_data;
						client.your_turn = 1;
						map.updateMap(r_data.get_row(), r_data.get_line(),1-client.turn);
						map.castToBoard();
						map.timeupdater();
				}else{
					System.out.println("no data");
				}
		}
		//run();
	}
}
