package com.seda.chatapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.seda.chatapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding :ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var email : String
    private lateinit var password : String
    private lateinit var firebaseUser: FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth







        binding.signInn.setOnClickListener {
            login(it)

        }
        binding.signUpp.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser

        if(currentUser != null){
            val intent =Intent(this,UserActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun login(view: View){
        email =binding.loginEmail.text.toString().trim()
        password= binding.loginPassword.text.toString().trim()



        if (TextUtils.isEmpty(email) ) {
            showErrorSnackBar("Eksik Girdiniz", "Tekrar Deneyin", view)

        }
        else if (TextUtils.isEmpty(password) ) {
            showErrorSnackBar("Eksik Girdiniz", "Tekrar Deneyin", view)

        }
        else{
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        binding.loginEmail.setText("")
                        binding.loginPassword.setText("")
                           val intent =Intent(this,UserActivity::class.java)
                        startActivity(intent)
                        finish()
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(this,"başarılı", Toast.LENGTH_SHORT).show()


                    } else {
                        // If sign in fails, display a message to the user.

                        Toast.makeText(this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }

                }
        }
    }

    fun showErrorSnackBar(message: String, errorMessage: String, view: View) {
        val sb = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        sb.setAction(errorMessage) {

        }
        sb.setActionTextColor(Color.RED)
        sb.setTextColor(Color.BLACK)
        sb.setBackgroundTint(Color.WHITE)
        sb.show()
    }
}