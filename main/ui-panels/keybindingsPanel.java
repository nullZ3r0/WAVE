import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class keybindingsPanel
{
    public AppFrame self;

    keybindingsPanel()
    {
        // Initialise the mainPanel
        // This will contain all the elements needed for the credits
        self = new AppFrame();
        self.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 8));
        self.setBorder(new EmptyBorder(16,0,0,0));
        self.transform.setCornerRadius(4);
        self.transform.setSize(0,0,1,1);
        self.setBackground(AppTheme.backgroundDark1);

        AppLabel label = new AppLabel("K E Y B I N D S");
        label.setFont(AppTheme.titleFont.deriveFont(Font.PLAIN, 16));
        label.transform.setSize(-48, 32, 1, 0);
        WaveGraphics.addChild(self, label);

        // Show selected device
        AppLabel selectedLabel = new AppLabel("Keyboard");
        selectedLabel.setHorizontalAlignment(SwingConstants.LEFT);
        selectedLabel.setFont(AppTheme.titleFont.deriveFont(Font.PLAIN, 16));
        selectedLabel.transform.setSize(-48, 32, 1, 0);
        WaveGraphics.addChild(self, selectedLabel);

        AppFrame keyboardHolder = new AppFrame();
        keyboardHolder.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 0));
        keyboardHolder.setBorder(new EmptyBorder(16,0,0,0));
        keyboardHolder.transform.setSize(-48, 216, 1, 0);
        keyboardHolder.transform.setCornerRadius(8);
        keyboardHolder.setBackground(AppTheme.backgroundDark3);
        WaveGraphics.addChild(self, keyboardHolder);

        String[] keyboardKeybindings =
                {
                        "Multimedia", "",
                        "[SPACE] Play/Pause",
                        "[R] Restart",
                        "[ARROW UP] Speed Up",
                        "[ARROW DOWN] Slow Down",
                        "","",

                        "Rendering", "",
                        "[ADD] Zoom In",
                        "[CTRL + EQUALS] Zoom In",
                        "[SUBTRACT] Zoom Out",
                        "[CTRL + MINUS] Zoom Out",
                        "","",

                        "Other", "",
                        "[ESCAPE] Toggle Menu",
                };

        for (String binding : keyboardKeybindings)
        {
            AppLabel bindingLabel = new AppLabel(binding);
            bindingLabel.setHorizontalAlignment(SwingConstants.LEFT);
            bindingLabel.setFont(AppTheme.textFont.deriveFont(Font.PLAIN, 16));
            switch (binding)
            {
                case "Multimedia":
                case "Rendering":
                case "Other":
                    bindingLabel.setFont(AppTheme.titleFont.deriveFont(Font.PLAIN, 16));
            }
            bindingLabel.transform.setSize(-48, 18, 0.5, 0);
            WaveGraphics.addChild(keyboardHolder, bindingLabel);
        }
    }
}
