package simplechat;



import java.io.*;
import java.net.Socket;
import java.util.*;

import static java.lang.StringTemplate.STR;



public class ClientHandling extends Thread{
    private Socket socket;
    private TreeMap<String,PrintWriter> all_client_out;
    private String myname;
    private BufferedReader in;
    private PrintWriter out;
    public ClientHandling(Socket socket, TreeMap<String,PrintWriter> all_client_out){
        this.socket = socket;
        this.all_client_out = all_client_out;
        this.myname = "Unknow";
    }

    public void run(){
        try {
            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(
                    socket.getOutputStream()),true);
            out.println("Hello! This is the Java EchoSever. \nEnter BYE to exit.\nPlease enter your name: ");
            out.flush();
            String yourName = in.readLine();
            if(yourName.length()>2)
                if(this.all_client_out.get(yourName)!=null){
                    out.println("This name already existed!");
                }else{
                    all_client_out.put(yourName,out);
                    this.myname=yourName;
                    out.println(STR."Let's chat with name: \{yourName}");
                    out.flush();
                }
            out.println("Enter message: ");
            out.flush();
            String str=null;
            while ((str = in.readLine())!=null) {

                notifyAll(str);
                if (str.toUpperCase().trim().equals("BYE")) {
                    break;
                }
            }
            all_client_out.remove(yourName,out);
            in.close();
            out.close();
            socket.close();

        }catch (IOException e){
            e.printStackTrace();
        }

    }
    private void notifyAll(String msg){
        synchronized (all_client_out){
            for(Map.Entry<String,PrintWriter> out: all_client_out.entrySet()){
                if(out.getKey()!=this.myname){
                    out.getValue().println(STR."\{this.myname}: \{msg}");
                    out.getValue().flush();
                }
            }
        }
    }
}
