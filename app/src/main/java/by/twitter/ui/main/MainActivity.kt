package by.twitter.ui.main

import android.app.Activity
import android.app.Notification.EXTRA_NOTIFICATION_ID
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import by.twitter.R


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    fun createTweetNotification(massage: String) {
        createNotificationChannel()
        val intent = Intent(this, ActionReceiver::class.java).apply {
            action = Activity.RESULT_OK.toString()
            putExtra(EXTRA_NOTIFICATION_ID, notificationId)
        }
        val okPendingIntent: PendingIntent = PendingIntent.getBroadcast(this, notificationId, intent, 0)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_twitter)
                .setContentTitle("Your new tweet has been posted")
                .setContentText("Massage: $massage")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(R.drawable.ic_ok, "OK", okPendingIntent)
                .build()

        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, notification)
        }
        notificationId++
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val CHANNEL_ID = "CHANNEL_ID"
        private var notificationId = 0
    }

}
