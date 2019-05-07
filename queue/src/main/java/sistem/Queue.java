package sistem;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;



public class Queue implements Runnable{
	
	private BlockingQueue<Client> clients;
	private AtomicInteger waitingPeriod;
	private boolean run=true;
	@SuppressWarnings("unused")
	private int numberOfClients=0;

	
	public Queue(int size) {
		this.clients=new ArrayBlockingQueue<Client>(size);
		this.waitingPeriod=new AtomicInteger();//initializeaza la 0
	}

	public void addClient(Client newClient,int id) {
		@SuppressWarnings("unused")
		boolean status=clients.offer(newClient);//offer returneaza true daca s-a introdus,altfel false
		this.waitingPeriod.addAndGet(newClient.getProcessingPeriod());
		numberOfClients++;		
	}

	
	public void run() {
		@SuppressWarnings("unused")
		int processingTime = 0;
		while(run) {
			if(!clients.isEmpty()) {
				if(clients.peek()!=null) {
					 processingTime=clients.peek().getProcessingPeriod();
					while(clients.peek().getProcessingPeriod()>0) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}//*1000  deoarace trebuie in milisecunde
						clients.peek().setProcessingPeriod(clients.peek().getProcessingPeriod()-1);
						this.waitingPeriod.addAndGet(-1);
					}				
				}
					try {
						clients.take();
						numberOfClients--;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}

		}
	}

	public Client[] getClient() {
		Client[] cl=new Client[clients.size()];
		return clients.toArray(cl);
		
	}

	public BlockingQueue<Client> getClients() {
		return clients;
	}

	public AtomicInteger getWaitingPeriod() {
		return waitingPeriod;
	}

	public void setWaitingPeriod(AtomicInteger waitingPeriod) {
		this.waitingPeriod = waitingPeriod;
	}
	
	public String toString() {
		String s = "";
		for (Client c : this.clients) {
			if (c != this.clients.peek())
				s += "client waiting\n " ;
			else
				s += "the client is being served. Remaining time:" + c.getProcessingPeriod() + "\n";
		}
		s += "\nTOTAL WAIT:" + waitingPeriod.toString();
		return s;
	}
	

	
}

