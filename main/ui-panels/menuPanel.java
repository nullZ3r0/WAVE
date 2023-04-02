import javax.swing.border.EmptyBorder;
import java.awt.*;

public class menuPanel
{
    public AppFrame self;

    // You can expose elements of the panel, this allows them to be accessed by other classes (most importantly main)
    // Example below
    public AppButton resumeButton;
    public AppButton deviceSettingsButton;
    public AppButton keybindsButton;
    public AppButton creditsButton;
    public AppFrame rightContainer;

    menuPanel()
    {
        // Initialise the menuPanel
        // NOTE: This "setBorder(new EmptyBorder())" stuff is hacky as f###
        // This will contain all the elements needed for the menu panel
        self = new AppFrame();
        self.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        self.setBorder(new EmptyBorder(50,0,0,0));

        // Create leftContainer
        AppFrame leftContainer = new AppFrame();
        leftContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 8));
        leftContainer.setBackground(AppTheme.backgroundDark1);
        leftContainer.transform.setSize(0, -100, 0.30, 1);
        leftContainer.transform.setPosition(0, 0, 0, 0);
        leftContainer.transform.addConstraint(new UISizeConstraint(300,300,0,9999));
        leftContainer.transform.setCornerRadius(8);
        leftContainer.setBorder(new EmptyBorder(16,0,0,0));

        // Add the leftContainer to the menuPanel via the custom renderer
        WaveGraphics.addChild(self, leftContainer);

        // Initialise buttons to add
        UITransform defaultSize = new UITransform(-48, 32, 1, 0);
        resumeButton = new AppButton("Resume Session");
        resumeButton.transform.setSize(defaultSize);

        AppButton newSessionButton = new AppButton("New Session");
        newSessionButton.transform.setSize(defaultSize);
        newSessionButton.addActionListener(e -> WaveAPI.fireAction("openFileChooser"));

        AppButton displaySettingsButton = new AppButton("Display Settings");
        displaySettingsButton.transform.setSize(defaultSize);
        displaySettingsButton.addActionListener(e -> WaveAPI.debugButton());

        deviceSettingsButton = new AppButton("Device Settings");
        deviceSettingsButton.transform.setSize(defaultSize);

        keybindsButton = new AppButton("Keybinds");
        keybindsButton.transform.setSize(defaultSize);
        keybindsButton.addActionListener(e -> WaveAPI.debugButton());

        creditsButton = new AppButton("Credits");
        creditsButton.transform.setSize(defaultSize);

        AppButton exitButton = new AppButton("Exit");
        exitButton.transform.setSize(defaultSize);
        exitButton.addActionListener(e -> WaveAPI.exit());

        WaveGraphics.addChild(leftContainer, resumeButton);
        WaveGraphics.addChild(leftContainer, newSessionButton);
        WaveGraphics.addChild(leftContainer, displaySettingsButton);
        WaveGraphics.addChild(leftContainer, deviceSettingsButton);
        WaveGraphics.addChild(leftContainer, keybindsButton);
        WaveGraphics.addChild(leftContainer, creditsButton);
        WaveGraphics.addChild(leftContainer, exitButton);

        // Create dividerContainer
        AppFrame dividerContainer = new AppFrame();
        dividerContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 50));
        dividerContainer.setBackground(AppTheme.transparent);
        dividerContainer.transform.setSize(50, -200, 0, 1);
        dividerContainer.setBorder(new EmptyBorder(-50,0,0,0));

        // Create divider
        AppFrame divider = new AppFrame();
        divider.setBackground(AppTheme.foreground);
        divider.transform.setSize(2, 0, 0, 1);
        divider.transform.setCornerRadius(4);

        // Set divider parent
        WaveGraphics.addChild(dividerContainer, divider);

        // Set dividerContainer parent
        WaveGraphics.addChild(self, dividerContainer);


        // Create rightContainer
        rightContainer = new AppFrame();
        // CardLayout is bugged with WaveGraphics!!!
        //rightContainer.setLayout(new CardLayout());
        rightContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        rightContainer.setBackground(AppTheme.backgroundDark1);
        rightContainer.transform.setCornerRadius(8);
        rightContainer.transform.setSize(600, -100, 0, 1);

        // Set rightContainer parent
        WaveGraphics.addChild(self, rightContainer);
    }
}
