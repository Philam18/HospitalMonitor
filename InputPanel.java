import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
public class InputPanel extends JPanel
{
    private JLabel namePrompt;
    private JLabel agePrompt;
    private JLabel roomPrompt;
    private JTextField name;
    private JTextField age;
    private JTextField room;
    public InputPanel()
    {
        namePrompt = new JLabel("Name: ");
        agePrompt = new JLabel("Age: ");
        roomPrompt = new JLabel("Room: ");
        name = new JTextField(10);
        age = new JTextField(2);
        room = new JTextField(3);
        
        add(namePrompt);
        add(name);
        add(roomPrompt);
        add(room);
        add(agePrompt);
        add(age);
    }
    public String getName()
    {
        return name.getText();
    }
    public int getRoom()
    {
        return Integer.parseInt(room.getText());
    }
    public int getAge()
    {
        return Integer.parseInt(age.getText());
    }
}