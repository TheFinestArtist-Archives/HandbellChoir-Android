package co.handbellchoir.audio;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;

import co.handbellchoir.enums.Instrument;
import co.handbellchoir.enums.Note_Octave;

/**
 * Created by Leonardo on 11/7/15.
 */
public class AudioPlayer {

    public static void play(Context context, Instrument instrument, Note_Octave noteOctave) {
        if (instrument == null || noteOctave == null)
            return;

        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });

            AssetFileDescriptor descriptor = context.getAssets().openFd("midi/" + instrument.getFolderName() + "/" + noteOctave.getFilename());
            mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();

            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
