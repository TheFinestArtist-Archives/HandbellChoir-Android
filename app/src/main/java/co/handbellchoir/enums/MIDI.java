package co.handbellchoir.enums;

/**
 * Created by Leonardo on 11/7/15.
 */
public enum MIDI {

    TINKLE_BELL("tinkel_bell", "Tinkle Bells"),
    TUBULAR_BELLS("tubular_bells", "Tubular Bells");

    private String folderName;
    private String name;

    MIDI(String folderName, String name) {
        this.folderName = folderName;
        this.name = name;
    }

    public String getFolderName() {
        return folderName;
    }

    public String getName() {
        return name;
    }
}
