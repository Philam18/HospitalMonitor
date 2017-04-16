import javax.swing.JFrame;
import java.awt.BorderLayout;

public class Main {
    
    public static void main(String[] args) {
        MonitorSystem frame = new MonitorSystem();
        ToolBar toolbar = new ToolBar(frame);
        frame.add(toolbar, BorderLayout.NORTH);
    }
    
}
