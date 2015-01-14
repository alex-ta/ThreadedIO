package Reader;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class Reader {

	private String path = null;
	private FileInputStream filein = null;
	private BufferedInputStream in = null;
	private ThreadString inmsg;
	private int indexes;
	private int step;
	
	public Reader(String path,int step) {
		this.step = step;
		indexes = (int) getFileSize(path)/step+1;
		inmsg = new ThreadString(indexes);
		try {
			createInput(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	private void createInput(String path) throws IOException{
		if(this.path == path)
			return;
		if(in != null)
			in.close();
		filein = new FileInputStream(path);
		in = new BufferedInputStream(filein);
	}
	
	private long getFileSize(String path){
		File f = new File(path);
		return f.length();
	}
	
	public int getIndexes(){
		return indexes;
	}

	public void close() {
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void read(long current){
		read(current,step);
	}
	
	private void read(final long current, final int add) {
		new Thread(){
			@Override
			public void run(){
				int start = (int) (add*current);
				byte[] b = new byte[add];
				try {
					in.skip(start);
					in.read(b, 0, (int) (add));
				} catch (IOException e) {
					e.printStackTrace();
				}
				inmsg.addpart((int)(current),new String(b));
			}
		}.start();
	}
	
	public String getString(){
		return inmsg.getString();
	}
	
}
