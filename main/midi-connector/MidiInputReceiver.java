import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import java.util.Observable;

public class MidiInputReceiver extends Observable implements Receiver
{
    public String name;
    public MidiDevice device;
    private AppVisualiser visualiser;
    private Wave wave;
    private boolean isListening;
    public MidiInputReceiver(String name, MidiDevice device)
    {
        this.name = name;
        this.device = device;
        this.isListening = false;
    }

    public void startListening()
    {
        this.isListening = true;
        if (!this.device.isOpen())
        {
            try
            {
                this.device.open();
            }
            catch (MidiUnavailableException e)
            {
                System.out.println("Failed to open device!");
                this.isListening = false;
            }
        }
    }

    public void stopListening()
    {
        this.isListening = false;
    }

    public boolean isListening()
    {
        return isListening;
    }

    public void send(MidiMessage msg, long timeStamp)
    {
        if (this.isListening)
        {
            if (this.visualiser != null)
            {
                // Send data to the visualiser
                byte[] convertedData = msg.getMessage();

                // convertedData[0] is the status byte of the message.
                // convertedData[1] is the note value as an int.
                // convertedData[2] is the note velocity.

                int keyValue = convertedData[1];
                int keyVelocity = convertedData[2];
                boolean keyPressed;
                if (convertedData.length == 3)
                {
                    keyPressed = keyVelocity > 0;
                }
                else
                {
                    keyPressed = convertedData[3] > 0;
                }

                this.visualiser.keyboard.changeKeyPressed(keyValue, keyVelocity, keyPressed);
            }
        }
    }

    public void setVisualiser(AppVisualiser visualiser)
    {
        this.visualiser = visualiser;
    }

    public void close() {}
}