package be.mafken.gowalkgamified.data.firebase


import be.mafken.gowalkgamified.data.OnServiceDataCallback
import be.mafken.gowalkgamified.data.service.WalkService
import be.mafken.gowalkgamified.model.Walk
import com.google.firebase.database.*
import timber.log.Timber

class FirebaseWalkService : WalkService {

    private val WalkReference: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Walks")

    override fun loadWalksFromDatabase(callback: OnServiceDataCallback<List<Walk>>) {
        val walkListener = object : ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                val walklist: MutableList<Walk> = mutableListOf()
                datasnapshot.children.mapNotNullTo(walklist) {
                    it.getValue<Walk>(Walk::class.java)
                }
                callback.onDataLoaded(walklist)
            }

            override fun onCancelled(p0: DatabaseError) {
                Timber.e(p0.message)
                callback.onError(p0.toException())
            }
        }

        WalkReference.addValueEventListener(walkListener)
    }

    override fun loadWalksOnceFromDatabase(callback: OnServiceDataCallback<List<Walk>>) {
        val walkListener = object : ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                val walklist: MutableList<Walk> = mutableListOf()
                datasnapshot.children.mapNotNullTo(walklist) {
                    it.getValue<Walk>(Walk::class.java)
                }
                callback.onDataLoaded(walklist)
            }

            override fun onCancelled(p0: DatabaseError) {
                Timber.e(p0.message)
                callback.onError(p0.toException())
            }
        }

        WalkReference.addListenerForSingleValueEvent(walkListener)
    }

    override fun loadWalk(walkId: Int, callback: OnServiceDataCallback<Walk>) {
        val walkListener = object : ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                if(datasnapshot.getValue(Walk::class.java) == null )
                    WalkReference.child(walkId.toString()).removeEventListener(this)
                else {
                    WalkReference.child(walkId.toString()).removeEventListener(this)
                    val walk: Walk = datasnapshot.getValue(Walk::class.java)!!
                    callback.onDataLoaded(walk)
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Timber.e(p0.message)
                callback.onError(p0.toException())
            }
        }

        WalkReference.child(walkId.toString()).addValueEventListener(walkListener)
    }

    override fun saveWalkToDatabase(walk: Walk) {
        WalkReference.child(walk.id.toString()).setValue(walk)
    }

    override fun deleteWalk(walkId: Int) {
        WalkReference.child(walkId.toString()).removeValue()
    }
}