package co.handbellchoir.enums;

import android.support.annotation.Nullable;

/**
 * Created by Leonardo on 11/7/15.
 */
public enum NoteOctave {

    A0("A0.mp3"),
    Bb0("Bb0.mp3"),
    B0("B0.mp3"),

    C1("C1.mp3"),
    Db1("Db1.mp3"),
    D1("D1.mp3"),
    Eb1("Eb1.mp3"),
    E1("E1.mp3"),
    F1("F1.mp3"),
    Gb1("Gb1.mp3"),
    G1("G1.mp3"),
    Ab1("Ab1.mp3"),
    A1("A1.mp3"),
    Bb1("Bb1.mp3"),
    B1("B1.mp3"),

    C2("C2.mp3"),
    Db2("Db2.mp3"),
    D2("D2.mp3"),
    Eb2("Eb2.mp3"),
    E2("E2.mp3"),
    F2("F2.mp3"),
    Gb2("Gb2.mp3"),
    G2("G2.mp3"),
    Ab2("Ab2.mp3"),
    A2("A2.mp3"),
    Bb2("Bb2.mp3"),
    B2("B2.mp3"),

    C3("C3.mp3"),
    Db3("Db3.mp3"),
    D3("D3.mp3"),
    Eb3("Eb3.mp3"),
    E3("E3.mp3"),
    F3("F3.mp3"),
    Gb3("Gb3.mp3"),
    G3("G3.mp3"),
    Ab3("Ab3.mp3"),
    A3("A3.mp3"),
    Bb3("Bb3.mp3"),
    B3("B3.mp3"),

    C4("C4.mp3"),
    Db4("Db4.mp3"),
    D4("D4.mp3"),
    Eb4("Eb4.mp3"),
    E4("E4.mp3"),
    F4("F4.mp3"),
    Gb4("Gb4.mp3"),
    G4("G4.mp3"),
    Ab4("Ab4.mp3"),
    A4("A4.mp3"),
    Bb4("Bb4.mp3"),
    B4("B4.mp3"),

    C5("C5.mp3"),
    Db5("Db5.mp3"),
    D5("D5.mp3"),
    Eb5("Eb5.mp3"),
    E5("E5.mp3"),
    F5("F5.mp3"),
    Gb5("Gb5.mp3"),
    G5("G5.mp3"),
    Ab5("Ab5.mp3"),
    A5("A5.mp3"),
    Bb5("Bb5.mp3"),
    B5("B5.mp3"),

    C6("C6.mp3"),
    Db6("Db6.mp3"),
    D6("D6.mp3"),
    Eb6("Eb6.mp3"),
    E6("E6.mp3"),
    F6("F6.mp3"),
    Gb6("Gb6.mp3"),
    G6("G6.mp3"),
    Ab6("Ab6.mp3"),
    A6("A6.mp3"),
    Bb6("Bb6.mp3"),
    B6("B6.mp3"),

    C7("C7.mp3"),
    Db7("Db7.mp3"),
    D7("D7.mp3"),
    Eb7("Eb7.mp3"),
    E7("E7.mp3"),
    F7("F7.mp3"),
    Gb7("Gb7.mp3"),
    G7("G7.mp3"),
    Ab7("Ab7.mp3"),
    A7("A7.mp3"),
    Bb7("Bb7.mp3"),
    B7("B7.mp3"),

    C8("C8.mp3");

    public static NoteOctave DEFAULT = C4;

    private String filename;

    NoteOctave(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    @Nullable
    public static NoteOctave fromName(String name) {
        for (NoteOctave noteOctave : values())
            if (noteOctave.name().equals(name))
                return noteOctave;

        return null;
    }

    public static NoteOctave fromOrdinal(int ordinal) {
        for (NoteOctave noteOctave : values())
            if (ordinal == noteOctave.ordinal())
                return noteOctave;

        return DEFAULT;
    }

    public static String[] asStringList() {
        NoteOctave[] noteOctaves = values();
        String[] list = new String[noteOctaves.length];
        for (int i = 0; i < noteOctaves.length; i++)
            list[i] = noteOctaves[i].name();

        return list;
    }

    public int getMidiNumber() {
        return ordinal() + 21;
    }

}
