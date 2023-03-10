package com.example.chatui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatui.databinding.UserLayoutBinding
import com.google.firebase.auth.FirebaseAuth

class UserAdapter(val context: Context,val userList: ArrayList<User>):RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    class UserViewHolder(val binding: UserLayoutBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            UserLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser=userList[position]
        holder.binding.txtName.text=currentUser.name
        holder.binding.itemView.setOnClickListener {
            val intent=Intent(context,chatActivity::class.java)

            intent.putExtra("name",currentUser.name)
            intent.putExtra("uid",currentUser.uid)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}