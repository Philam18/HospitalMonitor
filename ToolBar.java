import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ToolBar extends JToolBar
{
    private JButton addPatient;
    private MonitorSystem main;
    public ToolBar(MonitorSystem main){
        this.main = main;
        addPatient = new JButton("Add");
        addPatient.addActionListener(new ButtonHandler());
        
        add(addPatient);
    }
    
    private class ButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent evt)
        {
            InputPanel panel =  new InputPanel();
            int input = JOptionPane.showConfirmDialog(null, panel, "Please Enter Information: ", JOptionPane.OK_CANCEL_OPTION);
            if(input == JOptionPane.OK_OPTION)
            {
                main.addPatient(new Patient(panel.getName(), panel.getRoom(), panel.getAge()));
            }
        }
    }
}