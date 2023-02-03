import javax.swing.*;
import java.awt.*;

public class AppPanel extends JPanel
{
    public Dimension2 autoSize = new Dimension2(-20, -20, 1, 1);
    public Dimension2 autoPosition = new Dimension2(10, 10, 0, 0);

    public AppPanel()
    {
        this.setBackground(Color.white);
        this.setLayout(null);
    }

    public void autoBounds()
    {
        Container parent = this.getParent();
        if (parent != null && parent.getLayout() == null)
        {
            // Get parent information
            Rectangle parentBounds = parent.getBounds();
            int parentSizeX = (int) parentBounds.getWidth();
            int parentSizeY = (int) parentBounds.getHeight();

            // Get self information
            int positionX = this.autoPosition.getX();
            int positionY = this.autoPosition.getY();
            double positionXScale = this.autoPosition.getXScale();
            double positionYScale = this.autoPosition.getYScale();
            int sizeX = this.autoSize.getX();
            int sizeY = this.autoSize.getY();
            double sizeXScale = this.autoSize.getXScale();
            double sizeYScale = this.autoSize.getYScale();

            // Calculate absolute size and position
            int absoluteSizeX = sizeX + (int) (parentSizeX * sizeXScale);
            int absoluteSizeY = sizeY + (int) (parentSizeY * sizeYScale);
            int absolutePositionX = positionX + (int) (parentSizeX * positionXScale);
            int absolutePositionY = positionY + (int) (parentSizeY * positionYScale);

            this.setBounds(absolutePositionX, absolutePositionY, absoluteSizeX, absoluteSizeY);
        }
    }
}
