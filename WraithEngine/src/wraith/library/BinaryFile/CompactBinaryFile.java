package wraith.library.BinaryFile;

import java.io.File;
import java.net.URI;

@SuppressWarnings("serial")
public class CompactBinaryFile extends File{
	private byte[] binary;
	private int pos;
	private int subPos;
	private boolean reading = false;
	private boolean writing = false;
	public void read(){
		reading=true;
		writing=false;
		binary=BinaryFileUtil.readFile(this);
		pos=subPos=0;
	}
	public boolean nextBit(){
		if(!reading)throw new IllegalStateException("Not reading file!");
		if(hasFinished())throw new IllegalStateException("Iteration already finished!");
		boolean a = bitAt(binary[pos], subPos);
		subPos++;
		if(subPos==8){
			subPos=0;
			pos++;
		}
		return a;
	}
	private boolean bitAt(byte b, int pos){
		if(pos==0)return (b&128)==128;
		if(pos==1)return (b&64)==64;
		if(pos==2)return (b&32)==32;
		if(pos==3)return (b&16)==16;
		if(pos==4)return (b&8)==8;
		if(pos==5)return (b&4)==4;
		if(pos==6)return (b&2)==2;
		return (b&1)==1;
	}
	public long getNumber(int bits){
		if(!reading)throw new IllegalStateException("Not reading file!");
		if(bits<1)throw new IllegalArgumentException("Cannot return a number with less then 1 bit!");
		if(bits>64)throw new IllegalArgumentException("Cannot return a number with more then 64 bits!");
		long n = 0;
		for(int i = bits-1; i>=0; i--)if(nextBit())n+=power(i);
		return n;
	}
	private long power(int p){
		long a = 1;
		for(int i = 0; i<p; i++)a*=2;
		return a;
	}
	public void write(){
		reading=false;
		writing=true;
		binary=new byte[1];
		pos=subPos=0;
	}
	public void addBit(boolean bit){
		if(!writing)throw new IllegalStateException("Not writing file!");
		if(pos==binary.length){
			byte[] temp = new byte[binary.length+1];
			for(int i = 0; i<binary.length; i++)temp[i]=binary[i];
			binary=temp;
		}
		if(subPos==0)binary[pos]=bit?(byte)(binary[pos]|0b10000000):(byte)(binary[pos]&0b01111111);
		else if(subPos==1)binary[pos]=bit?(byte)(binary[pos]|0b01000000):(byte)(binary[pos]&0b10111111);
		else if(subPos==2)binary[pos]=bit?(byte)(binary[pos]|0b00100000):(byte)(binary[pos]&0b11011111);
		else if(subPos==3)binary[pos]=bit?(byte)(binary[pos]|0b00010000):(byte)(binary[pos]&0b11101111);
		else if(subPos==4)binary[pos]=bit?(byte)(binary[pos]|0b00001000):(byte)(binary[pos]&0b11110111);
		else if(subPos==5)binary[pos]=bit?(byte)(binary[pos]|0b00000100):(byte)(binary[pos]&0b11111011);
		else if(subPos==6)binary[pos]=bit?(byte)(binary[pos]|0b00000010):(byte)(binary[pos]&0b11111101);
		else binary[pos]=bit?(byte)(binary[pos]|0b00000001):(byte)(binary[pos]&0b11111110);
		subPos++;
		if(subPos==8){
			subPos=0;
			pos++;
		}
	}
	public void stopWriting(){
		if(!writing)throw new IllegalStateException("Not writing file!");
		BinaryFileUtil.writeFile(this, binary);
		writing=false;
		binary=null;
	}
	public long getLeft(){
		if(!reading)throw new IllegalStateException("Not reading file!");
		return binary.length*8-getPosition();
	}
	public void nextBits(boolean[] bits, int position, int length){
		if(!reading)throw new IllegalStateException("Not reading file!");
		for(int i = position; i<length+position; i++)bits[i]=nextBit();
	}
	public void resetIterator(){
		if(!reading||!writing)throw new IllegalStateException("Not editing file!");
		pos=subPos=0;
	}
	public boolean hasFinished(){
		if(!reading)throw new IllegalStateException("Not reading file!");
		return pos>=binary.length;
	}
	public void stopReading(){
		if(!reading)throw new IllegalStateException("Not reading file!");
		reading=false;
		binary=null;
	}
	public void addNumber(long number, int bits){
		if(!writing)throw new IllegalStateException("Not writing file!");
		if(bits<1)throw new IllegalArgumentException("Cannot write a number with less then 1 bit!");
		if(bits>64)throw new IllegalArgumentException("Cannot write a number with more then 64 bits!");
		long a;
		for(int i = bits-1; i>=0; i--){
			a=power(i);
			addBit((number&a)==a);
		}
	}
	public CompactBinaryFile(File parent, String child){ super(parent, child); }
	public CompactBinaryFile(String parent, String child){ super(parent, child); }
	public CompactBinaryFile(URI uri){ super(uri); }
	public CompactBinaryFile(String file){ super(file); }
	public long getPosition(){ return pos*8+subPos; }
	public boolean isReadingFile(){ return reading; }
	public boolean isWritingFile(){ return writing; }
}