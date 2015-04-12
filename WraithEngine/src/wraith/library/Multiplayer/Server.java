package wraith.library.Multiplayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Server{
	private int port;
	private boolean open = true;
	private ServerSocket ss;
	private ServerListener serverListener;
	private ArrayList<Socket> clients = new ArrayList<>();
	public Server(int port, ServerListener listener){
		serverListener=listener;
		try{
			ss=new ServerSocket(port);
			if(this.port==0)this.port=ss.getLocalPort();
			else this.port=port;
			Thread serverThread = new Thread(new Runnable(){
				public void run(){
					while(open){
						try{
							@SuppressWarnings("resource")final Socket s = ss.accept();
							Thread clientThread = new Thread(new Runnable(){
								public void run(){
									try{
										clients.add(s);
										BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
										PrintWriter out = new PrintWriter(s.getOutputStream(), true);
										ClientInstance client = new ClientInstance(s.getInetAddress(), s.getPort());
										serverListener.clientConncted(client, out);
										while(open){
											try{ serverListener.recivedInput(client, in.readLine());
											}catch(IOException e){
												serverListener.clientDisconnected(client);
												try{
													if(!s.isClosed()){
														s.shutdownOutput();
														s.close();
													}
												}catch(Exception exception){ exception.printStackTrace(); }
												clients.remove(s);
												return;
											}
										}
									}catch(Exception exception){ exception.printStackTrace(); }
									try{ s.close();
									}catch(Exception exception){ exception.printStackTrace(); }
									clients.remove(s);
								}
							});
							clientThread.setDaemon(true);
							clientThread.setName("Client "+s.getInetAddress().toString());
							clientThread.start();
						}catch(SocketException e){  //Do nothing
						}catch(IOException e){ e.printStackTrace(); }
					}
				}
			});
			serverThread.setDaemon(true);
			serverThread.setName("Server");
			serverThread.start();
		}catch(IOException e){ e.printStackTrace(); }
	}
	public void dispose(){
		open=false;
		try{ ss.close();
		}catch(IOException e){ e.printStackTrace(); }
		for(Socket s : clients){
			try{ s.close();
			}catch(Exception exception){ exception.printStackTrace(); }
		}
		clients.clear();
		clients=null;
		ss=null;
		serverListener.serverClosed();
		serverListener=null;
	}
	public String getIp(){
		try{ ss.getInetAddress();
		return InetAddress.getLocalHost().getHostAddress();
		}catch(UnknownHostException e){ e.printStackTrace(); }
		return null;
	}
	@SuppressWarnings("resource")public void kickClient(ClientInstance client){
		Socket s;
		for(int i = 0; i<clients.size(); i++){
			s=clients.get(i);
			if(client.ip==s.getInetAddress()&&s.getPort()==client.port){
				try{
					s.shutdownOutput();
					s.close();
				}catch(IOException e){ e.printStackTrace(); }
				return;
			}
		}
	}
}