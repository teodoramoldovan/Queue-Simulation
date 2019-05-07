package tema2_simulatingqueue;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import simulation.SimulationManager;
import sistem.Scheduler.SelectionPolicy;



public class Interfata extends Frame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton simulateButton=new JButton("Simulate");
	private JFrame frame=new JFrame("Simulating Queue");
	private SimulationManager s;
	private ArrayList<JTextPane> queues=new ArrayList<JTextPane>();
	private JPanel pnlQueues=new JPanel();
	private JPanel pnlMainPanel=new JPanel();
	private JPanel pnlTime=new JPanel();
	private JPanel pnlEnd=new JPanel();
	private Random rand=new Random();
	private JLabel currentTimeMin=new JLabel(),currentTimeSec=new JLabel(),currentTimeDel=new JLabel(":");
	private JTextField numberOfClients=new JTextField(20);
	private JTextField minSerTime=new JTextField(20);
	private JTextField maxSerTime=new JTextField(20);
	private JTextField nrOfQueues=new JTextField(20);
	private JTextField simTime=new JTextField(20);
	private JLabel numberOfClientsLabel=new JLabel("Total number of clients for the simulation:");
	private JLabel minSerLabel=new JLabel("Processing interval(min,max):");
	private JLabel nrQueuesLabel=new JLabel("Number of queues:");
	private JLabel simTimeLabel=new JLabel("Simulation time:");
	private JPanel pnlInput=new JPanel();
	private JPanel nrClienti=new JPanel();
	private JPanel minSer=new JPanel();
	private JPanel maxSer=new JPanel();
	private JPanel nrQ=new JPanel();
	private JPanel simT=new JPanel();
	
	private JRadioButton queueStrat=new JRadioButton("Queue Strategy");
	private JRadioButton timeStrat=new JRadioButton("Time Strategy");
	private JPanel strat=new JPanel();
    ButtonGroup group = new ButtonGroup();
    
    private int queuesNo,nrOfC,serMin,serMax,simulationTime;
	
	public void repaint(ArrayList<String> toPrint,String currTime,int ok,int emptyTime,int peakHour,
			int averageWaitingTime,int averageProcessingTime) 
	{	
		for (int i = 0; i <toPrint.size(); i++) {
			this.queues.get(i).setText(toPrint.get(i));
		}
		int min=Integer.parseInt(currTime)/60;
		int seconds=Integer.parseInt(currTime)-min*60;
		currentTimeMin.setText((min<10)?"0"+String.valueOf(min):String.valueOf(min));
		currentTimeSec.setText((seconds<10)?"0"+String.valueOf(seconds):String.valueOf(seconds));
		if(Integer.parseInt(currTime)>simulationTime) {
			currentTimeMin.setForeground(Color.red);
			currentTimeSec.setForeground(Color.red);
		}
		int overTime=(Integer.parseInt(currTime))-simulationTime;
		if(ok==1) {
			pnlQueues.setVisible(false);
			JTextPane endPane=new JTextPane();
			endPane.setText("END OF SIMULATION\n"+"Empty queue time: "+emptyTime+"\nPeak hour: "+peakHour+
					"\nAverage waiting time: "+averageWaitingTime+"\nAverage processing time: "+averageProcessingTime+
					"\nOvertime: "+overTime);
			pnlEnd.add(endPane);
			pnlMainPanel.revalidate();
		}
	}

	public Interfata() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1300, 800);
		pnlInput.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		nrClienti.setLayout(new FlowLayout());
		minSer.setLayout(new FlowLayout());
		maxSer.setLayout(new FlowLayout());
		nrQ.setLayout(new FlowLayout());
		simT.setLayout(new FlowLayout());
		strat.setLayout(new FlowLayout());
		nrClienti.add(numberOfClientsLabel);
		nrClienti.add(numberOfClients);
		minSer.add(minSerLabel);
		minSer.add(minSerTime);
		minSer.add(maxSerTime);
		nrQ.add(nrQueuesLabel);
		nrQ.add(nrOfQueues);
		simT.add(simTimeLabel);
		simT.add(simTime);
		c.gridx = 0;
		c.gridy = 1;
		pnlInput.add(nrClienti,c);
		c.gridx = 0;
		c.gridy = 2;
		pnlInput.add(minSer,c);
		c.gridx = 0;
		c.gridy =3;
		pnlInput.add(maxSer,c);
		c.gridx = 0;
		c.gridy = 4;
		pnlInput.add(nrQ,c);
		c.gridx = 0;
		c.gridy = 5;
		pnlInput.add(simT,c);
		pnlQueues.setLayout(new BoxLayout(pnlQueues, BoxLayout.X_AXIS));
		pnlTime.setLayout(new FlowLayout());
		pnlMainPanel.setLayout(new BoxLayout(pnlMainPanel,BoxLayout.Y_AXIS));
		
		group.add(timeStrat);
		group.add(queueStrat);
		
		timeStrat.setSelected(true);
		strat.add(timeStrat);
		strat.add(queueStrat);
		

		pnlMainPanel.add(pnlInput);
		pnlMainPanel.add(strat);
		pnlMainPanel.add(simulateButton);
		pnlMainPanel.add(pnlTime);
		pnlMainPanel.add(pnlQueues);
		pnlMainPanel.add(pnlEnd);
		
		frame.add(pnlMainPanel);
		frame.setVisible(true);
		frame.setResizable(false);
		final
		Interfata thisGui=this;
		nrOfQueues.setText("3");
		numberOfClients.setText("20");
		minSerTime.setText("1");
		maxSerTime.setText("5");
		simTime.setText("10");
		timeStrat.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(timeStrat.isSelected()) {
					s.selectionPolicy=SelectionPolicy.SHORTEST_TIME;
				}
			}
		});
		queueStrat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(timeStrat.isSelected()) {
					s.selectionPolicy=SelectionPolicy.SHORTEST_QUEUE;
				}
			}
			
		});
		simulateButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				try {
					queuesNo = Integer.parseInt(nrOfQueues.getText());
					nrOfC=Integer.parseInt(numberOfClients.getText());
					serMin=Integer.parseInt(minSerTime.getText());
					serMax=Integer.parseInt(maxSerTime.getText());
					simulationTime=Integer.parseInt(simTime.getText());
					pnlInput.setVisible(false);
					pnlInput.revalidate();
					
					s = new SimulationManager(thisGui,queuesNo,nrOfC,serMin,serMax,simulationTime);

					pnlTime.add(currentTimeMin);
					pnlTime.add(currentTimeDel);
					pnlTime.add(currentTimeSec);
				
					for (int i = 1; i <= queuesNo; i++) {
						JTextPane tp=new JTextPane();
						tp.setEditable(false);
						tp.setBackground(new Color((rand.nextInt(55)+200),(rand.nextInt(55)+100),(rand.nextInt(55)+100)));
						queues.add(tp);
						pnlQueues.add(tp);
						pnlQueues.revalidate();	
					}
					Thread t=new Thread(s);
					t.start();

					pnlMainPanel.revalidate();

					simulateButton.setEnabled(false);
					simulateButton.setOpaque(false);
					timeStrat.setEnabled(false);
					queueStrat.setEnabled(false);
					
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "All fields must contain numbers", null, JOptionPane.ERROR_MESSAGE);				
				}
			}
		});
		
	}
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Interfata i=new Interfata();
		
	}
}
