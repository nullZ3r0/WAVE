import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;

public class MidiFileReader
{
    public static void main(String[] args) {
        try {
            File midiFile = new File("main/assets/midi/Sample.mid");
            // Accessing the MIDI file data using SEQUENCE class
            Sequence sequence = MidiSystem.getSequence(midiFile);

            // Variable to show the number of tracks in the MIDI file
            int trackNumber = 0;
            // Loop to go through every track of the MIDI file
            for (Track track: sequence.getTracks()) {
                trackNumber++;
                System.out.println("Track number: " + trackNumber);
                // Track size is the number of events in a track
                System.out.println("Track size: " + track.size());

                // Loop to go through every event of the current track
                for (int i=0; i<track.size(); i++) {
                    // New variable for the event
                    MidiEvent event = track.get(i);
                    System.out.println("Tick: " + event.getTick());

                    // New variable for the info of the event
                    MidiMessage message = event.getMessage();
                    if (message instanceof ShortMessage) {
                        // Don't know what this is
                        ShortMessage sm = (ShortMessage) message;
                        System.out.println("Channel: " + sm.getChannel());

                        if (sm.getCommand() == ShortMessage.NOTE_ON) {
                            // Note ON, to show on notes
                            System.out.println("Note ON; Key="+sm.getData1()+"; Velocity="+sm.getData2());
                        } else if (sm.getCommand() == ShortMessage.NOTE_OFF) {
                            // Note OFF, to show off notes
                            System.out.println("Note OFF; Key="+sm.getData1()+"; Velocity="+sm.getData2());
                        } else {
                            // Case when the note is neither ON nor OFF
                            System.out.println("Command: " + sm.getCommand());
                        }

                    } else {
                        // Case when the message is not of SHORTMESSAGE class
                        System.out.println("Other Message: " + message.getClass());
                    }
                }
            }

        }
        catch (InvalidMidiDataException | IOException e) {
            // Error handling
            throw new RuntimeException(e);
        }
    }
}