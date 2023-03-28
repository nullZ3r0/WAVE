import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Map;

public class WaveGraphics extends Thread
{
    // Static stuff
    private static Map<?, ?> desktopHints = (Map<?, ?>) Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints");
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

    private static void drawPianoKey(Graphics2D graphics, Rectangle bounds, PianoAction pianoAction, long tickCurrent)
    {
        if (pianoAction.isWhite)
        {
            // Draw the white key
            if (pianoAction.isPlaying)
            {
                graphics.setColor(AppTheme.visualiser.keyPlaying);
                graphics.fillRect(bounds.x, bounds.y - 4, bounds.width, 4);
            }

            if (pianoAction.isPlaying && !pianoAction.isFeedback)
            {
                graphics.setColor(AppTheme.visualiser.whiteNoteBackground);
            }
            else
            {
                graphics.setColor(AppTheme.visualiser.whiteKeyBackground);
            }

            if (pianoAction.isPressed)
            {
                if (pianoAction.isCorrect)
                {
                    graphics.setColor(AppTheme.visualiser.whiteKeyPressedCorrect);
                    if (pianoAction.tickStart + pianoAction.tickDuration < tickCurrent)
                    {
                        graphics.setColor(AppTheme.visualiser.whiteKeyPlaying);
                    }
                }
                else
                {
                    graphics.setColor(AppTheme.visualiser.whiteKeyPressedIncorrect);
                }
            }

            graphics.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
            graphics.setColor(AppTheme.visualiser.whiteKeyForeground);
            graphics.drawRect(bounds.x, bounds.y + 1, bounds.width - 1, bounds.height - 2);
        }
        else
        {
            // Draw the black key
            if (pianoAction.isPlaying)
            {
                graphics.setColor(AppTheme.visualiser.keyPlaying);
                graphics.fillRect(bounds.x, bounds.y - 6, bounds.width, 4);
            }

            if (pianoAction.isPlaying && !pianoAction.isFeedback)
            {
                graphics.setColor(AppTheme.visualiser.blackNoteBackground);
            }
            else
            {
                graphics.setColor(AppTheme.visualiser.blackKeyBackground);
            }

            if (pianoAction.isPressed)
            {
                if (pianoAction.isCorrect)
                {
                    graphics.setColor(AppTheme.visualiser.blackKeyPressedCorrect);
                    if (pianoAction.tickStart + pianoAction.tickDuration < tickCurrent)
                    {
                        graphics.setColor(AppTheme.visualiser.blackKeyPlaying);
                    }
                }
                else
                {
                    graphics.setColor(AppTheme.visualiser.blackKeyPressedIncorrect);
                }
            }
            graphics.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);

            // Draw a gradient on the black key for style and improved visibility
            graphics.setStroke(new BasicStroke(1));
            GradientPaint gradient = new GradientPaint(bounds.x, bounds.y, AppTheme.visualiser.blackKeyForeground, bounds.x + bounds.width, bounds.y + bounds.height, AppTheme.transparent);
            graphics.setPaint(gradient);
            graphics.drawRect(bounds.x + 2, bounds.y + 2, bounds.width - 5, bounds.height - 5);
        }
    }

    private static void drawNote(Graphics2D graphics, Rectangle bounds, PianoAction pianoAction)
    {
        graphics.setColor(AppTheme.visualiser.blackNoteBackground);
        if (pianoAction.isWhite)
        {
            graphics.setColor(AppTheme.visualiser.whiteNoteBackground);
        }

        //graphics.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);

        // Note
        bounds.height += 4;
        graphics.fillRoundRect(bounds.x, bounds.y + bounds.height, bounds.width, -bounds.height, bounds.width / 2, bounds.width / 2);

        // Front dot
        if (-bounds.height / 2 > 12)
        {
            graphics.setColor(AppTheme.visualiser.blackNoteForeground);
            if (pianoAction.isWhite)
            {
                graphics.setColor(AppTheme.visualiser.whiteNoteForeground);
            }
            int circleSize = Math.min(bounds.width / 2, 12);
            graphics.fillOval(bounds.x + (bounds.width / 2) - (circleSize/2), bounds.y - 4 - circleSize, circleSize, circleSize);
        }
    }

    public static int mapMidiValue(PianoAction pianoAction, int rootKey, int whiteKeyWidth, int blackKeyWidth)
    {
        // Calculate the distance of the next key relative to C0
        // 0 1 2 3 4 5 6 7 8 9 10 11
        // W B W B W W B W B W B  W
        int[] positionIndex = new int[12];
        // White notes
        positionIndex[0] = 0;
        positionIndex[2] = whiteKeyWidth;
        positionIndex[4] = whiteKeyWidth * 2;
        positionIndex[5] = whiteKeyWidth * 3;
        positionIndex[7] = whiteKeyWidth * 4;
        positionIndex[9] = whiteKeyWidth * 5;
        positionIndex[11] = whiteKeyWidth * 6;

        // Black notes
        positionIndex[1] = whiteKeyWidth - (int) (blackKeyWidth * 0.666);
        positionIndex[3] = whiteKeyWidth + positionIndex[2] - (int) (blackKeyWidth * 0.333);
        positionIndex[6] = whiteKeyWidth + positionIndex[5] - (int) (blackKeyWidth * 0.666);
        positionIndex[8] = whiteKeyWidth + positionIndex[7] - (blackKeyWidth / 2);
        positionIndex[10] = whiteKeyWidth + positionIndex[9] - (int) (blackKeyWidth * 0.333);

        int midiValue = pianoAction.midiValue;

        int x = 0;
        int baseOctave = (12 * (int) (Math.floor(Math.abs(midiValue/12)))) / 12;
        int relativeOctave = (12 * (int) (Math.floor(Math.abs(rootKey/12)))) / 12;
        int octave = baseOctave - relativeOctave;
        int octaveWidth = whiteKeyWidth * 7;
        int octaveOffset = octaveWidth - positionIndex[rootKey % 12];
        int octaveFirst = octave == 0 ? -positionIndex[rootKey % 12] : 0;

        if (octave == 0)
        {
            // This is the first octave
            x = 0;
        }
        else
        {
            x = octaveOffset;
            x += (octave - 1) * octaveWidth;
        }

        int keyPosition = midiValue % 12;
        return x + octaveFirst + positionIndex[keyPosition];
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
            if (desktopHints != null)
            {
                graphics.setRenderingHints(desktopHints);
            }
            graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            Dimension arcs = new Dimension(Math.min(visualiser.transform.getCornerRadius(), height), Math.min(visualiser.transform.getCornerRadius(), height));

            // Get keyboard sizing info
            MidiKeyboard keyboard = visualiser.keyboard;
            if (keyboard.useAutoDimensions() == true)
            {
                keyboard.autoDimensions(width);
            }

            int keyboardHeight = keyboard.getHeight();
            int blackKeyHeight = keyboard.getBlackKeyHeight();
            int whiteKeyWidth = keyboard.getWhiteKeyWidth();
            int blackKeyWidth = keyboard.getBlackKeyWidth();

            // Get position info
            int keyboardY = height - keyboardHeight;
            int keyboardX = keyboard.getBufferWidth();
            int playbackHeight = height - keyboardHeight;
            long tickCurrent = keyboard.tickCurrent;
            long seekTick = keyboard.getSeekTick();

            // Get keyboard colour info
            Color boardBackground = AppTheme.visualiser.boardBackground;

            // Get key info
            PianoAction[] pianoActions = keyboard.getKeys();

            // Get note info
            ArrayList<PianoAction> noteActions = visualiser.keyboard.getNoteActions();

            // Get keyboard info
            int rootKey = keyboard.rootKeyValue;
            int tailKey = keyboard.getTail();

            // Draws the background for the MIDI notes to be placed on (can have rounded corners)
            graphics.setColor(visualiser.getBackground());
            graphics.fillRoundRect(0, 0, width, height, arcs.width, arcs.height);

            // Draws the background for the piano
            graphics.setColor(boardBackground);
            graphics.fillRoundRect(0, keyboardY, width, keyboardHeight, arcs.width, arcs.height);

            // Draws the raining notes
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Rectangle whiteNoteBounds = new Rectangle(0, 0, whiteKeyWidth - 4, -50);
            Rectangle blackNoteBounds = new Rectangle(0, 0, blackKeyWidth - 4, -50);
            for (PianoAction noteAction : noteActions)
            {
                long currentPosition = noteAction.tickStart - tickCurrent;

                // Only draw notes that are in the keyboard's range and the playback's range
                if (keyboard.noteActionVisible(noteAction))
                {
                    // Draw white notes first
                    double calcY = playbackHeight * (1.0 - currentPosition / (double) seekTick);
                    double heightY = playbackHeight * (noteAction.tickDuration / (double) seekTick);

                    if (noteAction.isWhite)
                    {
                        whiteNoteBounds.y = (int) Math.round(calcY);
                        whiteNoteBounds.height = -(int) Math.round(heightY);
                        whiteNoteBounds.x = 2 + keyboardX + mapMidiValue(noteAction, rootKey, whiteKeyWidth, blackKeyWidth);
                        drawNote(graphics, whiteNoteBounds, noteAction);
                    }
                    else
                    {
                        blackNoteBounds.y = (int) Math.round(calcY);
                        blackNoteBounds.height = -(int) Math.round(heightY);
                        blackNoteBounds.x = 2 + keyboardX + mapMidiValue(noteAction, rootKey, whiteKeyWidth, blackKeyWidth);
                        drawNote(graphics, blackNoteBounds, noteAction);
                    }
                }
            }
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

            // Draws the white keys
            Rectangle whiteKeyBounds = new Rectangle(0, height - keyboardHeight, whiteKeyWidth, keyboardHeight);
            graphics.setStroke(new BasicStroke(2));
            for (PianoAction pianoAction : pianoActions)
            {
                if (pianoAction.isWhite && keyboard.pianoActionVisible(pianoAction))
                {
                    int testX = mapMidiValue(pianoAction, rootKey, whiteKeyWidth, blackKeyWidth);
                    whiteKeyBounds.x = keyboardX + testX;

                    // Calculate the distance of the next key relative to C0
                    // 0 1 2 3 4 5 6 7 8 9 10 11
                    // W B W B W W B W B W B  W
                    int keyPosition = (pianoAction.midiValue) % 12;
                    switch(keyPosition)
                    {
                        case 0:
                            // Create a octave dividers
                            graphics.setColor(AppTheme.visualiser.octaveDivider);
                            graphics.fillRect(whiteKeyBounds.x - 2, 0, 3, height - keyboardHeight);

                            // Set the font
                            int baseOctave = (12 * (int) (Math.floor(Math.abs(pianoAction.midiValue/12)))) / 12;

                            graphics.setColor(AppTheme.fadeColor(AppTheme.visualiser.foreground, 0.5));
                            graphics.setFont(AppTheme.textFont.deriveFont(16F));
                            graphics.drawString("C" + String.valueOf(baseOctave), whiteKeyBounds.x + 4, height - keyboardHeight - 4);
                            break;
                        case 5:
                            // Create a light dividers
                            graphics.setColor(AppTheme.visualiser.octaveDividerLight1);
                            graphics.fillRect(whiteKeyBounds.x - 2, 0, 3, height - keyboardHeight);
                    }

                    drawPianoKey(graphics, whiteKeyBounds, pianoAction, tickCurrent);
                }
            }

            // Draws the black keys
            Rectangle blackKeyBounds = new Rectangle(keyboardX + whiteKeyWidth - (blackKeyWidth / 2), keyboardY + 2, blackKeyWidth, blackKeyHeight);
            for (PianoAction pianoAction : pianoActions)
            {
                if (!pianoAction.isWhite && keyboard.pianoActionVisible(pianoAction))
                {
                    int testX = mapMidiValue(pianoAction, rootKey, whiteKeyWidth, blackKeyWidth);
                    blackKeyBounds.x = keyboardX + testX;
                    drawPianoKey(graphics, blackKeyBounds, pianoAction, tickCurrent);
                }
            }

            // Draw information
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setColor(AppTheme.fadeColor(AppTheme.visualiser.boardBackground, 0));

            int infoWidth = 128;
            graphics.fillRoundRect(8, 8, infoWidth * 3, 24, 16, 16);
            graphics.fillRoundRect(8, 12 + 24, infoWidth, 24, 16, 16);
            graphics.fillRoundRect(-8 + width - infoWidth, 8, infoWidth, 24, 16, 16);
            graphics.fillRoundRect(-8 + width - infoWidth, 12 + 24, infoWidth, 24, 16, 16);

            graphics.setColor(AppTheme.fadeColor(AppTheme.visualiser.foreground, 0));
            graphics.setFont(AppTheme.titleFont.deriveFont(Font.PLAIN, 14F));
            FontMetrics fontMetrics = graphics.getFontMetrics(AppTheme.titleFont.deriveFont(Font.PLAIN, 14F));

            String arrangement = "Arrangement: " + visualiser.infoMidiFileName;
            String arrangementBPM = "BPM: " + visualiser.infoMidiFileBPM;
            String arrangementZoom = "Zoom: " + keyboard.getZoom() + "%";
            String arrangementSpeed = "Speed: " + visualiser.infoMidiFileSpeed + "%";

            graphics.drawString(arrangement, 16, 24 + 1);
            graphics.drawString(arrangementBPM, 16, 52 + 1);

            graphics.drawString(arrangementZoom, width - 16 - fontMetrics.stringWidth(arrangementZoom), 24 + 1);
            graphics.drawString(arrangementSpeed, width - 16 - fontMetrics.stringWidth(arrangementSpeed), 52 + 1);
        }

        else if (object.getClass() == AppLabel.class)
        {

        }

        else if(object.getClass().isAssignableFrom(JComponent.class))
        {
            JComponent jObject = (JComponent) object;
            jObject.paint(graphics);
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
        else if (child.getClass() == AppLabel.class)
        {
            AppLabel childLabel = (AppLabel) child;
            childLabel.useWaveGraphics(true);
            childLabel.useTransform(true);
        }
    }

    private boolean enableHighQuality = true;
    private long nanosecondsPerFrame = 1666666;
    public void enableHighQuality(boolean enableHighQuality)
    {
        this.enableHighQuality = enableHighQuality;
    }

    public void enableHighQuality(boolean enableHighQuality, int targetFPS)
    {
        this.enableHighQuality = enableHighQuality;
        this.nanosecondsPerFrame = (long) (1/ (double) targetFPS * 10000000.0);
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

                if (enableHighQuality)
                {
                    // Runs at a consistent 60fps using an expensive method
                    Thread.sleep(0);
                    long start = System.nanoTime();
                    long end = 0;
                    do
                    {
                        Thread.onSpinWait();
                        end = System.nanoTime();
                    }
                    while(start + nanosecondsPerFrame >= end);
                }
                else
                {
                    // Runs at a consistent 30fps using Thread.sleep
                    Thread.sleep(32); // Around (30 fps)
                }
                //Thread.sleep(16); // Around (60 fps)
                //Thread.sleep(32); // Around (30 fps)
                //Thread.sleep(20); // Around (20 fps)
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
