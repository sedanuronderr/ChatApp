package com.seda.chatapp.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.seda.chatapp.R
import com.seda.chatapp.databinding.ActivitySignUpBinding


class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var ad: String
    private lateinit var emaill: String
    private lateinit var sifre: String
    private lateinit var confirmm: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        binding.signUp.setOnClickListener {
            validation(it)

        }
        binding.signIn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    private fun validation(view: View) {

        ad = binding.name.text.toString().trim()
        emaill = binding.email.text.toString().trim()
        sifre = binding.password.text.toString().trim()
        confirmm = binding.confirmpassword.text.toString().trim()
        if (TextUtils.isEmpty(ad)) {
            showErrorSnackBar("Eksik Girdiniz", "Tekrar Deneyin", view)
           // hideProgressDialog()
        } else if (TextUtils.isEmpty(emaill)) {
            showErrorSnackBar("Eksik Girdiniz", "Tekrar Deneyin", view)
         //   hideProgressDialog()
        } else if (TextUtils.isEmpty(sifre)) {
            showErrorSnackBar("Eksik Girdiniz", "Tekrar Deneyin", view)
         //   hideProgressDialog()
        } else if (TextUtils.isEmpty(confirmm)) {
            showErrorSnackBar("Eksik Girdiniz", "Tekrar Deneyin", view)
          //  hideProgressDialog()
        } else if (sifre != confirmm) {
            showErrorSnackBar("Eksik Girdiniz", "Tekrar Deneyin", view)
            //hideProgressDialog()
        } else {

           // showProgress()

            auth.createUserWithEmailAndPassword(emaill, sifre)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val firebaseUser: FirebaseUser? = auth.currentUser
                         val userId:String = firebaseUser!!.uid

                        //   UserProfileChangeRequest.Builder().setDisplayName(ad).build()
                        //  auth.currentUser!!.updateProfile(update)

                        database = FirebaseDatabase.getInstance().getReference("Users").child(userId)
                        val hasMap:HashMap<String,String> = HashMap()
                        hasMap.put("userId",userId)
                        hasMap.put("username",ad)
                        hasMap.put("profileImage","")
                        showErrorSnackBar("Başarılı", "", view)
                        database.setValue(hasMap).addOnCompleteListener(this){
                            if(it.isSuccessful){


                                val intent =Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                            }
                        }




                    } else {
                        // If sign in fails, display a message to the user.
                        showErrorSnackBar("Bilgileri kontrol ediniz", "Tekrar Deneyin", view)


                    }



                }

        }

    }

    @SuppressLint("ResourceAsColor")
    fun showErrorSnackBar(message: String, errorMessage: String, view: View) {
        val sb = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        sb.setAction(errorMessage) {

        }
        sb.setActionTextColor(Color.RED)
        sb.setTextColor(Color.BLACK)
        sb.setBackgroundTint(R.color.primaryDarkColor)
        sb.show()
    }
}