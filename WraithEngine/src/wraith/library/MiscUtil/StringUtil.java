package wraith.library.MiscUtil;

public class StringUtil{
	public static String wrap(String str, int wrapLength, boolean wrapLongWords){
		if(str==null)return null;
		String newLineStr = "\n";
		if(wrapLength<1)wrapLength=1;
		int inputLineLength = str.length();
		int offset = 0;
		StringBuilder wrappedLine = new StringBuilder(inputLineLength+32);
		while(inputLineLength-offset>wrapLength){
			if(str.charAt(offset)==' '){
				offset++;
				continue;
			}
			int spaceToWrapAt = str.lastIndexOf(' ', wrapLength+offset);
			if(spaceToWrapAt>=offset){
				wrappedLine.append(str.substring(offset, spaceToWrapAt));
				wrappedLine.append(newLineStr);
				offset=spaceToWrapAt+1;
			}else{
				if(wrapLongWords){
					wrappedLine.append(str.substring(offset, wrapLength+offset));
					wrappedLine.append(newLineStr);
					offset+=wrapLength;
				}else{
					spaceToWrapAt=str.indexOf(' ', wrapLength+offset);
					if(spaceToWrapAt>=0){
						wrappedLine.append(str.substring(offset, spaceToWrapAt));
						wrappedLine.append(newLineStr);
						offset=spaceToWrapAt+1;
					}else{
						wrappedLine.append(str.substring(offset));
						offset=inputLineLength;
					}
				}
			}
		}
		wrappedLine.append(str.substring(offset));
		return wrappedLine.toString();
	}
	public static String arrayToString(Object array){
		if(array instanceof byte[])return arrayToString((byte[])array);
		if(array instanceof short[])return arrayToString((short[])array);
		if(array instanceof int[])return arrayToString((int[])array);
		if(array instanceof long[])return arrayToString((long[])array);
		if(array instanceof float[])return arrayToString((float[])array);
		if(array instanceof double[])return arrayToString((double[])array);
		if(array instanceof boolean[])return arrayToString((boolean[])array);
		if(array instanceof char[])return arrayToString((char[])array);
		return arrayToString((Object[])array);
	}
	private static String arrayToString(Object[] o){
		StringBuilder sb = new StringBuilder();
		String comma = ", ";
		String empty = "";
		sb.append("[");
		for(int i = 0; i<o.length; i++)sb.append(i>0?comma:empty).append(String.valueOf(o[i]));
		sb.append("]");
		return sb.toString();
	}
	private static String arrayToString(byte[] o){
		StringBuilder sb = new StringBuilder();
		String comma = ", ";
		String empty = "";
		sb.append("[");
		for(int i = 0; i<o.length; i++)sb.append(i>0?comma:empty).append(String.valueOf(o[i]));
		sb.append("]");
		return sb.toString();
	}
	private static String arrayToString(short[] o){
		StringBuilder sb = new StringBuilder();
		String comma = ", ";
		String empty = "";
		sb.append("[");
		for(int i = 0; i<o.length; i++)sb.append(i>0?comma:empty).append(String.valueOf(o[i]));
		sb.append("]");
		return sb.toString();
	}
	private static String arrayToString(int[] o){
		StringBuilder sb = new StringBuilder();
		String comma = ", ";
		String empty = "";
		sb.append("[");
		for(int i = 0; i<o.length; i++)sb.append(i>0?comma:empty).append(String.valueOf(o[i]));
		sb.append("]");
		return sb.toString();
	}
	private static String arrayToString(long[] o){
		StringBuilder sb = new StringBuilder();
		String comma = ", ";
		String empty = "";
		sb.append("[");
		for(int i = 0; i<o.length; i++)sb.append(i>0?comma:empty).append(String.valueOf(o[i]));
		sb.append("]");
		return sb.toString();
	}
	private static String arrayToString(float[] o){
		StringBuilder sb = new StringBuilder();
		String comma = ", ";
		String empty = "";
		sb.append("[");
		for(int i = 0; i<o.length; i++)sb.append(i>0?comma:empty).append(String.valueOf(o[i]));
		sb.append("]");
		return sb.toString();
	}
	private static String arrayToString(double[] o){
		StringBuilder sb = new StringBuilder();
		String comma = ", ";
		String empty = "";
		sb.append("[");
		for(int i = 0; i<o.length; i++)sb.append(i>0?comma:empty).append(String.valueOf(o[i]));
		sb.append("]");
		return sb.toString();
	}
	private static String arrayToString(boolean[] o){
		StringBuilder sb = new StringBuilder();
		String comma = ", ";
		String empty = "";
		sb.append("[");
		for(int i = 0; i<o.length; i++)sb.append(i>0?comma:empty).append(String.valueOf(o[i]));
		sb.append("]");
		return sb.toString();
	}
	private static String arrayToString(char[] o){
		StringBuilder sb = new StringBuilder();
		String comma = ", ";
		String empty = "";
		sb.append("[");
		for(int i = 0; i<o.length; i++)sb.append(i>0?comma:empty).append(String.valueOf(o[i]));
		sb.append("]");
		return sb.toString();
	}

}