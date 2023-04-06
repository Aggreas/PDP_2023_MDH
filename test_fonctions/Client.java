
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
	private DataOutputStream out;
	private DataInputStream in;


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
			in = new DataInputStream( 
				new BufferedInputStream(socket.getInputStream())); 
			out = new DataOutputStream(socket.getOutputStream()); 
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

		// keep reading until "Stop" is input 
		while (!line.equals("exit")) 
		{ 
			try
			{ 
				line = input.readLine(); // reads the line from the keyboard
				if(line.equals("gps")){

					out.writeUTF(line); // writes it to the output stream
					// now we just need to collect the data  from the socket on our server
					line = in.readUTF(); 
					System.out.println(line);
				}
				else{
					out.writeUTF(line); // writes it to the output stream

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