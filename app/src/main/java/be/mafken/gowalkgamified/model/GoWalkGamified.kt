package be.mafken.gowalkgamified.model

import android.app.Application
import be.mafken.gowalkgamified.BuildConfig
import timber.log.Timber

class GoWalkGamified : Application() {

 override fun onCreate() {
  super.onCreate()
  if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
 }
}
