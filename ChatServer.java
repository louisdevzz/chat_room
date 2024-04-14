package simplechat;
import java.io.*;
import java.net.*;
import java.util.TreeMap;

public class ChatServer {

    public static void main(String[] agrs){
        try {
            TreeMap<String,PrintWriter> all_client = new TreeMap<String,PrintWriter>();
            ServerSocket server = new ServerSocket(8080);
            while (true){
                Socket client = server.accept();
                new ClientHandling(client,all_client).start();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
