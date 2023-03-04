import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class AppButton extends JButton implements MouseListener
{
    public UITransform transform;
    private Component parent;
    private Boolean useTransform = false;
    private Boolean useWaveGraphics = false;

    public Color background;
    public Color backgroundHover;
    public Color backgroundPressed;
    public Color foreground;
    private boolean mouseIn = false;
    private boolean pressed = false;
    private void init()
    {
        background = AppTheme.button.background;
        backgroundHover = AppTheme.button.backgroundHover;
        backgroundPressed = AppTheme.button.backgroundPressed;
        transform = new UITransform();
        transform.setCornerRadius(AppTheme.button.cornerRadius);

        addMouseListener(this);
        this.setSize(300, 34);
        this.setPreferredSize(this.getSize());
        this.setBackground(background);

        // Hacky - Makes awt only render the text allowing us to deal with the shape of the button
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);

        // Initialise custom fonts
        this.setFont(AppTheme.button.font.deriveFont(Font.PLAIN, 16));
        this.setHorizontalAlignment(SwingConstants.LEFT);

    }

    // Constructors
    public AppButton()
    {
        this.setText("Button");
        init();
    }

    public AppButton(String _setText)
    {
        this.setText(_setText);
        init();
    }

    // Custom button render
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

    public void mouseClicked(MouseEvent e)
    {
        this.setBackground(backgroundHover);
    }

    public void mouseEntered(MouseEvent e)
    {
        if (!pressed)
        {
            this.setBackground(backgroundHover);
        }
        else
        {
            this.setBackground(backgroundPressed);
        }

        mouseIn = true;
    }

    public void mouseExited(MouseEvent e)
    {
        this.setBackground(background);
        mouseIn = false;
    }

    public void mousePressed(MouseEvent e)
    {
        this.setBackground(backgroundPressed);
        pressed = true;
    }

    public void mouseReleased(MouseEvent e)
    {
        if (mouseIn)
        {
            this.setBackground(backgroundHover);
        }
        else
        {
            this.setBackground(background);
        }
        pressed = false;
    }

    public void useTransform(Boolean set) {useTransform = set;}
    public Boolean useTransform() {return useTransform;}
    public void useWaveGraphics(Boolean set) {useWaveGraphics = set;}
    public boolean mouseIn() {return mouseIn;}
}
