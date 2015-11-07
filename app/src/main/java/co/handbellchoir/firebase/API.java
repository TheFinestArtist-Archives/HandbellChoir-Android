package co.handbellchoir.firebase;

import android.support.annotation.NonNull;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import co.handbellchoir.enums.Instrument;
import co.handbellchoir.enums.Note_Octave;

/**
 * Created by Leonardo on 11/7/15.
 */
public class API {

    private static Firebase firebase = null;
    private static final Object lock = new Object();

    private static Firebase getInstance() {
        synchronized (lock) {
            if (firebase != null)
                return firebase;

            firebase = new Firebase("https://handbellchoir.firebaseio.com/");
            return firebase;
        }
    }

    public static void play(Instrument instrument, Note_Octave noteOctave) {
        if (instrument == null || noteOctave == null)
            return;

        Firebase firebase = getInstance().child("play").push();
        firebase.child("i").setValue(instrument.getFolderName());
        firebase.child("n").setValue(noteOctave.name());
        firebase.child("t").setValue(System.currentTimeMillis());
    }

    public static void setListener(@NonNull final OnPlayListener listener) {
        getInstance().child("play").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                try {
                    String i = (String) dataSnapshot.child("i").getValue();
                    String n = (String) dataSnapshot.child("n").getValue();
                    long t = (Long) dataSnapshot.child("t").getValue();

                    Instrument instrument = Instrument.fromName(i);
                    Note_Octave noteOctave = Note_Octave.fromName(n);
                    listener.onPlay(instrument, noteOctave);
                } catch (Exception e) {
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                try {
                    String i = (String) dataSnapshot.child("i").getValue();
                    String n = (String) dataSnapshot.child("n").getValue();
                    long t = (Long) dataSnapshot.child("t").getValue();

                    Instrument instrument = Instrument.fromName(i);
                    Note_Octave noteOctave = Note_Octave.fromName(n);
                    listener.onPlay(instrument, noteOctave);
                } catch (Exception e) {
                }
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
        void onPlay(Instrument instrument, Note_Octave noteOctave);
    }
}
