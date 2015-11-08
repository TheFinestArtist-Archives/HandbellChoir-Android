package co.handbellchoir.firebase;

import android.support.annotation.NonNull;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import co.handbellchoir.enums.Instrument;
import co.handbellchoir.enums.NoteOctave;
import co.handbellchoir.models.Note;
import co.handbellchoir.models.Notes;
import co.handbellchoir.models.Play;

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

        Play play = new Play();
        play.setN(noteOctave.name());
        play.setT(System.currentTimeMillis());
        getInstance().child("plays").push().setValue(play);
    }

    public static void setListener(@NonNull final OnPlayListener listener) {
        timestamp = System.currentTimeMillis();
        getInstance()
                .child("plays")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Play play = dataSnapshot.getValue(Play.class);
                        NoteOctave noteOctave = NoteOctave.fromName(play.getN());
                        if (play.getT() > timestamp)
                            listener.onPlay(null, noteOctave);
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

    public static void findEmptyNoteOctave(@NonNull final OnFoundEmptyNoteOctaveListener listener) {
        getInstance().child("notes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Notes notes = dataSnapshot.getValue(Notes.class);
                NoteOctave[] noteOctaves = NoteOctave.values();
                for (int i = 39; i < noteOctaves.length + 39; i++) {
                    NoteOctave noteOctave = noteOctaves[i % noteOctaves.length];

                    boolean taken = false;
                    for (Note note : notes.getNotes())
                        if (noteOctave.name().equals(note.getK()) && note.getO() != null)
                            taken = true;

                    if (!taken) {
                        listener.onFound(noteOctave);
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public interface OnFoundEmptyNoteOctaveListener {
        void onFound(NoteOctave noteOctave);
    }

    public static void assignNoteOctave(NoteOctave noteOctave, boolean assign) {
        if (noteOctave == null)
            return;

        Firebase firebase = getInstance()
                .child("notes")
                .child("" + noteOctave.getMidiNumber())
                .child("o");

        if (assign) {
            firebase.setValue("X");
        } else {
            firebase.removeValue();
        }
    }
}
