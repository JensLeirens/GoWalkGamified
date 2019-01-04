package be.mafken.gowalkgamified

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import be.mafken.gowalkgamified.data.OnServiceDataCallback
import be.mafken.gowalkgamified.data.firebase.FirebaseServiceProvider
import be.mafken.gowalkgamified.data.service.UserService
import be.mafken.gowalkgamified.extensions.goToFragment
import be.mafken.gowalkgamified.extensions.goToFragmentWithoutBackstack
import be.mafken.gowalkgamified.model.User
import be.mafken.gowalkgamified.ui.achievements.AchievementsFragment
import be.mafken.gowalkgamified.ui.home.HomeFragment
import be.mafken.gowalkgamified.ui.profile.ProfileFragment
import be.mafken.gowalkgamified.ui.scoreboard.ScoreboardFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    goToFragmentWithoutBackstack(HomeFragment.newInstance())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_scoreboard -> {
                    goToFragmentWithoutBackstack(ScoreboardFragment.newInstance())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_achievement -> {
                    goToFragmentWithoutBackstack(AchievementsFragment.newInstance())
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        goToFragmentWithoutBackstack(HomeFragment.newInstance())

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
            goToFragment("Home",ProfileFragment.newInstance())
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun createNewUser(email: String, userId: String){
        val userService: UserService = FirebaseServiceProvider.getFirebaseUserService()
        val currentUser = User(uid = userId, email = email)
        var userAlreadyExists = false

        userService.loadUsersOnceFromDatabase(object : OnServiceDataCallback<List<User>> {
            override fun onDataLoaded(data: List<User>) {
                data.forEach {
                    if(it.uid == currentUser.uid)
                        userAlreadyExists = true
                }

                if(!userAlreadyExists){
                    val nameList = currentUser.email.split("@")[0].split(".")
                    currentUser.name = nameList.joinToString(separator = " ") { it.capitalize() }
                    userService.saveUserToDatabase(currentUser)
                }
            }
            override fun onError(error: Throwable) {
            }
        })


    }
}
