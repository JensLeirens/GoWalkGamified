package be.mafken.gowalkgamified.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.mafken.gowalkgamified.R
import be.mafken.gowalkgamified.model.Walk
import kotlinx.android.synthetic.main.walk_card.view.*
import kotlin.properties.Delegates

class WalkAdapter : RecyclerView.Adapter<WalkAdapter.WalkViewHolder>() {

 var walkings: List<Walk> by Delegates.observable(
  emptyList()) { _, _, _ -> notifyDataSetChanged() }

 override fun onCreateViewHolder(parent: ViewGroup,
                                 viewType: Int): WalkViewHolder {
  return WalkViewHolder(
   LayoutInflater.from(parent.context).inflate(
    R.layout.walk_card,
    parent,
    false
   )
  )
 }

 override fun getItemCount(): Int {
  return walkings.size
 }

 override fun onBindViewHolder(holder: WalkViewHolder, position: Int) {
  holder.walkDistance.text = "${walkings[position].amountKm} Km"
  holder.walkTime.text = walkings[position].displayTime
  holder.walkScore.text = walkings[position].score.toString()
 }

 inner class WalkViewHolder(itemview: View) :
  RecyclerView.ViewHolder(itemview) {
  val walkDistance: TextView = itemview.walkCardDistance
  val walkTime: TextView = itemview.walkCardTime
  val walkScore: TextView = itemview.walkCardScore
 }
}