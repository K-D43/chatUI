package com.example.chatui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatui.databinding.RecieveMsgLayoutBinding
import com.example.chatui.databinding.SentLayoutBinding
import com.example.chatui.databinding.UserLayoutBinding
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context,val messageList:ArrayList<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECEIVE=1;
    val ITEM_SEND=2;

    class SentViewHolder(val binding: SentLayoutBinding):RecyclerView.ViewHolder(binding.root){

    }
    class ReceiveViewHolder(val binding: RecieveMsgLayoutBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun getItemViewType(position: Int): Int {
            val currentMessage = messageList[position]

        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return ITEM_SEND
        }else{
            return ITEM_RECEIVE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType==1){
            return SentViewHolder(
                SentLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }else{
            return ReceiveViewHolder(
                RecieveMsgLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        var currentMessage = messageList[position]

        if (holder.javaClass == SentViewHolder::class.java){


            val viewHolder = holder as SentViewHolder
            holder.binding.txtSendMessage.text=currentMessage.message




        }else{
            val viewHolder = holder as ReceiveViewHolder
            holder.binding.txtRceiveMessage.text=currentMessage.message
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

}