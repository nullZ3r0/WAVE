import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.List;

public class MidiConnector
{
    public MidiDevice.Info[] devices;
    public ArrayList<MidiInputReceiver> transmitters = new ArrayList<MidiInputReceiver>();
    public ArrayList<MidiInputReceiver> receivers = new ArrayList<MidiInputReceiver>();

    MidiConnector()
    {
        refreshDevices();
        refreshReceivers();
    }

    public void refreshDevices()
    {
        devices = MidiSystem.getMidiDeviceInfo();
        if (devices.length == 0)
        {
            System.out.println("No devices found!");
        }
    }

    public void printDevices()
    {
        // Attempt to refresh
        if (devices.length == 0) {refreshDevices();}

        for (int i = 0; i < devices.length; i++)
        {
            try
            {
                MidiDevice device = MidiSystem.getMidiDevice(devices[i]);
                System.out.println("[Device] " + i + " " + device.getDeviceInfo().toString());
                // NOTE: if a loopback driver is being used, you will see "duplicate" entries, however
                //       one of them is actually used as a receiver, while the other is a transmitter

                // Check if the device has a transmitter
                Transmitter transmitter = device.getTransmitter();
                if (transmitter != null)
                {
                    System.out.println("    This device has a transmitter");
                }

                // Check if the device has a receiver
                Receiver receiver = device.getReceiver();
                if (receiver != null)
                {
                    System.out.println("    This device has a receiver");
                }
            }
            catch (MidiUnavailableException e) {}
        }
    }

    public void refreshReceivers()
    {
        // Attempt to refresh
        if (devices.length == 0) {refreshDevices();}

        // Find transmitters
        for (int i = 0; i < devices.length; i++)
        {
            try
            {
                MidiDevice device = MidiSystem.getMidiDevice(devices[i]);
                device.close();

                // Check if the device has a transmitter
                Transmitter transmitter = device.getTransmitter();
                if (transmitter != null)
                {
                    // Create a java receiver for the device
                    MidiInputReceiver inputReceiver = new MidiInputReceiver(device.getDeviceInfo().toString(), device);
                    transmitter.setReceiver(inputReceiver);
                    receivers.add(inputReceiver);
                }
            }
            catch (MidiUnavailableException e) {}
        }

        if (receivers.size() == 0)
        {
            System.out.println("No devices with transmitters found!");
        }
    }

    public void printReceivers()
    {
        // Attempt to refresh
        if (receivers.size() == 0) {refreshReceivers();}

        for (int i = 0; i < receivers.size(); i++)
        {
            System.out.println("[Java Receiver] " + i + " " + receivers.get(i).name);
            if (receivers.get(i).isListening())
            {
                System.out.println("    This receiver is listening for input");
            }
            else
            {
                System.out.println("    This receiver is not listening for input");
            }
        }
    }

    public void closeReceiver(MidiInputReceiver receiver)
    {
        receiver.stopListening();
        receiver.device.close();
        this.receivers.remove(receiver);
    }

    public MidiInputReceiver findReceiver(String name)
    {
        for (int i = 0; i < receivers.size(); i++)
        {
            if (receivers.get(i).name.matches(name))
            {
                return receivers.get(i);
            }
        }

        return null;
    }
}
