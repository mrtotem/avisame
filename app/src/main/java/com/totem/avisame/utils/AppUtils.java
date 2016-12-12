package com.totem.avisame.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Collection;

public class AppUtils {

    private static final int REQUEST_LOCATION_PERMISSION = 1;

    public static boolean requestPermissions(Activity activity, String... permissions) {

        Collection<String> permissionsToRequest = new ArrayList<>();
        boolean granted = true;
        for (String permission : permissions) {

            if (ActivityCompat.checkSelfPermission(activity, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                granted = false;
                permissionsToRequest.add(permission);
            }
        }
        if (!granted) {
            ActivityCompat.requestPermissions(activity,
                    permissionsToRequest.toArray(new String[]{}),
                    REQUEST_LOCATION_PERMISSION);
        }

        return granted;
    }

    public static boolean checkPermissions(Activity activity, String... permissions) {

        for (String permission : permissions) {

            if (ActivityCompat.checkSelfPermission(activity, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}
