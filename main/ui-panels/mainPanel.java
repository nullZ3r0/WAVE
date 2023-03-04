import javax.swing.border.EmptyBorder;
import java.awt.*;

public class mainPanel
{
    public AppFrame self;

    // Public UI elements
    public AppFrame visualiserContainer;
    public AppFrame rainfallContainer;
    public AppVisualiser visualiser;
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

        // Create visualiserContainer
        visualiserContainer = new AppFrame();
        visualiserContainer.setLayout(null);
        visualiserContainer.setBackground(AppTheme.background);
        visualiserContainer.transform.setSize(0, -24, 1, 1);
        visualiserContainer.transform.setPosition(0, 0, 0, 0);

        // Create visualiser
        visualiser = new AppVisualiser();
        visualiser.keyboard.useAutoDimensions(true);
        visualiser.transform.setSize(0, 0, 1, 1);
        visualiser.transform.setPosition(0, 0, 0, 0);

        // Set visualiser parent
        WaveGraphics.addChild(visualiserContainer, visualiser);

        // Create seekContainer
        seekContainer = new AppFrame();
        seekContainer.setLayout(null);
        seekContainer.setBackground(AppTheme.backgroundDark3);
        seekContainer.transform.setSize(0, 20, 1, 0);
        seekContainer.transform.setPosition(0, -20, 0, 1);

        // Create controlContainer
        /*
        controlContainer = new AppFrame();
        controlContainer.setLayout(null);
        controlContainer.setBackground(AppTheme.backgroundLight2);
        controlContainer.transform.setSize(0, 80, 1, 0);
        controlContainer.transform.setPosition(0, 0, 0, 0);
        */

        // Set midiDisplayContainer and seekContainer parent
        //WaveGraphics.addChild(self, controlContainer);
        WaveGraphics.addChild(self, visualiserContainer);
        WaveGraphics.addChild(self, seekContainer);

    }
}
