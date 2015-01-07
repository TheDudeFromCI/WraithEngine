package wraith.library.File.BinaryFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;

/**
 * A class designed to offer several basic functions that are almost required to binary file read and writing.
 * @author TheDudeFromCI
 */
public class BinaryFileUtil{
	/**
	 * Reads a file, and returns all bytes from that file.
	 * @param file - The file to read.
	 * @return A byte array containing every byte from the file.
	 */
	public static byte[] readFile(File file){
		BufferedInputStream in = null;
		try{
			in=new BufferedInputStream(new FileInputStream(file));
			byte[] d = new byte[in.available()];
			in.read(d);
			return d;
		}catch(Exception exception){ exception.printStackTrace(); }
		finally{
			if(in!=null){
				try{ in.close();
				}catch(Exception exception){ exception.printStackTrace(); }
			}
		}
		return null;
	}
	/**
	 * Takes a byte array, or list of byte arrays, and writes them into a file. This method replaces any current data, with the new
	 * data provided by the byte array. Using a list of byte arrays will combine the bytes arrays into a single byte array as it writes
	 * them.
	 * @param file - The file to write in.
	 * @param data - The byte array, or list of byte arrays to write.
	 */
	public static void writeFile(File file, byte[]... data){
		if(data==null)return;
		BufferedOutputStream out = null;
		try{
			out=new BufferedOutputStream(new FileOutputStream(file));
			for(byte[] d : data)out.write(d);
		}catch(Exception exception){ exception.printStackTrace(); }
		finally{
			if(out!=null){
				try{ out.close();
				}catch(Exception exception){ exception.printStackTrace(); }
			}
		}
	}
	/**
	 * Converts an integer to a byte array with a length of 4.
	 * @param a - The integer to convert.
	 * @param buf - The byte array to store the integer in. This must be a length of at least 4.
	 */
	public static void integerToByteArray(int a, byte[] buf){
		buf[0]=(byte)((a>>24)&0xFF);
		buf[1]=(byte)((a>>16)&0xFF);
		buf[2]=(byte)((a>>8)&0xFF);
		buf[3]=(byte)(a&0xFF);
	}
	/**
	 * Converts an long to a byte array with a length of 8.
	 * @param a - The long to convert.
	 * @param buf - The byte array to store the integer in. This must be a length of at least 8.
	 */
	public static void longToByteArray(long a, byte[] buf){
		buf[0]=(byte)((a>>56)&0xFF);
		buf[1]=(byte)((a>>48)&0xFF);
		buf[2]=(byte)((a>>40)&0xFF);
		buf[3]=(byte)((a>>32)&0xFF);
		buf[4]=(byte)((a>>24)&0xFF);
		buf[5]=(byte)((a>>16)&0xFF);
		buf[6]=(byte)((a>>8)&0xFF);
		buf[7]=(byte)(a&0xFF);
	}
	/**
	 * Turns the requested byte into a binary based boolean array with a length of 8. Each digit of the binary code indicated
	 * each boolean in the array. For example, a binary value of 10011001 would return the values of "true, false, false, true,
	 * true, false, false, true."
	 * @param a - The byte to convert.
	 * @param buf - The boolean array to store the data in. Must be a length of at least 8.
	 */
	public static void toBooleanArray(byte a, boolean[] buf){
		buf[0]=(a&128)==128;
		buf[1]=(a&64)==64;
		buf[2]=(a&32)==32;
		buf[3]=(a&16)==16;
		buf[4]=(a&8)==8;
		buf[5]=(a&4)==4;
		buf[6]=(a&2)==2;
		buf[7]=(a&1)==1;
	}
	/**
	 * Converys a boolean array into a byte. Each boolean refers to a different digit in the byte's binary. For example, a boolean
	 * array of [true, true, true, false, false, false, true, false] would return a byte with the binary makeup of 11100010. (Or -98)
	 * @param a - The boolean array to convert. Must be a length of at least 8.
	 * @return The byte.
	 */
	public static byte fromBooleanArray(boolean[] a){
		byte b = 0;
		if(a[7])b+=1;
		if(a[6])b+=2;
		if(a[5])b+=4;
		if(a[4])b+=8;
		if(a[3])b+=16;
		if(a[2])b+=32;
		if(a[1])b+=64;
		if(a[0])b+=128;
		return b;
	}
	/**
	 * Combines multiple byte arrays into a single, longer byte array. Each byte array is appended to the end of the previous byte
	 * array. The arrays do not need to be the same length. An example: [1, 2, 3] and [4, 5] and [6, 7, 8, 9] would return the byte
	 * array of [1, 2, 3, 4, 5, 6, 7, 8, 9].
	 * @param a - The byte arrays to combine.
	 * @return The combined byte array.
	 */
	public static byte[] combineArrays(byte[]... a){
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
		return c;
	}
	private static String compileFolders(String... folders){
		if(folders.length==0)return "";
		String a = "";
		for(String s : folders){
			if(a.isEmpty())a=s;
			else a+=File.separatorChar+s;
		}
		return a+File.separatorChar;
	}
	/**
	 * A method to quickly get a file from inside a list of folders. If the file does not exist, it will create the file, and all
	 * the folders it rest in. Use it like: "getFile(FILE_NAME, TOP_MOST_FOLDER, FOLDER_INSIDE_THAT, FOLDER_INSIDE_PREVIOUS, etc.);"
	 * The folder path starts inside the folder that this program is currently running from. Not putting any folders will simply look
	 * for a file inside the exact same folder that this program is running from.
	 * @param name - The name of the file to look for.
	 * @param folders - The folders to check inside of. See above.
	 * @return The file.
	 */
	public static File getFile(String name, String... folders){
		File f = new File(compileFolders(folders)+name);
		if(!f.exists()){
			try{
				f.getParentFile().mkdirs();
				f.createNewFile();
			}catch(Exception exception){ exception.printStackTrace(); }
		}
		return f;
	}
	/**
	 * This method works exactly the same as writeFile(), seen above. However, instead of replacing current file data, it appends the
	 * bytes to the end of the file.
	 * @param f - The file to write in.
	 * @param data - The bytes to append.
	 */
	public static void append(File f, byte[]... data){
		if(data==null)return;
		BufferedOutputStream out = null;
		try{
			out=new BufferedOutputStream(new FileOutputStream(f, true));
			for(byte[] d : data)out.write(d);
		}catch(Exception exception){ exception.printStackTrace(); }
		finally{
			if(out!=null){
				try{ out.close();
				}catch(Exception exception){ exception.printStackTrace(); }
			}
		}
	}
	/**
	 * Converts a byte array to a float.
	 * @param b - The byte array. Must be a length of at least 4.
	 * @return The float.
	 */
	public static float byteArrayToFloat(byte[] b){ return ByteBuffer.wrap(b).getFloat(); }
	/**
	 * Converts a float to a byte array with a length of 4.
	 * @param f - The float.
	 * @return The byte array.
	 */
	public static byte[] floatToByteArray(float f){ return ByteBuffer.allocate(4).putFloat(f).array(); }
	/**
	 *Converts a byte array to an integer.
	 * @param b - The byte array. Must be a length of at least 4.
	 * @return The integer.
	 */
	public static int byteArrayToInteger(byte[] b){ return b[3]&0xFF|(b[2]&0xFF)<<8|(b[1]&0xFF)<<16|(b[0]&0xFF)<<24; }
	/**
	 * Converts a byte array to a long.
	 * @param b - The byte array. Must be a length of at least 8.
	 * @return The long.
	 */
	public static long byteArrayToLong(byte[] b){ return b[7]&0xFF|(b[6]&0xFF)<<8|(b[5]&0xFF)<<16|(b[4]&0xFF)<<24|(b[3]&0xFF)<<32L|(b[2]&0xFF)<<40L|(b[1]&0xFF)<<48L|(b[0]&0xFF)<<56L; }
}