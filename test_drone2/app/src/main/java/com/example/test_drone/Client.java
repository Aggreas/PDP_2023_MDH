package com.example.test_drone;

import android.os.AsyncTask;

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


	public boolean openConnection(Boolean isAdmin) {
		try {
			// Créer une nouvelle socket pour la connexion
			socket = new Socket();
			// Configurer la socket pour qu'elle se connecte à l'adresse et au port spécifiés
			socket.connect(new InetSocketAddress(this.address, this.port), 5000);
			// Créer un flux d'entrée pour la réception de données depuis le serveur
			input = new BufferedReader(new InputStreamReader(System.in));
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// Créer un flux de sortie pour l'envoi de données au serveur
			out = new PrintWriter(socket.getOutputStream(), true);
			// string to read message from input
			if(isAdmin){
				this.sendMessage("connexion par un admin");
			}
			else{
				this.sendMessage("connexion par un hackeur");
			}
			// La connexion a réussi
			return true;
		} catch (UnknownHostException e) {
			// L'adresse IP du serveur est inconnue
			e.printStackTrace();
		} catch (IOException e) {
			// Erreur lors de la création de la socket ou de la connexion au serveur
			e.printStackTrace();
		}
		// La connexion a échoué
		return false;
	}

	private void sendMessage(String message){
		new Thread(new Runnable() {
			@Override
			public void run() {
				out.println(message);
			}
		}).start();
	}

	public void closeConnection() {
		try{
			sendMessage("exit");
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
		this.sendMessage("arm");
	}

	public void disarm(){
		this.sendMessage("disarm");
	}

	public void takeoff(){
		this.sendMessage("takeoff");
	}

	public void land(){
		this.sendMessage("land");
	}

}
