import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class displaySettingsPanel
{
    public AppFrame self;
    public AppTextField rootKeyField;
    public AppTextField keysVisibleField;
    public AppButton setButton;
    private int lastRootKeyValue = 21;
    private int lastKeysVisibleValue = 88;

    displaySettingsPanel()
    {
        // Initialise the mainPanel
        // This will contain all the elements needed for the credits
        self = new AppFrame();
        self.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 8));
        self.setBorder(new EmptyBorder(16,0,0,0));
        self.transform.setCornerRadius(4);
        self.transform.setSize(0,0,1,1);
        self.setBackground(AppTheme.backgroundDark1);

        AppLabel label = new AppLabel("D I S P L A Y   S E T T I N G S");
        label.setFont(AppTheme.titleFont.deriveFont(Font.PLAIN, 16));
        label.transform.setSize(-48, 32, 1, 0);
        WaveGraphics.addChild(self, label);

        // Show selected device
        AppLabel selectedLabel = new AppLabel("Root Note (0-115)");
        selectedLabel.setHorizontalAlignment(SwingConstants.LEFT);
        selectedLabel.setFont(AppTheme.titleFont.deriveFont(Font.PLAIN, 16));
        selectedLabel.transform.setSize(-48, 32, 1, 0);
        WaveGraphics.addChild(self, selectedLabel);

        AppFrame deviceSelectedHolder = new AppFrame();
        deviceSelectedHolder.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 0));
        deviceSelectedHolder.transform.setSize(-48, 32, 1, 0);
        deviceSelectedHolder.transform.setCornerRadius(8);
        deviceSelectedHolder.setBackground(AppTheme.backgroundDark3);
        WaveGraphics.addChild(self, deviceSelectedHolder);

        rootKeyField = new AppTextField();
        rootKeyField.setHorizontalAlignment(SwingConstants.LEFT);
        rootKeyField.setFont(AppTheme.titleFont.deriveFont(Font.PLAIN, 16));
        rootKeyField.transform.setSize(0, 0, 1, 1);
        rootKeyField.setText("21");
        WaveGraphics.addChild(deviceSelectedHolder, rootKeyField);

        // Show octaves
        AppLabel octavesLabel = new AppLabel("Keys Visible (12-127)");
        octavesLabel.setHorizontalAlignment(SwingConstants.LEFT);
        octavesLabel.setFont(AppTheme.titleFont.deriveFont(Font.PLAIN, 16));
        octavesLabel.transform.setSize(-48, 32, 1, 0);
        WaveGraphics.addChild(self, octavesLabel);

        AppFrame holder2 = new AppFrame();
        holder2.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 0));
        holder2.transform.setSize(-48, 32, 1, 0);
        holder2.transform.setCornerRadius(8);
        holder2.setBackground(AppTheme.backgroundDark3);
        WaveGraphics.addChild(self, holder2);

        keysVisibleField = new AppTextField();
        keysVisibleField.setHorizontalAlignment(SwingConstants.LEFT);
        keysVisibleField.setFont(AppTheme.titleFont.deriveFont(Font.PLAIN, 16));
        keysVisibleField.transform.setSize(0, 0, 1, 1);
        keysVisibleField.setText("88");
        WaveGraphics.addChild(holder2, keysVisibleField);

        setButton = new AppButton("Apply");
        setButton.transform.setSize(-48, 32, 1, 0);
        WaveGraphics.addChild(self, setButton);

    }

    public void applyInput(AppVisualiser appVisualiser)
    {
        if (appVisualiser == null) {return;}
        if (appVisualiser.keyboard == null) {return;}

        MidiKeyboard midiKeyboard = appVisualiser.keyboard;
        boolean[] check = new boolean[2];
        check[0] = false;
        check[1] = false;

        try
        {
            // Get text and remove all spaces
            String rootKeyInput = this.rootKeyField.getText().replaceAll("\\s+","");
            int rootKeyValue = Integer.parseInt(rootKeyInput);
            check[0] = true;

            if (rootKeyValue < 0)
            {
                rootKeyValue = 0;
            }
            else if(rootKeyValue > 115)
            {
                rootKeyValue = 115;
            }

            midiKeyboard.setRootKeyValue(rootKeyValue);
            this.rootKeyField.setText(String.valueOf(rootKeyValue));
            this.lastRootKeyValue = rootKeyValue;
        }
        catch (NumberFormatException e)
        {
            if (!check[0])
            {
                this.rootKeyField.setText(String.valueOf(this.lastRootKeyValue));
            }
        }

        try
        {
            // Get text and remove all spaces
            String keysVisibleInput = this.keysVisibleField.getText().replaceAll("\\s+","");
            int keysVisibleValue = Integer.parseInt(keysVisibleInput);
            check[1] = true;

            if (keysVisibleValue < 12)
            {
                keysVisibleValue = 12;
            }
            else if(keysVisibleValue > 127)
            {
                keysVisibleValue = 127;
            }

            midiKeyboard.setKeysVisible(keysVisibleValue);

            this.keysVisibleField.setText(String.valueOf(keysVisibleValue));
            this.lastKeysVisibleValue = keysVisibleValue;
        }
        catch (NumberFormatException e)
        {
            if (!check[1])
            {
                this.keysVisibleField.setText(String.valueOf(this.lastKeysVisibleValue));
            }
        }
    }
}
