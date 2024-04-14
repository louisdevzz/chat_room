package simplechat;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientPrinter extends Thread{
    private BufferedReader in;
    private boolean stop;
    public ClientPrinter(BufferedReader in){
        this.in = in;
        this.stop = false;
    }

    public void run(){
        try {
            String str = null;
            while (!stop && (str = in.readLine())!= null)
                System.out.println(str);
            in.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void close(){
        stop = true;
    }
}
