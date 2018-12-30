package be.mafken.gowalkgamified.data.firebase

import be.mafken.gowalkgamified.data.OnServiceDataCallback
import be.mafken.gowalkgamified.data.service.TrackerService
import be.mafken.gowalkgamified.model.Tracker
import com.google.firebase.database.*
import timber.log.Timber

class FirebaseTrackerService : TrackerService {
    private val trackerReference: DatabaseReference =
        FirebaseDatabase.getInstance().reference.child("tracker")

    override fun loadTrackerOnceFromDatabase(callback: OnServiceDataCallback<Tracker>) {
        val trackerListener = object : ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                if (datasnapshot.getValue(Tracker::class.java) == null) trackerReference.removeEventListener(
                    this
                )
                else {
                    callback.onDataLoaded(datasnapshot.getValue(Tracker::class.java)!!)
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Timber.e(p0.message)
                callback.onError(p0.toException())
            }
        }
        trackerReference.addListenerForSingleValueEvent(trackerListener)

    }

    override fun saveTrackerToDatabase(tracker: Tracker) {
        trackerReference.setValue(tracker)
    }
}