package be.mafken.gowalkgamified.ui.scoreboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.mafken.gowalkgamified.R
import be.mafken.gowalkgamified.model.User
import kotlinx.android.synthetic.main.user_card.view.*
import kotlin.properties.Delegates

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    var users: List<User> by Delegates.observable(emptyList()) { _, _, _ -> notifyDataSetChanged() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_card, parent, false))
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.userName.text = users[position].name
        holder.userDistance.text = "${users[position].totalDistance} Km"
        holder.userTime.text = users[position].totalTime
        holder.userScore.text = users[position].totalScore.toString()
    }

    inner class UserViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val userName: TextView = itemview.userCardName
        val userDistance: TextView = itemview.userCardDistance
        val userTime: TextView = itemview.userCardTime
        val userScore: TextView = itemview.userCardScore
    }
}