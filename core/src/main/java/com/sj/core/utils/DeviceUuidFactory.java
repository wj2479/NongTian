package com.sj.core.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * Created by 申健 on 2018/8/12.
 */

public class DeviceUuidFactory {
    private static final String PREFS_DEVICE_ID = "device_id";
    private static UUID uuid;

    public DeviceUuidFactory() {
    }

    public static String get(Context context) {
        if (uuid == null) {
            String id = SharedPreferencesUtil.get(context, "device_id");
            if (id != null) {
                uuid = UUID.fromString(id);
            } else {
                String androidId = Settings.Secure.getString(context.getContentResolver(), "android_id");
                try {
                    if (!"9774d56d682e549c".equals(androidId)) {
                        uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
                    } else {
                        String deviceId=null;
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
                                == PackageManager.PERMISSION_GRANTED) {
                            deviceId  = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE))
                                    .getDeviceId();
                        }
                        uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID();


                    }
                } catch (UnsupportedEncodingException var4) {
                    throw new RuntimeException(var4);
                }

                SharedPreferencesUtil.save(context, "device_id", uuid.toString());
            }
        }

        return uuid.toString();
    }
}
