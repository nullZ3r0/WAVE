import javax.swing.*;
import java.awt.*;
import javax.swing.JPanel;

public class AppVisualiser extends JPanel
{
    /** Public AppVisualiser Attributes **/
    public UITransform transform;
    public MidiKeyboard keyboard;
    public String infoMidiFileName = "Unknown";
    public String infoMidiFileBPM = "000";
    public String infoMidiFileSpeed = "100";
    public String infoKeyboardZoom = "100";

    /** Private AppVisualiser Attributes **/
    private Component parent;
    private Boolean useTransform = false;
    private Boolean useWaveGraphics = false;

    /** Constructors **/
    public AppVisualiser()
    {
        this.setBackground(AppTheme.visualiser.background);
        this.setOpaque(true);
        this.transform = new UITransform();
        this.keyboard = new MidiKeyboard();
        this.keyboard.setVisualiser(this);
    }

    /** Overridden Methods **/
    @Override
    protected void paintComponent(Graphics g)
    {
        if (useWaveGraphics == true)
        {
            // Render using WaveGraphics
            WaveGraphics.draw(this, g);
        }
        else
        {
            // Render using Java Swing
            super.paintComponent(g);
        }
    }

    /** Getters & Setters **/
    public Boolean useTransform() {return useTransform;}
    public void useTransform(Boolean set) {useTransform = set;}
    public Boolean useWaveGraphics() {return useWaveGraphics;}
    public void useWaveGraphics(Boolean set) {useWaveGraphics = set;}
}
