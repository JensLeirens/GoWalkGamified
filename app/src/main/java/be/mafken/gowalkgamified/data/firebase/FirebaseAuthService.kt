package be.mafken.gowalkgamified.data.firebase

import be.mafken.gowalkgamified.data.OnServiceDataCallback
import be.mafken.gowalkgamified.data.service.AuthService
import com.google.firebase.auth.FirebaseAuth


class FirebaseAuthService : AuthService {

 val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

 override fun signInUserWithEmailAndPassword(
  email: String,
  pword: String,
  callback: OnServiceDataCallback<String>
 ) {


  firebaseAuth.signInWithEmailAndPassword(email, pword)
   .addOnCompleteListener { task ->
   if (task.isSuccessful) {
    firebaseAuth.uid?.let {
     callback.onDataLoaded(it)
    } ?: callback.onDataLoaded("")
   } else {
    callback.onError(Exception().initCause(task.exception))
   }
  }
 }


 override fun createUserWithEmailAndPassword(email: String, pword: String) {
  firebaseAuth.createUserWithEmailAndPassword(email, pword)
 }
}