package co.handbellchoir.enums;

/**
 * Created by Leonardo on 11/7/15.
 */
public enum Shake {
    ON, OFF;

    public static Shake DEFAULT = ON;

    public static Shake fromOrdinal(int ordinal) {
        for (Shake shake : values())
            if (ordinal == shake.ordinal())
                return shake;

        return DEFAULT;
    }

    public static String[] asStringList() {
        Shake[] shakes = values();
        String[] list = new String[shakes.length];
        for (int i = 0; i < shakes.length; i++)
            list[i] = shakes[i].name();

        return list;
    }
}
