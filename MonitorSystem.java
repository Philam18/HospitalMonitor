import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * First Iteration, due Friday March 3
 * @author brendon
 * MonitorSystem extends JFrame and when created, creates all the necessary components for the application
 * 
 */
public class MonitorSystem extends JFrame{
    private Simulator simulator;
    public static ArrayList<Patient> patientList =  new ArrayList<Patient>();
    public static JPanel mainPanel;
    private ActionListener updatePatients;
    private StorageHandler xmlHandler;
    /**
     * Constructor for Monitor System
     */
    public MonitorSystem(){
        simulator = new Simulator();
        xmlHandler = new StorageHandler();
        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        
        updatePatients = new ActionListener(){
            public synchronized void actionPerformed(ActionEvent event) {
                for(Patient p : patientList)
                {
                    p.update();
                }
                mainPanel.repaint();
            }
        };
        
        new Timer(1000, updatePatients).start();
        
        add(mainPanel, BorderLayout.CENTER);
        this.setTitle("Patient Monitor System");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    public void addPatient(Patient patient)
    {
        patientList.add(patient);
        mainPanel.add(patient.getDisplayBox());
    }
}