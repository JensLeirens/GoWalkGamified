package be.mafken.gowalkgamified.ui.achievements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import be.mafken.gowalkgamified.R
import be.mafken.gowalkgamified.extensions.nonNull
import be.mafken.gowalkgamified.extensions.observe
import kotlinx.android.synthetic.main.user_achievement_fragment.*

class UserAchievementFragment : Fragment() {

 companion object {
  fun newInstance() = UserAchievementFragment()
 }

 private val viewModel: UserAchievementViewModel by lazy {
  ViewModelProviders.of(this)
   .get(UserAchievementViewModel::class.java)
 }
 private val achievementAdapter = AchievementAdapter()

 override fun onCreateView(
  inflater: LayoutInflater, container: ViewGroup?,
  savedInstanceState: Bundle?
 ): View? {
  return inflater.inflate(R.layout.user_achievement_fragment,
   container, false)
 }

 override fun onActivityCreated(savedInstanceState: Bundle?) {
  super.onActivityCreated(savedInstanceState)
  viewModel.incrementUserAchievementOpendTracker()
  viewModel.getUserFromDatabase()
  viewModel.getAchievements()

  userAchievementRecycler.layoutManager =
   LinearLayoutManager(context)
  userAchievementRecycler.adapter = achievementAdapter

  viewModel.achievements.nonNull()
   .observe(this) {
   achievementAdapter.achievements = it
  }

  viewModel.user.nonNull().observe(this) {
   achievementAdapter.user = it
  }

 }
}
