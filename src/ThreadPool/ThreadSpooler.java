package ThreadPool;
import java.util.ArrayList;
import java.util.LinkedList;


public class ThreadSpooler extends Thread {
	/**
	 * @author Alex
	 *
	 * Threadpool der einzelne Objekte entgegenimmt und diese
	 * auf einer vorgegebenen Anzahl an Threads ausführt.
	 * 
	 * */
	
	ArrayList<PoolThread> threads = new ArrayList<PoolThread>();
	LinkedList<PoolObject> waiting = new LinkedList<PoolObject>();
	private boolean running;
	
	public ThreadSpooler(int count){
		/**
		 * Inezialisiert die Anzahl der Threads
		 * und startet den Spooler
		 * */
		for(int i=0; i<count; i++){
			threads.add(new PoolThread());
		}
		running = true;
		this.start();
	}
	
	public void push(PoolObject obj){
		/**
		 * pusht ein Objekt in den Spooler
		 * */
			waiting.add(obj);
	}
	
	public void close(){
		/**
		 * Beended alle Threads und den Spooler
		 * nachdem alle Objekte abgearbeitet sind
		 * */
		while(waiting.size()>0){
		timeout(100);	
		}
		running = false;
		for(PoolThread t: threads){
			t.finish();
		}
	}

	public void run(){
		/**
		 * Verwaltet alle Objekte die in den Spooler gepusht werden
		 * und führt diese falls Möglich in einem Thread aus.
		 * */
		while(running){
		for(PoolThread t: threads){
			if(waiting.size()<1)
				break;
			if(t.isbusy())
				continue;
			t.push(waiting.get(0));
			waiting.remove(0);
		}
		if(waiting.size() == 0)
			wakeup();
		timeout(5);
		}
	}
	
	private synchronized void timeout(int wait){
		/**
		 * Wartet eine Zeit lang
		 * */
		try {
			this.wait(wait);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void ready(){
		/**waits until the Pool is empty*/
		try {
			this.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private synchronized void wakeup(){
		/**wakes all Up who wait on the Spooler*/
		for(PoolThread t: threads){
			while(t.isbusy()){
				try {
					this.wait(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}}
		this.notifyAll();
	}
	
}
