package be.mafken.gowalkgamified.data.service

import be.mafken.gowalkgamified.data.OnServiceDataCallback
import be.mafken.gowalkgamified.model.User

interface UserService {

    fun loadUsersFromDatabase(callback: OnServiceDataCallback<List<User>>)

    fun loadUsersOnceFromDatabase(callback: OnServiceDataCallback<List<User>>)

    fun loadUser(userId: String, callback: OnServiceDataCallback<User>)

    fun loadUserOnce(userId: String, callback: OnServiceDataCallback<User>)

    fun saveUserToDatabase(user: User)

}