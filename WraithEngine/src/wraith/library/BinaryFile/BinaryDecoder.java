package wraith.library.BinaryFile;

/**
 * A class designed to decode a byte array into more useable primitive objects.
 * @author TheDudeFromCI
 */
public class BinaryDecoder{
	private byte[] bytes;
	private int position;
	byte[] buf_4 = new byte[4];
	byte[] buf_8 = new byte[8];
	/**
	 * Gets the next 4 bytes and compresses them into an integer.
	 * @return The integer.
	 */
	public int getInt(){
		buf_4[0]=bytes[position];
		buf_4[1]=bytes[position+1];
		buf_4[2]=bytes[position+2];
		buf_4[3]=bytes[position+3];
		position+=4;
		return BinaryFileUtil.byteArrayToInteger(buf_4);
	}
	/**
	 * Gets the next 8 bytes and compresses them into a long.
	 * @return The long.
	 */
	public long getLong(){
		buf_8[0]=bytes[position];
		buf_8[1]=bytes[position+1];
		buf_8[2]=bytes[position+2];
		buf_8[3]=bytes[position+3];
		buf_8[4]=bytes[position+4];
		buf_8[5]=bytes[position+5];
		buf_8[6]=bytes[position+6];
		buf_8[7]=bytes[position+7];
		position+=8;
		return BinaryFileUtil.byteArrayToLong(buf_8);
	}
	/**
	 * Gets the next 4 bytes and compresses them into a float.
	 * @return The float.
	 */
	public float getFloat(){
		buf_4[0]=bytes[position];
		buf_4[1]=bytes[position+1];
		buf_4[2]=bytes[position+2];
		buf_4[3]=bytes[position+3];
		position+=4;
		return BinaryFileUtil.byteArrayToFloat(buf_4);
	}
	/**
	 * Gets the next byte.
	 * @return The byte.
	 */
	public byte getByte(){
		byte b = bytes[position];
		position++;
		return b;
	}
	/**
	 * Gets the next "short string." This means that the very next byte is used to determine the length of the string, (1-256), and
	 * that number of bytes following are turned into a string of that length.
	 * @return The string.
	 */
	public String getShortString(){
		int length = getByte();
		byte[] s = new byte[length];
		for(int i = 0; i<length; i++)s[i]=getByte();
		return new String(s);
	}
	/**
	 * Gets the next "long string." This means that the next 4 bytes are used to determine the length of the string, (1-Integer.MAX_VALUE),
	 * and that number of bytes following are turned into a string of that length.
	 * @return The string.
	 */
	public String getLongString(){
		int length = getInt();
		byte[] s = new byte[length];
		for(int i = 0; i<length; i++)s[i]=getByte();
		return new String(s);
	}
	/**
	 * Gets the next few bytes of the desired length, and turns them into a string of that length.
	 * @param length - The length of the string to try and create.
	 * @return The string.
	 */
	public String getSetString(int length){
		byte[] s = new byte[length];
		for(int i = 0; i<length; i++)s[i]=getByte();
		return new String(s);
	}
	/**
	 * Creates a byte array of the desired length.
	 * @param length - The length of the byte array to create.
	 * @return The byte array.
	 */
	public byte[] getByteArray(int length){
		byte[] a = new byte[length];
		for(int i = 0; i<length; i++)a[i]=getByte();
		return a;
	}
	/**
	 * Gets a boolean array of the requested length from the byte array. Because the value of a boolean is so low, 8 booleans can be
	 * stored inside a single byte. The method takes advantage of that fact, and only uses the needed number of bytes to form an array
	 * of the requested length. For example, a boolean[] of 5 would only use a single byte, because 5 <= 8. A boolean[] of 10, however,
	 * would use two bytes. A boolean[] of 64 would use 8 bytes, and so on. Note: The byte is consumed whether that entire byte is used,
	 * or not. In other words, calling this method twice, each with a length of 1, would use the next two bytes, instead of one.
	 * @param length - The length of the boolean array to crate.
	 * @return The boolean array.
	 */
	public boolean[] getBooleanArray(int length){
		boolean[] bools = new boolean[length];
		if(length==0)return bools;
		int byteCount = length/8+(length%8==0?0:1);
		boolean[] fromByte = new boolean[8];
		int index = 0;
		for(int i = 0; i<byteCount; i++){
			BinaryFileUtil.toBooleanArray(getByte(), fromByte);
			for(int a = index; a<length&&a<index+8; a++)bools[a]=fromByte[a-index];
			index+=8;
		}
		return bools;
	}
	/**
	 * Creates a new BinaryDecoder.
	 * @param bytes - The byte array that needs to be decoded.
	 */
	public BinaryDecoder(byte[] bytes){ this.bytes=bytes; }
	/**
	 * Resets the iterator back to the begining of the byte array.
	 */
	public void reset(){ position=0; }
	/**
	 * Gets the current position of the iterator.
	 * @return The position.
	 * 
	 */
	public int getPosition(){ return position; }
	/**
	 * Moves the iterator ahead this number of index's, without reading them.
	 * @param a - The number of bytes to skip.
	 */
	public void skip(int a){ position+=a; }
	/**
	 * Gets the length of this byte array.
	 * @return The length of the byte array.
	 */
	public int getLength(){ return bytes.length; }
	/**
	 * Gets the number of remaining bytes in the iterator.
	 * @return The number of remaining bytes.
	 */
	public int getLeft(){ return bytes.length-position; }
	/**
	 * Checks to see if the iterator is done or not.
	 * @return True if the iterator has reached, or surpassed, the end of the byte array.
	 */
	public boolean isDone(){ return position>=bytes.length; }
	/**
	 * Returns the original byte array passed to this decoder.
	 * @return The byte array.
	 */
	public byte[] getOriginalArray(){ return bytes; }
}