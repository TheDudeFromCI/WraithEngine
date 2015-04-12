package Test;

import java.util.Scanner;
import wraith.library.Multiplayer.Client;
import wraith.library.Multiplayer.ClientListener;

public class MultiplayerTestClient{
	public static void main(String[] args){
		System.out.println("Client online.");
		ClientListener listener = new ClientListener(){
			public void unknownHost(){
				System.err.println("Unknown host!");
				System.exit(1);
			}
			public void couldNotConnect(){
				System.err.println("Could not connect to server!");
				System.exit(1);
			}
			public void serverClosed(){
				System.out.println("Server closed.");
				System.exit(1);
			}
			public void disconnected(){
				System.out.println("Disconnected.");
				System.exit(1);
			}
			public void recivedInput(String msg){ System.out.println("Recived: "+msg); }
			public void connectedToServer(){ System.out.println("Connected."); }
		};
		@SuppressWarnings("resource")Scanner scan = new Scanner(System.in);
		System.out.println("Enter server ip.");
		Client c = new Client(scan.nextLine(), 10050, listener);
		while(true)c.send(scan.nextLine());
	}
}