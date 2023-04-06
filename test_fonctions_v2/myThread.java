import java.io.BufferedReader;
import java.io.IOException;

public class myThread extends Thread  {
    String line;
    BufferedReader in;
    public myThread(String line, BufferedReader in){
        this.line=line;
        this.in=in;
    }

    @Override
    public void run(){
        // Code exécuté dans le thread
        while(true){
            try {
                line = in.readLine();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } 
		    System.out.println(line);
        }
    }



}