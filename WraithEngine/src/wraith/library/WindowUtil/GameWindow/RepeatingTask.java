package wraith.library.WindowUtil.GameWindow;

public abstract class RepeatingTask implements GameThreadTask{
	private int tickDelay;
	private int between;
	private int steps;
	private int passed;
	private boolean running = true;
	public RepeatingTask(GameThread gameThread, int delay, int between){
		tickDelay=delay;
		this.between=between;
		gameThread.addGameThreadTask(this);
	}
	public boolean update(){
		if(!running)return true;
		passed=steps-tickDelay;
		if(passed>0&&passed%between==0)run();
		steps++;
		return false;
	}
	public void stop(){ running=false; }
	public abstract void run();
}