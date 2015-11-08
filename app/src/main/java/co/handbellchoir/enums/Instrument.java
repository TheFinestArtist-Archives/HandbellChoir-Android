package co.handbellchoir.enums;

import android.support.annotation.Nullable;

/**
 * Created by Leonardo on 11/7/15.
 */
public enum Instrument {

    ELECTRIC_GRAND_PIANO("electric_grand_piano", "Electric Grand Piano"),
    OVERDRIVEN_GUITAR("overdriven_guitar", "Overdriven_Guitar"),
    PAD_1_NEW_AGE("pad_1_new_age", "Pad 1 New Age"),
    TINKLE_BELL("tinkle_bell", "Tinkle Bells"),
    TUBULAR_BELLS("tubular_bells", "Tubular Bells");

    public static Instrument DEFAULT = TUBULAR_BELLS;

    private String folderName;
    private String name;

    Instrument(String folderName, String name) {
        this.folderName = folderName;
        this.name = name;
    }

    public String getFolderName() {
        return folderName;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public static Instrument fromName(String name) {
        for (Instrument instrument : values())
            if (instrument.getFolderName().equals(name))
                return instrument;

        return null;
    }

    public static Instrument fromOrdinal(int ordinal) {
        for (Instrument instrument : values())
            if (ordinal == instrument.ordinal())
                return instrument;

        return DEFAULT;
    }

    public static String[] asStringList() {
        Instrument[] instruments = values();
        String[] list = new String[instruments.length];
        for (int i = 0; i < instruments.length; i++)
            list[i] = instruments[i].getName();

        return list;
    }
}
