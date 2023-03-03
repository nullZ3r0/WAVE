import java.awt.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class WaveGraphics extends Thread
{
    // Static stuff
    private static Rectangle transformToRectangle(UITransform transform, Component parent)
    {
        if (transform == null) {return new Rectangle();}
        double parentWidth = 0;
        double parentHeight = 0;

        if (parent != null)
        {
            parentWidth = parent.getWidth();
            parentHeight = parent.getHeight();
        }

        // Make transform compatible with java swing
        int relativeWidth = (int) (parentWidth * transform.size.getXScale());
        int relativeHeight = (int) (parentHeight * transform.size.getYScale());
        int convertedWidth = transform.size.getX() + relativeWidth;
        int convertedHeight = transform.size.getY() + relativeHeight;

        int relativeX = (int) (parentWidth * transform.position.getXScale());
        int relativeY = (int) (parentHeight * transform.position.getYScale());
        int convertedX = transform.position.getX() + relativeX;
        int convertedY = transform.position.getY() + relativeY;

        for (UIConstraint constraint : transform.getConstraints())
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

        return new Rectangle(convertedX, convertedY, convertedWidth, convertedHeight);
    }

    private static void applyTransform(Component object, UITransform transform)
    {
        // Return if object or transform are null (this shouldn't really be occurring though)
        if (object == null || transform == null) {return;}

        Component parent = object.getParent();
        Rectangle calculated = transformToRectangle(transform, parent);

        if (Container.class.isAssignableFrom(parent.getClass()))
        {
            Container container = (Container) parent;
            if (container.getLayout() != null)
            {
                object.setSize(calculated.getSize());
                object.setPreferredSize(calculated.getSize());
                object.revalidate();
            }
            else
            {
                // Set bounds
                object.setBounds(calculated);
            }
        }
        else
        {
            // Set bounds
            object.setBounds(calculated);
        }
    }
    public static void update(Component object)
    {
        // Check if the component actually exists
        if (object == null) {return;}

        /** Update AppFrames **/
        if (object.getClass() == AppFrame.class)
        {
            AppFrame frame = (AppFrame) object;
            if (frame.useTransform() == true)
            {
                applyTransform((Component) frame, frame.transform);
            }
        }

        /** Update AppVisualisers **/
        else if (object.getClass() == AppVisualiser.class)
        {
            AppVisualiser visualiser = (AppVisualiser) object;
            if (visualiser.useTransform() == true)
            {
                applyTransform((Component) visualiser, visualiser.transform);
            }
        }

        /** Update AppButtons */
        else if (object.getClass() == AppButton.class)
        {
            AppButton button = (AppButton) object;
            if (button.useTransform() == true)
            {
                applyTransform((Component) button, button.transform);
            }
        }
    }

    public static void draw(Component object, Graphics g)
    {
        // Check if the component actually exists
        if (object == null) {return;}

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
