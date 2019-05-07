package simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import sistem.Client;
import sistem.Queue;
import sistem.Scheduler;
import sistem.Scheduler.SelectionPolicy;
import tema2_simulatingqueue.Interfata;

public class SimulationManager implements Runnable {
	public int timeLimit;
	public int maxProcessingTime,minProcessingTime,numberOfQueues,numberOfClients;
	public SelectionPolicy selectionPolicy=SelectionPolicy.SHORTEST_TIME;
	private Scheduler scheduler;
	private List<Client> generatedClients=new ArrayList<Client>();
	private Random rand=new Random();
	private Interfata gui;
	private int i=0,currentTime=0,longest=0;
	private Integer emptyTime=0,peakHour=0,totalWaitingTime=0,allWaitingTime=0,waitingTimePerSecond,totalWaitingForInterval=0,
	 averageWaitingTime=0,totalProcessingTime=0,totalClientsProcessed=0,averageProcessingTime=0;
	
	public SimulationManager(Interfata g,int nrQ,int nrC,int procMin,int procMax,int simTime) {
		this.gui=g;
		this.maxProcessingTime=procMax;
		this.minProcessingTime=procMin;
		this.timeLimit=simTime;
		this.numberOfQueues=nrQ;
		this.numberOfClients=nrC;
		scheduler=new Scheduler(3,20);
		scheduler.changeStrategy(selectionPolicy);
		for(int i=0;i<this.numberOfQueues;i++) {
			Queue q=new Queue(15);
			scheduler.addNewQueue(q);
			Thread t=new Thread(q);
			t.start();	
		}
		for(int j=0;j<this.numberOfClients;j++) {
			Client c=generateNRandomClients();
			generatedClients.add(c);
		}
		sortare();		
	}
	public void setSelectionPolicy(SelectionPolicy selectionPolicy) {
		this.selectionPolicy = selectionPolicy;
	}
	public Client generateNRandomClients() {
		int arrTime=rand.nextInt(timeLimit-1)+1;
		int proTime=rand.nextInt(maxProcessingTime)+minProcessingTime;
		Client c=new Client(proTime,arrTime);
		return c;
	}
	public void afisare() {
		for(Client c:generatedClients) {
			i++;
			System.out.println(i+".Client: ArrTime: "+c.getArrivalTime()+" ProcessTime: "+c.getProcessingPeriod());
		}
	}
	public void peak() {
		allWaitingTime=0;
		for(Queue q:scheduler.getQueues()) {//calculeaza peak hour;daca timpul total de asteptare(suma timpului de asteptare
											//de la toate cozile e mai mare decat cea precedenta atunci peakHour e timpul curent
			allWaitingTime+=q.getWaitingPeriod().intValue();
			if(allWaitingTime>totalWaitingTime) {
				totalWaitingTime=allWaitingTime;
				peakHour=currentTime;
			}
		}
	}
	public void averageWait(){
		waitingTimePerSecond=0;
		for(Queue q:scheduler.getQueues()) {
			waitingTimePerSecond+=q.getWaitingPeriod().intValue()/((q.getClients().size()==0)?1:q.getClients().size());
																						//timpul de asteptare e suma 
																						//timpiilor de asteptare la fiecare
																						//coada impartiti la nr de clienti 
																						//in acel moment
			totalWaitingForInterval+=waitingTimePerSecond;
		}
	}
	public void empty() {
		for(Queue q:scheduler.getQueues()) {
			if(q.getClients().isEmpty()) {
				emptyTime++;
			}
		}
	}
	public void run() {
		while(currentTime<(timeLimit+longest)) {//proceseaza clienti pana se termina toate coziile
			for(int i=0;i<generatedClients.size();i++) {
				Client c=generatedClients.get(i);
				if(c.getArrivalTime()==currentTime) {
					if(currentTime<timeLimit) {//adauga clienti la coada numai pana la limita de timp
						scheduler.dispatchClient(c);
						totalProcessingTime+=c.getProcessingPeriod();
						totalClientsProcessed++;
						generatedClients.remove(c);
					}
				}		
			}
			peak();
			averageWait();
			empty();
			currentTime++;
			if(currentTime==timeLimit) {//daca se atinge limita de timp calculeaza care e cel mai lung timp de asteptare si
										//extinde simularea astfel incat sa se proceseze toti clienti care sunt deja la o coada
				for(Queue q:scheduler.getQueues()) {
					if(q.getWaitingPeriod().intValue()>longest){
						longest=q.getWaitingPeriod().intValue();
					}
				}
			}
			sendInformationToUI();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}		
	}
	
	public void sortare() {
		Collections.sort(this.generatedClients, Client.comparareDupaArrivalTime());//sorteaza in ordine crescatoare a timpiilor de ajungere
	}
	public  void sendInformationToUI() {
		int ok=0;
		ArrayList<String> toPrint = new ArrayList<String>();
		ArrayList<String> end = new ArrayList<String>(numberOfQueues);
		if(currentTime<(timeLimit+longest)) {
			for (int i = 0; i < this.numberOfQueues; i++) {
				String s = new String("QUEUE " + (i+1) + "\n\n" + scheduler.getQueues().get(i).toString());
				toPrint.add(s);
			}
			gui.repaint(toPrint,String.valueOf(currentTime),ok,emptyTime,peakHour,averageWaitingTime,averageProcessingTime);
		}
		else {
			ok=1;
		}
		if(ok==1) {
			averageWaitingTime=(totalWaitingForInterval/(timeLimit+longest))/numberOfQueues;//pt o coada
			averageProcessingTime=totalProcessingTime/totalClientsProcessed;
			gui.repaint(end,String.valueOf(currentTime),ok,emptyTime,peakHour,averageWaitingTime,averageProcessingTime);
		}	
	}
}
