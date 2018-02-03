package com.leon.filepicker.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;

public class USBStatesReceiver extends BroadcastReceiver {

    public Intent registerReceiver(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_MEDIA_CHECKING);//表明对象正在磁盘检查
        filter.addAction(Intent.ACTION_MEDIA_MOUNTED);//表明sd对象是存在并具有读/写权限
        filter.addAction(Intent.ACTION_MEDIA_EJECT);//物理的拔出 SDCARD
        filter.addAction(Intent.ACTION_MEDIA_REMOVED);//完全拔出
        filter.addDataScheme("file");
        return context.registerReceiver(this, filter);
    }

    public void unregisterReceiver(Context context) {
        context.unregisterReceiver(this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Consant.TAG_KV_CACHE, Context.MODE_PRIVATE).edit();
        if (intent.getAction().equals(Intent.ACTION_MEDIA_MOUNTED)) {
            editor.putString(Consant.USB_PATH, intent.getData().getPath());
        } else {
            editor.putString(Consant.USB_PATH, "");
        }
        editor.commit();
    }

}
