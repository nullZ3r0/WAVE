import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class developerPanel
{
    public AppFrame self;
    public AppButton closeCameraButton;
    public AppButton closeVoiceButton;

    developerPanel()
    {
        // Initialise the mainPanel
        // This will contain all the elements needed for the credits
        self = new AppFrame();
        self.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 8));
        self.setBorder(new EmptyBorder(16,0,0,0));
        self.transform.setCornerRadius(4);
        self.transform.setSize(0,0,1,1);
        self.setBackground(AppTheme.backgroundDark1);

        AppLabel label = new AppLabel("D E V E L O P E R   P A N E L");
        label.setFont(AppTheme.titleFont.deriveFont(Font.PLAIN, 16));
        label.transform.setSize(-48, 32, 1, 0);
        WaveGraphics.addChild(self, label);

        // Show selected device
        AppLabel label1 = new AppLabel("Sub Programs");
        label1.setHorizontalAlignment(SwingConstants.LEFT);
        label1.setFont(AppTheme.titleFont.deriveFont(Font.PLAIN, 16));
        label1.transform.setSize(-48, 32, 1, 0);
        WaveGraphics.addChild(self, label1);

        closeCameraButton = new AppButton("Stop Gesture System");
        closeCameraButton.transform.setSize(-48, 32, 1, 0);
        WaveGraphics.addChild(self, closeCameraButton);

        closeVoiceButton = new AppButton("Stop Voice System");
        closeVoiceButton.transform.setSize(-48, 32, 1, 0);
        WaveGraphics.addChild(self, closeVoiceButton);

    }
}
