package strategie;

import java.util.List;

import sistem.Client;
import sistem.Queue;

public interface Strategy {
	public void addClient(List<Queue> queues,Client c);
}

