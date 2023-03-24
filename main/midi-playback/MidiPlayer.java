import javax.sound.midi.*;

// Imported class, look into it and learn
public class MidiPlayer
{
    // https://docs.oracle.com/javase/7/docs/api/javax/sound/midi/Synthesizer.html
    Boolean isReady = false;
    Synthesizer midiSynth;
    Instrument[] instruments;
    MidiChannel[] midiChannels;
    MidiPlayer()
    {
        try
        {
            // Create a new Synthesiser and open it
            midiSynth = MidiSystem.getSynthesizer();
            midiSynth.open();

            //get and load default instrument and channel lists
            instruments = midiSynth.getDefaultSoundbank().getInstruments();
            midiChannels = midiSynth.getChannels();

            // load an instrument
            midiSynth.loadInstrument(instruments[0]);
            isReady = true;
        } catch (MidiUnavailableException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void playNote(int keyValue, int velocity)
    {
        if (this.isReady)
        {
            // Turn note on with specific velocity
            this.midiChannels[0].noteOn(keyValue, velocity);
        }
    }

    public void stopNote(int keyValue)
    {
        if (this.isReady)
        {
            // Turn the note off
            this.midiChannels[0].noteOff(keyValue);
        }
    }

}
