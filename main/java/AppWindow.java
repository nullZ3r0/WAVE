import javax.swing.*;
import java.awt.*;

public class AppWindow extends JFrame
{
    public AppWindow()
    {
        // Initialise components
        ImageIcon defaultIcon = new ImageIcon("main/assets/icon.png"); // Creates the default icon to be used

        // Modify the frame
        this.setTitle("W A V E • Skeleton Program"); // Sets the title of the frame
        this.setSize(420, 420); // Sets the x-dimension and y-dimension of the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Terminate the application when the X is hit
        this.setVisible(true); // Make the frame visible
        this.setIconImage(defaultIcon.getImage()); // Set the frame icon to the image given
        this.getContentPane().setBackground(new Color(46, 48, 62)); // Set the background colour of the frame
    }
}
