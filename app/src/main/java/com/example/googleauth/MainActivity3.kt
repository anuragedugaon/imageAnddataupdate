package com.example.googleauth

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)


        val db = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance().currentUser?.uid
        val gallaryAccess = findViewById<ImageView>(R.id.images)
        val name = findViewById<EditText>(R.id.names)
        val number = findViewById<EditText>(R.id.numbers)
        val email = findViewById<EditText>(R.id.emails)


        val button = findViewById<Button>(R.id.edit)

        button.setOnClickListener {
  startActivity(Intent(this, MainActivity2::class.java))

        }


        if (auth != null) {
            db.collection("users").document(auth)
                .get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot != null && snapshot.exists()) { // Check if snapshot is not null and exists

                        val myData: DataModel? = snapshot.toObject(DataModel::class.java)
                        if (myData != null) { // Check if myData is not null

//                            FashionList.add(myData)

                            name.setText(myData.name)
                            number.setText(myData.number)
                            email.setText(myData.email)
                            Glide.with(this)
                                .load(myData.imageUrl)
                                .error(R.drawable.ic_launcher_background)
                                .into(gallaryAccess)


                        } else {
                            Log.e(ContentValues.TAG, "DataModel is null")
                        }
                    } else {
                        Log.e(ContentValues.TAG, "Snapshot is null or doesn't exist")
                    }
                }
                .addOnFailureListener { e ->
                    Log.e(ContentValues.TAG, "Error adding document", e)
                }
        }
    }
}