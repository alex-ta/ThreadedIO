package ThreadPool;

public class PoolThread extends Thread {
	/**
	 * @author Alex
	 * Thread der im Pool läuft und die Methode 
	 * eines Objekts ausführt. Dazu wird ein Objekt in den
	 * Thread gepusht. Der Thread führt die Methode aus.
	 * */
	PoolObject obj = null;
	
	private static int num =0;
	private int tnum;
	
	private boolean running;
	private boolean busy;
	public PoolThread(){
		/**
		 * Initalisiert den Thread
		 * und gibt ihm seine Nummer bzw ID.
		 * */
		tnum = num++;
		running = true;
		busy = false;
		this.start();
	}
	
	public boolean isbusy(){
		/**überprüft ob der Thread noch läuft*/
		return busy;
	}
	
	public void push(PoolObject obj){
		/**added eine Objekt in den Pool*/
		this.obj = obj;
		busy = true;
		enable();
	}
	
	public void run(){
		/**
		 * Führt ein Objekt aus und 
		 * solange der Thread läuft
		 * */
		while(running){
			if(obj != null)
				obj.threaded(tnum);
			obj = null;
			busy = false;
			disable();
		}	
	}
	
	private synchronized void disable(){
		/**
		 * Lässt den Thread auf ein Objekt warten
		 * */
		try {
			this.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private synchronized void disable(int time){
		/**
		 * Lässt den Thread eine bestimmte Zeit auf ein Objekt warten
		 * */
		try {
			this.wait(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private synchronized void enable(){
		/**lässt den Thread ein neues Objekt verarbeiten*/
		this.notify();
	}
		
	
	
	public void finish(){
		/**Beended den aktuellen Thread wenn dieser fertig gearbeitet hat*/
		// könnte man auch über notify all machen
		while(busy){
			disable(100);
		}
		running = false;
		enable();
	}

	
}
