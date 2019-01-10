package be.mafken.gowalkgamified.data.service

import be.mafken.gowalkgamified.data.OnServiceDataCallback


interface AuthService {

 fun signInUserWithEmailAndPassword(
  email: String,
  pword: String,
  callback: OnServiceDataCallback<String>
 )

 fun createUserWithEmailAndPassword(email: String, pword: String)
}