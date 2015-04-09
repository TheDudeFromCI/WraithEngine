package Test;

import java.io.PrintWriter;
import java.util.HashMap;
import wraith.library.Multiplayer.ClientInstance;
import wraith.library.Multiplayer.Server;
import wraith.library.Multiplayer.ServerListener;

public class MultiplayerTestServer{
	public static void main(String[] args){
		System.out.println("Server online.");
		final HashMap<ClientInstance,PrintWriter> map = new HashMap();
		ServerListener listener = new ServerListener(){
			public void clientConncted(ClientInstance client, PrintWriter out){
				System.out.println("Client connected: "+client);
				map.put(client, out);
			}
			public void clientDisconnected(ClientInstance client){
				System.out.println("Client disconnected: "+client);
				map.remove(client);
			}
			public void recivedInput(ClientInstance client, String msg){
				System.out.println(client+": "+msg);
				map.get(client).println("ECHO! "+msg);
			}
			public void serverClosed(){
				System.out.println("Server closed.");
				System.exit(0);
			}
		};
		Server server = new Server(10050, listener);
		System.out.println("Ip: "+server.getIp());
		while(true){
			try{ Thread.sleep(1000000);
			}catch(Exception exception){}
		}
	}
}