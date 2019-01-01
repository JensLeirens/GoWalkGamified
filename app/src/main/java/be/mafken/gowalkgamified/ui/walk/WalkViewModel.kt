package be.mafken.gowalkgamified.ui.walk

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.mafken.gowalkgamified.data.OnServiceDataCallback
import be.mafken.gowalkgamified.data.firebase.FirebaseServiceProvider
import be.mafken.gowalkgamified.data.service.TrackerService
import be.mafken.gowalkgamified.data.service.WalkService
import be.mafken.gowalkgamified.model.Tracker
import be.mafken.gowalkgamified.model.Walk
import com.google.firebase.auth.FirebaseAuth

class WalkViewModel : ViewModel() {
    private val walkService: WalkService = FirebaseServiceProvider.getWalkService()
    val walk : MutableLiveData<Walk> = MutableLiveData()

    fun getWalkingsFromDatabase(){
        walk.value = Walk(userId = FirebaseAuth.getInstance().uid!!)
        walkService.loadWalksOnceFromDatabase(object: OnServiceDataCallback<List<Walk>>{
            override fun onDataLoaded(data: List<Walk>) {
                walk.value?.id = getNewIdForWalk(data)
            }

            override fun onError(error: Throwable) {
                //ToDo: implement

            }
        })
    }

    fun incrementWalkingsCreatedTracker(){
        val trackerService: TrackerService = FirebaseServiceProvider.getFirebaseTrackerService()
        trackerService.loadTrackerOnceFromDatabase(object : OnServiceDataCallback<Tracker> {
            override fun onDataLoaded(data: Tracker) {
                data.addWalkingScreenOpend += 1
                trackerService.saveTrackerToDatabase(data)

            }

            override fun onError(error: Throwable) {
            }
        })
    }

    fun saveWalkToDatabase(){
        walkService.saveWalkToDatabase(walk.value!!)
    }

    fun getNewIdForWalk(walkings: List<Walk>): Int{
        return if(walkings.isNotEmpty()) {
            walkings.sortedBy{ it.id }.last().id + 1
        } else 0

    }
}
