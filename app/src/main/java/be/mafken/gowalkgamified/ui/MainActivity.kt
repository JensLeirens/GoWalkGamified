package be.mafken.gowalkgamified

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import be.mafken.gowalkgamified.data.OnServiceDataCallback
import be.mafken.gowalkgamified.data.firebase.FirebaseServiceProvider
import be.mafken.gowalkgamified.data.service.UserService
import be.mafken.gowalkgamified.model.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    message.setText(R.string.title_home)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_dashboard -> {
                    message.setText(R.string.title_dashboard)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_notifications -> {
                    message.setText(R.string.title_notifications)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseAuth.getInstance().addAuthStateListener {
            it.currentUser?.let {user ->
                createNewUser(user.email!!,user.uid)
            }
        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menuUserBtn -> {
            //val intent = Intent(this, ProfileActivity::class.java)
            //startActivity(intent)
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun createNewUser(email: String, userId: String){
        val userService: UserService = FirebaseServiceProvider.getFirebaseUserService()
        val users : MutableList<User> = mutableListOf()
        val currentUser = User(uid = userId, email = email)
        var userAlreadyExists = false

        userService.loadUsersFromDatabase(object : OnServiceDataCallback<List<User>> {
            override fun onDataLoaded(data: List<User>) {
                users.addAll(data)
            }
            override fun onError(error: Throwable) {
            }
        })

        users.forEach {
            if(it.uid == currentUser.uid)
                userAlreadyExists = true
        }

        if(!userAlreadyExists){
            val nameList = currentUser.email.split("@")[0].split(".")
            currentUser.name = nameList.joinToString(separator = " ") { it.capitalize() }
            userService.saveUserToDatabase(currentUser)
        }
    }
}
