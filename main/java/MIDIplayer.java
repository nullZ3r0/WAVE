import java.io.File;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

public class MIDIplayer {
    public static void main(String[] args) {
        try {
            // Load the MIDI file as SEQUENCE class
            Sequence sequence = MidiSystem.getSequence(new File("test01.mid"));
            // Introduce sequencer to handle the MIDI data
            Sequencer sequencer = MidiSystem.getSequencer();
            if (sequencer==null) {
                System.err.println("Sequencer device not supported");
                return;
            }
            sequencer.open(); // Open device
            sequencer.setSequence(sequence); // load it into sequencer
            sequencer.start();  // start the playback
        } catch (MidiUnavailableException | InvalidMidiDataException | IOException ex) {
            ex.printStackTrace();
        }
    }
}