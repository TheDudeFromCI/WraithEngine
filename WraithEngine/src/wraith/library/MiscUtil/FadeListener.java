package wraith.library.MiscUtil;

public interface FadeListener{
	public void onFadeOutTick();
	public void onFadeOutComplete();
	public void onFadeInTick();
	public void onFadeInComplete();
	public void onFadeStayTick();
	public void onFadeStayComplete();
	public void onComplete();
}