package com.rjsquare.kkmt.Activity.Register

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable.ApiCallingInstance
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.AppConstant.Constants
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.Events.NetworkServices
import com.rjsquare.kkmt.RetrofitInstance.LogInCall.UploadDoc_Model
import com.rjsquare.kkmt.databinding.ActivityUploadDocBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream


class upload_doc : AppCompatActivity(), View.OnClickListener {

    var photoFileName = "photo.jpg"
    var photoFile: File? = null

    var resultLauncher: ActivityResultLauncher<Intent>? = null


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    companion object {
        lateinit var uploadDocActyivity: Activity
        lateinit var selUri: Uri
        lateinit var DB_UploadDoc: ActivityUploadDocBinding
        var PDFString: String = ""
        var selFileSize = 0.0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_UploadDoc = DataBindingUtil.setContentView(this, R.layout.activity_upload_doc)
        try {
            ApplicationClass.StatusTextWhite(this, true)
            uploadDocActyivity = this

            if (ApplicationClass.IsRegisterFlow) {
                DB_UploadDoc.txtSkip.visibility = View.VISIBLE
//                DB_UploadDoc.txtSaveandexplore.text = "Save"
            } else {
                DB_UploadDoc.txtSkip.visibility = View.GONE
            }

            DB_UploadDoc.cntUploadDoc.setOnClickListener(this)
            DB_UploadDoc.txtSaveandContinue.setOnClickListener(this)
            DB_UploadDoc.txtPdf.setOnClickListener(this)
            DB_UploadDoc.txtSkip.setOnClickListener(this)
            DB_UploadDoc.txtCamera.setOnClickListener(this)
            DB_UploadDoc.txtGallery.setOnClickListener(this)
            DB_UploadDoc.cntUploadDocType.setOnClickListener(this)
            DB_UploadDoc.txtCancel.setOnClickListener(this)
            DB_UploadDoc.txtAlertok.setOnClickListener(this)
            DB_UploadDoc.txtUnauthOk.setOnClickListener(this)


            // Initialize result launcher
            resultLauncher = registerForActivityResult(
                StartActivityForResult(),
                ActivityResultCallback<ActivityResult> { result ->
                    // Initialize result data
                    val data: Intent = result.data!!
                    // check condition
                    if (data != null) {
                        DB_UploadDoc.cntLoader.visibility = View.VISIBLE
                        // When data is not equal to empty
                        // Get PDf uri
                        val sUri: Uri? = data.data

                        // Get PDF path
                        val sPath: String = sUri!!.path!!

                        ConvertFileOrImageToString(sUri)
                    }
                })
        } catch (NE: NullPointerException) {
            NE.printStackTrace()
        } catch (IE: IndexOutOfBoundsException) {
            IE.printStackTrace()
        } catch (AE: ActivityNotFoundException) {
            AE.printStackTrace()
        } catch (E: IllegalArgumentException) {
            E.printStackTrace()
        } catch (RE: RuntimeException) {
            RE.printStackTrace()
        } catch (E: Exception) {
            E.printStackTrace()
        }
    }

    fun ConvertFileOrImageToString(sUri: Uri) {
        //Convert File to Base64String
        try {
            Log.e("TAG", "CHECKURIPATH ; ")
            selUri = sUri
            Base64Converter().execute()

        } catch (e: java.lang.Exception) {
            // TODO: handle exception
            e.printStackTrace()
            Log.d("error", "onActivityResult: $e")
            DB_UploadDoc.cntLoader.visibility = View.GONE
        }
    }


    private fun selectPDF() {
        // Initialize intent
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        // set type
        intent.type = "application/pdf"
        // Launch intent
        resultLauncher!!.launch(intent)
    }

    private fun selectImage() {
        // Initialize intent
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        // set type
        intent.type = "image/*"
        // Launch intent
        resultLauncher!!.launch(intent)
    }

