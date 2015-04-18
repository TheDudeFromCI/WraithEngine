package wraith.library.WindowUtil.GameWindow;

import java.util.ArrayList;
import java.util.Comparator;

public class GameThread{
	private Thread thread;
	private boolean isRunning = true;
	private final ArrayList<GameThreadTask> tasks = new ArrayList();
	private static final int TICKS_PER_SECOND = 20;
	private static final long TICK_LENGTH = 1000000000/TICKS_PER_SECOND;
	public GameThread(){
		thread=new Thread(new Runnable(){
			public void run(){
				try{
					int index;
					long time = System.nanoTime();
					long now = System.nanoTime();
					while(isRunning){
						while(now-time>TICK_LENGTH){
							synchronized(tasks){ for(index=0; index<tasks.size();)if(!tasks.get(index).update())index++; }
							time+=TICK_LENGTH;
						}
						while((now=System.nanoTime())-time<=TICK_LENGTH){
							Thread.yield();
							try{ Thread.sleep(1);
							}catch(Exception exception){}
						}
					}
				}catch(Exception exception){
					exception.printStackTrace();
					System.exit(1);
				}
			}
		});
		thread.setName("Game Thread");
		thread.setDaemon(false);
		thread.start();
	}
	public void addGameThreadTask(GameThreadTask task){
		synchronized(tasks){
			tasks.add(task);
			tasks.sort(new Comparator<GameThreadTask>(){
				public int compare(GameThreadTask a, GameThreadTask b){ return a.getPriority()==b.getPriority()?0:a.getPriority()>b.getPriority()?1:-1; }
			});
		}
	}
	public void kill(){ isRunning=false; }
}