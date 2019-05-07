package sistem;

import java.util.ArrayList;
import java.util.List;
import strategie.ConcreteStategyTime;
import strategie.ConcreteStrategyQueue;
import strategie.Strategy;

public class Scheduler {
	
	private List<Queue> queues=new ArrayList<Queue>();
	@SuppressWarnings("unused")
	private int maxNoQueues,maxClientsPerQueue;
	public int getMaxClientsPerQueue() {
		return maxClientsPerQueue;
	}
	
	private Strategy strategy;
	
	public enum SelectionPolicy{
		SHORTEST_QUEUE,SHORTEST_TIME
	}
	
	public Scheduler(int maxNoQueues,int maxClientsPerQueue) {
		this.maxNoQueues=maxNoQueues;
		this.maxClientsPerQueue=maxClientsPerQueue;
	}
	public void changeStrategy(SelectionPolicy policy) {
		if(policy==SelectionPolicy.SHORTEST_QUEUE) {
			strategy=new ConcreteStrategyQueue();
		}
		if(policy==SelectionPolicy.SHORTEST_TIME) {
			strategy=new ConcreteStategyTime();
		}
	}
	public void dispatchClient(Client c) {
		strategy.addClient(queues, c);
	}
	public void addNewQueue(Queue q) {
		this.queues.add(q);
	}
	public List<Queue> getQueues() {
		return queues;
	}




}
