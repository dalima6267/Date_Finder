package com.example.datefinder

import android.content.Context
import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream
import com.googlecode.tesseract.android.TessBaseAPI

class OCRProcessor(private val context: Context) {
    private val tessBaseAPI: TessBaseAPI = TessBaseAPI()

    init {
        copyTessDataForTextRecognizer()
        val dataPath = context.filesDir.absolutePath
        tessBaseAPI.init(dataPath, "eng")
    }

    fun recognizeText(bitmap: Bitmap): String {
        tessBaseAPI.setImage(bitmap)
        val text = tessBaseAPI.utF8Text
        tessBaseAPI.clear()
        return text
    }

    private fun copyTessDataForTextRecognizer() {
        val tessDataDir = File(context.filesDir, "tessdata")
        if (!tessDataDir.exists()) {
            tessDataDir.mkdir()
        }

        val filePath = File(tessDataDir, "eng.traineddata")
        if (!filePath.exists()) {
            context.assets.open("tessdata/eng.traineddata").use { input ->
                FileOutputStream(filePath).use { output ->
                    input.copyTo(output)
                }
            }
        }
    }
}