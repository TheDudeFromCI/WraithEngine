package wraith.library.MiscUtil;

public class BoundingBox{
	public float x1, y1, z1;
	public float x2, y2, z2;
	public Vector3 getMin(){
		Vector3 v = new Vector3();
		v.x=Math.min(x1, x2);
		v.y=Math.min(y1, y2);
		v.z=Math.min(z1, z2);
		return v;
	}
	public Vector3 getMax(){
		Vector3 v = new Vector3();
		v.x=Math.max(x1, x2);
		v.y=Math.max(y1, y2);
		v.z=Math.max(z1, z2);
		return v;
	}
}