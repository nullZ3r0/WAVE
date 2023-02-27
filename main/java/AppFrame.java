import java.awt.*;
import javax.swing.JPanel;

public class AppFrame extends JPanel
{
    public Transform transform;
    private Component parent;
    private Boolean absoluteBounds = false;

    public AppFrame()
    {
        this.setBackground(AppTheme.frame.background);
        this.setOpaque(true);
        this.transform = new Transform();
    }

    // Custom button render
    @Override
    protected void paintComponent(Graphics g)
    {
        if (absoluteBounds == true)
        {
            int width = getWidth();
            int height = getHeight();
            Dimension arcs = new Dimension(Math.min(transform.cornerRadius, height), Math.min(transform.cornerRadius, height));
            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draws the rounded panel with borders.
            graphics.setColor(this.getBackground());

            graphics.fillRoundRect(0, 0, width, height, arcs.width, arcs.height); //paint background
        }
        else
        {
            // Draw default
            super.paintComponent(g);
        }
    }

    public void enableAbsoluteBounds(Boolean _absolutePositioning) {absoluteBounds = _absolutePositioning;}
    public Boolean absoluteBoundsEnabled() {return absoluteBounds;}

    public Component currentParent()
    {
        return this.parent;
    }

    @Override public void addNotify()
    {
        super.addNotify();
        parent = this.getParent();
    }

    @Override
    public void removeNotify()
    {
        super.removeNotify();
        parent = null;
    }

}
