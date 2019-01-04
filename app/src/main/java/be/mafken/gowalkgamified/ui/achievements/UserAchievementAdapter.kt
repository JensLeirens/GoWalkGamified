package be.mafken.gowalkgamified.ui.achievements

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

class UserAchievementAdapter : RecyclerView.Adapter<UserAchievementAdapter.UserAchievementViewHolder>() {

    var users: List<User> by Delegates.observable(emptyList()) { _, _, _ -> notifyDataSetChanged() }
    var context : Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAchievementViewHolder {
        context = parent.context
        return UserAchievementViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_achievement_card, parent, false))
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserAchievementViewHolder, position: Int) {
        holder.userName.text = users[position].name
        holder.userDistance.text = "Totale Amount of achievements: ${users[position].completedAchievements.count()}"
        Picasso.with(context).load(users[position].imageUrl).error(R.drawable.ic_user_dark).into(holder.userImage)
    }

    inner class UserAchievementViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val userName: TextView = itemview.userCardName
        val userDistance: TextView = itemview.userCardDistance
        val userImage: ImageView = itemview.userCardImage
    }
}