package eu.mobcomputing.dima.registration.app

import android.app.Application
import com.google.firebase.FirebaseApp

/** This is a class to handle Login flow. */
class LogInFlowApp : Application() {

    /**
     * Called when the application is first created. It initializes the Firebase
     * application instance to enable Firebase services in the application.
     *
     */
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}