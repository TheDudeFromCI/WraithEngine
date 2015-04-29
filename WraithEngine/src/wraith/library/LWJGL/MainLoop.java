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
	private GLFWMouseButtonCallback mouseButtonCallback;
	private GLFWCursorPosCallback cursorPosCallback;
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
			mouseButtonCallback.release();
			cursorPosCallback.release();
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
			public void invoke(long window, int key, int scancode, int action, int mods){ windowInitalizer.loopObjective.key(window, key, action); }
		});
		glfwSetMouseButtonCallback(window, mouseButtonCallback=new GLFWMouseButtonCallback(){
			public void invoke(long window, int button, int action, int mods){ windowInitalizer.loopObjective.mouse(window, button, action); }
		});
		glfwSetCursorPosCallback(window, cursorPosCallback=new GLFWCursorPosCallback(){
			public void invoke(long window, double xpos, double ypos){ windowInitalizer.loopObjective.mouseMove(window, xpos, ypos); }
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
		double lastTime = 0;
		double currentTime;
		double delta;
		windowInitalizer.loopObjective.preLoop();
		glEnable(GL_DEPTH_TEST);
		while(glfwWindowShouldClose(window)==GL_FALSE){
			glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
			currentTime=glfwGetTime();
			delta=currentTime-lastTime;
			lastTime=currentTime;
			glPushMatrix();
			windowInitalizer.loopObjective.update(delta, currentTime);
			windowInitalizer.loopObjective.render();
			glPopMatrix();
			glfwSwapBuffers(window);
			glfwPollEvents();
		}
	}
	public void dispose(){ glfwSetWindowShouldClose(window, GL_TRUE); }
	public long getWindow(){ return window; }
}