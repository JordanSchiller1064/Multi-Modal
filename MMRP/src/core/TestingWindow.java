package core;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.*;

import javax.swing.JFrame;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.Point;
import java.awt.Window.Type;
import java.awt.BorderLayout;


public class TestingWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestingWindow window = new TestingWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TestingWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	JList Trucks;
	JList Schedule;
	JPanel SchedulePanel;
	private void initialize() {
		frame = new JFrame();
		frame.setType(Type.UTILITY);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SchedulePanel=new JPanel();
		if(Schedule!=null)
			SchedulePanel.add(Schedule);
		SchedulePanel.setVisible(false);
		
		SchedulePanel.setSize(350,300);
		SchedulePanel.setLocation(100,100);
		Trucks = new JList(Truck.LoadAll("").toArray());
		frame.add(Trucks);
		Trucks.setLocation(new Point(10, 10));
		Trucks.setSize(350,120);
		Trucks.setLocation(32,220-36);
		Trucks.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent arg0)
			{
				if(!arg0.getValueIsAdjusting())
				{
					List<Segment> sch= ((Truck)Trucks.getSelectedValue()).getSchedule();
					Schedule = new JList(sch.toArray());
					SchedulePanel.add(Schedule);
					SchedulePanel.setVisible(true);
				}
			}
		});
		frame.getContentPane().add(SchedulePanel, BorderLayout.CENTER);
		JLabel lvl = new JLabel();
		SchedulePanel.add(lvl, BorderLayout.SOUTH);
		lvl.setText("TESTING");
	}

}
