package be.mafken.gowalkgamified.ui.scoreboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.mafken.gowalkgamified.R
import be.mafken.gowalkgamified.model.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.user_card.view.*
import kotlin.properties.Delegates

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

 var users: List<User> by Delegates.observable(
  emptyList()) { _, _, _ -> notifyDataSetChanged() }
 var context: Context? = null

 override fun onCreateViewHolder(parent: ViewGroup,
                                 viewType: Int): UserViewHolder {
  context = parent.context
  return UserViewHolder(
   LayoutInflater.from(parent.context).inflate(
    R.layout.user_card,
    parent,
    false
   )
  )
 }

 override fun getItemCount(): Int {
  return users.size
 }

 override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
  holder.userName.text = users[position].name
  holder.userDistance.text = "Totale afstand: ${users[position].totalDistance} Km"
  holder.userTime.text = "Totale tijd: ${users[position].totalTime}"
  holder.userScore.text = "Score: ${users[position].totalScore}"
  Picasso.with(context).load(users[position].imageUrl).
   error(R.drawable.ic_user_dark)
   .into(holder.userImage)
 }

 inner class UserViewHolder(itemview: View) :
  RecyclerView.ViewHolder(itemview) {
  val userName: TextView = itemview.userCardName
  val userDistance: TextView = itemview.userCardDistance
  val userTime: TextView = itemview.userCardTime
  val userScore: TextView = itemview.userCardScore
  val userImage: ImageView = itemview.userCardImage
 }
}