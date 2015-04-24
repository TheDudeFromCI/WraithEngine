package wraith.library.LWJGL;

public interface LoopObjective{
	public void preLoop();
	public void update(float delta, long time);
	public void render();
}