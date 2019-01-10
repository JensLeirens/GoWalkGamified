package be.mafken.gowalkgamified.ui.scoreboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.mafken.gowalkgamified.data.OnServiceDataCallback
import be.mafken.gowalkgamified.data.firebase.FirebaseServiceProvider
import be.mafken.gowalkgamified.data.service.TrackerService
import be.mafken.gowalkgamified.data.service.UserService
import be.mafken.gowalkgamified.model.Tracker
import be.mafken.gowalkgamified.model.User

class ScoreboardViewModel : ViewModel() {
 val userService: UserService = FirebaseServiceProvider.getFirebaseUserService()
 val users: MutableLiveData<List<User>> = MutableLiveData()

 fun getUserFromDatabase() {
  userService.loadUsersOnceFromDatabase(object :
   OnServiceDataCallback<List<User>> {
   override fun onDataLoaded(data: List<User>) {
    users.value = data.sortedBy { it.totalScore }.reversed()
   }

   override fun onError(error: Throwable) {
   }
  })
 }

 fun incrementScoreboardOpendTracker() {
  val trackerService: TrackerService =
   FirebaseServiceProvider.getFirebaseTrackerService()
  trackerService.loadTrackerOnceFromDatabase(object :
   OnServiceDataCallback<Tracker> {
   override fun onDataLoaded(data: Tracker) {
    data.scoreboardOpenend += 1
    trackerService.saveTrackerToDatabase(data)

   }

   override fun onError(error: Throwable) {
   }
  })
 }
}
