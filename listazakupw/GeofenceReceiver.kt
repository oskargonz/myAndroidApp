package com.example.listazakupw

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings.Global.getString
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.gms.location.GeofencingEvent

class GeofenceReceiver : BroadcastReceiver() {
    private var id = 0
    override fun onReceive(context: Context, intent: Intent) {

        val geoEvent = GeofencingEvent.fromIntent(intent)

        for(geo in geoEvent.triggeringGeofences){
            Toast.makeText(context, "${geo.requestId}", Toast.LENGTH_LONG).show()
            Log.i("Start", "GeofenceEntry")

            //Notyfikacja
            createChannel(context)
            val intentPlacesList = Intent(context, PlacesListActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                    context,
                    id,
                    intentPlacesList,
                    PendingIntent.FLAG_ONE_SHOT
            )
            val noti = NotificationCompat.Builder(context, "channelPlace")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Jesteś w pobliżu ulubionego sklepu.")
                    .setContentText(intent.getStringExtra("nazwa"))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build()
            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(id++, noti)

            //return super.onReceive(context, intent)
        }

    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun createChannel(context: Context){
        val notiChannel = NotificationChannel(
                "channelPlace",
                "addPlace",
                NotificationManager.IMPORTANCE_DEFAULT
        )

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.createNotificationChannel(notiChannel)
    }

}