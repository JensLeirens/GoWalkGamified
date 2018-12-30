package be.mafken.gowalkgamified.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import be.mafken.gowalkgamified.MainActivity
import be.mafken.gowalkgamified.R
import be.mafken.gowalkgamified.data.OnServiceDataCallback
import be.mafken.gowalkgamified.data.firebase.FirebaseServiceProvider
import be.mafken.gowalkgamified.data.service.AuthService
import be.mafken.gowalkgamified.data.service.TrackerService
import be.mafken.gowalkgamified.extensions.gone
import be.mafken.gowalkgamified.extensions.visible
import be.mafken.gowalkgamified.model.Tracker
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import timber.log.Timber

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        incrementAppOpendTracker()
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            email.gone()
            password.gone()
            signInBtn.gone()
            registerBtn.gone()
            login_progress.visible()
            Timber.d("user == ${user.uid} ")
            updateUI()
        } ?: Timber.d("user == null ")


        signInBtn.setOnClickListener { attemptLogin() }
        registerBtn.setOnClickListener { createUser() }
    }

    private fun attemptLogin() {

        // Reset errors.
        email.error = null
        password.error = null

        // Store values at the time of the login attempt.
        val emailStr = email.text.toString()
        val passwordStr = password.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(passwordStr) && !isPasswordValid(passwordStr)) {
            password.error = getString(R.string.error_invalid_password)
            focusView = password
            cancel = true
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(emailStr)) {
            email.error = getString(R.string.error_field_required)
            focusView = email
            cancel = true
        } else if (!isEmailValid(emailStr)) {
            email.error = getString(R.string.error_invalid_email)
            focusView = email
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {
            email.gone()
            password.gone()
            signInBtn.gone()
            registerBtn.gone()
            login_progress.visible()
            login(emailStr, passwordStr)
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return email.contains("@")
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    private fun createUser() {
        val authService: AuthService = FirebaseServiceProvider.getFirebaseAuthService()
        // Reset errors.
        email.error = null
        password.error = null

        // Store values at the time of the login attempt.
        val emailStr = email.text.toString()
        val passwordStr = password.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(passwordStr) && !isPasswordValid(passwordStr)) {
            password.error = getString(R.string.error_invalid_password)
            focusView = password
            cancel = true
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(emailStr)) {
            email.error = getString(R.string.error_field_required)
            focusView = email
            cancel = true
        } else if (!isEmailValid(emailStr)) {
            email.error = getString(R.string.error_invalid_email)
            focusView = email
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {
            email.gone()
            password.gone()
            signInBtn.gone()
            registerBtn.gone()
            login_progress.visible()
            authService.createUserWithEmailAndPassword(emailStr, passwordStr)
            updateUI()
        }

    }

    private fun login(mEmail: String, mPassword: String) {
        val authService: AuthService = FirebaseServiceProvider.getFirebaseAuthService()

        authService.signInUserWithEmailAndPassword(mEmail, mPassword, object : OnServiceDataCallback<String> {
                override fun onDataLoaded(data: String) { // returns the UID of the logged in user if it failed returns ""
                    if (data.isNotBlank()) {
                        updateUI()

                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Authentication failed. please try again",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onError(error: Throwable) {
                    Timber.e("signInWithEmail:failure ${error.cause?.localizedMessage}")
                    Toast.makeText(
                        this@LoginActivity,
                        "Authentication failed. reason: " + error.cause?.message,
                        Toast.LENGTH_LONG
                    ).show()
                    email.visible()
                    password.visible()
                    signInBtn.visible()
                    registerBtn.visible()
                    login_progress.gone()
                }
            })
    }

    fun incrementAppOpendTracker(){

        val trackerService: TrackerService = FirebaseServiceProvider.getFirebaseTrackerService()
        trackerService.loadTrackerOnceFromDatabase(object : OnServiceDataCallback<Tracker>{
            override fun onDataLoaded(data: Tracker) {
                data.applicationOpend += 1
                trackerService.saveTrackerToDatabase(data)
            }

            override fun onError(error: Throwable) {
            }
        })
    }

    fun updateUI() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
