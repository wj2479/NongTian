package com.qdhc.ny.service

import android.annotation.TargetApi
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.Build
import android.os.IBinder
import com.qdhc.ny.MainActivity
import com.qdhc.ny.R


class LocationForegoundService0518 : Service() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun onBind(intent: Intent): IBinder? {
        return mBinder
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        //Android O上才显示通知栏
        if (Build.VERSION.SDK_INT >= 26) {
            showNotify()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    //显示通知栏
    fun showNotify() {
        val builder = Notification.Builder(applicationContext)
        val nfIntent = Intent(this, MainActivity::class.java)
        builder.setContentIntent(PendingIntent.getActivity(this, 0, nfIntent, 0))
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("正在后台定位")
                .setContentText("定位进行中")
                .setWhen(System.currentTimeMillis())
        val notification = builder.build()
        notification.defaults = Notification.DEFAULT_SOUND
        //调用这个方法把服务设置成前台服务
        startForeground(110, notification)
    }

    private val mBinder = LocalBinder()

    inner class LocalBinder : Binder() {
        internal val service: LocationForegoundService0518
            get() = this@LocationForegoundService0518
    }

    
}
