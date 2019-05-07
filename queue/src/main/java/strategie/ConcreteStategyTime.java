package strategie;

import java.util.List;

import sistem.Client;
import sistem.Queue;


public class ConcreteStategyTime implements Strategy {

	public void addClient(List<Queue> queues, Client c) {
		// TODO Auto-generated method stub
		Queue selectedQueue = getMinTime(queues);
		selectedQueue.addClient(c,queues.indexOf(selectedQueue));
	}
	public Queue getMinTime(List<Queue> queues) {
		int min = queues.get(0).getWaitingPeriod().intValue();
		Queue minQueue = queues.get(0);
		for (Queue q:queues) {
			if (min > q.getWaitingPeriod().intValue()) {
				min = q.getWaitingPeriod().intValue();
				minQueue = q;
			}
		}
		return minQueue;
	}

}
