package datart.core.base.consts;

public enum FileOwner {

    USER_AVATAR("resources/user/avatar/"),

    ORG_AVATAR("resources/org/avatar/"),

    DATACHART("resources/image/datachart/"),

    DASHBOARD("resources/image/dashboard/"),

    DATA_SOURCE("resources/data/source/"),

    FILE_DATA("resources/file/data/"),

    FILE_SAVE_DATA("resources/filesave/data/"),

    STATIC_FILE_SAVE_DATA("resources/staticfile/data/"),

    SCHEDULE("schedule/files/"),

    DOWNLOAD("download/"),

    EXPORT("export/");

    private final String path;

    FileOwner(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}