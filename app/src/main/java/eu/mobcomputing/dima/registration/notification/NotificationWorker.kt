package eu.mobcomputing.dima.registration.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import eu.mobcomputing.dima.registration.activities.MainActivity
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.models.Ingredient
import eu.mobcomputing.dima.registration.models.User
import eu.mobcomputing.dima.registration.utils.Constants.Companion.CHANNEL_ID
import eu.mobcomputing.dima.registration.utils.Constants.Companion.CHANNEL_NAME
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Worker class for background tasks, including checking user's pantry and sending notifications.
 *
 * @param context The application context.
 * @param params Parameters to configure the worker.
 */
class MyNotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    private val TAG = MyNotificationWorker::class.java.simpleName
    private val defaultNotificationText = "Open your pantry to see."

    /**
     * Performs background work, including fetching user's pantry, checking for ingredients
     * approaching expiration, and sending notifications if necessary.
     *
     * @return The result of the work execution.
     */
    override fun doWork(): Result = runBlocking {
        // Fetch user data and decide whether to send a notification
        var shouldSendNotification = false
        var numberOfIngredients = 0

        FirebaseAuth.getInstance().currentUser?.uid?.let { userID ->
            val pantryResult = fetchUserPantry(userID)
            val checkPantryState = async { checkPantry(pantryResult) }

            // Use coroutineScope to wait for the completion of all coroutines
            coroutineScope {
                // Wait for the completion of checkPantry coroutine
                val checkPantryResult = checkPantryState.await()

                Log.d(TAG, checkPantryResult.toString())
                if (checkPantryResult.isNotEmpty()) {
                    shouldSendNotification = true
                    numberOfIngredients = checkPantryResult.size

                }
            }


        }
        if (shouldSendNotification) {
            sendNotification("Ingredients Notification", numberOfIngredients)
        }

        Result.success()
    }

    /**
     * Fetches the user's pantry from Firestore.
     *
     * @param userID The ID of the current user.
     * @return The list of ingredients in the user's pantry.
     */
    private suspend fun fetchUserPantry(userID: String): List<Ingredient> {
        val db = FirebaseFirestore.getInstance()
        val userDocumentRef: DocumentReference = db.collection("users").document(userID)
        return try {
            val userDocument = userDocumentRef.get().await()
            if (userDocument.exists()) {
                // Parse the data and return the list of ingredients
                val ingredientList =
                    userDocument.toObject(User::class.java)?.ingredientsInPantry ?: emptyList()
                ingredientList
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Checks the user's pantry for ingredients approaching expiration.
     *
     * @param userPantry The list of ingredients in the user's pantry.
     * @return The list of ingredients approaching expiration.
     */
    private suspend fun checkPantry(userPantry: List<Ingredient>): List<Ingredient> =
        coroutineScope {
            val ingredientsApproachingDueDate = mutableListOf<Ingredient>()
            val today = Calendar.getInstance().apply { time = Date() }
            val tomorrow = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, 1) }
            val dayAfterTomorrow = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, 2) }

            // Use async for concurrent execution of checks
            val checks = userPantry.map { ingredient ->
                async {
                    Log.d(TAG, "This is the ingredient: $ingredient")
                    try {
                        val expirationDate = parseStringToDate(ingredient.expiringDate.toString())

                        Log.d(TAG, "expiration date $expirationDate")
                        val calendarExpirationDate = Calendar.getInstance().apply {
                            if (expirationDate != null) {
                                time = expirationDate
                            }
                        }
                        Log.d(TAG, "Checking expiration date:  " + ingredient.name + expirationDate)
                        // Check if the expiration date is today, tomorrow, or the day after tomorrow
                        if (isSameDay(calendarExpirationDate, today)) {
                            ingredientsApproachingDueDate.add(ingredient)
                        } else if (isSameDay(calendarExpirationDate, tomorrow)) {
                            // Handle tomorrow
                            ingredientsApproachingDueDate.add(ingredient)
                        } else if (isSameDay(calendarExpirationDate, dayAfterTomorrow)) {
                            // Handle day after tomorrow
                            ingredientsApproachingDueDate.add(ingredient)

                        } else {
                            Log.d(TAG, "No ingredient with approaching date")
                            // If the date is not approaching nothing is needed to be dome
                        }
                    } catch (e: Exception) {
                        Log.d(TAG, "There is a problem while checking the ingredient date")
                    }
                }
            }

            // Wait for all async checks to complete
            checks.awaitAll()

            ingredientsApproachingDueDate


        }

    // Function to check if two Calendar instances represent the same day
    private fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }

    // Function to parse string date into a Date object
    private fun parseStringToDate(dateString: String): Date? {
        val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
        return try {
            dateFormat.parse(dateString)
        } catch (e: ParseException) {
            null
        }
    }

    /**
     * Sends a notification directly using NotificationCompat.Builder.
     *
     * @param title The title of the notification.
     * @param numberOfIngredients The number of ingredients to be used.
     */
    private fun sendNotification(title: String, numberOfIngredients: Int) {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = 1

        // Define the notification channel.
        val channelId = CHANNEL_ID
        val channelName = CHANNEL_NAME
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            )
        }

        // Create an intent to launch the MainActivity when the notification is clicked.
        val intent = Intent(applicationContext, MainActivity::class.java)
        val requestCode = 1
        val pendingIntentFlag = PendingIntent.FLAG_IMMUTABLE
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            requestCode,
            intent,
            pendingIntentFlag
        )

        // Build the notification using NotificationCompat.
        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle(title)
            .setContentText(
                if (numberOfIngredients > 1) {
                    "There are $numberOfIngredients ingredients you must use." +
                           defaultNotificationText
                } else {
                    "There is an ingredient you must use.$defaultNotificationText"
                }
            )
            .setSmallIcon(R.drawable.pot_svgrepo_com)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        // Display the notification using the NotificationManager.
        notificationManager.notify(notificationId, notification)
    }

}
