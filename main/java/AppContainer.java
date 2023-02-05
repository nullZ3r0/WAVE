import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class AppContainer extends JPanel
{
    public AppContainer()
    {
        this.setBackground(AppTheme.window);
        this.setLayout(new CardLayout());

        // This acts as padding to it's descendants
        this.setBorder(new EmptyBorder(0,2,2,2));
    }

    public ArrayList<AppFrame> getFrames()
    {
        ArrayList<AppFrame> childFrames = new ArrayList<AppFrame>(); // Create an ArrayList object

        Component[] children =  this.getComponents();
        for (Component child : children)
        {
            if (child.getClass() == AppFrame.class)
            {
                childFrames.add((AppFrame) child);
            }
        }

        return childFrames;
    }
}
