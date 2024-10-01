package com.example.project

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class AdminTopicAdd : AppCompatActivity() {

    private lateinit var bcktoAdminHome: ImageButton

    private lateinit var addTopicButton: Button

    private lateinit var topicNameEdt: EditText
    private lateinit var topicDescEdt: EditText
    private lateinit var topicHoursEdt: EditText
    private lateinit var topicBigDescEdt: EditText

    private lateinit var firestore: FirebaseFirestore
    private lateinit var storageReference: StorageReference

    private val IMAGE_REQUEST_CODE = 1001
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin_topic_add)


        // Initialize Firestore and Firestorage
        firestore = FirebaseFirestore.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        // Initialize UI components
        bcktoAdminHome = findViewById(R.id.adminHomeBck)

        addTopicButton = findViewById(R.id.TopicAddBtn)

        topicNameEdt = findViewById(R.id.AdminTopicNameEdt)
        topicDescEdt = findViewById(R.id.AdminTopicDescEdt)
        topicHoursEdt = findViewById(R.id.AdminTopicHoursEdt)
        topicBigDescEdt = findViewById(R.id.AdminTopicBigdescEdt)

        val selectImageBtn: Button = findViewById(R.id.selectImageBtn)
        val imageUploadStatus: TextView = findViewById(R.id.imageUploadStatus)

        selectImageBtn.setOnClickListener {
            selectImage()
        }

        bcktoAdminHome.setOnClickListener {
            val intent = Intent(this, AdminHome::class.java)
            startActivity(intent)
        }

        // Set onClickListener for Add Topic button
        addTopicButton.setOnClickListener {
            val topicName = topicNameEdt.text.toString().trim()
            val topicDesc = topicDescEdt.text.toString().trim()
            val topicHours = topicHoursEdt.text.toString().trim()
            val topicBigDesc = topicBigDescEdt.text.toString().trim()

            if (topicName.isNotEmpty() && topicDesc.isNotEmpty() && topicHours.isNotEmpty() && topicBigDesc.isNotEmpty()) {
                selectedImageUri?.let {
                    uploadVideoToFirebase(it, topicName, topicDesc, topicHours, topicBigDesc)
                } ?: run {
                    imageUploadStatus.text = "Please select a image first!"
                }
            } else {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data
            findViewById<TextView>(R.id.imageUploadStatus).text =
                "Image selected: ${selectedImageUri?.lastPathSegment}"
        }
    }

    private fun uploadVideoToFirebase(
        imageUri: Uri,
        topicName: String,
        topicDesc: String,
        topicHours: String,
        topicBigDesc: String
    ) {
        val imageRef = storageReference.child("images/${System.currentTimeMillis()}.jpg")
        imageRef.putFile(imageUri)
            .addOnSuccessListener { taskSnapshot ->
                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    saveImageUrlToFirestore(
                        uri.toString(),
                        topicName,
                        topicDesc,
                        topicHours,
                        topicBigDesc
                    )
                }
            }
            .addOnFailureListener {
                findViewById<TextView>(R.id.imageUploadStatus).text =
                    "Image upload failed: ${it.message}"
            }
    }

    // Function to add topic to Firestore
    private fun saveImageUrlToFirestore(
        imageUrl: String,
        name: String,
        description: String,
        hours: String,
        bigDesc: String
    ) {
        val topic = hashMapOf(
            "name" to name,
            "description" to description,
            "hours" to hours,
            "big_description" to bigDesc,
            "imageUrl" to imageUrl
        )

        firestore.collection("Topics")
            .add(topic)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(
                    this,
                    "Topic added with ID: ${documentReference.id}",
                    Toast.LENGTH_SHORT
                ).show()
                findViewById<TextView>(R.id.imageUploadStatus).text =
                    "Topic added successfully with image!"
                clearForm()
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error adding topic: ${e.message}", Toast.LENGTH_SHORT).show()
                findViewById<TextView>(R.id.imageUploadStatus).text =
                    "Failed to add topic: ${e.message}"
            }
    }


    // Function to add topic to Firestore
    /* private fun addTopicToFirestore(
         name: String,
         description: String,
         hours: String,
         videoUrl: String,
         bigDesc: String
     ) {
         val topic = hashMapOf(
             "name" to name,
             "description" to description,
             "hours" to hours,
             "big_description" to bigDesc,
             "videoUrl" to videoUrl
         )

         firestore.collection("Topics")
             .add(topic)
             .addOnSuccessListener { documentReference ->
                 Toast.makeText(
                     this,
                     "Topic added with ID: ${documentReference.id}",
                     Toast.LENGTH_SHORT
                 ).show()

                 findViewById<TextView>(R.id.videoUploadStatus).text =
                     "Topic added successfully with video!"
                 // Optionally clear the form fields
                 clearForm()
                 finish()
             }
             .addOnFailureListener { e ->
                 Toast.makeText(this, "Error adding topic: ${e.message}", Toast.LENGTH_SHORT).show()
                 findViewById<TextView>(R.id.videoUploadStatus).text =
                     "Failed to add topic: ${e.message}"
             }
     }*/

    private fun clearForm() {
        topicNameEdt.text.clear()
        topicDescEdt.text.clear()
        topicHoursEdt.text.clear()
        topicBigDescEdt.text.clear()
    }

}

