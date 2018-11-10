package com.deedcorps.edward.project_zeal.mlservice

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.deedcorps.edward.project_zeal.R
import com.deedcorps.edward.project_zeal.api.Injection
import com.deedcorps.edward.project_zeal.api.model.Article
import com.deedcorps.edward.project_zeal.api.model.ZealResponse
import com.deedcorps.edward.project_zeal.floatingView.FloatingViewControlFragment
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.android.synthetic.main.activity_text_detector.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import android.provider.MediaStore




class TextDetectorActivity : AppCompatActivity(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val zealAdapter by lazy { ZealAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_detector)

//        val drawable = this.resources.getDrawable(R.drawable.test_pick, null)
//        val bitmap = (drawable as BitmapDrawable).bitmap


        intent?.data?.let { uri ->
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
            analyzeTextFromBitmap(bitmap)
        }
        resultsRecyclerView.apply {
            adapter = zealAdapter
            setHasFixedSize(true)
        }
    }

    private fun analyzeTextFromBitmap(bitmap: Bitmap) {
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        val textRecognizer = FirebaseVision.getInstance().onDeviceTextRecognizer
        textRecognizer.processImage(image)
            .addOnSuccessListener { result ->
                val resultText = result.text
                launch {
                    val zealResponse = getResponse(Article(content = resultText))
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()
            }

    }

    private suspend fun getResponse(article: Article): ZealResponse {
        return withContext(Dispatchers.IO) {
            Injection.getZealResponse(article)
        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

}
