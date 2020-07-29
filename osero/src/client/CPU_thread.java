package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import transData.transData;

public class CPU_thread extends Thread{
	//Oserov4_cpu CPU_osero = null;
	Client client = null;
	
	public CPU_thread(Client client) {
		//this.CPU_osero = CPU_osero;
		this.client = client;
		//this.oos = oos;
		//this.ois = ois;
	}

	public void run() {
		transData r_data = null;
		try {
			// receive battle start notice 80
			System.out.println("B receive 80");
			r_data = (transData) client.ois.readObject();
			System.out.println("A receive 80");
		} catch (Exception e11) {
			e11.printStackTrace();
		} finally {

		}
		if(r_data instanceof transData) {
			if (r_data.get_protocol() == 80) {
				System.out.println("Stop CPU battle");
				client.oserov4_cpu.j.setVisible(false);
				System.out.println("Battle start!");
				client.CPUflag = true;
				new Oserov4(client, client.oos, client.ois);
			} else {
				System.out.println("cant receive start");
			}
		}
	
	}
		
		
}
