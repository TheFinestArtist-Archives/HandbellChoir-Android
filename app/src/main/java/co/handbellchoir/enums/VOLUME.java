package co.handbellchoir.enums;

/**
 * Created by Leonardo on 11/7/15.
 */
public enum Volume {

    SILENT("Silent"),
    MY_SELF("My Self"),
    ALL("All");

    public static Volume DEFAULT = MY_SELF;

    private String name;

    Volume(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Volume fromOrdinal(int ordinal) {
        for (Volume volume : values())
            if (ordinal == volume.ordinal())
                return volume;

        return DEFAULT;
    }

    public static String[] asStringList() {
        Volume[] volumes = values();
        String[] list = new String[volumes.length];
        for (int i = 0; i < volumes.length; i++)
            list[i] = volumes[i].getName();

        return list;
    }
}
