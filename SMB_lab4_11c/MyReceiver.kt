package com.example.myapp11

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MyReceiver : BroadcastReceiver() {

    private var id = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        if(intent.action == context.getString(R.string.dodanieStudenta)){
            createChannel(context)
            val lista = Intent(context, SecondaryActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                context,
                id,
                lista,
                PendingIntent.FLAG_ONE_SHOT
            )

            val noti = NotificationCompat.Builder(context, context.getString(R.string.channelID))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Dodano studenta")
                .setContentText(intent.getStringExtra("nazwa"))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

            NotificationManagerCompat.from(context).notify(id++, noti)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createChannel(context: Context){
        val notiChannel = NotificationChannel(
            context.getString(R.string.channelID),
            context.getString(R.string.channelName),
            NotificationManager.IMPORTANCE_DEFAULT
        )

        NotificationManagerCompat.from(context).createNotificationChannel(notiChannel)
    }
}