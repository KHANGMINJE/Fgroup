package client;
import java.net.*;
import java.util.*;
import transData.transData;
import java.io.*;
import transData.*;
public class Client {
	//atributions for Register_display
	private String register_name = "default";
	private String register_pass = "default";
	//atributions for Login_display
	private String login_name = "default";
	private String login_pass = "default";
	//atributions for Room selecting
	int room_num = 1;
	int room_port = 0;
	private int login_port = 0;
	
	Socket s = null;
	public ObjectOutputStream oos = null;
	public ObjectInputStream ois = null;
	
	int first_port = 10000;
	String ServerAddress = "localhost";
	int turn = 1;
	int your_turn = 0;
	String secret_ans = null;
	
	boolean CPUflag = false;
	Oserov4_cpu oserov4_cpu = null;
	
	transData r_data = null;
	Oserov4 osero = null;
	public  Client() {
	}
	public void setLoginPort(int login_port){
		this.login_port = login_port;
	}
	public int getLoginPort(){
		return login_port;
	}
	//M:TODO receive login-port(10100,10200,...,10800)
	public int FirstConnect(String ServerAddress, int first_port){
		Socket s = null;
		try{
			s = new Socket(ServerAddress, first_port);
			OutputStream os = s.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			InputStream is = s.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			transData login_port_obj = (transData)ois.readObject();
			//M:protocol-85-object is used to exchange port number
			if(login_port_obj.get_protocol()==85){
				login_port = login_port_obj.get_port();
			}else{
				System.out.println("Error(FirstConnect Method)1: can't receive login-port");
				System.exit(1);
			}
		}catch (Exception se){
			System.out.println("Error(FirstConnect Method)2: can't connect Server");
			se.printStackTrace();
			System.exit(2);
		}finally{
			try{
				s.close();
			}catch(Exception ser){
				System.out.println("Error(FirstConnect Method)3: can't close Socket");
				ser.printStackTrace();
			}finally {
			}
		}
		return login_port;
	}
	//method for
	public boolean send_login_info(String usr,String pass, ObjectInputStream ois, ObjectOutputStream oos) {
			boolean flag = false;
			// TODO send login info (name, pass) to server
			transData user = new transData(10);
			user.set_login_name_pass(usr,pass);
			try {
					oos.writeObject(user);
					transData answer = (transData)ois.readObject();
					String ans = answer.get_login_answer();
					if (ans.equals("login succeed")){
							// todo
							System.out.println("receive login success");
							flag = true;
					}else if (ans.equals("login failed : false password")){
							// todo reset pass
						System.out.println("receive login failed");
							flag = false;
					}else if (ans.equals("login failed : this name does not exist")){
							// todo reset name or name&pass
							flag = false;
					}
			}catch(Exception e){
					//e.printStackTrace();
			}finally {
					return flag;
			}
	}
	//method for Register_display
	
/*	public void send_room_num(int room_num){
		this.room_num = room_num;
		int port = 13;
		transData data = new transData(port);
		try{
			Socket socket = new Socket(ServerAddress,port);
			OutputStream os = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(data);
			oos.flush();
			socket.shutdownOutput();
			System.out.println("Send room number successfully");
		}catch(Exception erro){
			erro.printStackTrace();
			System.out.println("Send room number failed");
		}
	}*/
	public boolean send_register_name_pass(String register_name,String register_pass, String secret_ans,ObjectInputStream ois,ObjectOutputStream oos) {
		int protocol = 20;
		transData data = new transData(protocol);
		boolean flag=false;
		
		this.register_name = register_name;
		this.register_pass = register_pass;
		this.secret_ans = secret_ans;
		data.set_register_name_pass_question(register_name, register_pass, secret_ans);
		System.out.println("set the register data suceess");
		try {
			oos.writeObject(data);
			transData answer = (transData)ois.readObject();
			String ans = answer.get_login_answer();
			System.out.println("answer.get login answer : "+ans);
			if (ans.equals("register succeed")){
				// todo
				System.out.println("receive register succeed");
					flag = true;
			}else if (ans.equals("Register failed : this name is already used")){
					// todo reset pass
				System.out.println("receive register failed");
					flag = false;
			}
			System.out.println("Send register info successfully");
			return flag;
		}catch(Exception erro) {
			System.out.println("Send register info erro");
			erro.printStackTrace();
			return flag;
		}
	}
	public void choose_room(ObjectOutputStream oos, ObjectInputStream ois){
        int room_port=10011;
        Room room = null;
        Room.Display4 dis4 = null;
        try{
            System.out.println("room_obj start");
            //room info 受け取る
            transData room_obj = (transData)ois.readObject();
            System.out.println("room_obj end");
            Map<Integer, Integer> room_info = room_obj.get_room_info();
			System.out.println(room_info);
			room = new Room(this,room_info, oos, ois);
			
			//Scanner scan = new Scanner(System.in);
			//room_num = scan.nextInt();
            // todo chose room having 0or1 player
            //System.out.println(return_port.get_port());
			
			//Display4のactionlistenerによる書き換えまち
			//room_port =  room.getRoomPort();
			
			/*transData room_choice = new transData(13);
            room_choice.set_room_num(room_num);
            System.out.println("room choice="+room_choice.get_room_num());
            try {
				oos.writeObject(room_choice);
				System.out.println("sent room choice");
				transData return_port = (transData)ois.readObject();
				System.out.println("receive return port");
	            room_port = return_port.get_port();
			} catch (IOException | ClassNotFoundException e1) {
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
			}finally {
				
			}*/
        }catch(Exception e){
            //e.printStackTrace();
        }finally {
            //return room_port;
        }
	}
	
	public String send_wait_time(int time,ObjectInputStream ois,ObjectOutputStream oos){
		int protocal = 0;
		String result;
		transData data = new transData(protocal);
		data.set_wait_time(time);
		try{
			oos.writeObject(data);
			oos.reset();
			data = (transData)ois.readObject();
			result = data.get_result_time();
			return result;
		}catch(Exception e){
			e.printStackTrace();
		    result = "fail";
			return result;
			
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Client client = new Client();
		client.setLoginPort(client.FirstConnect(client.ServerAddress, client.first_port));
		new Login_display("Login",client);
		
		
	}
}