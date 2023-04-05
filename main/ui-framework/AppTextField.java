import javax.swing.*;
import java.awt.*;

public class AppTextField extends JTextField
{
    public UITransform transform;
    private Component parent;
    private Boolean useTransform = false;
    private Boolean useWaveGraphics = false;

    public AppTextField()
    {
        this.setBackground(AppTheme.transparent);
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setOpaque(false);
        this.transform = new UITransform();
        this.setFont(AppTheme.textFont.deriveFont(Font.PLAIN, 16));
        this.setHorizontalAlignment(SwingConstants.LEFT);
    }

    // Custom render
    @Override
    protected void paintComponent(Graphics g)
    {
        if (useWaveGraphics == true)
        {
            // Render using WaveGraphics
            WaveGraphics.draw(this, g);
            // Draw the button text, image etc
            super.paintComponent(g);
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