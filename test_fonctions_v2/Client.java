
import java.net.*; 
import java.io.*; 
public class Client 
{ 
	// We initialize our socket( tunnel )
	// and our input reader and output stream
	// we will take the input from the user
	// and send it to the socket using output stream
	private Socket socket;
	private BufferedReader input;
	private PrintWriter out;
	private BufferedReader in;


	// constructor that takes the IP Address and the Port
	public Client(String address, int port) 
	{ 
		// we try to establish a connection 
		try
		{ 
			// creates a socket with the given information
			socket = new Socket(address, port); 
			System.out.println("Connected"); 

			// we 'ready' the input reader 
			input = new BufferedReader(new InputStreamReader(System.in));

			// and the output that is connected to the Socket
			in = 
				new BufferedReader(
                            new InputStreamReader(socket.getInputStream())); 
			out =
                new PrintWriter(socket.getOutputStream(), true);
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
		String line = ""; 
		Thread t = new myThread(line,in);
		t.start();
		// keep reading until "Stop" is input 
		while (true) 
		{ 
			try
			{ 

				line = input.readLine(); // reads the line from the keyboard
				out.println(line); // writes it to the output stream
				if(line.equals("exit")){
					break;
				}
			} 
			catch(IOException i) 
			{ 
				System.out.println(i); 
			} 
		} 

		// close the connection 
		try
		{ 
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

	public static void main(String args[]) 
	{ 
		Client client = new Client("127.0.0.1", 6666); 
	} 
} 