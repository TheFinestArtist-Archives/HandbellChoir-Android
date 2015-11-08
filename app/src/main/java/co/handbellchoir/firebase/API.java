package co.handbellchoir.firebase;

import android.support.annotation.NonNull;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

import co.handbellchoir.enums.Instrument;
import co.handbellchoir.enums.NoteOctave;

/**
 * Created by Leonardo on 11/7/15.
 */
public class API {

    private static Firebase firebase = null;
    private static final Object lock = new Object();
    private static long timestamp;

    private static Firebase getInstance() {
        synchronized (lock) {
            if (firebase != null)
                return firebase;

            firebase = new Firebase("https://handbellchoir.firebaseio.com/");
            return firebase;
        }
    }

    public static void play(Instrument instrument, NoteOctave noteOctave) {
        if (instrument == null || noteOctave == null)
            return;

        Map<String, Object> map = new HashMap<>();
        map.put("i", instrument.getFolderName());
        map.put("n", noteOctave.name());
        map.put("t", System.currentTimeMillis());
        getInstance().child("plays").push().setValue(map);
    }

    public static void setListener(@NonNull final OnPlayListener listener) {
        timestamp = System.currentTimeMillis();
        getInstance()
                .child("plays")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        try {
                            String i = (String) dataSnapshot.child("i").getValue();
                            String n = (String) dataSnapshot.child("n").getValue();
                            long t = (Long) dataSnapshot.child("t").getValue();

                            Instrument instrument = Instrument.fromName(i);
                            NoteOctave noteOctave = NoteOctave.fromName(n);
                            if (t > timestamp)
                                listener.onPlay(instrument, noteOctave);
                        } catch (Exception e) {
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
    }

    public interface OnPlayListener {
        void onPlay(Instrument instrument, NoteOctave noteOctave);
    }
}
