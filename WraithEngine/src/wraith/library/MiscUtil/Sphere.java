package wraith.library.MiscUtil;

public class Sphere{
	public float x, y, z, r;
	public Vector3 getCenter(){
		Vector3 v = new Vector3();
		v.x=x;
		v.y=y;
		v.z=z;
		return v;
	}
}