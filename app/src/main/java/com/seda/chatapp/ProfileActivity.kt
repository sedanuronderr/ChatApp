package com.seda.chatapp

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.seda.chatapp.FirebaseStorage.FirebaseClass
import com.seda.chatapp.databinding.ActivityProfileBinding
import com.seda.chatapp.model.User
import com.seda.chatapp.utils.GlideLoader
import java.io.IOException

class ProfileActivity : AppCompatActivity() , View.OnClickListener{
    private lateinit var binding :ActivityProfileBinding
    var userList =ArrayList<User>()
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var databaseReference: DatabaseReference

    private var mselectedImage : Uri?= null
    private var imageUrl:String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)



        firebaseUser =FirebaseAuth.getInstance().currentUser!!
        databaseReference=FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.uid)

        databaseReference.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                    val user = snapshot.getValue(User::class.java)

                    binding.name.text =user!!.username
                if (user.profileImage == " "){
                    binding.imgprofile.setImageResource(R.drawable.acount)
                }
                else{
                    Glide.with(this@ProfileActivity).load(user.profileImage).placeholder(R.drawable.acount)
                        .into(binding.imgprofile)
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }


        })

        binding.imgback.setOnClickListener{
            onBackPressed()
        }
        binding.imgprofile.setOnClickListener {
            val intent = Intent(this,ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.imgprofile.setOnClickListener(this)
        binding.save.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
      if(v !=null){
          when(v.id){
          R.id.imgprofile ->{
              if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                  val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                  startActivityForResult(galleryIntent, 1)

              }
              else {
                  ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 2)
              }
          }
              R.id.save->{
                  if (mselectedImage != null)
                      FirebaseClass.uploadImage(this,mselectedImage)
                  else {
                      updateProfildetails()
                  }
              }


          }
      }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 2){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galleryIntent,1)
            }
            else{
                Toast.makeText(this,"sorun", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(data != null){
                try{
                    mselectedImage = data.data!!
                    //   binding.userPhoto.setImageURI(Uri.parse(selected.toString()))
GlideLoader(this).loadUserPicture(mselectedImage!!,binding.imgprofile)
                   // GlideLoader(this).loadUserPicture(mselectedImage!!,binding.imgprofile)
                }
                catch (e: IOException){
                    e.printStackTrace()
                }
            }
            else if(resultCode == Activity.RESULT_CANCELED){
                Log.e("request canceled","ımage")
            }
        }

    }
    fun imageUploadSuccess(imageUrll:String){

        imageUrl =imageUrll
        updateProfildetails()
    }

    private fun updateProfildetails() {
        val userHashMap = mutableMapOf<String,Any>()
        userHashMap["profileImage"]=imageUrl
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.uid)
        databaseReference.updateChildren(userHashMap).addOnCompleteListener {
            if(it.isSuccessful){


                Toast.makeText(this,"başarılı",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this,"başarısız",Toast.LENGTH_SHORT).show()
            }
        }

    }

}