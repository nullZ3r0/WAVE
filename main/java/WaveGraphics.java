import java.awt.*;
import java.util.ArrayList;

class UDim2
{
    int x = 0;
    int y = 0;
    double xScale = 0;
    double yScale = 0;

    public void set(int _x, int _y, double _xScale, double _yScale)
    {
        this.x = _x;
        this.y = _y;
        this.xScale = _xScale;
        this.yScale = _yScale;
    }
    public void setOffset(int _x, int _y)
    {
        this.x = _x;
        this.y = _y;
    }

    public int getX() {return x;}
    public int getY() {return y;}

    public double getXScale() {return xScale;}
    public double getYScale() {return yScale;}
}

class Transform
{
    UDim2 position = new UDim2();
    UDim2 size = new UDim2();
    int cornerRadius = 0;
    boolean enabled = false;

    public void setEnabled(boolean _enabled) {enabled = _enabled;}

    public void setSize(int _x, int _y, double _xScale, double _yScale)
    {
        size.set(_x, _y, _xScale, _yScale);
    }

    public void setPosition(int _x, int _y, double _xScale, double _yScale)
    {
        position.set(_x, _y, _xScale, _yScale);
    }

    public boolean getEnabled() {return enabled;}
}

public class WaveGraphics extends Thread
{
    // Static stuff
    public static void update(AppFrame frame)
    {
        // Update absoluteBounds
        if (frame.absoluteBoundsEnabled() == true)
        {
            Component parent = frame.currentParent();
            double parentWidth = 0;
            double parentHeight = 0;

            if (parent != null)
            {
                parentWidth = parent.getWidth();
                parentHeight = parent.getHeight();
            }

            // Make translate compatible with java swing
            int relativeWidth = (int) (parentWidth * frame.transform.size.getXScale());
            int relativeHeight = (int) (parentHeight * frame.transform.size.getYScale());
            int convertedWidth = frame.transform.size.getX() + relativeWidth;
            int convertedHeight = frame.transform.size.getY() + relativeHeight;

            int relativeX = (int) (parentWidth * frame.transform.position.getXScale());
            int relativeY = (int) (parentHeight * frame.transform.position.getYScale());
            int convertedX = frame.transform.position.getX() + relativeX;
            int convertedY = frame.transform.position.getY() + relativeY;

            // Set bounds
            frame.setBounds(convertedX, convertedY, convertedWidth, convertedHeight);
        }
    }

    // Object stuff
    private static ArrayList<Component> objects = new ArrayList<Component>();

    public static void add(Component component)
    {
        objects.add(component);
    }

    public static void addChild(AppFrame parent, Component child)
    {
        parent.add(child);
        objects.add(child);
    }

    @Override
    public void run()
    {
        while (true)
        {
            for (Component object : WaveGraphics.objects)
            {
                if (object.getClass() == AppFrame.class)
                {
                    WaveGraphics.update((AppFrame) object);
                }
            }

            try
            {
                Thread.sleep(2);
            }
            catch (InterruptedException e)
            {

            }
        }
    }

    // Getters
    public static ArrayList<Component> getObjects() {return objects;}
}
