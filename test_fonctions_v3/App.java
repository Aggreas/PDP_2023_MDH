import java.io.*;
import java.lang.Thread;
public class App {
    public static void main(String[] args){
        try{
        Client client = new Client("127.0.0.1", 6666);
        client.openConnection();
        Thread.sleep(500);
        client.arm();
        Thread.sleep(500);


        client.takeoff();
        Thread.sleep(500);

        client.land();
        Thread.sleep(500);

        client.disarm();
        Thread.sleep(500);

        client.closeConnection();
        Thread.sleep(500);

    }
    catch(Exception i)
    {
        System.out.println(i);
    }	
    }
}
