import java.awt.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class WaveGraphics extends Thread
{
    // Static stuff
    public static void update(Component object)
    {
        // Update absoluteBounds
        if (object.getClass() == AppFrame.class)
        {
            AppFrame frame = (AppFrame) object;
            if (frame.useTransform() == true)
            {
                Component parent = frame.getParent();
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

                for (UIConstraint constraint : frame.transform.getConstraints())
                {
                    if (constraint.getClass() == UISizeConstraint.class)
                    {
                        UISizeConstraint sizeConstraint = (UISizeConstraint) constraint;
                        convertedWidth = Math.min(convertedWidth, sizeConstraint.maxWidth());
                        convertedWidth = Math.max(convertedWidth, sizeConstraint.minWidth());
                    }
                    else if (constraint.getClass() == UIPositionConstraint.class)
                    {
                        UIPositionConstraint positionConstraint = (UIPositionConstraint) constraint;
                        convertedX = Math.min(convertedX, positionConstraint.maxX());
                        convertedX = Math.max(convertedX, positionConstraint.minX());
                    }
                }

                if (parent.getClass() == AppFrame.class)
                {
                    AppFrame parentFrame = (AppFrame) parent;
                    if (parentFrame.getLayout() != null)
                    {
                        frame.setSize(convertedWidth, convertedHeight);
                        frame.setPreferredSize(frame.getSize());

                        frame.revalidate();
                    }
                    else
                    {
                        // Set bounds
                        frame.setBounds(convertedX, convertedY, convertedWidth, convertedHeight);
                    }
                }
                else
                {
                    // Set bounds
                    frame.setBounds(convertedX, convertedY, convertedWidth, convertedHeight);
                }
            }
        }
        else if (object.getClass() == AppButton.class)
        {
            AppButton button = (AppButton) object;
            if (button.useTransform() == true)
            {
                Component parent = button.getParent();
                double parentWidth = 0;
                double parentHeight = 0;

                if (parent != null)
                {
                    parentWidth = parent.getWidth();
                    parentHeight = parent.getHeight();
                }

                // Make translate compatible with java swing
                int relativeWidth = (int) (parentWidth * button.transform.size.getXScale());
                int relativeHeight = (int) (parentHeight * button.transform.size.getYScale());
                int convertedWidth = button.transform.size.getX() + relativeWidth;
                int convertedHeight = button.transform.size.getY() + relativeHeight;

                int relativeX = (int) (parentWidth * button.transform.position.getXScale());
                int relativeY = (int) (parentHeight * button.transform.position.getYScale());
                int convertedX = button.transform.position.getX() + relativeX;
                int convertedY = button.transform.position.getY() + relativeY;

                for (UIConstraint constraint : button.transform.getConstraints())
                {
                    if (constraint.getClass() == UISizeConstraint.class)
                    {
                        UISizeConstraint sizeConstraint = (UISizeConstraint) constraint;
                        convertedWidth = Math.min(convertedWidth, sizeConstraint.maxWidth());
                        convertedWidth = Math.max(convertedWidth, sizeConstraint.minWidth());
                    }
                }

                if (parent.getClass() == AppFrame.class)
                {
                    AppFrame parentFrame = (AppFrame) parent;
                    if (parentFrame.getLayout() != null)
                    {
                        button.setSize(convertedWidth, convertedHeight);
                        button.setPreferredSize(button.getSize());

                        button.revalidate();
                    }
                    else
                    {
                        // Set bounds
                        button.setBounds(convertedX, convertedY, convertedWidth, convertedHeight);
                    }
                }
                else
                {
                    // Set bounds
                    button.setBounds(convertedX, convertedY, convertedWidth, convertedHeight);
                }
            }
        }
    }

    public static void draw(Component object, Graphics g)
    {
        int width = object.getWidth();
        int height = object.getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (object.getClass() == AppFrame.class)
        {
            AppFrame frame = (AppFrame) object;
            Dimension arcs = new Dimension(Math.min(frame.transform.getCornerRadius(), height), Math.min(frame.transform.getCornerRadius(), height));

            // Draws the panel (can have rounded corners)
            graphics.setColor(frame.getBackground());
            graphics.fillRoundRect(0, 0, width, height, arcs.width, arcs.height);
        }
        else if (object.getClass() == AppButton.class)
        {
            AppButton button = (AppButton) object;
            Dimension arcs = new Dimension(Math.min(button.transform.getCornerRadius(), height), Math.min(button.transform.getCornerRadius(), height));

            // Draws the panel (can have rounded corners)
            graphics.setColor(button.getBackground());
            graphics.fillRoundRect(0, 0, width, height, arcs.width, arcs.height);

            if (button.mouseIn())
            {
                button.setForeground(AppTheme.button.foregroundHover);
            }
            else
            {
                button.setForeground(AppTheme.button.foreground);
            }
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

        if (child.getClass() == AppFrame.class)
        {
            AppFrame childFrame = (AppFrame) child;
            childFrame.useWaveGraphics(true);
            childFrame.useTransform(true);
        }
        else if (child.getClass() == AppButton.class)
        {
            AppButton childButton = (AppButton) child;
            childButton.useWaveGraphics(true);
            childButton.useTransform(true);
        }
    }

    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                for (Component object : WaveGraphics.objects)
                {
                    WaveGraphics.update(object);
                }

                //Thread.sleep(16); // Around (60 fps)
                //Thread.sleep(32); // Around (30 fps)
                Thread.sleep(20); // Around (20 fps)
            }
            catch (InterruptedException e)
            {
                System.out.println("WaveGraphics was interrupted!");
            }
            catch (ConcurrentModificationException e)
            {
                System.out.println("WaveGraphics tried to modify a Collection while another thread was iterating over it!");
            }
        }
    }

    // Getters
    public static ArrayList<Component> getObjects() {return objects;}
}
