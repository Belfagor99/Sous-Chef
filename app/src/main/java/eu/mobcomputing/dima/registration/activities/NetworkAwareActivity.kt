package eu.mobcomputing.dima.registration.activities

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.notification.MyNotificationWorker
import eu.mobcomputing.dima.registration.screens.ConnectionLostScreen
import java.time.LocalTime
import java.time.ZonedDateTime
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
abstract class NetworkAwareActivity : ComponentActivity() {

    /**
     * Called when the activity is first created.
     * Initializes the content of the activity and sets up the navigation graph.
     * Fetches the FCM token.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *     being shut down, this Bundle contains the data it most recently supplied in
     *     [onSaveInstanceState].
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {

        // Get instance of FM to fetch the FCM registration token
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and the FCM
            Log.d("FCM", token.toString())
        })

        // create worker for notification
        this.enqueueNotificationWorker()

        super.onCreate(savedInstanceState)


        // Determine if the device is a tablet
        val isTablet = resources.getBoolean(R.bool.isTablet)

        // Set the screen orientation based on the device type
        if (isTablet) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }


        setContent {
            ConnectionLostScreen {
                // Your main content here
                setContent()
            }
        }
    }


    @Composable
    abstract fun setContent()

    /**
     *  Function to create instance of work manager to trigger the notification process.
     *
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun Context.enqueueNotificationWorker(
    ) {

        val localTime = LocalTime.of(8, 0)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val now = ZonedDateTime.now()

        // Calculate the trigger time for today at 8 A.M.
        val trigger = now.with(localTime)

        // If the trigger time for today has already passed, set the trigger time for tomorrow
        val realTrigger = if (trigger <= now) trigger.plusDays(1) else trigger

        // Calculate the initial delay until the trigger time
        val initialDelay = realTrigger.toEpochSecond() - now.toEpochSecond()

        val notificationWork = PeriodicWorkRequestBuilder<MyNotificationWorker>(
            repeatInterval = 1, // Repeat every 24 hours
            repeatIntervalTimeUnit = TimeUnit.DAYS,
            10,
            TimeUnit.MINUTES
        ).setInitialDelay(initialDelay, TimeUnit.SECONDS)
            .setConstraints(constraints)
            .addTag("notification_worker_tag")
            .build()

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                "SousChefAppUniqueWorkName",
                ExistingPeriodicWorkPolicy.UPDATE,
                notificationWork
            )
    }

}