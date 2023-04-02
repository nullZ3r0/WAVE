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
        self.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 8));
        self.setBorder(new EmptyBorder(16,0,0,0));
        self.transform.setCornerRadius(4);
        self.transform.setSize(0,0,1,1);
        self.setBackground(AppTheme.backgroundDark1);

        AppLabel label = new AppLabel("C R E D I T S");
        label.setFont(AppTheme.titleFont.deriveFont(Font.PLAIN, 16));
        label.transform.setSize(-48, 32, 1, 0);
        WaveGraphics.addChild(self, label);

        String[] creditList =
                {
                "Alan Robinson - Lead Java Programmer",
                "Lukas Maslianikas - Lead Python Programmer",
                "Shriharsh Shriharsh - Project Manager",
                "Hanan Lalo - Lead Report Editor",
                "Fayad Mususwa - Creative Lead"
                };

        for (String name : creditList)
        {
            AppLabel selectedLabel = new AppLabel(name);
            selectedLabel.setHorizontalAlignment(SwingConstants.LEFT);
            selectedLabel.setFont(AppTheme.textFont.deriveFont(Font.PLAIN, 16));
            selectedLabel.transform.setSize(-48, 32, 1, 0);
            WaveGraphics.addChild(self, selectedLabel);
        }
    }
}