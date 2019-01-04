package be.mafken.gowalkgamified.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.mafken.gowalkgamified.data.OnServiceDataCallback
import be.mafken.gowalkgamified.data.firebase.FirebaseServiceProvider
import be.mafken.gowalkgamified.data.service.UserService
import be.mafken.gowalkgamified.data.service.WalkService
import be.mafken.gowalkgamified.model.User
import be.mafken.gowalkgamified.model.Walk
import be.mafken.gowalkgamified.ui.achievements.AchievementsGetter
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel : ViewModel() {
    private val walkService: WalkService = FirebaseServiceProvider.getWalkService()
    val userService: UserService = FirebaseServiceProvider.getFirebaseUserService()
    val walkList: MutableLiveData<List<Walk>> = MutableLiveData()
    val user: MutableLiveData<User> = MutableLiveData()
    val achievements = AchievementsGetter.getAchievements()

    var totalDistance = 0
    var totalHours = 0
    var totalMinutes = 0
    var totalSeconds = 0
    var totalScore = 0

    fun getWalkingsForUserFromDatabase(){
        getUserFromDatabase()
        walkService.loadWalksFromDatabase(object: OnServiceDataCallback<List<Walk>>{
            override fun onDataLoaded(data: List<Walk>) {
                walkList.value = data.filter {
                    it.userId == FirebaseAuth.getInstance().uid
                }.sortedBy { it.score }.reversed()
            }

            override fun onError(error: Throwable) {
            }
        })
    }

    fun getUserFromDatabase(){
        userService.loadUserOnce(FirebaseAuth.getInstance().currentUser!!.uid, object: OnServiceDataCallback<User> {
            override fun onDataLoaded(data: User) {
               user.value = data
            }

            override fun onError(error: Throwable) {
            }
        })
    }

    fun updateUser(){
        userService.loadUserOnce(FirebaseAuth.getInstance().currentUser!!.uid, object: OnServiceDataCallback<User> {
            override fun onDataLoaded(data: User) {
                user.value = data
                user.value!!.totalDistance = totalDistance
                user.value!!.totalScore = totalScore
                user.value!!.totalTime = "${totalHours}h : ${totalMinutes}m : ${totalSeconds}s"
                userService.saveUserToDatabase(user.value!!)
            }

            override fun onError(error: Throwable) {
            }
        })

    }


    private fun calculateDayStreak() {

        val dateFormat = SimpleDateFormat("dd/M/yyyy", Locale.GERMANY)
        val currentDate = dateFormat.format(Date())

        val currentLoggedDate = currentDate.substring(0..1).toInt()

        if(user.value!!.lastLoggedDay != currentLoggedDate) {
            if (user.value!!.lastLoggedDay + 1 == currentLoggedDate) {
                user.value!!.dayStreak += 1
            }
            user.value!!.lastLoggedDay = currentLoggedDate
        }


    }

    fun calculateAchievements(){
        val amountOfWalkings = walkList.value!!.count()
        user.value?.let {
            // -----------------------Walks------------------------
            if (amountOfWalkings >= 1) {
                if (!it.completedAchievements.contains(achievements[1].id))
                    it.completedAchievements.add(achievements[1].id)
            }
            if (amountOfWalkings >= 10) {
                if (!it.completedAchievements.contains(achievements[2].id))
                    it.completedAchievements.add(achievements[2].id)
            }
            if (amountOfWalkings >= 20) {
                if (!it.completedAchievements.contains(achievements[3].id))
                    it.completedAchievements.add(achievements[3].id)
            }
            if (amountOfWalkings >= 50) {
                if (!it.completedAchievements.contains(achievements[4].id))
                    it.completedAchievements.add(achievements[4].id)
            }

            // -----------------------Points------------------------
            if (totalScore >= 50){
                if(!it.completedAchievements.contains(achievements[5].id))
                    it.completedAchievements.add(achievements[5].id)
            }
            if (totalScore >= 150){
                if(!it.completedAchievements.contains(achievements[6].id))
                    it.completedAchievements.add(achievements[6].id)
            }
            if (totalScore >= 500){
                if(!it.completedAchievements.contains(achievements[7].id))
                    it.completedAchievements.add(achievements[7].id)
            }
            if (totalScore >= 1000){
                if(!it.completedAchievements.contains(achievements[8].id))
                    it.completedAchievements.add(achievements[8].id)
            }

            // -----------------------Days------------------------
            if (it.dayStreak >= 2){
                if(!it.completedAchievements.contains(achievements[9].id))
                    it.completedAchievements.add(achievements[9].id)
            }
            if (it.dayStreak >= 5){
                if(!it.completedAchievements.contains(achievements[10].id))
                    it.completedAchievements.add(achievements[10].id)
            }
            if (it.dayStreak >= 7){
                if(!it.completedAchievements.contains(achievements[11].id))
                    it.completedAchievements.add(achievements[11].id)
            }


            if (totalDistance >= 5){
                if(!it.completedAchievements.contains(achievements[12].id))
                    it.completedAchievements.add(achievements[12].id)
            }
            if (totalDistance >= 15){
                if(!it.completedAchievements.contains(achievements[13].id))
                    it.completedAchievements.add(achievements[13].id)
            }
            if (totalDistance >= 30){
                if(!it.completedAchievements.contains(achievements[14].id))
                    it.completedAchievements.add(achievements[14].id)
            }

        }
        userService.saveUserToDatabase(user.value!!)
    }

    fun getTotalsFromWalkings(){
        totalDistance = 0
        totalScore = 0
        totalHours = 0
        totalMinutes = 0
        totalSeconds = 0

        walkList.value!!.forEach {
            totalDistance += it.amountKm
            totalScore += it.score

            val temp = it.displayTime.split(":")
            val hours = temp[0].replace("h ", "").toInt()
            val minutes = temp[1].replace("m ", "").replace(" ", "").toInt()
            val seconds = temp[2].replace("s", "").replace(" ", "").toInt()

            totalHours += hours
            totalMinutes += minutes
            totalSeconds += seconds

            if (totalSeconds > 59) {
                totalSeconds -= 60
                totalMinutes += 1
            }

            if (totalMinutes > 59) {
                totalMinutes -= 60
                totalHours += 1
            }

        }

        calculateDayStreak()
        calculateAchievements()
    }

}
