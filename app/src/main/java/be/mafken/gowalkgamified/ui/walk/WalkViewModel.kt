package be.mafken.gowalkgamified.ui.walk

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.mafken.gowalkgamified.data.OnServiceDataCallback
import be.mafken.gowalkgamified.data.firebase.FirebaseServiceProvider
import be.mafken.gowalkgamified.data.service.TrackerService
import be.mafken.gowalkgamified.data.service.UserService
import be.mafken.gowalkgamified.data.service.WalkService
import be.mafken.gowalkgamified.model.Tracker
import be.mafken.gowalkgamified.model.User
import be.mafken.gowalkgamified.model.Walk
import be.mafken.gowalkgamified.ui.achievements.AchievementsGetter
import com.google.firebase.auth.FirebaseAuth

class WalkViewModel : ViewModel() {
 val userService: UserService =
  FirebaseServiceProvider.getFirebaseUserService()
 private val walkService: WalkService =
  FirebaseServiceProvider.getWalkService()
 val walk: MutableLiveData<Walk> = MutableLiveData()
 var user = User()

 fun getWalkingsFromDatabase() {
  walk.value = Walk(userId = FirebaseAuth.getInstance().uid!!)
  walkService.loadWalksOnceFromDatabase(
   object : OnServiceDataCallback<List<Walk>> {
   override fun onDataLoaded(data: List<Walk>) {
    walk.value?.id = getNewIdForWalk(data)
   }

   override fun onError(error: Throwable) {
   }
  })
 }

 fun getUser() {

  userService.loadUserOnce(
   FirebaseAuth.getInstance().currentUser!!.uid,
   object : OnServiceDataCallback<User> {
    override fun onDataLoaded(data: User) {
     user = data
    }

    override fun onError(error: Throwable) {
    }
   })

 }

 fun incrementWalkingsCreatedTracker() {
  val trackerService: TrackerService =
   FirebaseServiceProvider.getFirebaseTrackerService()
  trackerService.loadTrackerOnceFromDatabase(
   object : OnServiceDataCallback<Tracker> {
   override fun onDataLoaded(data: Tracker) {
    data.addWalkingScreenOpend += 1
    trackerService.saveTrackerToDatabase(data)

   }

   override fun onError(error: Throwable) {
   }
  })
 }

 fun saveWalkToDatabase() {
  walkService.saveWalkToDatabase(walk.value!!)
 }

 fun addMysteryAchievement() {
  val achievements =
   AchievementsGetter.getAchievements()

  if (!user.completedAchievements.contains(achievements[0].id))
   user.completedAchievements.add(achievements[0].id)

  userService.saveUserToDatabase(user)

 }

 fun addWalkFriendAchievement() {
  val achievements =
   AchievementsGetter.getAchievements()

  if (!user.completedAchievements.contains(achievements[15].id))
   user.completedAchievements.add(achievements[15].id)

  userService.saveUserToDatabase(user)
 }

 fun getNewIdForWalk(walkings: List<Walk>): Int {
  return if (walkings.isNotEmpty()) {
   walkings.sortedBy { it.id }.last().id + 1
  } else 0

 }
}
