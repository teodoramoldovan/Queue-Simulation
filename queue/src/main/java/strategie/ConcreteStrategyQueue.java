package strategie;

import java.util.List;

import sistem.Client;
import sistem.Queue;

public class ConcreteStrategyQueue implements Strategy {
	
	public void addClient(List<Queue> queues, Client c) {
		// TODO Auto-generated method stub
		Queue selectedQueue = getShortestQueue(queues);
		selectedQueue.addClient(c, queues.indexOf(selectedQueue));
	}
	public Queue getShortestQueue(List<Queue> queues) {
		int shortest=queues.get(0).getClients().size();
		Queue shortQueue=queues.get(0);
		for(Queue q:queues) {
			if(shortest>q.getClients().size()) {
				shortest=q.getClients().size();
				shortQueue=q;
			}
		}
		return shortQueue;
	}
}
