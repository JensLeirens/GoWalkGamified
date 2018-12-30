package be.mafken.gowalkgamified.data.firebase

import be.mafken.gowalkgamified.data.OnServiceDataCallback
import be.mafken.gowalkgamified.data.service.UserService
import be.mafken.gowalkgamified.model.User
import com.google.firebase.database.*
import timber.log.Timber

class FirebaseUserService: UserService {

    private val usersReference: DatabaseReference = FirebaseDatabase.getInstance().reference.child("users")

    override fun loadUsersFromDatabase(callback: OnServiceDataCallback<List<User>>) {
        val usersListener = object : ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                val userlist: MutableList<User> = mutableListOf()
                datasnapshot.children.mapNotNullTo(userlist) {
                    it.getValue<User>(User::class.java)
                }
                callback.onDataLoaded(userlist)
            }

            override fun onCancelled(p0: DatabaseError) {
                Timber.e(p0.message)
                callback.onError(p0.toException())
            }
        }
        usersReference.addValueEventListener(usersListener)
    }

    override fun loadUsersOnceFromDatabase(callback: OnServiceDataCallback<List<User>>) {
        val usersListener = object : ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                val userlist: MutableList<User> = mutableListOf()
                datasnapshot.children.mapNotNullTo(userlist) {
                    it.getValue<User>(User::class.java)
                }
                callback.onDataLoaded(userlist)
            }

            override fun onCancelled(p0: DatabaseError) {
                Timber.e(p0.message)
                callback.onError(p0.toException())
            }
        }
        usersReference.addValueEventListener(usersListener)
    }

    override fun loadUser(userId: String, callback: OnServiceDataCallback<User>) {
        val userListener = object : ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                if(datasnapshot.getValue(User::class.java) == null ) usersReference.child(userId).removeEventListener(this)
                else {
                    callback.onDataLoaded(datasnapshot.getValue(User::class.java)!!)
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Timber.e(p0.message)
                callback.onError(p0.toException())
            }
        }
        usersReference.child(userId).addValueEventListener(userListener)
    }

    override fun loadUserOnce(userId: String, callback: OnServiceDataCallback<User>) {
        val userListener = object : ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                if(datasnapshot.getValue(User::class.java) == null ) usersReference.child(userId).removeEventListener(this)
                else {
                    callback.onDataLoaded(datasnapshot.getValue(User::class.java)!!)
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Timber.e(p0.message)
                callback.onError(p0.toException())
            }
        }
        usersReference.child(userId).addListenerForSingleValueEvent(userListener)
    }

    override fun saveUserToDatabase(user: User) {
        usersReference.child(user.uid).setValue(user)
    }
}