package com.example.gawekerjo.utility

import android.app.Activity
import android.app.ProgressDialog
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import com.example.gawekerjo.env
import com.example.gawekerjo.view.EditProfileUserActivity
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class UploadUtility(activity:Activity) {
    var activity = activity;
    var dialog: ProgressDialog? = null
    var serverURL: String = env.API_URL+"upload"
    //var serverUploadDirectoryPath: String = "https://handyopinion.com/tutorials/uploads/"
    val client = OkHttpClient()

    fun uploadFile(sourceFilePath: String,a:EditProfileUserActivity, uploadedFileName: String? = null) {
        uploadFile(File(sourceFilePath),a, uploadedFileName)
    }

    fun uploadFile(sourceFileUri: Uri, a: EditProfileUserActivity, uploadedFileName: String? = null):String {
        val pathFromUri = URIPathHelper().getPath(activity,sourceFileUri)
        val file=File(pathFromUri)
        val mimeType = getMimeType(file)
        val filename=uploadedFileName+mimeType!!.replace("/",".").substringAfter("image")
        uploadFile(file,a,filename)
        return filename
    }

    fun uploadFile(sourceFile: File,a: EditProfileUserActivity, uploadedFileName: String? = null) {
        Thread {
            val mimeType = getMimeType(sourceFile);
            if (mimeType == null) {
                Log.e("file error", "Not able to get mime type")
                return@Thread
            }
            val fileName: String = uploadedFileName ?: sourceFile.name
            //showToast(fileName)
            toggleProgressDialog(true)
            try {
                val requestBody: RequestBody =
                    MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("uploaded_file", fileName,sourceFile.asRequestBody(mimeType.toMediaTypeOrNull()))
                        .build()

                val request: Request = Request.Builder().url(serverURL).post(requestBody)
                    .header("apitoken",env.API_TOKEN).build()

                val response: Response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    Log.d("File upload","success")
                    showToast("File uploaded successfully")
                    a.EditDataUser()
                } else {
                    Log.e("File upload", "failed")
                    showToast("File uploading failed")
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                Log.e("File upload", "failed")
                showToast("File uploading failed")
            }
            toggleProgressDialog(false)
        }.start()
    }

    // url = file path or whatever suitable URL you want.
    fun getMimeType(file: File): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(file.path)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }

    fun showToast(message: String) {
        activity.runOnUiThread {
            Toast.makeText( activity, message, Toast.LENGTH_LONG ).show()
        }
    }

    fun toggleProgressDialog(show: Boolean) {
        activity.runOnUiThread {
            if (show) {
                dialog = ProgressDialog.show(activity, "", "Uploading file...", true);
            } else {
                dialog?.dismiss();
            }
        }
    }
}