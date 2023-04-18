
import java.net.*;
import java.io.*;

public class Client {
	private String address;
	private int port;

	public Client(String address, int port){
		this.address = address;
		this.port = port;
	}

	// We initialize our socket( tunnel )
	// and our input reader and output stream
	// we will take the input from the user
	// and send it to the socket using output stream
	private Socket socket;
	private BufferedReader input;
	private PrintWriter out;
	private BufferedReader in;

	public void openConnection(){
			// we try to establish a connection 
		try
		{ 
			// creates a socket with the given information
			socket = new Socket(this.address, this.port); 
			System.out.println("Connected"); 

			// we 'ready' the input reader 
			input = new BufferedReader(new InputStreamReader(System.in));

			// and the output that is connected to the Socket
			in = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
			out = new PrintWriter(socket.getOutputStream(), true);
		} 
		catch(UnknownHostException u) 
		{ 
			System.out.println(u); 
		} 
		catch(IOException i) 
		{ 
			System.out.println(i); 
		} 

		// string to read message from input 
		out.println("connexion"); // writes it to the output stream

	}

	public void closeConnection() throws Exception{
		try{
			out.println("exit");
			input.close();
			out.close();
			in.close();
			socket.close();
		}
		catch(IOException i)
		{
			System.out.println(i);
		}	
	}
	
	public void arm(){
		out.println("arm");
	}

	public void disarm(){
		out.println("disarm");
	}

	public void takeoff(){
		out.println("takeoff");
	}

	public void land(){
		out.println("land");
	}
}
