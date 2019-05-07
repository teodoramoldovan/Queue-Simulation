package sistem;

import java.util.Comparator;

public class Client {
	
	private int arrivalTime,processingPeriod;
	
	public Client(int processingPeriod,int currentTime) {
		this.arrivalTime=currentTime;
		this.processingPeriod=processingPeriod;
	}
	public int getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public int getProcessingPeriod() {
		return processingPeriod;
	}
	public void setProcessingPeriod(int processingPeriod) {
		this.processingPeriod = processingPeriod;
	}
	public static Comparator<Client> comparareDupaArrivalTime() {//pentru argument la Collections.sort 
		Comparator<Client> comp = new Comparator<Client>(){
		    public int compare(Client c1, Client c2) {
		    	return Integer.compare(c1.arrivalTime, c2.arrivalTime);
		    }        
		};
		return comp;
	}  
}

