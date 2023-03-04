import org.w3c.dom.css.Rect;

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

    private static void drawBlackKey(AppVisualiser visualiser, Graphics2D graphics, Rectangle bounds, Keyboard.PianoKey pianoKey)
    {
        // Draw the black key
        if (pianoKey.isPlaying)
        {
            graphics.setColor(AppTheme.visualiser.blackKeyPlaying);
        }
        else
        {
            graphics.setColor(AppTheme.visualiser.blackKeyBackground);
        }
        graphics.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);

        // Draw a gradient on the black key for style and improved visibility
        graphics.setStroke(new BasicStroke(1));
        GradientPaint gradient = new GradientPaint(bounds.x, bounds.y, AppTheme.visualiser.blackKeyForeground, bounds.x + bounds.width, bounds.y + bounds.height, AppTheme.transparent);
        graphics.setPaint(gradient);
        graphics.drawRect(bounds.x + 2, bounds.y + 2, bounds.width - 5, bounds.height - 5);
    }

    public static void draw(Component object, Graphics g)
    {
        // Check if the component actually exists
        if (object == null) {return;}

        int width = object.getWidth();
        int height = object.getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        /** Draw AppFrames **/
        if (object.getClass() == AppFrame.class)
        {
            AppFrame frame = (AppFrame) object;
            Dimension arcs = new Dimension(Math.min(frame.transform.getCornerRadius(), height), Math.min(frame.transform.getCornerRadius(), height));

            // Draws the panel (can have rounded corners)
            graphics.setColor(frame.getBackground());
            graphics.fillRoundRect(0, 0, width, height, arcs.width, arcs.height);
        }

        /** Draw Buttons **/
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

        /** Draw AppVisualisers **/
        else if (object.getClass() == AppVisualiser.class)
        {
            AppVisualiser visualiser = (AppVisualiser) object;
            graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            Dimension arcs = new Dimension(Math.min(visualiser.transform.getCornerRadius(), height), Math.min(visualiser.transform.getCornerRadius(), height));

            // Get keyboard sizing info
            if (visualiser.keyboard.useAutoDimensions() == true)
            {
                visualiser.keyboard.autoDimensions(width);
            }

            int keyboardHeight = visualiser.keyboard.getHeight();
            int blackKeyHeight = visualiser.keyboard.getBlackKeyHeight();
            int whiteKeyWidth = visualiser.keyboard.getWhiteKeyWidth();
            int blackKeyWidth = visualiser.keyboard.getBlackKeyWidth();

            // Get position info
            int keyboardY = height - keyboardHeight;
            int keyboardX = visualiser.keyboard.getBufferWidth();

            // Get keyboard colour info
            Color boardBackground = AppTheme.visualiser.boardBackground;
            Color whiteKeyBackground = AppTheme.visualiser.whiteKeyBackground;
            Color whiteKeyForeground = AppTheme.visualiser.whiteKeyForeground;

            // Get key info
            Keyboard.PianoKey[] pianoKeys = visualiser.keyboard.getKeys();

            // Draws the background for the MIDI notes to be placed on (can have rounded corners)
            graphics.setColor(visualiser.getBackground());
            graphics.fillRoundRect(0, 0, width, height, arcs.width, arcs.height);

            // Draws the background for the piano
            graphics.setColor(boardBackground);
            graphics.fillRoundRect(0, keyboardY, width, keyboardHeight, arcs.width, arcs.height);

            // Draws the white keys
            // Starting from A0
            int a = 0;
            graphics.setStroke(new BasicStroke(2));
            for (int i = 0; i < pianoKeys.length; i++)
            {
                if (pianoKeys[i].isWhite)
                {
                    int keyPosition = (i + 1) % 12;
                    switch(keyPosition)
                    {
                        case 3:
                            // Create a octave dividers
                            graphics.setColor(AppTheme.visualiser.octaveDivider);
                            graphics.fillRect(keyboardX + (a * whiteKeyWidth) + whiteKeyWidth - 1, 0, 2, height - keyboardHeight);
                            break;
                        case 8:
                            // Create a light dividers
                            graphics.setColor(AppTheme.visualiser.octaveDividerLight1);
                            graphics.fillRect(keyboardX + (a * whiteKeyWidth) + whiteKeyWidth - 1, 0, 2, height - keyboardHeight);
                    }

                    if (!pianoKeys[i].isPlaying)
                    {
                        graphics.setColor(whiteKeyBackground);
                    }
                    else
                    {
                        graphics.setColor(AppTheme.visualiser.whiteKeyPlaying);
                    }
                    graphics.fillRect(keyboardX + (a * whiteKeyWidth), height - keyboardHeight, whiteKeyWidth, keyboardHeight);
                    graphics.setColor(whiteKeyForeground);
                    graphics.drawRect(keyboardX + (a * whiteKeyWidth), height + 1 - keyboardHeight, whiteKeyWidth - 1, keyboardHeight - 2);
                    a++;
                }
            }

            // Draws the black keys
            // Create default bounds
            Rectangle blackKeyBounds = new Rectangle(keyboardX + whiteKeyWidth - (blackKeyWidth / 2), keyboardY + 2, blackKeyWidth, blackKeyHeight);

            for (int i = 0; i < pianoKeys.length; i++)
            {
                if (pianoKeys[i].isWhite == false)
                {
                    drawBlackKey(visualiser, graphics, blackKeyBounds, pianoKeys[i]);

                    // Calculate the distance of the next key
                    // Octave starting at A0 key colours
                    // 1 2 3 4 5 6 7 8 9 10 11 12
                    // W B W W B W B W W B  W  B

                    int keyPosition = (i + 1) % 12;
                    switch(keyPosition)
                    {
                        case 2:
                        case 3:
                        case 7:
                            blackKeyBounds.translate(whiteKeyWidth * 2, 0);
                            break;
                        default:
                            blackKeyBounds.translate(whiteKeyWidth, 0);
                    }
                }
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
        else if (child.getClass() == AppVisualiser.class)
        {
            AppVisualiser childVisualiser = (AppVisualiser) child;
            childVisualiser.useWaveGraphics(true);
            childVisualiser.useTransform(true);
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
