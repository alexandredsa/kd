package me.alexandredsa.kd.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import me.alexandredsa.kd.service.LocationService;

/**
 * Created by Alexandre on 23/02/2016.
 */
public class LocationServiceReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 34575;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, LocationService.class);
        context.startService(i);
    }
}
