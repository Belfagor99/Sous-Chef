package eu.mobcomputing.dima.registration.notification

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import eu.mobcomputing.dima.registration.MyMessagingService
import eu.mobcomputing.dima.registration.models.Ingredient
import eu.mobcomputing.dima.registration.models.User
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
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
class MyWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    private val TAG = MyWorker::class.java.simpleName

    /**
     * Performs background work, including fetching user's pantry, checking for ingredients
     * approaching expiration, and sending notifications if necessary.
     *
     * @return The result of the work execution.
     */
    override fun doWork(): Result = runBlocking {
        // Fetch user data and decide whether to send a notification
        var shouldSendNotification = false

        FirebaseAuth.getInstance().currentUser?.uid?.let { userID ->
            val pantryResult = fetchUserPantry(userID)
            val checkPantryState = async { checkPantry(pantryResult) }

            // Use coroutineScope to wait for the completion of all coroutines
            coroutineScope {
                // Wait for the completion of checkPantry coroutine
                val checkPantryResult = checkPantryState.await()

                if (checkPantryResult.isNotEmpty()) {
                    shouldSendNotification = true
                }
            }

        }
        if (shouldSendNotification) {
            sendNotification("Notification Title", "Notification Content")
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

            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            // Use async for concurrent execution of checks
            val checks = userPantry.map { ingredient ->
                async {
                    try {
                        val expirationDate = dateFormat.parse(ingredient.expiringDate.toString())
                        val calendarExpirationDate = Calendar.getInstance().apply {
                            if (expirationDate != null) {
                                time = expirationDate
                            }
                        }

                        // Check if the expiration date is today, tomorrow, or the day after tomorrow
                        if (calendarExpirationDate == today || calendarExpirationDate == tomorrow || calendarExpirationDate == dayAfterTomorrow) {
                            ingredientsApproachingDueDate.add(ingredient)
                        } else {
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


    /**
     * Sends a notification using the MyMessagingService.
     *
     * @param title The title of the notification.
     * @param content The content of the notification.
     */
    private fun sendNotification(title: String, content: String) {
        val myFirebaseMessagingService = MyMessagingService()
        // Call the showNotification function
        myFirebaseMessagingService.showNotification(title, content)
    }
}
