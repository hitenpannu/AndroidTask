package com.hitenderpannu.userlist.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hitenderpannu.userlist.entity.User

class UserListAdapter(private var userList: List<User> = listOf()) : RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    fun updateUserList(list: List<User>) {
        this.userList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_item_user_list, parent, false)
        return UserListViewHolder(view)
    }

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        val item = userList.get(position)
        holder.bind(item)
    }

    inner class UserListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val idTextView by lazy { view.findViewById<TextView>(R.id.userId) }
        private val emailTextView by lazy { view.findViewById<TextView>(R.id.userEmail) }
        private val nameTextView by lazy { view.findViewById<TextView>(R.id.userName) }

        fun bind(user: User) {
            idTextView.text = user.userId
            emailTextView.text = user.email
            nameTextView.text = user.name
        }
    }
}
