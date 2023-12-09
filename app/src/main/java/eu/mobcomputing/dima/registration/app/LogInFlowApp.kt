package eu.mobcomputing.dima.registration.app

import android.app.Application
import com.google.firebase.FirebaseApp

class LogInFlowApp: Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}