package com.example.movieapp.common.data_store

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream


fun copyUriToFile(context: Context, uri: Uri, destinationFile: File): Boolean {
    return try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        inputStream?.use { input ->
            val outputStream: OutputStream = FileOutputStream(destinationFile)
            outputStream.use { output ->
                val buffer = ByteArray(4 * 1024) // Buffer size
                var bytesRead: Int
                while (input.read(buffer).also { bytesRead = it } != -1) {
                    output.write(buffer, 0, bytesRead)
                }
                output.flush()
            }
        }
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}