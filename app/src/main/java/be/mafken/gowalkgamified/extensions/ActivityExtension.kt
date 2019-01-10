package be.mafken.gowalkgamified.extensions

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import be.mafken.gowalkgamified.R


fun Activity.hideKeyboard(): Boolean {
 val view = currentFocus
 view?.let {
  val inputMethodManager =
   getSystemService(Context.INPUT_METHOD_SERVICE)
   as InputMethodManager
  return inputMethodManager.hideSoftInputFromWindow(
   view.windowToken,
   InputMethodManager.HIDE_NOT_ALWAYS
  )
 }
 return false
}

fun FragmentActivity.goToFragment(backStackMessage: String?,
                                  fragment: Fragment) {
 supportFragmentManager.beginTransaction()
  .replace(R.id.container, fragment)
  .addToBackStack(backStackMessage)
  .commit()
}

fun FragmentActivity
 .goToFragmentWithoutBackstack(fragment: Fragment) {
 supportFragmentManager.beginTransaction()
  .replace(R.id.container, fragment)
  .commit()
}