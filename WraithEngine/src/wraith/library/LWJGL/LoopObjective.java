package wraith.library.LWJGL;

public interface LoopObjective{
	public void preLoop();
	public void update(double delta, double time);
	public void render();
	public void key(long window, int key, int action);
	public void mouse(long window, int button, int action);
	public void mouseMove(long window, double xpos, double ypos);
}