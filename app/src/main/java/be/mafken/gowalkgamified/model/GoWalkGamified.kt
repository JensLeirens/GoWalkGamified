package be.mafken.gowalkgamified.model

import android.app.Application
import be.mafken.gowalkgamified.BuildConfig
import com.google.firebase.database.FirebaseDatabase
import timber.log.Timber

class GoWalkGamified : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}
