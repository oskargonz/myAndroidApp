package com.example.mini_project_2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.*

class MyService : Service() {
    //implementacja wyswietlania notyfikacji

    private var id = 0

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        createChannel(this) //tak samo jak nizej w funkcji createChannle - powinienem dodac na gorzez @Requires...
        //tworze notyfikacje ale najpierw musze miec intent i przepustke
        //val intent_1 = Intent(context, "com.example.listazakupw.ProductListActivity") //intent - powiazany z aktywnoscia jaka ma byc wykonana - bedzie otwierala sie secondary activity

        val intent_1 = Intent()
        //intent_1.component = ComponentName("com.example.listazakupw", "com.example.listazakupw.ProductListActivity")
        intent_1.component =  ComponentName("com.example.listazakupw", "com.example.listazakupw.ProductListActivity")

        val pendingIntent = PendingIntent.getActivity(
                this,
                id,
                intent_1,
                PendingIntent.FLAG_ONE_SHOT
        )//przepustka


        //tworze notyfikacje
        val noti = NotificationCompat.Builder(this, getString(R.string.channelID)) //jak tu bylo channelName to nie dzialala notyfikacja
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Dodano produkt")
                .setContentText(intent?.getStringExtra("nazwa"))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()
        //val notificationManager = NotificationManagerCompat.from(this)
        //notificationManager.notify(id++, noti)
        NotificationManagerCompat.from(this).notify(id++, noti)

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createChannel(context: Context){
        //tu powininem kliknac prawym na NotificationChannel i powinno wyskoczyc cos wyzej @RequiresApi ... - moze byc blad wtedy nagranie 16:00
        // nizej wrzucone do strings = bylo "channelStudent", "Dodawanie studentów",
        val notiChanel = NotificationChannel(
                context.getString(R.string.channelID),
                context.getString(R.string.channelName),
                NotificationManager.IMPORTANCE_DEFAULT
        )
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.createNotificationChannel(notiChanel)

        //NotificationManagerCompat.from(context).createNotificationChannel(notiChanel) //na wykladzie bylo to inaczej opisane 1:12:00
    }

    /*
    override fun onBind(intent: Intent): IBinder {
        return mBinder

        //startService(Intent_Mój)
        //zamiast mBound w onstop startForegroundService()


    }

     */


}