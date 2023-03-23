import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import java.util.Observable;

public class MidiInputReceiver implements Receiver
{
    public String name;
    public MidiDevice device;
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

            Wave.changePianoKeyPressed(keyValue, keyVelocity, keyPressed);
        }
    }

    public void close() {}
}