import javax.swing.border.EmptyBorder;
import java.awt.*;

public class menuPanel
{
    public AppFrame self;

    // You can expose elements of the panel, this allows them to be accessed by other classes (most importantly main)
    // Example below
    public AppButton resumeButton;

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
        resumeButton = new AppButton("Resume Practice");
        resumeButton.transform.setSize(-48, 32, 1, 0);

        // This is what we want the button to do when clicked, however we're going to try link this action in main
        //resumeButton.addActionListener(e -> WaveAPI.hideMenu());

        AppButton button2 = new AppButton("Credits");
        button2.transform.setSize(-48, 32, 1, 0);
        button2.addActionListener(e -> WaveAPI.switchToCredits());

        WaveGraphics.addChild(leftContainer, resumeButton);
        WaveGraphics.addChild(leftContainer, button2);

        // Initialise dummy buttons
        for (int i = 0; i < 9; i++)
        {
            AppButton dummyButton = new AppButton("Button " + String.valueOf(i + 1));
            dummyButton.transform.setSize(-48, 32, 1, 0);
            dummyButton.addActionListener(e -> WaveAPI.debugButton());
            WaveGraphics.addChild(leftContainer, dummyButton);
        }


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
        AppFrame rightContainer = new AppFrame();
        rightContainer.setLayout(new CardLayout());
        rightContainer.setBackground(AppTheme.backgroundDark1);
        rightContainer.transform.setCornerRadius(8);
        rightContainer.transform.setSize(600, -100, 0, 1);

        // Set rightContainer parent
        WaveGraphics.addChild(self, rightContainer);
    }
}
