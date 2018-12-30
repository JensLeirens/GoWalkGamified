package be.mafken.gowalkgamified.data.firebase

import be.mafken.gowalkgamified.data.service.AuthService
import be.mafken.gowalkgamified.data.service.TrackerService
import be.mafken.gowalkgamified.data.service.UserService
import be.mafken.gowalkgamified.data.service.WalkService


object FirebaseServiceProvider {

    fun getWalkService() : WalkService {
        return FirebaseWalkService()
    }

    fun getFirebaseUserService() : UserService {
        return FirebaseUserService()
    }

    fun getFirebaseAuthService(): AuthService {
        return FirebaseAuthService()
    }

    fun getFirebaseTrackerService(): TrackerService {
        return FirebaseTrackerService()
    }
}