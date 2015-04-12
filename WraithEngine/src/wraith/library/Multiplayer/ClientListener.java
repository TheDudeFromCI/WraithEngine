package wraith.library.Multiplayer;

public interface ClientListener{
	public void unknownHost();
	public void couldNotConnect();
	public void recivedInput(String msg);
	public void serverClosed();
	public void disconnected();
	public void connectedToServer();
}