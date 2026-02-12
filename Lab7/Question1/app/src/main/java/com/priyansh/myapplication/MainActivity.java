package com.priyansh.myapplication;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AppsAdapter.OnAppLongClickListener {

    private List<AppInfo> appList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView appsRecyclerView = findViewById(R.id.appsRecyclerView);
        appsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadInstalledApps();
        AppsAdapter adapter = new AppsAdapter(appList, this);
        appsRecyclerView.setAdapter(adapter);
    }

    private void loadInstalledApps() {
        appList = new ArrayList<>();
        PackageManager packageManager = getPackageManager();
        List<PackageInfo> packages = packageManager.getInstalledPackages(PackageManager.GET_PERMISSIONS);

        for (PackageInfo packageInfo : packages) {
            String appName = packageInfo.applicationInfo.loadLabel(packageManager).toString();
            String packageName = packageInfo.packageName;
            String versionName = packageInfo.versionName;
            long appSize = new File(packageInfo.applicationInfo.sourceDir).length();

            boolean isSystemApp = (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;

            AppInfo appInfo = new AppInfo(
                    appName,
                    packageName,
                    packageInfo.applicationInfo.loadIcon(packageManager),
                    versionName,
                    appSize,
                    isSystemApp
            );
            appList.add(appInfo);
        }
    }

    @Override
    public void onAppLongClick(AppInfo appInfo) {
        showOptionsDialog(appInfo);
    }

    private void showOptionsDialog(final AppInfo appInfo) {
        String[] options = {"Open App", "App Details", "Uninstall"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(appInfo.getName());
        builder.setItems(options, (dialog, which) -> {
            switch (which) {
                case 0:
                    openApp(appInfo.getPackageName());
                    break;
                case 1:
                    showAppDetails(appInfo);
                    break;
                case 2:
                    confirmUninstall(appInfo.getPackageName());
                    break;
            }
        });

        String appType = appInfo.isSystemApp() ? "System App" : "User-Installed App";
        String specialPermissions = getSpecialPermissions(appInfo.getPackageName());

        builder.setMessage(appType + "\n" + specialPermissions);

        builder.setPositiveButton("OK", null);
        builder.create().show();
    }

    private void openApp(String packageName) {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage(packageName);
        if (launchIntent != null) {
            startActivity(launchIntent);
        } else {
            Toast.makeText(this, "Could not open the app", Toast.LENGTH_SHORT).show();
        }
    }

    private void showAppDetails(AppInfo appInfo) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + appInfo.getPackageName()));
        startActivity(intent);
    }

    private void confirmUninstall(final String packageName) {
        new AlertDialog.Builder(this)
                .setTitle("Uninstall App")
                .setMessage("Are you sure you want to uninstall this app?")
                .setPositiveButton("Yes", (dialog, which) -> uninstallApp(packageName))
                .setNegativeButton("No", null)
                .show();
    }

    private void uninstallApp(String packageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + packageName));
        startActivity(intent);
    }

    private String getSpecialPermissions(String packageName) {
        PackageManager pm = getPackageManager();
        StringBuilder permissionsText = new StringBuilder("Special Permissions: ");
        boolean hasSpecialPermission = false;
        try {
            PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
            String[] requestedPermissions = packageInfo.requestedPermissions;
            if (requestedPermissions != null) {
                for (String permission : requestedPermissions) {
                    if (permission.equals("android.permission.CAMERA")) {
                        permissionsText.append("Camera, ");
                        hasSpecialPermission = true;
                    }
                    if (permission.equals("android.permission.ACCESS_FINE_LOCATION") || permission.equals("android.permission.ACCESS_COARSE_LOCATION")) {
                        permissionsText.append("Location, ");
                        hasSpecialPermission = true;
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (hasSpecialPermission) {
            return permissionsText.substring(0, permissionsText.length() - 2);
        } else {
            return "Special Permissions: None";
        }
    }
}
