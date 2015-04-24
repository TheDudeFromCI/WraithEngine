package wraith.library.LWJGL;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import java.nio.ByteBuffer;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class MainLoop{
	private GLFWErrorCallback errorCallback;
	private GLFWKeyCallback keyCallback;
	private long window;
	private WindowInitalizer windowInitalizer;
	private WindowInitalizer recreateInitalizer;
	public void create(WindowInitalizer windowInitalizer){
		runLoop(windowInitalizer);
		while(recreateInitalizer!=null)runLoop(recreateInitalizer);
	}
	public void recreate(WindowInitalizer windowInitalizer){
		recreateInitalizer=windowInitalizer;
		dispose();
	}
	private void runLoop(WindowInitalizer windowInitalizer){
		this.windowInitalizer=windowInitalizer;
		recreateInitalizer=null;
		try{
			init();
			loop();
			glfwDestroyWindow(window);
			keyCallback.release();
		}finally{
			glfwTerminate();
			errorCallback.release();
			System.exit(0);
		}
	}
	private void init(){
		glfwSetErrorCallback(errorCallback=errorCallbackPrint(System.err));
		if(glfwInit()!=GL11.GL_TRUE)throw new IllegalStateException("Unable to initialize GLFW");
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, windowInitalizer.resizeable?GL_TRUE:GL_FALSE);
		window=glfwCreateWindow(windowInitalizer.width, windowInitalizer.height, windowInitalizer.windowName, windowInitalizer.fullscreen?glfwGetPrimaryMonitor():NULL, NULL);
		if(window==NULL)throw new RuntimeException("Failed to create the GLFW window");
		glfwSetKeyCallback(window, keyCallback=new GLFWKeyCallback(){
			@Override public void invoke(long window, int key, int scancode, int action, int mods){
				if(key==GLFW_KEY_ESCAPE&&action==GLFW_RELEASE)glfwSetWindowShouldClose(window, GL_TRUE);
			}
		});
		ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		if(!windowInitalizer.fullscreen)glfwSetWindowPos(window, (GLFWvidmode.width(vidmode)-windowInitalizer.width)/2, (GLFWvidmode.height(vidmode)-windowInitalizer.height)/2);
		glfwMakeContextCurrent(window);
		glfwSwapInterval(windowInitalizer.vSync?1:0);
		glfwShowWindow(window);
	}
	private void loop(){
		GLContext.createFromCurrent();
		glEnable(GL_DEPTH_TEST);
		glClearColor(windowInitalizer.clearRed, windowInitalizer.clearGreen, windowInitalizer.clearBlue, 0.0f);
		long lastTime = System.nanoTime();
		long currentTime;
		float delta;
		windowInitalizer.loopObjective.preLoop();
		glEnable(GL_DEPTH_TEST);
		while(glfwWindowShouldClose(window)==GL_FALSE){
			glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
			currentTime=System.nanoTime();
			delta=(currentTime-lastTime)/1000000000f;
			lastTime=currentTime;
			windowInitalizer.loopObjective.update(delta);
			glPushMatrix();
			windowInitalizer.loopObjective.render(delta);
			glPopMatrix();
			glfwSwapBuffers(window);
			glfwPollEvents();
		}
	}
	public void dispose(){ glfwSetWindowShouldClose(window, GL_TRUE); }
}