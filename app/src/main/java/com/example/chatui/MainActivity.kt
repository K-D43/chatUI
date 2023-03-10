package com.example.chatui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatui.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var userList: ArrayList<User>
    private lateinit var userAdapter: UserAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth= FirebaseAuth.getInstance()
        mDbRef=FirebaseDatabase.getInstance().getReference()

        userList=ArrayList()
        userAdapter= UserAdapter(this,userList)

        recyclerView=binding.userRecyclerView
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.adapter=userAdapter
        
        mDbRef.child("user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                userList.clear()
                for (postSnapshot in snapshot.children){
                    val currentUser = postSnapshot.getValue(User::class.java)

                    if(mAuth.currentUser?.uid != currentUser?.uid){
                        userList.add(currentUser!!)
                    }
                    Log.d("chat",currentUser.toString())


                }
                userAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

       


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout){
            mAuth.signOut()
            intent= Intent(this,LoginActivity::class.java)
            finish()
            startActivity(intent)
            return true
        }
        return true
    }
}