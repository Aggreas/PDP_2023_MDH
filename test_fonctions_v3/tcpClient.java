import java.net.*;
import java.io.*;
import java.util.Scanner;

import javax.sound.sampled.SourceDataLine;

public class tcpClient {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        String hostName = "127.0.0.1";
        int portNumber = 65432;
        Scanner scanner;
        String line ;
        ObjectInputStream ois = null;
        try (
            Socket echoSocket = new Socket(hostName, portNumber);
            PrintWriter out =
                new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader in =
                new BufferedReader(
                    new InputStreamReader(echoSocket.getInputStream()));
        ) {
            while(true){
                scanner = new Scanner(System.in);
                line = scanner.nextLine();
                out.println(line);
                System.out.println("Sent message: "+line);
                if(line.compareTo("exit")==0){
                    scanner.close();
                    break;
                }
                ois = new ObjectInputStream(echoSocket.getInputStream());
                String message = (String) ois.readObject();
                System.out.println("Message : " + message);
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        }
    }
}

/*
 * if(line.compareTo("gps")==0){
                    System.out.println("1");
                    serv_response=in.readLine();
                    System.out.println(serv_response);
                }
 */