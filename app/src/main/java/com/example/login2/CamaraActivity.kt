package com.example.login2

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore

import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class CamaraActivity : AppCompatActivity() {
    lateinit var currentPhotoPath: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camara)
        val btnCamara = findViewById<ImageButton>(R.id.ImgBtnCama)
        btnCamara.setOnClickListener{
            startForResult.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))

        }
    }
private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
    result: ActivityResult ->
    if (result.resultCode == Activity.RESULT_OK){
        val intent = result.data
        val imageBitmap = intent?.extras?.get("data") as Bitmap
        val imageView = findViewById<ImageView>(R.id.imageViewCamara)
        imageView.setImageBitmap(imageBitmap)
    }

}

    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }



}