    private fun selectImageCam() {
        // Initialize intent
        photoFile = getPhotoFileUri(photoFileName)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            // Continue only if the File was successfully created
            if (photoFile != null) {
                val photoURI = FileProvider.getUriForFile(
                    this,
                    "com.rjsquare.kkmt.fileprovider",
                    photoFile!!
                )
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(intent, 1)
            }
        } else {
            Log.e("TAG", "ActivityNotFound")
        }
    }

    // Returns the File for a photo stored on disk given the fileName
    fun getPhotoFileUri(fileName: String): File {
        val mediaStorageDir =
            File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "KKMT")

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d("KKMT", "failed to create directory")
        }

        // Return the file target for the photo based on filename
        return File(mediaStorageDir.path + File.separator + fileName)
    }

    private fun UploadDoc(fileString: String) {
        try {

            //Here the json data is add to a hash map with key data
            val params: MutableMap<String, String> =
                HashMap()
            params[Constants.paramKey_UserId] =
                ApplicationClass.userInfoModel.data!!.userid.toString()
            params[Constants.paramKey_Document] = fileString

            val service =
                ApiCallingInstance.retrofitInstance.create<NetworkServices.UploadDocService>(
                    NetworkServices.UploadDocService::class.java
                )
            val call =
                service.UploadDoc(
                    params,
                    ApplicationClass.userInfoModel.data!!.access_token.toString()
                )

            call.enqueue(object : Callback<UploadDoc_Model> {
                override fun onFailure(call: Call<UploadDoc_Model>, t: Throwable) {
                    Log.e("GetResponsesasXASX", "Hell: ")
                    DB_UploadDoc.cntLoader.visibility = View.GONE
                }

                override fun onResponse(
                    call: Call<UploadDoc_Model>,
                    response: Response<UploadDoc_Model>
                ) {
                    DB_UploadDoc.cntLoader.visibility = View.GONE

                    if (response.body()!!.status.equals(Constants.ResponseSucess)) {
                        var HomeIntent = Intent(this@upload_doc, Upload_Selfie::class.java)
                        startActivity(HomeIntent)
                        overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
                    } else if (response.body()!!.status.equals(Constants.ResponseUnauthorized)) {
                        DB_UploadDoc.cntUnAuthorized.visibility = View.VISIBLE
                    } else {
                        DB_UploadDoc.txtAlertmsg.text = response.body()!!.message
                        DB_UploadDoc.cntAlert.visibility = View.VISIBLE
                    }
                }
            })
        } catch (E: Exception) {
            print(E)
        } catch (NE: NullPointerException) {
            print(NE)
        } catch (IE: IndexOutOfBoundsException) {
            print(IE)
        } catch (IE: IllegalStateException) {
            print(IE)
        } catch (AE: ActivityNotFoundException) {
            print(AE)
        } catch (KNE: KotlinNullPointerException) {
            print(KNE)
        } catch (CE: ClassNotFoundException) {
            print(CE)
        }
    }

    override fun onClick(view: View?) {
        try {
            if (System.currentTimeMillis() < ApplicationClass.lastClick) return else {
                ApplicationClass.lastClick =
                    System.currentTimeMillis() + ApplicationClass.clickInterval
                if (view == DB_UploadDoc.cntUploadDoc) {
                    DB_UploadDoc.cntUploadDocType.visibility = View.VISIBLE
                } else if (view == DB_UploadDoc.txtSkip) {
                    var HomeIntent = Intent(this, Upload_Selfie::class.java)
                    startActivity(HomeIntent)
                    overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
                } else if (view == DB_UploadDoc.txtUnauthOk) {
                    DB_UploadDoc.cntUnAuthorized.visibility = View.GONE
                    ApplicationClass.UserLogout(this)
                } else if (view == DB_UploadDoc.txtPdf) {
                    val permissions =
                        arrayOf(
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    Permissions.check(this, permissions, null, null, object : PermissionHandler() {
                        override fun onGranted() {
                            // do your task.
                            DB_UploadDoc.cntUploadDocType.visibility = View.GONE
                            selectPDF()
                        }

                        override fun onDenied(
                            context: Context?,
                            deniedPermissions: java.util.ArrayList<String>?
                        ) {
                            super.onDenied(context, deniedPermissions)
                        }

                        override fun onBlocked(
                            context: Context?,
                            blockedList: java.util.ArrayList<String>?
                        ): Boolean {
                            return super.onBlocked(context, blockedList)
                        }

                        override fun onJustBlocked(
                            context: Context?,
                            justBlockedList: java.util.ArrayList<String>?,
                            deniedPermissions: java.util.ArrayList<String>?
                        ) {
                            super.onJustBlocked(context, justBlockedList, deniedPermissions)
                        }
                    })
                } else if (view == DB_UploadDoc.txtCamera) {
                    val permissions =
                        arrayOf(
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    Permissions.check(this, permissions, null, null, object : PermissionHandler() {
                        override fun onGranted() {
                            // do your task.
                            DB_UploadDoc.cntUploadDocType.visibility = View.GONE
                            selectImageCam()
                        }

                        override fun onDenied(
                            context: Context?,
                            deniedPermissions: java.util.ArrayList<String>?
                        ) {
                            super.onDenied(context, deniedPermissions)
                        }

                        override fun onBlocked(
                            context: Context?,
                            blockedList: java.util.ArrayList<String>?
                        ): Boolean {
                            return super.onBlocked(context, blockedList)
                        }

                        override fun onJustBlocked(
                            context: Context?,
                            justBlockedList: java.util.ArrayList<String>?,
                            deniedPermissions: java.util.ArrayList<String>?
                        ) {
                            super.onJustBlocked(context, justBlockedList, deniedPermissions)
                        }
                    })
                } else if (view == DB_UploadDoc.txtGallery) {
                    val permissions =
                        arrayOf(
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    Permissions.check(this, permissions, null, null, object : PermissionHandler() {
                        override fun onGranted() {
                            // do your task.
                            DB_UploadDoc.cntUploadDocType.visibility = View.GONE
                            selectImage()
                        }

                        override fun onDenied(
                            context: Context?,
                            deniedPermissions: java.util.ArrayList<String>?
                        ) {
                            super.onDenied(context, deniedPermissions)
                        }

                        override fun onBlocked(
                            context: Context?,
                            blockedList: java.util.ArrayList<String>?
                        ): Boolean {
                            return super.onBlocked(context, blockedList)
                        }

                        override fun onJustBlocked(
                            context: Context?,
                            justBlockedList: java.util.ArrayList<String>?,
                            deniedPermissions: java.util.ArrayList<String>?
                        ) {
                            super.onJustBlocked(context, justBlockedList, deniedPermissions)
                        }
                    })
                } else if (view == DB_UploadDoc.txtCancel) {
                    DB_UploadDoc.cntUploadDocType.visibility = View.GONE
                } else if (view == DB_UploadDoc.txtSaveandContinue) {
                    if (!PDFString.equals("", true)) {
                        DB_UploadDoc.cntLoader.visibility = View.VISIBLE
                        UploadDoc(PDFString)
                    } else {
                        DB_UploadDoc.txtAlertmsg.text = "Invalid Document."
                        DB_UploadDoc.cntAlert.visibility = View.VISIBLE
                    }
                } else if (view == DB_UploadDoc.txtAlertok) {
                    DB_UploadDoc.cntAlert.visibility = View.GONE
                }
            }
        } catch (NE: NullPointerException) {
            NE.printStackTrace()
        } catch (IE: IndexOutOfBoundsException) {
            IE.printStackTrace()
        } catch (AE: ActivityNotFoundException) {
            AE.printStackTrace()
        } catch (E: IllegalArgumentException) {
            E.printStackTrace()
        } catch (RE: RuntimeException) {
            RE.printStackTrace()
        } catch (E: Exception) {
            E.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.e("TAG", "CHECKURIPATH ; " + requestCode)
        Log.e("TAG", "CHECKURIPATH ; " + resultCode)
        Log.e("TAG", "CHECKURIPATH ; " + photoFile)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            ConvertFileOrImageToString(Uri.fromFile(photoFile))
        }
    }

    class Base64Converter() : AsyncTask<Void, Void, String>() {
        @Throws(IOException::class)
        fun getBytes(inputStream: InputStream): ByteArray? {
            val byteBuffer = ByteArrayOutputStream()
            val bufferSize = 1024
            val buffer = ByteArray(bufferSize)
            var len = 0
            while (inputStream.read(buffer).also { len = it } != -1) {
                byteBuffer.write(buffer, 0, len)
            }
            return byteBuffer.toByteArray()
        }

        fun getFolderSizeLabel(file: File): Double {
            val size =
                getFolderSize(file).toDouble() / 1000.0 // Get size and convert bytes into KB.
//        return if (size >= 1024) {
//            (size / 1024).toString() + " MB"
//        } else {
//            "$size KB"
//        }
            return (size / 1024)
        }

        fun getFolderSize(file: File): Long {
            var size: Long = 0
//        if (file.isDirectory) {
//            for (child in file.listFiles()) {
//                size += getFolderSize(child)
//            }
//        } else {
            size = file.length()
//        }
            return size
        }

        fun isfilesizelow(fileUri: Uri): Boolean {
            val selectedFilePath: String = fileUri.getPath().toString()
            val file = File(selectedFilePath)
            selFileSize = getFolderSizeLabel(file)
            return if (selFileSize < 5.0) true else false
        }

        override fun onPreExecute() {
            super.onPreExecute()
            DB_UploadDoc.cntLoader.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg params: Void?): String? {
            if (isfilesizelow(selUri)) {
                Log.e("TAG", "CHECKURIPATH  sUri ; ")

                val baos = ByteArrayOutputStream()
                val `in` = uploadDocActyivity.contentResolver.openInputStream(selUri)
                val bytes: ByteArray = getBytes(`in`!!)!!
                PDFString = Base64.encodeToString(bytes, Base64.DEFAULT)
                Log.e("TAG", "ImageString : " + PDFString)
            } else {
                Log.e("TAG", "CHECKURIPATH selFileSize ; ")
                val bitmapImage = BitmapFactory.decodeFile(selUri.path)
                val nh = (bitmapImage.height * (512.0 / bitmapImage.width)).toInt()
                val scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true)
//                your_imageview.setImageBitmap(scaled)

                val baos = ByteArrayOutputStream()

                scaled.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val imageBytes = baos.toByteArray()
                PDFString = Base64.encodeToString(imageBytes, Base64.DEFAULT)


                Log.e("TAG", "ImageStringLarge : " + PDFString)
                //Invalid File document File size large.
                selFileSize
            }

            return ""
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            DB_UploadDoc.cntLoader.visibility = View.GONE
        }
    }
}
