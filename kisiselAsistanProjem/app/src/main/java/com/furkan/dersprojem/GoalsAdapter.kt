package com.furkan.dersprojem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GoalsAdapter(private val goals: List<MainActivityAnaSayfa.Goal>) : RecyclerView.Adapter<GoalsAdapter.GoalViewHolder>() {

    class GoalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.goalTitleTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.goalDescriptionTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_goal, parent, false)
        return GoalViewHolder(view)
    }

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        val currentGoal = goals[position]
        holder.titleTextView.text = currentGoal.title
        holder.descriptionTextView.text = currentGoal.description
    }

    override fun getItemCount(): Int {
        return goals.size
    }
}

