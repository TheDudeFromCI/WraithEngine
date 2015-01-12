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
}