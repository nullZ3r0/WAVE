import javax.swing.*;
import java.awt.*;

public class AppWindow extends JFrame
{
    public AppWindow()
    {
        // Initialise components
        ImageIcon defaultIcon = new ImageIcon("main/assets/images/icon.png"); // Creates the default icon to be used

        // Modify the frame
        this.setTitle("W A V E"); // Sets the title of the window
        this.setSize(540, 540); // Sets the x-dimension and y-dimension of the window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Terminate the application when the X is hit
        this.setIconImage(defaultIcon.getImage()); // Set the window icon to the image given
        this.setMinimumSize(new Dimension(360, 360));

        // Make the frame visible
        this.setVisible(true);
    }
}

