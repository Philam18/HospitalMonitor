/**
 * This class generates a GUI box displaying all the information regarding the patient taken from the parameter. 
 * This will also continuously check if the patient’s vital signs are in danger, if so the box will start flashing 
 * and an alarm will start to alert the user. 
 * 
 * @author Alpha Mai alpha.mai@ryerson.ca
 * @version 1.0
 * @since 2017-03-01
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import java.io.File;
public class PatientBox extends JPanel
{
    //instance variables
    private JLabel room, age, heartRate, temp, bldPressure, bldOxylvl;
    private JButton settingBtn, removeBtn;
    private int status;
    private Color color;
    private Patient patient;
    /**
     * Construtor
     */
    public PatientBox(Patient patient)
    {
        room = new JLabel("room: "+ Integer.toString(patient.getRoom()));
        age = new JLabel("Age: "+ Integer.toString(patient.getAge()));
        heartRate = new JLabel("Heart rate: " + Integer.toString(patient.getHeartRate()));
        temp = new JLabel("Temperature: " + Float.toString(patient.getTemperature()));
        bldPressure = new JLabel("Blood Pressure: "+ patient.getSystolicBP() + "/" + patient.getDiastolicBP());
        bldOxylvl = new JLabel("Blood Oxygen Level: " + Integer.toString(patient.getBloodOxyLvl()));
        removeBtn = new JButton("Remove");
        //settingBtn = new JButton("Setting");
        
        status = 0;
        this.patient = patient;
        setBackground(Color.white);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(75,75,75),2), patient.getName()));
        ActionListener remove = new Remove();
        removeBtn.addActionListener(remove);
        
        setPreferredSize(new Dimension(250, 200));
       
        c.anchor = GridBagConstraints.LINE_START;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        add(room,c);
        c.gridy = 1;
        add(age,c);
        c.gridy = 2;
        add(heartRate,c);
        c.gridy = 3;
        add(temp,c);
        c.gridy = 4;
        add(bldPressure,c);
        c.gridy = 5;
        add(bldOxylvl,c);
        c.gridy = 6;
        add(removeBtn,c);
        //add(settingBtn,c);
    }
    /**
     * A helper class to make sure that one of the RGB values is within 0-255.
     * 
     * @param value The value to be checked.
     * @return Either the min or max value (0-255) if the value is out of the range, otherwise the value is returned with no change.
     */
    private int inRange(int value)
    {
        return Math.min(Math.max(value, 0), 255);
    }
    /**
     * The main loop that will update the patient's information, as well as setting off an alarm if the patient's vital signs are in danger.
     * 
     *@param patient The patient's information to be displayed.
     */
    public void update(Patient patient)
    {
        //update the patient's information
        //System.out.println(patient.getHeartRate());
        heartRate.setText("Heart Rate: " + patient.getHeartRate() + " BPM");
        temp.setText("Temperature: " + patient.getTemperature() + " C");
        bldPressure.setText("Blood Pressure: " + patient.getSystolicBP() + "/" + patient.getDiastolicBP() + " mmHg");
        bldOxylvl.setText("Blood Oxygen Level: " + patient.getBloodOxyLvl() + " %");
        if(status == 0)
        {
            if(patient.getHeartRate() > 100 || patient.getHeartRate() < 60)//patient.getBPM() > 100 || patient.getTemp > 41)
            {
                color = new Color(255, 70, 70);
                status = 1;
                new Alarm().start();
            }
        }
        repaint();
    }
    /**
     * A private helper class that will play a sound file. Using threads, we can play sounds concurrent to the program.
     * <b>Note: </b>On;l works with .wav files.
     */
    private class PlaySound extends Thread
    {
        //instance variable
        private String fileName;
        
        /**
         * Construtor
         * 
         * @param name The name of the sound file to be played.
         */
        public PlaySound(String name)
        {
            fileName = name;
        }
        /**
         * The run method for the thread.
         */
        public void run()
        {
            try{
                
                Clip clip = AudioSystem.getClip(); //Get a clip from the AudioSystem that can be used to playback an audio file
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(fileName)); //generate an audio input stream
                
                //open and start playing the sound
                clip.open(inputStream);
                clip.start();
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
    }
    /**
     * The ActionListener class for the buttons.
     */
    private class Remove implements ActionListener
    {
        /**
         * The method that is going to be ran when the button is pressed. Depending on the button pressed a 
         * window will appeared to perform whatever task the button is suppose to perform.
         */
        public void actionPerformed(ActionEvent evt)
        {
            if(JOptionPane.showConfirmDialog(null, "Are you sure you want to remove?") == JOptionPane.YES_OPTION)
            {
                patient.remove();
            }
        }
    }
    /**
     * A private helper class that will play a sound and flash the background of the panel to alarm the user that the patient's vital signs are in danger.
     */
    private class Alarm extends Thread
    {
        /**
         * The run method for the thread.
         */
        public void run()
        {
            new PlaySound("beep.wav").start(); //play the alarm sound file
            //slowly change the color to be lighter and lighter
            for(int i = 0; i < 255; i++){
                setBackground(new Color(inRange(color.getRed()+i), 
                                        inRange(color.getGreen()+i), 
                                        inRange(color.getBlue()+i)));
                try { Thread.sleep(3); }catch(InterruptedException e){ System.out.println(e); } //pausing slightly so that the user can see the change
            }
            status = 0; //set status back to 0 to indicate that 1 instance of alarm has completed
        }
    }
}