package be.mafken.gowalkgamified.ui.achievements

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.mafken.gowalkgamified.data.OnServiceDataCallback
import be.mafken.gowalkgamified.data.firebase.FirebaseServiceProvider
import be.mafken.gowalkgamified.data.service.TrackerService
import be.mafken.gowalkgamified.data.service.UserService
import be.mafken.gowalkgamified.model.Tracker
import be.mafken.gowalkgamified.model.User

class AchievementsViewModel : ViewModel() {
 val userService: UserService =
  FirebaseServiceProvider.getFirebaseUserService()
 val users: MutableLiveData<List<User>> = MutableLiveData()

 fun getUsersFromDatabase() {
  userService.loadUsersOnceFromDatabase(object :
   OnServiceDataCallback<List<User>> {
   override fun onDataLoaded(data: List<User>) {
    users.value = data.sortedBy {
     it.completedAchievements.count() }.reversed()
   }

   override fun onError(error: Throwable) {
   }
  })
 }

 fun incrementAchievementOpendTracker() {
  val trackerService: TrackerService =
   FirebaseServiceProvider.getFirebaseTrackerService()
  trackerService.loadTrackerOnceFromDatabase(object :
   OnServiceDataCallback<Tracker> {
   override fun onDataLoaded(data: Tracker) {
    data.achievementsOpend += 1
    trackerService.saveTrackerToDatabase(data)

   }

   override fun onError(error: Throwable) {
   }
  })
 }
}
