import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class deviceSettingsPanel
{
    public AppFrame self;
    public MidiConnector midiConnector;
    private AppButton[] deviceButtons;
    private AppLabel deviceSelected;

    deviceSettingsPanel()
    {
        // Initialise the mainPanel
        // This will contain all the elements needed for the credits
        self = new AppFrame();
        self.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 8));
        self.setBorder(new EmptyBorder(16,0,0,0));
        self.transform.setCornerRadius(4);
        self.transform.setSize(0,0,1,1);
        self.setBackground(AppTheme.backgroundDark1);

        AppLabel label = new AppLabel("D E V I C E   S E T T I N G S");
        label.setFont(AppTheme.titleFont.deriveFont(Font.PLAIN, 16));
        label.transform.setSize(-48, 32, 1, 0);
        WaveGraphics.addChild(self, label);

        // Show selected device
        AppLabel selectedLabel = new AppLabel("Selected");
        selectedLabel.setHorizontalAlignment(SwingConstants.LEFT);
        selectedLabel.setFont(AppTheme.titleFont.deriveFont(Font.PLAIN, 16));
        selectedLabel.transform.setSize(-48, 32, 1, 0);
        WaveGraphics.addChild(self, selectedLabel);

        AppFrame deviceSelectedHolder = new AppFrame();
        deviceSelectedHolder.setLayout(new FlowLayout(FlowLayout.CENTER, 16, 0));
        deviceSelectedHolder.setBorder(new EmptyBorder(0,32,0,0));
        deviceSelectedHolder.transform.setSize(-48, 32, 1, 0);
        deviceSelectedHolder.transform.setCornerRadius(8);
        deviceSelectedHolder.setBackground(AppTheme.backgroundDark3);
        WaveGraphics.addChild(self, deviceSelectedHolder);

        deviceSelected = new AppLabel("None");
        deviceSelected.setHorizontalAlignment(SwingConstants.LEFT);
        deviceSelected.setFont(AppTheme.titleFont.deriveFont(Font.PLAIN, 16));
        deviceSelected.transform.setSize(0, 0, 1, 1);
        //deviceSelected.transform.setPosition(0, 0, 0, 0);
        WaveGraphics.addChild(deviceSelectedHolder, deviceSelected);

        AppLabel label2 = new AppLabel("Input MIDI Device");
        label2.setHorizontalAlignment(SwingConstants.LEFT);
        label2.setFont(AppTheme.titleFont.deriveFont(Font.PLAIN, 16));
        label2.transform.setSize(-48, 32, 1, 0);
        WaveGraphics.addChild(self, label2);

        // Initialise dummy buttons
        deviceButtons = new AppButton[5];

        for (int i = 0; i < 5; i++)
        {
            AppButton dummyButton = new AppButton("MIDI Device " + String.valueOf(i + 1));
            dummyButton.transform.setSize(-48, 32, 1, 0);
            dummyButton.addActionListener(e -> WaveAPI.debugButton());
            WaveGraphics.addChild(self, dummyButton);
            deviceButtons[i] = dummyButton;
        }

    }

    deviceSettingsPanel(MidiConnector midiConnector)
    {
        this();
        this.setMidiConnector(midiConnector);
    }

    public void setMidiConnector(MidiConnector midiConnector)
    {
        this.midiConnector = midiConnector;
    }

    public void removeDeviceButtons()
    {
        for (AppButton button : this.deviceButtons)
        {
            WaveGraphics.removeChild(this.self, button);
        }
    }

    public void refreshSelectedDevices()
    {
        if (this.midiConnector != null)
        {

        }
    }

    public void selectDevice(int index)
    {
        if (this.midiConnector != null)
        {
            this.midiConnector.listenToReceiver(index);
            MidiInputReceiver midiInputReceiver = this.midiConnector.findReceiver(index);
            Wave.setMidiInputReceiver(midiInputReceiver);
            this.deviceSelected.setText(midiInputReceiver.name);
        }
    }
    public void refreshDevices()
    {
        if (this.midiConnector != null)
        {
            ArrayList<MidiInputReceiver> receivers = this.midiConnector.receivers;
            this.removeDeviceButtons();
            deviceButtons = new AppButton[receivers.size() + 1];
            for (int i = 0; i < receivers.size(); i++)
            {
                AppButton dummyButton = new AppButton(receivers.get(i).name);
                dummyButton.transform.setSize(-48, 32, 1, 0);
                final int value = i;
                dummyButton.addActionListener(e -> this.selectDevice(value));
                WaveGraphics.addChild(self, dummyButton);
                deviceButtons[i] = dummyButton;
            }
        }
    }

    public void refresh()
    {
        refreshSelectedDevices();
        refreshDevices();
    }
}

