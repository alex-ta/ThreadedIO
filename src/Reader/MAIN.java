package Reader;
import ThreadPool.PoolObject;
import ThreadPool.ThreadSpooler;


public class MAIN {
	
	public static void main(String[] args){
		//30730 <-> 11953 Mulitthreaded is ways faster than normal  117825
		ThreadSpooler spooler = new ThreadSpooler(6);
		String uri = "./PimeValues.txt";
		Reader read = new Reader(uri,5000);
		
		long time = System.currentTimeMillis();
		//String r ="";
		
		
		for(int i = 0; i<read.getIndexes(); i++){
			spooler.push(new Execute(i,read));
		}
		
		spooler.ready();
		
		System.out.println(System.currentTimeMillis()-time);
		spooler.close();
		//System.out.println(read.getString());
		read.close();
	}
}
	class Execute implements PoolObject{
		private long start;
		Reader r;
		
		public Execute(long start, Reader read){
		this.start = start;
		r = read;
		}
		
		@Override
		public void threaded(int num) {
			r.read(start);
		}
	}