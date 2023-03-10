package com.example.chatui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.chatui.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogIn: Button
    private lateinit var btnSignUp: Button

    private lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        mAuth=FirebaseAuth.getInstance()

        edtEmail=binding.edtEmail
        edtPassword=binding.edtPassword
        btnLogIn=binding.btnLogin
        btnSignUp=binding.btnSignup

        btnSignUp.setOnClickListener {
            intent=Intent(this,SignUpActivity::class.java)
            finish()
            startActivity(intent)
        }
        btnLogIn.setOnClickListener {
            val email=edtEmail.text.toString()
            val password=edtPassword.text.toString()

            login(email,password)

        }


    }

    private fun login(email: String, password: String) {
//        logic for logging in

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    intent= Intent(this@LoginActivity,MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this,"User does not Exists",Toast.LENGTH_SHORT).show()
                }
            }
    }
}