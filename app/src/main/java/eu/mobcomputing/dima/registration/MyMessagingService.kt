package eu.mobcomputing.dima.registration

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import eu.mobcomputing.dima.registration.utils.Constants.Companion.CHANNEL_ID
import eu.mobcomputing.dima.registration.utils.Constants.Companion.CHANNEL_NAME

/**
 * Service to handle incoming Firebase Cloud Messaging (FCM) messages.
 */
class MyMessagingService : FirebaseMessagingService() {
    /**
     * Called when a new FCM token is generated for the device.
     *
     * @param token The new FCM token.
     */
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    /**
     * Called when a new FCM message is received.
     *
     * @param message The received FCM message.
     */
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        // Get the NotificationManager service.
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = 1
        val requestCode = 1

        // Define the notification channel.
        val channelId = CHANNEL_ID
        val channelName = CHANNEL_NAME
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the notification channel for Android Oreo and above.
            notificationManager.createNotificationChannel(
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            )
        }
        // Create an intent to launch the MainActivity when the notification is clicked.
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntentFlag =
            PendingIntent.FLAG_IMMUTABLE
        val pendingIntent = PendingIntent.getActivity(this, requestCode, intent, pendingIntentFlag)

        // Build the notification using NotificationCompat.
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(message.notification?.title)
            .setContentText(message.notification?.body)
            .setSmallIcon(R.drawable.pot_svgrepo_com)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        // Display the notification using the NotificationManager.
        notificationManager.notify(notificationId, notification)
    }
}