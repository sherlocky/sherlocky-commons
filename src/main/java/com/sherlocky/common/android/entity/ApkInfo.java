package com.sherlocky.common.android.entity;

public class ApkInfo {
    // 应用名称
    private String label;
    // 应用包名
    private String packageName;
    // 版本号
    private String versionCode;
    // 当前版本
    private String versionName;
    // 应用主Activity名
    private String mainActivity;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(String mainActivity) {
        this.mainActivity = mainActivity;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "com.sherlocky.common.android.entity.ApkInfo{" +
                "packageName='" + packageName + '\'' +
                ", versionCode='" + versionCode + '\'' +
                ", versionName='" + versionName + '\'' +
                ", mainActivity='" + mainActivity + '\'' +
                '}';
    }
}
