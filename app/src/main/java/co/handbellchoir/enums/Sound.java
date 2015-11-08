package co.handbellchoir.enums;

/**
 * Created by Leonardo on 11/7/15.
 */
public enum Sound {

    SILENT("Silent"),
    MY_SELF("My Self"),
    ALL("All");

    public static Sound DEFAULT = SILENT;

    private String name;

    Sound(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Sound fromOrdinal(int ordinal) {
        for (Sound sound : values())
            if (ordinal == sound.ordinal())
                return sound;

        return DEFAULT;
    }

    public static String[] asStringList() {
        Sound[] sounds = values();
        String[] list = new String[sounds.length];
        for (int i = 0; i < sounds.length; i++)
            list[i] = sounds[i].getName();

        return list;
    }
}
