package wraith.library.Multiplayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client{
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private ClientListener clientListener;
	private boolean open = true;
	public Client(String ip, int port, ClientListener listener){
		clientListener=listener;
		try{
			socket=new Socket(ip, port);
			in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out=new PrintWriter(socket.getOutputStream(), true);
			Thread clientThread = new Thread(new Runnable(){
				public void run(){
					while(open){
						try{ clientListener.recivedInput(in.readLine());
						}catch(IOException exception){ clientListener.serverClosed();
						}catch(Exception exception){ exception.printStackTrace(); }
					}
				}
			});
			clientThread.setName("Client Connection");
			clientThread.setDaemon(true);
			clientThread.start();
			listener.connectedToServer();
		}catch(UnknownHostException exception){ listener.unknownHost();
		}catch(IOException exception){ listener.couldNotConnect();
		}catch(Exception exception){ exception.printStackTrace(); }
	}
	public void dispose(){
		try{
			open=false;
			socket.close();
			in.close();
			out.close();
			socket=null;
			in=null;
			out=null;
			clientListener.disconnected();
			clientListener=null;
		}catch(Exception exception){ exception.printStackTrace();}
	}
	public void send(String msg){ out.println(msg); }
}