package com.rjsquare.kkmt.Activity.Register

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable.ApiCallingInstance
import com.rjsquare.kkmt.Activity.HomeActivity
import com.rjsquare.kkmt.Activity.Login.Login
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.AppConstant.Constants
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.Events.NetworkServices
import com.rjsquare.kkmt.RetrofitInstance.LogInCall.UploadDoc_Model
import com.rjsquare.kkmt.databinding.ActivityUploadSelfieBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream

class Upload_Selfie : AppCompatActivity(), View.OnClickListener {

    var PDFString: String = ""
    val PICK_IMAGE = 1
    var photoFileName = "photo.jpg"
    var photoFile: File? = null

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    companion object {
        lateinit var DB_UploadSelfie: ActivityUploadSelfieBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_UploadSelfie = DataBindingUtil.setContentView(this, R.layout.activity_upload__selfie)
        try {
            ApplicationClass.StatusTextWhite(this, true)
            if (ApplicationClass.IsRegisterFlow) {
                DB_UploadSelfie.txtSkip.visibility = View.VISIBLE
            } else {
                DB_UploadSelfie.txtSkip.visibility = View.GONE
            }

            DB_UploadSelfie.cntUploadSelfie.setOnClickListener(this)
            DB_UploadSelfie.txtSaveandexplore.setOnClickListener(this)
            DB_UploadSelfie.txtPdf.setOnClickListener(this)
            DB_UploadSelfie.txtSkip.setOnClickListener(this)
            DB_UploadSelfie.txtCamera.setOnClickListener(this)
            DB_UploadSelfie.txtGallery.setOnClickListener(this)
            DB_UploadSelfie.cntUploadDocType.setOnClickListener(this)
            DB_UploadSelfie.txtCancel.setOnClickListener(this)
            DB_UploadSelfie.txtAlertok.setOnClickListener(this)
            DB_UploadSelfie.txtUnauthOk.setOnClickListener(this)

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === 1 && resultCode === RESULT_OK) {
            DB_UploadSelfie.cntLoader.visibility = View.VISIBLE
            ConvertFileOrImageToString(Uri.fromFile(photoFile))
        }
    }

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

    fun ConvertFileOrImageToString(sUri: Uri) {
        //Convert File to Base64String
        try {
            val baos = ByteArrayOutputStream()
            val `in` = contentResolver.openInputStream(sUri)
            val bytes: ByteArray = getBytes(`in`!!)!!

            PDFString = Base64.encodeToString(bytes, Base64.DEFAULT)

            Log.e("TAG", "ActivityResult: " + PDFString)
            DB_UploadSelfie.cntLoader.visibility = View.GONE
        } catch (e: java.lang.Exception) {
            // TODO: handle exception
            e.printStackTrace()
            Log.d("error", "onActivityResult: $e")
            DB_UploadSelfie.cntLoader.visibility = View.GONE
        }
    }

    private fun UploadSelfie(fileString: String) {
        try {

            //Here the json data is add to a hash map with key data
            val params: MutableMap<String, String> =
                HashMap()
            params[Constants.paramKey_UserId] =
                ApplicationClass.userInfoModel.data!!.userid.toString()
            params[Constants.paramKey_Selfie] = fileString

            val service =
                ApiCallingInstance.retrofitInstance.create<NetworkServices.UploadSelfieService>(
                    NetworkServices.UploadSelfieService::class.java
                )
            val call =
                service.UploadSelfieDoc(
                    params,
                    ApplicationClass.userInfoModel.data!!.access_token.toString()
                )

            call.enqueue(object : Callback<UploadDoc_Model> {
                override fun onFailure(call: Call<UploadDoc_Model>, t: Throwable) {
                    Log.e("GetResponsesasXASX", "Hell: ")
                        DB_UploadSelfie.cntLoader.visibility = View.GONE
                }

                override fun onResponse(
                    call: Call<UploadDoc_Model>,
                    response: Response<UploadDoc_Model>
                ) {
                    DB_UploadSelfie.cntLoader.visibility = View.GONE
                    if (response.body()!!.status.equals(Constants.ResponseSucess)) {
                        var HomeIntent = Intent(this@Upload_Selfie, HomeActivity::class.java)
                        startActivity(HomeIntent)
                        overridePendingTransition(R.anim.activity_in,R.anim.activity_out)
                        if (ApplicationClass.IsRegisterFlow) {
                            Login.LoginActivity.finish()
                            Register_User.Register_UserActivity.finish()
                        }
                        upload_doc.uploadDocActyivity.finish()
                        finish()
                        overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
                    } else if (response.body()!!.status.equals(Constants.ResponseUnauthorized)) {
                        DB_UploadSelfie.cntUnAuthorized.visibility = View.VISIBLE
                    } else {
                        DB_UploadSelfie.txtAlertmsg.text = response.body()!!.message
                        DB_UploadSelfie.cntAlert.visibility = View.VISIBLE
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
            if (System.currentTimeMillis()< ApplicationClass.lastClick) return else {
                ApplicationClass.lastClick = System.currentTimeMillis() + ApplicationClass.clickInterval
            if (view == DB_UploadSelfie.cntUploadSelfie) {
                val permissions =
                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                Permissions.check(this, permissions, null, null, object : PermissionHandler() {
                    override fun onGranted() {
                        // do your task.
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
            } else if (view == DB_UploadSelfie.txtSaveandexplore) {
                if (!PDFString.equals("", true)) {
                    DB_UploadSelfie.cntLoader.visibility = View.VISIBLE
                    UploadSelfie(PDFString)
                } else {
                    DB_UploadSelfie.txtAlertmsg.text = "Invalid Selfie."
                    DB_UploadSelfie.cntAlert.visibility = View.VISIBLE
                }
            } else if (view == DB_UploadSelfie.txtSkip) {
                var HomeIntent = Intent(this, HomeActivity::class.java)
                startActivity(HomeIntent)
                Login.LoginActivity.finish()
                Register_User.Register_UserActivity.finish()
                upload_doc.uploadDocActyivity.finish()
                finish()
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
            } else if (view == DB_UploadSelfie.txtAlertok) {
                DB_UploadSelfie.cntAlert.visibility = View.GONE
            } else if (view == DB_UploadSelfie.txtPdf) {

            } else if (view == DB_UploadSelfie.txtCamera) {

            } else if (view == DB_UploadSelfie.txtCancel) {
                DB_UploadSelfie.cntUploadDocType.visibility = View.GONE
            } else if (view == DB_UploadSelfie.txtGallery) {

            } else if (view == DB_UploadSelfie.txtUnauthOk) {
                DB_UploadSelfie.cntUnAuthorized.visibility = View.GONE
                ApplicationClass.UserLogout(this)
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
}