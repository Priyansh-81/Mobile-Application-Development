
package com.priyansh.myapplication;

import android.graphics.drawable.Drawable;

public class AppInfo {
    private String name;
    private String packageName;
    private Drawable icon;
    private String version;
    private long size;
    private boolean isSystemApp;

    public AppInfo(String name, String packageName, Drawable icon, String version, long size, boolean isSystemApp) {
        this.name = name;
        this.packageName = packageName;
        this.icon = icon;
        this.version = version;
        this.size = size;
        this.isSystemApp = isSystemApp;
    }

    public String getName() {
        return name;
    }

    public String getPackageName() {
        return packageName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public String getVersion() {
        return version;
    }

    public long getSize() {
        return size;
    }

    public boolean isSystemApp() {
        return isSystemApp;
    }
}
