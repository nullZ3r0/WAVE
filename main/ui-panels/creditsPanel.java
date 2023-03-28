import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class creditsPanel
{
    public AppFrame self;

    creditsPanel()
    {
        // Initialise the mainPanel
        // This will contain all the elements needed for the credits
        self = new AppFrame();
        self.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        self.setBorder(new EmptyBorder(50,0,0,0));
        self.transform.setCornerRadius(4);

        //AppLabel label = new AppLabel("W A V E");
        //WaveGraphics.addChild(self, label);
    }
}
