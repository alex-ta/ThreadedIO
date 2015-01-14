package ThreadPool;

public class MAIN {

	
	public static void main(String[] args){
		ThreadSpooler spool = new ThreadSpooler(5);		
		Execute e = new Execute();
		
		spool.push(e);
		spool.push(e);
		
		spool.push(e);
		spool.push(e);
		spool.push(e);
		spool.push(e);
		spool.push(e);
		spool.push(e);
		spool.push(e);
		spool.push(e);
		spool.push(e);
		
		
		spool.close();
	}
	
}

class Execute implements PoolObject{
		public Execute(){}
		

		@Override
		public void threaded(int num) {
			System.out.println("Hallo ich bin "+num);	
		}
	}

