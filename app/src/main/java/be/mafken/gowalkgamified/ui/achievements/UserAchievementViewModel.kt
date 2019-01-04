package be.mafken.gowalkgamified.ui.achievements

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import be.mafken.gowalkgamified.data.OnServiceDataCallback
import be.mafken.gowalkgamified.data.firebase.FirebaseServiceProvider
import be.mafken.gowalkgamified.data.service.TrackerService
import be.mafken.gowalkgamified.data.service.UserService
import be.mafken.gowalkgamified.model.Achievement
import be.mafken.gowalkgamified.model.Tracker
import be.mafken.gowalkgamified.model.User
import com.google.firebase.auth.FirebaseAuth

class UserAchievementViewModel : ViewModel() {
    val userService: UserService = FirebaseServiceProvider.getFirebaseUserService()
    val achievements : MutableLiveData<List<Achievement>> = MutableLiveData()
    val user : MutableLiveData<User> = MutableLiveData()

    fun getAchievements(){
        achievements.value = AchievementsGetter.getAchievements()
    }

    fun getUserFromDatabase(){
        userService.loadUserOnce(FirebaseAuth.getInstance().currentUser!!.uid ,object: OnServiceDataCallback<User> {
            override fun onDataLoaded(data: User) {
                user.value = data
            }

            override fun onError(error: Throwable) {
            }
        })
    }

    fun incrementUserAchievementOpendTracker(){
        val trackerService: TrackerService = FirebaseServiceProvider.getFirebaseTrackerService()
        trackerService.loadTrackerOnceFromDatabase(object : OnServiceDataCallback<Tracker>{
            override fun onDataLoaded(data: Tracker) {
                data.userAchievementsOpend += 1
                trackerService.saveTrackerToDatabase(data)

            }

            override fun onError(error: Throwable) {
            }
        })
    }
}
