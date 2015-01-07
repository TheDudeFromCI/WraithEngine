package wraith.library.File.BinaryFile;

/**
 * A helpful class designed to aid developers in creating bytes arrays from primitive objects.
 * @author TheDudeFromCI
 */
public class BinaryEncoder{
	private byte[] bytes = new byte[0];
	/**
	 * Adds a string of unstatic length to the end of the byte array. A short string doesn't use as many bytes to store the string's
	 * length, so trying to save a string with a length greater then 256 will through an error. If a longer string must be stored, use
	 * addLongString(), instead.
	 * @param a - A string, or list of strings to add.
	 * @throws IllegalArgumentException If a string longer then 256 characters is used.
	 * @return This BinaryEncoder, for chaining purposes.
	 */
	public BinaryEncoder addShortString(String... a){
		for(int i = 0; i<a.length; i++){
			if(a[i].isEmpty())continue;
			if(a[i].length()>256)throw new IllegalArgumentException("String is too long!");
			byte[] b = a[i].getBytes();
			addByteArray(new byte[]{(byte)(b.length-129)}, b);
		}
		return this;
	}
	/**
	 * Adds a string with a static length to the end of this byte array. No extra bytes are used to store the length of the string,
	 * so this information MUST be already known when you decode this byte array.
	 * @param a - A string, or list of strings to add.
	 * @return This BinaryEncoder, for chaining purposes.
	 */
	public BinaryEncoder addSetString(String... a){
		for(int i = 0; i<a.length; i++){
			if(a[i].isEmpty())continue;
			addByteArray(a[i].getBytes());
		}
		return this;
	}
	/**
	 * Adds a string of unstatic length to the end of the byte array. A long string uses more bytes to store the length of the string,
	 * so a string of any size can be safely used.
	 * @param a - A string, or list of strings to add.
	 * @return This BinaryEncoder, for chaining purposes.
	 */
	public BinaryEncoder addLongString(String... a){
		byte[] buf = new byte[4];
		for(int i = 0; i<a.length; i++){
			if(a[i].isEmpty())continue;
			byte[] b = a[i].getBytes();
			BinaryFileUtil.integerToByteArray(b.length, buf);
			addByteArray(buf, b);
		}
		return this;
	}
	/**
	 * Adds a long to the end of the byte array.
	 * @param a - A long, or list of longs to add.
	 * @return This BinaryEncoder, for chaining purposes.
	 */
	public BinaryEncoder addLong(long... a){
		byte[] buf = new byte[8];
		for(int i = 0; i<a.length; i++){
			BinaryFileUtil.longToByteArray(a[i], buf);
			addByteArray(buf);
		}
		return this;
	}
	/**
	 * Adds an integer to the end of the byte array.
	 * @param a - An integer, or list of integers to add.
	 * @return This BinaryEncoder, for chaining purposes.
	 */
	public BinaryEncoder addInt(int... a){
		byte[] buf = new byte[4];
		for(int i = 0; i<a.length; i++){
			BinaryFileUtil.integerToByteArray(a[i], buf);
			addByteArray(buf);
		}
		return this;
	}
	/**
	 * Adds a float to the end of the byte array.
	 * @param a - A float, or list of floats to add.
	 * @return This BinaryEncoder, for chaining purposes.
	 */
	public BinaryEncoder addFloat(float... a){
		for(int i = 0; i<a.length; i++)addByteArray(BinaryFileUtil.floatToByteArray(a[i]));
		return this;
	}
	/**
	 * Adds a new byte array to the end of this byte array. Please note that the length of the byte array is not stored. The bytes
	 * are simply added as is. If the length of the byte array must be stored, please addInt() to store the length of this array.
	 * @param a - A byte array, or list of byte arrays to add.
	 * @return This BinaryEncoder, for chaining purposes.
	 */
	public BinaryEncoder addByteArray(byte[]... a){
		int massLength = 0;
		for(byte[] b : a)massLength+=b.length;
		byte[] c = new byte[massLength];
		byte[] d;
		int index = 0;
		for(int i = 0; i<a.length; i++){
			d=a[i];
			for(int j = 0; j<d.length; j++)c[j+index]=d[j];
			index+=d.length;
		}
		bytes=c;
		return this;
	}
	/**
	 * Adds a byte to the end of the byte array.
	 * @param a - A byte, or list of bytes to add.
	 * @return This BinaryEncoder, for chaining purposes.
	 */
	public BinaryEncoder addByte(byte... a){
		addByteArray(a);
		return this;
	}
	/**
	 * Adds a boolean to the of the byte array. Note: because a boolean's required space is soo low, up to 8 bytes can be stored in
	 * a single byte. This method takes advantage of that. In other words, add multiple booleans as a parameter to this method will
	 * store them at at much higher density then calling the method multuple times, with fewer parameters.
	 * @param a - A boolean, or a list of booleans to add.
	 * @return
	 */
	public BinaryEncoder addBoolean(boolean... a){
		boolean[] bools = new boolean[8];
		for(int i = 0; i<a.length; i++){
			bools[i%8]=a[i];
			if(i%8==7){
				addByte(BinaryFileUtil.fromBooleanArray(bools));
				if(a.length%8!=0)for(int c = 0; c<bools.length; c++)bools[c]=false;
			}
		}
		if(a.length%8!=0)addByte(BinaryFileUtil.fromBooleanArray(bools));
		return this;
	}
	/**
	 * Gets the byte array at it's current state.
	 * @return The byte array.
	 */
	public byte[] get(){ return bytes; }
}