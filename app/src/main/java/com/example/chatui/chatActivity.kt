package com.example.chatui

import android.content.Intent
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatui.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class chatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sentButton: ImageView
    private lateinit var messageAdapter:MessageAdapter
    private lateinit var messageList: ArrayList<com.example.chatui.Message>
    private lateinit var mDbRef:DatabaseReference

    var receiverRoom:String?=null
    var senderRoom:String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val name = intent.getStringExtra("name")
        val receiveruid = intent.getStringExtra("uid")


        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        mDbRef=FirebaseDatabase.getInstance().getReference()
        senderRoom = receiveruid + senderUid
        receiverRoom = senderUid + receiveruid

        supportActionBar?.title=name

        messageRecyclerView=binding.chatrecyclerView
        messageBox=binding.edtMsg
        sentButton=binding.sendMessage

        messageList= ArrayList()
        messageAdapter= MessageAdapter(this,messageList)

        messageRecyclerView.layoutManager=LinearLayoutManager(this)
        messageRecyclerView.adapter=MessageAdapter(this,messageList)


//        logic of data to add in the recycler view
        mDbRef.child("chats").child(senderRoom!!).child("massage")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear()

                    for (postSnapshot in snapshot.children){
                        val message = postSnapshot.getValue(com.example.chatui.Message::class.java)
                        messageList.add(message!!)
                        Log.d("CHATMESSAGE",message.toString())

                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@chatActivity,"Network Issue",Toast.LENGTH_SHORT).show()
                }

            })




//        adding the message to the database
        binding.sendMessage.setOnClickListener {
            val message = messageBox.text.toString()
            val messageObject = com.example.chatui.Message(message,senderUid!!)

            mDbRef.child("chats").child(senderRoom!!).child("massage").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDbRef.child("chats").child(receiverRoom!!).child("massage").push()
                        .setValue(messageObject)
                }
            messageBox.setText("")

        }
    }
}