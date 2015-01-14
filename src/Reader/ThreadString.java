package Reader;

public class ThreadString {
	private String[] str;
	
	public ThreadString(int size){
		str = new String[size];
	}
	
	public void addpart(int i, String s){
		str[i] = s;}
	
	public String getString(){
		StringBuffer b = new StringBuffer();
		for(String s: str)
			b.append(s);
		return b.toString();
	}
}
