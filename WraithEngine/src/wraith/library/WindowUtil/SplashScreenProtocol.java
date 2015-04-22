package wraith.library.WindowUtil;

import java.awt.Image;

public interface SplashScreenProtocol{
	public void showSplash();
	public void addCompletionListener(Runnable run);
	public void setTitle(String title);
	public void setIcon(Image icon);
}