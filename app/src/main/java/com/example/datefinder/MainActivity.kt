package com.example.datefinder

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.datefinder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var ttsManager: TTSManager
    private lateinit var ocrProcessor: OCRProcessor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ttsManager = TTSManager(this)
        ocrProcessor = OCRProcessor(this)

        val selectImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, it)
                binding.imgPreview.setImageBitmap(bitmap)
                processImage(bitmap)
            }
        }

        binding.btnSelectImage.setOnClickListener {
            selectImageLauncher.launch("image/*")
        }
    }

    private fun processImage(bitmap: Bitmap) {
        val text = ocrProcessor.recognizeText(bitmap)
        val date = DateExtractor.extractRelevantDate(text)
        binding.txtExtractedDate.text = "Detected Date: ${date ?: "None"}"
        ttsManager.speak(date ?: "No date detected")
    }

    override fun onDestroy() {
        ttsManager.shutdown()
        super.onDestroy()
    }
}