package com.example.chatui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.chatui.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSignUp: Button

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef:DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth=FirebaseAuth.getInstance()
        edtName=binding.edtName
        edtEmail=binding.edtEmail
        edtPassword=binding.edtPassword
        btnSignUp=binding.btnSignup

        btnSignUp.setOnClickListener {
            val name = edtName.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            signup(name,email,password)
        }

    }

    private fun signup(name:String,email: String, password: String) {
//         logic of creating user
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)

                    intent= Intent(this@SignUpActivity,LoginActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this,"Some error occured",Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String?) {
        mDbRef=FirebaseDatabase.getInstance().getReference()

        mDbRef.child("user").child(uid!!).setValue(User(name,email,uid))
    }
}