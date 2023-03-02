import javax.swing.border.EmptyBorder;
import java.awt.*;

class pianoContainer
{
    public AppFrame self;
    pianoContainer()
    {
        self = new AppFrame();
        self.setLayout(null);
        self.setBackground(AppTheme.backgroundDark3);
        self.transform.setSize(0, 80, 1, 0);
        self.transform.setPosition(0, -80, 0, 1);
    }
}

public class mainPanel
{
    public AppFrame self;

    // Public UI elements
    public AppFrame midiDisplayContainer;
    public AppFrame rainfallContainer;
    public AppFrame pianoContainer;
    public AppFrame seekContainer;
    public AppFrame controlContainer;

    mainPanel()
    {
        // Initialise the mainPanel
        // This will contain all the elements needed for the main panel
        // mainPanel -> midiDisplayContainer / seekContainer / controlContainer
        // midiDisplayContainer -> rainfallContainer / pianoContainer
        self = new AppFrame();
        self.setLayout(null);

        // Create midiDisplayContainer
        midiDisplayContainer = new AppFrame();
        midiDisplayContainer.setLayout(null);
        midiDisplayContainer.setBackground(AppTheme.backgroundDark1);
        midiDisplayContainer.transform.setSize(0, -24, 1, 1);
        midiDisplayContainer.transform.setPosition(0, 0, 0, 0);

        // Create pianoContainer
        pianoContainer = new pianoContainer().self;

        // Set pianoContainer parent
        WaveGraphics.addChild(midiDisplayContainer, pianoContainer);

        // Create seekContainer
        seekContainer = new AppFrame();
        seekContainer.setLayout(null);
        seekContainer.setBackground(AppTheme.backgroundDark3);
        seekContainer.transform.setSize(0, 20, 1, 0);
        seekContainer.transform.setPosition(0, -20, 0, 1);

        // Create controlContainer
        controlContainer = new AppFrame();
        controlContainer.setLayout(null);
        controlContainer.setBackground(AppTheme.backgroundLight2);
        controlContainer.transform.setSize(0, 80, 1, 0);
        controlContainer.transform.setPosition(0, 0, 0, 0);

        // Set midiDisplayContainer and seekContainer parent
        WaveGraphics.addChild(self, controlContainer);
        WaveGraphics.addChild(self, midiDisplayContainer);
        WaveGraphics.addChild(self, seekContainer);

    }
}
