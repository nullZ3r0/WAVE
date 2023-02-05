import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

public class AppFrame extends JPanel implements ActionListener
{
    public AppFrame()
    {
        this.setBackground(AppTheme.frame.background);
        this.setOpaque(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        System.out.println("Something was clicked on this frame");
    }
}
