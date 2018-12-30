package be.mafken.gowalkgamified.data

import com.google.firebase.messaging.FirebaseMessagingService
import timber.log.Timber

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String?) {
        Timber.d("Refreshed token: $token")
    }
}