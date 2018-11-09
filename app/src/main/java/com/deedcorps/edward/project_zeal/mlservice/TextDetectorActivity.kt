package com.deedcorps.edward.project_zeal.mlservice

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import com.deedcorps.edward.project_zeal.R
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage


class TextDetectorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_detector)

        val drawable = this.getResources().getDrawable(R.drawable.test_pick)
        val bitmap = (drawable as BitmapDrawable).bitmap
        getTextFromPhoto(bitmap)
    }

    fun getTextFromPhoto(bitmap: Bitmap) {
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        val textRecognizer = FirebaseVision.getInstance()
            .onDeviceTextRecognizer
        textRecognizer.processImage(image)
            .addOnSuccessListener { result ->
                val resultText = result.text
                val text = findViewById<TextView>(R.id.text)
                text.setOnClickListener {
                    text.text = resultText
                }
                Toast.makeText(this, "Success Text Retrived", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                // Task failed with an exception
                // ...
                Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()
            }

    }

}
