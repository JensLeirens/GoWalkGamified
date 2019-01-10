package be.mafken.gowalkgamified.ui.achievements

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.mafken.gowalkgamified.R
import be.mafken.gowalkgamified.model.Achievement
import be.mafken.gowalkgamified.model.User
import kotlinx.android.synthetic.main.achievement_card.view.*
import kotlin.properties.Delegates

class AchievementAdapter : RecyclerView.Adapter
<AchievementAdapter.AchievementViewHolder>() {

 var achievements: List<Achievement> by Delegates.observable(
  emptyList()) { _, _, _ -> notifyDataSetChanged() }
 var user: User by Delegates.observable(
  User()) { _, _, _ -> notifyDataSetChanged() }

 override fun onCreateViewHolder(parent: ViewGroup,
                                 viewType: Int):
  AchievementViewHolder {
  return AchievementViewHolder(
   LayoutInflater.from(parent.context).inflate(
    R.layout.achievement_card,
    parent,
    false
   )
  )
 }

 override fun getItemCount(): Int {
  return achievements.size
 }

 override fun onBindViewHolder(holder:
                               AchievementViewHolder, position: Int) {
  holder.achievementName.text = achievements[position].name
  holder.achievementDescription.text =
   achievements[position].description

  holder.achievementCompleted.isChecked =
   user.completedAchievements
    .contains(achievements[position].id)
 }

 inner class AchievementViewHolder(itemview: View) :
  RecyclerView.ViewHolder(itemview) {
  val achievementDescription: TextView = itemview.achievementDescription
  val achievementName: TextView = itemview.achievementName
  val achievementCompleted: CheckBox = itemview.achievementCompletedCheck
 }
}