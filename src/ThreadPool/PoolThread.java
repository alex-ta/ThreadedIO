package ThreadPool;

public class PoolThread extends Thread {
	/**
	 * @author Alex
	 * Thread der im Pool l�uft und die Methode 
	 * eines Objekts ausf�hrt. Dazu wird ein Objekt in den
	 * Thread gepusht. Der Thread f�hrt die Methode aus.
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
		/**�berpr�ft ob der Thread noch l�uft*/
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
		 * F�hrt ein Objekt aus und 
		 * solange der Thread l�uft
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
		 * L�sst den Thread auf ein Objekt warten
		 * */
		try {
			this.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private synchronized void disable(int time){
		/**
		 * L�sst den Thread eine bestimmte Zeit auf ein Objekt warten
		 * */
		try {
			this.wait(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private synchronized void enable(){
		/**l�sst den Thread ein neues Objekt verarbeiten*/
		this.notify();
	}
		
	
	
	public void finish(){
		/**Beended den aktuellen Thread wenn dieser fertig gearbeitet hat*/
		// k�nnte man auch �ber notify all machen
		while(busy){
			disable(100);
		}
		running = false;
		enable();
	}

	
}
