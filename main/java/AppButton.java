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
    public Color background;
    public Color backgroundHover;
    public Color backgroundPressed;
    public Color foreground;
    private int cornerRadius = 0;
    private boolean mouseIn = false;
    private boolean pressed = false;
    private void init()
    {
        background = AppTheme.button.background;
        backgroundHover = AppTheme.button.backgroundHover;
        backgroundPressed = AppTheme.button.backgroundPressed;
        cornerRadius = AppTheme.button.cornerRadius;

        addMouseListener(this);
        this.setSize(400, 34);
        this.setPreferredSize(this.getSize());
        this.setBackground(background);

        // Hacky - Makes awt only render the text allowing us to deal with the shape of the button
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);

        // Initialise custom fonts
        this.setFont(AppTheme.button.font.deriveFont(Font.PLAIN, 16));
    }

    // Constructors
    public AppButton()
    {
        this.setText("Button");
        init();
    }

    // Custom button render
    @Override
    protected void paintComponent(Graphics g)
    {
        int width = getWidth();
        int height = getHeight();
        Dimension arcs = new Dimension(Math.min(cornerRadius, height), Math.min(cornerRadius, height));
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draws the rounded panel with borders.
        graphics.setColor(this.getBackground());
        graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height); //paint background
        //graphics.setColor(getForeground());
        //graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height); //paint border

        // Draw the button text, image etc
        super.paintComponent(g);
    }

    public AppButton(String _setText)
    {
        this.setText(_setText);
        init();
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

    public void setCornerRadius(int _cornerRadius)
    {
        cornerRadius = _cornerRadius;
    }
}
