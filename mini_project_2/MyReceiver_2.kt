package com.example.mini_project_2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


class MyReceiver_2 : BroadcastReceiver() {

    private var id = 0



    // Dodałem <intent-filter> w android manifest
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {

        val intent = Intent(context, MyService::class.java)
        val id = intent.getIntExtra("id", -1)
        intent.putExtra("id", id)
        context.startForegroundService(intent)




        //intent.component = ComponentName("com.example.mini_project_2",  "com.example.mini_project_2.MyService")
        //startForegroundService(intent)

//        if(intent.action == context.getString("com.example.mini_project_2.MyReceiver_2")){ // bylo "com.example.helloworld.DODANO_PRODUKT" i dodalem to do strin resources
            // tworze notyfikacje ale wczesniej potrzebuije kanalu notyfikacji - nizen w fun createChanel
/*
        createChannel(context) //tak samo jak nizej w funkcji createChannle - powinienem dodac na gorzez @Requires...
            //tworze notyfikacje ale najpierw musze miec intent i przepustke
            //val intent_1 = Intent(context, "com.example.listazakupw.ProductListActivity") //intent - powiazany z aktywnoscia jaka ma byc wykonana - bedzie otwierala sie secondary activity

        val intent_1 = Intent()
        //intent_1.component = ComponentName("com.example.listazakupw", "com.example.listazakupw.ProductListActivity")
        intent_1.component =  ComponentName("com.example.listazakupw", "com.example.listazakupw.ProductListActivity")

        val pendingIntent = PendingIntent.getActivity(
                context,
                id,
                intent_1,
                PendingIntent.FLAG_ONE_SHOT
        )//przepustka


            //tworze notyfikacje
        val noti = NotificationCompat.Builder(context, context.getString(R.string.channelID)) //jak tu bylo channelName to nie dzialala notyfikacja
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Dodano produkt")
                .setContentText(intent.getStringExtra("nazwa"))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(id++, noti)
        //NotificationManagerCompat.from(context).notify(id++, noti)

 */


    }

//    }


/*
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

 */







}