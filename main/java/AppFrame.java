import java.awt.*;
import javax.swing.JPanel;

public class AppFrame extends JPanel
{
    public UITransform transform;
    private Component parent;
    private Boolean useTransform = false;
    private Boolean useWaveGraphics = false;

    public AppFrame()
    {
        this.setBackground(AppTheme.frame.background);
        this.setOpaque(true);
        this.transform = new UITransform();
    }

    // Custom render
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

    public void useTransform(Boolean set) {useTransform = set;}
    public Boolean useTransform() {return useTransform;}
    public void useWaveGraphics(Boolean set) {useWaveGraphics = set;}

}
