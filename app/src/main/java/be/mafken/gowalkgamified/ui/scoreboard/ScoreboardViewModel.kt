package be.mafken.gowalkgamified.ui.scoreboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import be.mafken.gowalkgamified.data.OnServiceDataCallback
import be.mafken.gowalkgamified.data.firebase.FirebaseServiceProvider
import be.mafken.gowalkgamified.data.service.UserService
import be.mafken.gowalkgamified.model.User

class ScoreboardViewModel : ViewModel() {
    val userService: UserService = FirebaseServiceProvider.getFirebaseUserService()
    val users: MutableLiveData<List<User>> = MutableLiveData()

    fun getUserFromDatabase(){
        userService.loadUsersFromDatabase(object : OnServiceDataCallback<List<User>>{
            override fun onDataLoaded(data: List<User>) {
                users.value = data.sortedBy { it.totalScore }.reversed()
            }
            override fun onError(error: Throwable) {
            }
        })
    }
}
