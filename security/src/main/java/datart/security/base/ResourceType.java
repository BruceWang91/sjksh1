package datart.security.base;

public enum ResourceType {

    SOURCE,
    VIEW,

    DATACHART("shareChart"),
    WIDGET,
    DASHBOARD("shareDashboard"),
    FOLDER,
    DATACHART_FOLDER,
    DASHBOARD_FOLDER,
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
    IMM,
    IMM_ASSET,
    IMM_DATA,
    IMM_SCREEN,
    TASK,
    SCREEN01,
    SCREEN02,
    SCREEN03,
    SCREEN04,
    SCREEN05,
    SCREEN06,
    SCREEN07,
    SCREEN08,
    SCREEN01_MODULE01;

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