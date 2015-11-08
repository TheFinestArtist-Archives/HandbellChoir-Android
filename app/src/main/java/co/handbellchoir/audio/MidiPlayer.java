package co.handbellchoir.audio;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Build;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import co.handbellchoir.enums.Instrument;
import co.handbellchoir.enums.NoteOctave;

/**
 * Created by Leonardo on 11/7/15.
 */
public class MidiPlayer {

    private static final Object mSingletonLock = new Object();
    private static SoundPool soundPool;
    private static ConcurrentMap<String, Integer> soundMap = new ConcurrentHashMap<>();
    private static ConcurrentMap<Integer, Boolean> soundLoaded = new ConcurrentHashMap<>();
    private static final int MAX_STEAMS = 100;

    private static SoundPool getInstance(Context context) {
        synchronized (mSingletonLock) {
            if (soundPool != null)
                return soundPool;

            if (Build.VERSION.SDK_INT >= 21) {
                soundPool = new SoundPool.Builder().setMaxStreams(MAX_STEAMS).build();
            } else {
                soundPool = new SoundPool(MAX_STEAMS, AudioManager.STREAM_MUSIC, 0);
            }
            return soundPool;
        }
    }

    public static void load(final Context context) {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                SoundPool soundPool = getInstance(context);
                soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                    @Override
                    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                        if (status == 0) {
                            soundLoaded.put(sampleId, true);
                        }
                    }
                });

                // Load Sounds
                for (Instrument instrument : Instrument.values()) {
                    for (NoteOctave noteOctave : NoteOctave.values()) {
                        try {
                            int sampleId = soundPool.load(context.getAssets()
                                    .openFd("midi/" + instrument.getFolderName()
                                            + "/" + noteOctave.getFilename()), 1);

                            soundMap.put(getKey(instrument, noteOctave), sampleId);
                        } catch (Exception e) {
                        }
                    }
                }
            }
        });
    }

    private static boolean isLoaded(Instrument instrument, NoteOctave noteOctave) {
        try {
            int sampleId = soundMap.get(getKey(instrument, noteOctave));
            return soundLoaded.get(sampleId);
        } finally {
            return false;
        }
    }

    public static void play(Context context, Instrument instrument, NoteOctave noteOctave) {
        if (instrument == null || noteOctave == null)
            return;

        if (isLoaded(instrument, noteOctave))
            getInstance(context).play(soundMap.get(getKey(instrument, noteOctave)), 1, 1, 1, 1, 1f);
        else
            AudioPlayer.play(context, instrument, noteOctave);
    }

    private static String getKey(Instrument instrument, NoteOctave noteOctave) {
        return instrument.getFolderName() + "_" + noteOctave.name();
    }
}
