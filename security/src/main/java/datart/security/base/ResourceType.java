package datart.security.base;

public enum ResourceType {

    SOURCE,
    VIEW,

    DATACHART("shareChart"),
    WIDGET,
    DASHBOARD("shareDashboard"),
    FOLDER,
    STORYBOARD("shareStoryPlayer"),
    VIZ,
    FILE,
    EXCEL_VIEW,
    REPORT,
    EXCEL_TEMPLATE,
    SYSTEM,
    DEPARTMENT,
    PERMISSION,
    CATEGORY,
    VARIABLE,
    RESOURCE_MIGRATION,
    DATA_ACCESS,
    DATA_IMPORT,
    VIZ_DATACHART,
    VIZ_DASHBOARD,
    SHARE,
    DOWNLOAD,
    MANAGER,
    SCHEDULE,
    ROLE,
    USER,
    TASK;

    private String shareRoute;

    ResourceType() {
    }

    ResourceType(String shareRoute) {
        this.shareRoute = shareRoute;
    }

    public String getShareRoute() {
        return shareRoute;
    }
}