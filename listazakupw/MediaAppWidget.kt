package com.example.listazakupw

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.widget.ImageView
import android.widget.RemoteViews
import android.widget.Toast
import java.net.URI.create

/**
 * Implementation of App Widget functionality.
 */
class MediaAppWidget : AppWidgetProvider() {
    companion object{
        private lateinit var mediaPlayer: MediaPlayer
        private var imgID = 0
        private var songID = 0
    }



    private val songList = listOf(R.raw.mamasaid, R.raw.whiskeyinthejar)
    private val imgList = listOf(R.raw.zdjecie_1, R.raw.zdjecie_2)



    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
   /*class MediaPlayer{
       fun start() {
           TODO("Not yet implemented")
       }

       fun pause() {
           TODO("Not yet implemented")
       }

       companion object CompanionMediaPlayer{
           fun create(context: Context, songID: Int): MediaPlayer = MediaPlayer()
       }
   }*/


    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        /*val mediaPlayer = context?.let {
            MediaPlayer.create(
                    it,
                    songID
            )
        }*/
        if(mediaPlayer.isPlaying) {
            val mediaPlayer = MediaPlayer.create(
                    context,
                    songList[++songID % songList.size]
            )
        }

        
        if (intent?.action == context?.getString(R.string.action1)) {
            Toast.makeText(context, "Action1", Toast.LENGTH_LONG).show()
        } else if (intent?.action == context?.getString(R.string.actionMusic)) {
            mediaPlayer?.start()
        }
        if(intent?.action == context?.getString(R.string.actionMusicStop))
            mediaPlayer?.pause()
        if(intent?.action == context?.getString(R.string.actionImage)){
            val views = RemoteViews(context!!.packageName, R.layout.media_app_widget)
            //views.setImageViewResource(R.id.imageView, R.raw.zdjecie_1)

            views.setImageViewResource(R.id.imageView, imgList[++imgID % imgList.size])
            val manager = AppWidgetManager.getInstance(context)
            manager.updateAppWidget(
                    manager.getAppWidgetIds(ComponentName(context, MediaAppWidget::class.java)),
                    views
            )

        }
/*        if(intent?.action == context?.getString(R.string.actionImage2)){
            val views = RemoteViews(context!!.packageName, R.layout.media_app_widget)
            views.setImageViewResource(R.id.imageView, R.raw.zdjecie_2)
            val manager2 = AppWidgetManager.getInstance(context)
            manager2.updateAppWidget(
                    manager2.getAppWidgetIds(ComponentName(context, MediaAppWidget::class.java)),
                    views
            )
        }*/




    }

    internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val widgetText = context.getString(R.string.appwidget_text)
        val views = RemoteViews(context.packageName, R.layout.media_app_widget)
        views.setTextViewText(R.id.widget_tv, widgetText)
        views.setImageViewResource(R.id.imageView, R.raw.zdjecie)

        val intentWWW = Intent(Intent.ACTION_VIEW)
        intentWWW.data = Uri.parse("https://www.google.com")
        val pendingWWW = PendingIntent.getActivity(
                context,
                0,
                intentWWW,
                PendingIntent.FLAG_UPDATE_CURRENT
        )
        views.setOnClickPendingIntent(R.id.widget_bt1, pendingWWW)


/*
        val intentIMG = Intent(Intent.ACTION_VIEW)
        intentIMG.action = view.setImageViewResource(R.id.imageView, R.raw.zdjecie)

*/



        val intentAction = Intent(context.getString(R.string.action1))
        intentAction.component = ComponentName(context, MediaAppWidget::class.java)
        val pendingAction = PendingIntent.getBroadcast(
                context,
                0,
                intentAction,
                PendingIntent.FLAG_UPDATE_CURRENT
        )
        views.setOnClickPendingIntent(R.id.widget_bt2, pendingAction)

        //Music start
        val intentMusic = Intent(context.getString(R.string.actionMusic))
        intentMusic.component = ComponentName(context, MediaAppWidget::class.java)
        val pendingActionMusic = PendingIntent.getBroadcast(
                context,
                0,
                intentMusic,
                PendingIntent.FLAG_UPDATE_CURRENT
        )
        views.setOnClickPendingIntent(R.id.widget_bt_music, pendingActionMusic)

        //Music stop
        val intentMusicStop = Intent(context.getString(R.string.actionMusicStop))
        intentMusicStop.component = ComponentName(context, MediaAppWidget::class.java)
        val pendingActionMusicStop = PendingIntent.getBroadcast(
                context,
                0,
                intentMusicStop,
                PendingIntent.FLAG_UPDATE_CURRENT
        )
        views.setOnClickPendingIntent(R.id.widget_bt_music_stop, pendingActionMusicStop)


        //Image
        val intentImage = Intent(context.getString(R.string.actionImage))
        intentImage.component = ComponentName(context, MediaAppWidget::class.java)
        val pendingActionImage = PendingIntent.getBroadcast(
                context,
                0,
                intentImage,
                PendingIntent.FLAG_UPDATE_CURRENT
        )
        views.setOnClickPendingIntent(R.id.widget_bt_image, pendingActionImage)

        //Image_2
        val intentImage2 = Intent(context.getString(R.string.actionImage2))
        intentImage2.component = ComponentName(context, MediaAppWidget::class.java)
        val pendingActionImage2 = PendingIntent.getBroadcast(
                context,
                0,
                intentImage,
                PendingIntent.FLAG_UPDATE_CURRENT
        )
        views.setOnClickPendingIntent(R.id.widget_bt_image_2, pendingActionImage2)



        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}
