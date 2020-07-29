package transData;
import java.io.Serializable;
import java.util.*;

public class transData implements Serializable{
    /*
    client ---> server
    10 : login
    13 : room_choice
    20 : register
    36 : battle_end_info
    37 : change_pass
    50 : battle end

    server ---> client
    11 : login_answer
    12 : room_info
    80 : battle_start
    85 : port_send
    1000 : your turn notice

    client ---> server ---> client
    2 : time_choice
    3 : board_info

    share between server and client
    35 : dummy
    */


    private static final long serialVersionUID = 1L;

    int protocol=3;

    public transData(int protocol) {
        this.protocol = protocol;
    }
    public int get_protocol() {
        return protocol;
    }

    public void set_protocol(int p){
        this.protocol = p;
    }

    // your turn notice--------------
    // protocol = 1000
    // --------------your turn notice

    // dummy----------------------
    // protocol = 35
    // ----------------------dummy

    // time_over---------------------------
    // server ---> client
    // protocol = 2000
    // ---------------------------time_over


    // time_choice------------------------
    // client ---> server ---> client
    // protocol = 2
    private int time_choice=10; // sec
    public int get_time_choice(){
        return time_choice;
    }
    public void set_time_choice(int time_choice){
        this.time_choice = time_choice;
    }
    // ------------------------time_choice

    // board_info--------------------------
    // client ---> server ---> client
    // protocol = 3
    private int row=-1;
    private int line=-1;
    public int get_row() {
        return row;
    }
    public int get_line() {
        return line;
    }
    public void set_row(int row){
        this.row = row;
    }
    public void set_line(int line){
        this.line = line;
    }
    // --------------------------board_info


    // battle end---------------------------
    // client ---> server
    // protocol = 50
    private boolean battle_end;
    public boolean get_battle_end(){
        return battle_end;  // true when battle finished
    }
    public void set_battle_end(boolean battle_end){
        this.battle_end = battle_end;
    }
    //---------------------------battle end

    // room_info------------------------------
    // server ---> client
    // protocol = 12
    // room_info
    private Map<Integer,Integer> room_info = new HashMap<Integer, Integer>();
    private List<String> players_info = new ArrayList<String>();
    public Map<Integer, Integer> get_room_info(){
        return room_info;
    }
    public List<String> get_players_info(){
        return players_info;
    }
    public void set_room_info(Map<Integer, Integer> room_info, List<String> players_info){
        this.room_info = room_info;
        this.players_info = players_info;
    }
    // ------------------------------room_info

    // room_choice------------------------------
    // client ---> server
    // protocol = 13
    private int room_num;
    public int get_room_num(){
        return room_num;
    }
    public void set_room_num(int room_num){
        this.room_num = room_num;
    }
    // ------------------------------room_choice

    // port_send--------------------------
    // server ---> client
    // protocol = 85
    private int port_send;
    public int get_port(){
        return port_send;
    }
    public void set_port_send(int port_send){
        this.port_send = port_send;
    }
    // --------------------------port_send


    // battle_start--------------------------
    // server ---> client
    // protocol = 80
//    boolean first_turn = false;


    // --------------------------battle_start

    // login------------------------------
    // client ---> server
    // protocol = 10
    private String login_name="no set", login_pass="no set";
    public String get_login_name(){
        return login_name;
    }
    public String get_login_pass(){
        return login_pass;
    }
    public void set_login_name_pass(String login_name, String login_pass){
        this.login_name = login_name;
        this.login_pass = login_pass;
    }
    // ------------------------------login

    // login_answer--------------------------
    // server ---> client
    // protocol = 11
    private String login_answer="no set";
    public String get_login_answer(){
        return login_answer;
    }
    public void set_login_answer(String login_answer){
        this.login_answer = login_answer;
    }
    // --------------------------login_answer

    // register------------------------------
    // client ---> server
    // protocol = 20
    private String register_name="no set", register_pass="no set", secret_question="sogabe";
    public String get_register_name(){
        return register_name;
    }
    public String get_register_pass(){
        return register_pass;
    }
    public String get_register_secret_question(){
        return secret_question;
    }
    public void set_register_name_pass_question(String register_name, String register_pass, String secret_question){
        this.register_name = register_name;
        this.register_pass = register_pass;
        this.secret_question = secret_question;
    }
    // ------------------------------register


    // battle_end_info------------------------
    // client ---> server
    // protocol = 36
    private String winner, loser;
    boolean draw_flag = false; // if the battle end with draw ---> true
    public String get_winner(){
        return winner;
    }
    public String get_loser(){
        return loser;
    }
    public boolean get_draw_flag(){
        return draw_flag;
    }
    public void set_draw_result(String winner, String loser){
        this.draw_flag = true;
        this.winner = winner;
        this.loser = loser;
    }
    public void set_no_draw_result(String winner, String loser){
        this.winner = winner;
        this.loser = loser;
    }
    private boolean draw=false, win=false, lose=false;
    public void set_endinfo_draw(){
        this.draw = true;
    }
    public void set_endinfo_win(){
        this.win = true;
    }
    public void set_endinfo_lose(){
        this.lose = true;
    }
    public boolean get_endinfo_draw(){
        return draw;
    }
    public boolean get_endinfo_win(){
        return win;
    }
    public boolean get_endinfo_lose(){
        return lose;
    }
    // ------------------------battle_end_info

    // change_pass---------------------------
    // client ---> server
    // protocol = 37
    private String change_name="no set", change_pass="no set", change_question="sogabe";
    public String get_change_name(){
        return change_name;
    }
    public String get_change_pass(){
        return change_pass;
    }
    public String get_change_secret_question(){
        return change_question;
    }
    public void set_change_name_pass_question(String change_name, String change_pass, String change_question){
        this.change_name = change_name;
        this.change_pass = change_pass;
        this.change_question = change_question;
    }
    // ---------------------------change_pass
    
    //時間選択に使う
    private int wait_time = 0;
    public void set_wait_time(int time){
		this.wait_time = time;
    }
    private int result_time=0;
    public String get_result_time(){
        return String.valueOf(result_time);
    }

    //CPU対戦から出るのSWITCH
    private boolean cpu_switch = false;
    public boolean get_time_switch(){
        return cpu_switch;
    }
}