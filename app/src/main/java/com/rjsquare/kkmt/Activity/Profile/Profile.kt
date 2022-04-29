package com.rjsquare.kkmt.Activity.Profile

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
import android.widget.CompoundButton
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.DatePicker
import com.applandeo.materialcalendarview.builders.DatePickerBuilder
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener
import com.google.gson.Gson
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable.ApiCallingInstance
import com.rjsquare.kkmt.Activity.Dialog.Alert
import com.rjsquare.kkmt.Activity.Dialog.Loader
import com.rjsquare.kkmt.Activity.Dialog.Network
import com.rjsquare.kkmt.Activity.Register.upload_doc
import com.rjsquare.kkmt.Activity.Dialog.UnAuthorized
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.AppConstant.Constants
import com.rjsquare.kkmt.AppConstant.GlobalUsage
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.Events.NetworkServices
import com.rjsquare.kkmt.RetrofitInstance.OTPCall.DisplayNameModel
import com.rjsquare.kkmt.RetrofitInstance.RegisterUserCall.UserInfoData_Model
import com.rjsquare.kkmt.databinding.ActivityProfileBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_gender_list.view.*
import retrofit2.Call
import retrofit2.Callback
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*


class Profile : AppCompatActivity(), View.OnClickListener, OnSelectDateListener {

    var photoFileName = "photo.jpg"
    var photoFile: File? = null
    var resultLauncher: ActivityResultLauncher<Intent>? = null

    var DatePickerVISIBLE = false
    lateinit var datePicker: DatePicker


    private lateinit var mGenderListView: View

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    companion object {
        lateinit var thisProfileActivity: Activity
        lateinit var selUri: Uri
        var imageString: String = ""
        lateinit var DB_Profile: ActivityProfileBinding
        var selFileSize = 0.0
        var selDate = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_Profile = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        try {
            thisProfileActivity = this
            GlobalUsage.StatusTextWhite(this, true)

            SetProfileData()
            initListners()

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

    private fun initListners() {

        DB_Profile.imgBack.setOnClickListener(this)
        DB_Profile.cntUploadDocument.setOnClickListener(this)
        DB_Profile.imgUserProfile.setOnClickListener(this)
        DB_Profile.cntSave.setOnClickListener(this)
        DB_Profile.txtCamera.setOnClickListener(this)
        DB_Profile.txtGallery.setOnClickListener(this)
        DB_Profile.txtCancel.setOnClickListener(this)
        DB_Profile.txtGender.setOnClickListener(this)
        DB_Profile.txtDob.setOnClickListener(this)
        DB_Profile.imgEditProfile.setOnClickListener(this)
        mGenderListView.txt_male.setOnClickListener(this)
        mGenderListView.txt_female.setOnClickListener(this)
        mGenderListView.txt_other.setOnClickListener(this)

        DB_Profile.imgUserProfile.isClickable = false
        DB_Profile.txtGender.isEnabled = false
        DB_Profile.txtDob.isEnabled = false
        DB_Profile.cntSave.visibility = View.GONE

        DB_Profile.cntEmpProfile.visibility = View.VISIBLE

        DB_Profile.swtNameVisible.setOnCheckedChangeListener(object :
            CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, isEnable: Boolean) {
                DisplayName(isEnable)
            }
        })

        // Initialize result launcher
        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback<ActivityResult> { result ->
                // Initialize result data
                val data: Intent = result.data!!
                // check condition
                if (data != null) {
                    Loader.showLoader(this)
                    // When data is not equal to empty
                    // Get PDf uri
                    val sUri: Uri? = data.data

                    // Get PDF path
                    val sPath: String = sUri!!.path!!

                    ConvertFileOrImageToString(sUri)
                }
            })

    }

    private fun SetupDatePicker() {

        val calendar = Calendar.getInstance()
        if (!GlobalUsage.userInfoModel.data!!.dob!!.trim().equals("")) {
            calendar.set(Calendar.DAY_OF_MONTH, 10)
            calendar.set(Calendar.MONTH, Calendar.NOVEMBER)
            calendar.set(Calendar.YEAR, 2022)
        }


        val builder = DatePickerBuilder(this, this)
            .pickerType(CalendarView.ONE_DAY_PICKER)
            .setHeaderColor(R.color.appcolor) // Color of the dialog header
            .setHeaderLabelColor(R.color.white) // Color of the header label
            .setSelectionColor(R.color.appcolor) // Color of the selection circle
//                .setSelectionLabelColor(R.color.white) // Color of the label in the circle
//                .setTodayColor(R.color.appcolor) // Color of the present day background
            .setTodayLabelColor(R.color.appcolor) // Color of the today number
//                .setDialogButtonsColor(R.color.blue_dark); // Color of "Cancel" and "OK" buttons
            .setDate(calendar)

        datePicker = builder.build()

        datePicker.show()
    }

    private fun SetProfileData() {
        mGenderListView = findViewById<View>(R.id.genderlist)

        DB_Profile.txtProfileName.hint = GlobalUsage.userInfoModel.data!!.username
        DB_Profile.edtFirstName.hint = GlobalUsage.userInfoModel.data!!.firstname
        DB_Profile.edtLastName.hint = GlobalUsage.userInfoModel.data!!.lastname
        DB_Profile.edtPhoneNumber.hint = GlobalUsage.userInfoModel.data!!.contactno
        DB_Profile.edtEmail.hint = GlobalUsage.userInfoModel.data!!.email
        DB_Profile.swtNameVisible.isChecked =
            GlobalUsage.userInfoModel.data!!.display_name_show!!.equals(
                "Yes",
                true
            )
        if (GlobalUsage.userInfoModel.data!!.dob!!.trim().equals("")) {
            DB_Profile.txtDob.hint = getString(R.string.birthdate)
        } else {
            DB_Profile.txtDob.hint = GlobalUsage.userInfoModel.data!!.dob
        }
        if (GlobalUsage.userInfoModel.data!!.gender!!.trim().equals("")) {
            DB_Profile.txtGender.hint = getString(R.string.gender)
        } else {
            DB_Profile.txtGender.hint =
                GlobalUsage.Gender(GlobalUsage.userInfoModel.data!!.gender!!)
        }
        if (GlobalUsage.userInfoModel.data!!.display_name!!.trim().equals("")) {
            DB_Profile.edtDisplayName.hint = getString(R.string.displayname)
        } else {
            DB_Profile.edtDisplayName.hint = GlobalUsage.userInfoModel.data!!.display_name!!
        }

        DB_Profile.txtLevel.text =
            "Level : ${GlobalUsage.userInfoModel.data!!.credit_details!!.level}"
        DB_Profile.txtCredits.text =
            GlobalUsage.formatNumber(GlobalUsage.userInfoModel.data!!.credit_details!!.credit!!.toInt())


        DB_Profile.cntDob.background = ContextCompat.getDrawable(this, R.drawable.profile_field)
        DB_Profile.cntDisplayName.background =
            ContextCompat.getDrawable(this, R.drawable.profile_field)
        DB_Profile.cntEmail.background = ContextCompat.getDrawable(this, R.drawable.profile_field)
        DB_Profile.cntGender.background = ContextCompat.getDrawable(this, R.drawable.profile_field)

        var UserImage = ""
        if (GlobalUsage.userInfoModel.data!!.userimage != null && !GlobalUsage.userInfoModel.data!!.userimage.equals(
                ""
            )
        ) {
            UserImage = GlobalUsage.userInfoModel.data!!.userimage!!
            Picasso.with(this).load(UserImage)
                .placeholder(R.drawable.expe_logo).into(DB_Profile.imgUserProfile)
        }

        var verifiedImage = ContextCompat.getDrawable(
            this,
            R.drawable.ic_nonverified
        )
        if (GlobalUsage.isApprove) {
            verifiedImage = ContextCompat.getDrawable(
                this,
                R.drawable.ic_verified
            )
        } else {
            verifiedImage = ContextCompat.getDrawable(
                this,
                R.drawable.ic_nonverified
            )
        }
        DB_Profile.imgUserverified.setImageDrawable(verifiedImage)

        if (!GlobalUsage.isApprove) {
            DB_Profile.cntUploadDocument.visibility = View.VISIBLE
        } else {
            DB_Profile.cntUploadDocument.visibility = View.GONE
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
        Log.e("KKMT", "Create directory")

        // Return the file target for the photo based on filename
        return File(mediaStorageDir.path + File.separator + fileName)
    }

    fun ConvertFileOrImageToString(sUri: Uri) {
        //Convert File to Base64String
        try {
            selUri = sUri
            Base64Converter().execute()
        } catch (e: java.lang.Exception) {
            // TODO: handle exception
            e.printStackTrace()
            Loader.hideLoader()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            print("Image1 : " + photoFile)
            ConvertFileOrImageToString(Uri.fromFile(photoFile))
        }
    }

    private fun selectImageCam() {
        // Initialize intent
        photoFile = getPhotoFileUri(photoFileName)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            // Continue only if the File was successfully created

            print("Image12 : " + photoFile)
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

    private fun selectImage() {
        // Initialize intent
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        // set type
        intent.type = "image/*"
        // Launch intent
        resultLauncher!!.launch(intent)
    }

    override fun onClick(view: View?) {
        try {
            if (System.currentTimeMillis() < GlobalUsage.lastClick) return else {
                GlobalUsage.lastClick =
                    System.currentTimeMillis() + GlobalUsage.clickInterval
                if (view == DB_Profile.imgBack) {
                    onBackPressed()
                } else if (view == DB_Profile.cntUploadDocument) {
                    GlobalUsage.NextScreen(this, Intent(this, upload_doc::class.java))
                } else if (view == DB_Profile.imgUserProfile) {
                    DB_Profile.cntUserProfile.visibility = View.VISIBLE
                } else if (view == DB_Profile.imgEditProfile) {
                    EditProfileSetup()
                } else if (view == mGenderListView.txt_male) {
                    DB_Profile.txtGender.text = "Male"
                    mGenderListView.visibility = View.GONE
                } else if (view == mGenderListView.txt_female) {
                    DB_Profile.txtGender.text = "Female"
                    mGenderListView.visibility = View.GONE
                } else if (view == mGenderListView.txt_other) {
                    DB_Profile.txtGender.text = "Other"
                    mGenderListView.visibility = View.GONE
                } else if (view == DB_Profile.txtGender) {
                    Log.e("TAG", "Gender")
                    if (mGenderListView.visibility == View.GONE)
                        mGenderListView.visibility = View.VISIBLE
                } else if (view == DB_Profile.txtDob) {
                    Log.e("TAG", "DOB")
//                    if (!DatePickerVISIBLE) {
//                    datePicker.show()
//                        DatePickerVISIBLE = true
//                    }
                    SetupDatePicker()
                } else if (view == DB_Profile.txtCamera) {
                    val permissions =
                        arrayOf(
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    Permissions.check(this, permissions, null, null, object : PermissionHandler() {
                        override fun onGranted() {
                            // do your task.
                            DB_Profile.cntUserProfile.visibility = View.GONE
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
                } else if (view == DB_Profile.txtGallery) {
                    val permissions =
                        arrayOf(
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    Permissions.check(this, permissions, null, null, object : PermissionHandler() {
                        override fun onGranted() {
                            // do your task.
                            DB_Profile.cntUserProfile.visibility = View.GONE
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
                } else if (view == DB_Profile.cntSave) {
                    if (!GlobalUsage.IsNetworkAvailable(this)) {
                        Network.showDialog(this)
                        return
                    }
                    UpdateProfile()
                } else if (view == DB_Profile.txtCancel) {
                    DB_Profile.cntUserProfile.visibility = View.GONE
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

    private fun EditProfileSetup() {
        DB_Profile.imgEditProfile.visibility = View.GONE
        DB_Profile.edtDisplayName.isEnabled = true
        DB_Profile.edtEmail.isEnabled = true
//        DB_Profile.txtDob.isEnabled = true
//        DB_Profile.txtGender.isEnabled = true

        DB_Profile.imgUserProfile.isClickable = true
        DB_Profile.txtDob.isEnabled = true
        DB_Profile.txtGender.isEnabled = true
        DB_Profile.cntSave.visibility = View.VISIBLE
        DB_Profile.edtDisplayName.setText(DB_Profile.edtDisplayName.hint)
        DB_Profile.edtEmail.setText(DB_Profile.edtEmail.hint)
        if (GlobalUsage.userInfoModel.data!!.dob!!.trim().equals("")) {
            DB_Profile.txtDob.hint = getString(R.string.birthdate)
        } else {
            DB_Profile.txtDob.text = DB_Profile.txtDob.hint
        }
        if (GlobalUsage.userInfoModel.data!!.gender!!.trim().equals("")) {
            DB_Profile.txtGender.hint = getString(R.string.gender)
        } else {
            DB_Profile.txtGender.text = DB_Profile.txtGender.hint
        }
        if (GlobalUsage.userInfoModel.data!!.display_name!!.trim().equals("")) {
            DB_Profile.edtDisplayName.hint = getString(R.string.displayname)
//            DB_Profile.edtDisplayName.setText("")
        } else {
            DB_Profile.edtDisplayName.setText(DB_Profile.edtDisplayName.hint)
        }

        DB_Profile.cntDob.background = ContextCompat.getDrawable(this, R.drawable.prof_edit)
        DB_Profile.cntDisplayName.background = ContextCompat.getDrawable(this, R.drawable.prof_edit)
        DB_Profile.cntEmail.background = ContextCompat.getDrawable(this, R.drawable.prof_edit)
        DB_Profile.cntGender.background = ContextCompat.getDrawable(this, R.drawable.prof_edit)

//        DB_Profile.edtDisplayName.isFocusable = true
        Log.e("TAG", "Editable")
    }

    private fun UpdateProfile() {
        try {
            //Here the json data is add to a hash map with key data
            Loader.showLoader(this)
            val params: MutableMap<String, String> =
                HashMap()

            params[Constants.paramKey_UserId] = GlobalUsage.userInfoModel.data!!.userid!!

            if (!DB_Profile.edtDisplayName.text.toString().trim().equals("")) {
                params[Constants.paramKey_DisplayName] = DB_Profile.edtDisplayName.text.toString()
            } else {
                params[Constants.paramKey_DisplayName] =
                    GlobalUsage.userInfoModel.data!!.display_name!!
            }

            if (!DB_Profile.edtEmail.text.toString().trim().equals("")) {
                if (GlobalUsage.isValidEmail(DB_Profile.edtEmail.text.toString())) {
                    params[Constants.paramKey_EmailAddress] = DB_Profile.edtEmail.text.toString()
                } else {
                    Alert.showDialog(this,getString(R.string.invalidemail))
//                    DB_Profile.txtAlertmsg.setText(getString(R.string.invalidemail))
//                    DB_Profile.cntAlert.visibility = View.VISIBLE
                    return
                }
            } else {
                params[Constants.paramKey_EmailAddress] =
                    GlobalUsage.userInfoModel.data!!.email!!
            }

            if (!DB_Profile.txtDob.text.toString().trim().equals("")) {
                params[Constants.paramKey_DOB] = DB_Profile.txtDob.text.toString()
            } else {
                params[Constants.paramKey_DOB] = GlobalUsage.userInfoModel.data!!.dob!!
            }

            if (!DB_Profile.txtGender.text.toString().trim().equals("")) {
                params[Constants.paramKey_Gender] = DB_Profile.txtGender.text.toString()
            } else {
                params[Constants.paramKey_Gender] = GlobalUsage.userInfoModel.data!!.gender!!
            }

            params[Constants.paramKey_PhoneNo] = GlobalUsage.userInfoModel.data!!.contactno!!
            params[Constants.paramKey_UserImage] = imageString

            val service =
                ApiCallingInstance.retrofitInstance.create<NetworkServices.EditProfileService>(
                    NetworkServices.EditProfileService::class.java
                )
            val call = service.EditProfileData(
                params,
                GlobalUsage.userInfoModel.data?.access_token.toString()
            )
            call.enqueue(object : Callback<UserInfoData_Model> {
                override fun onFailure(call: Call<UserInfoData_Model>, t: Throwable) {
                    Loader.hideLoader()
                }

                override fun onResponse(
                    call: Call<UserInfoData_Model>,
                    response: retrofit2.Response<UserInfoData_Model>
                ) {
                    Loader.hideLoader()
                    if (response.body()!!.status.equals(
                            Constants.ResponseSucess, true
                        )
                    ) {
                        ApplicationClass.updateUserInfo()
                        UpdateDone()
                    } else if (response.body()!!.status.equals(
                            Constants.ResponseUnauthorized, true
                        )
                    ) {
                        UnAuthorized.showDialog(this@Profile)
                    } else {
                        Log.e("TAG", "Messageerror : " + response.body()!!.message)
                        Alert.showDialog(this@Profile,response.body()!!.message!!)
//                        DB_Profile.txtOtpAlertmsg.text = response.body()!!.message
//                        DB_Profile.cntAlert.visibility = View.VISIBLE
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

    private fun UpdateDone() {

        DB_Profile.cntDob.background = ContextCompat.getDrawable(this, R.drawable.profile_field)
        DB_Profile.cntDisplayName.background =
            ContextCompat.getDrawable(this, R.drawable.profile_field)
        DB_Profile.cntEmail.background = ContextCompat.getDrawable(this, R.drawable.profile_field)
        DB_Profile.cntGender.background = ContextCompat.getDrawable(this, R.drawable.profile_field)

        DB_Profile.imgUserProfile.isClickable = false
        DB_Profile.txtDob.isEnabled = false
        DB_Profile.txtGender.isEnabled = false
        DB_Profile.imgEditProfile.visibility = View.VISIBLE
        DB_Profile.edtDisplayName.isEnabled = false
        DB_Profile.edtEmail.isEnabled = false

//        if (GlobalUsage.userInfoModel.data!!.dob!!.trim().equals("")) {
//            DB_Profile.txtDob.setHint(getString(R.string.birthdate))
//        } else {
//        }
//        if (GlobalUsage.userInfoModel.data!!.gender!!.trim().equals("")) {
//            DB_Profile.txtGender.setHint(getString(R.string.gender))
//        } else {
//        }
//        if (GlobalUsage.userInfoModel.data!!.display_name!!.trim().equals("")) {
//            DB_Profile.edtDisplayName.setHint(getString(R.string.displayname))
////            DB_Profile.edtDisplayName.setText("")
//        } else {
//        }
        DB_Profile.txtDob.hint = DB_Profile.txtDob.text
        DB_Profile.txtGender.hint = DB_Profile.txtGender.text
        DB_Profile.edtDisplayName.hint = DB_Profile.edtDisplayName.text.toString()
    }

    private fun DisplayName(isEnable: Boolean) {
        try {
            //Here the json data is add to a hash map with key data
            val params: MutableMap<String, String> =
                HashMap()

            params[Constants.paramKey_UserId] = GlobalUsage.userInfoModel.data!!.userid!!

            params[Constants.paramKey_IsnameShow] = if (isEnable) "Yes" else "No"

            val service =
                ApiCallingInstance.retrofitInstance.create<NetworkServices.DisplayNameService>(
                    NetworkServices.DisplayNameService::class.java
                )
            val call = service.DisplayNameData(
                params,
                GlobalUsage.userInfoModel.data?.access_token.toString()
            )
            call.enqueue(object : Callback<DisplayNameModel> {
                override fun onFailure(call: Call<DisplayNameModel>, t: Throwable) {
                    Loader.hideLoader()
                }

                override fun onResponse(
                    call: Call<DisplayNameModel>,
                    response: retrofit2.Response<DisplayNameModel>
                ) {
                    Loader.hideLoader()
                    if (response.body()!!.status.equals(
                            Constants.ResponseSucess, true
                        )
                    ) {

                    } else if (response.body()!!.status.equals(
                            Constants.ResponseUnauthorized, true
                        )
                    ) {
                        UnAuthorized.showDialog(this@Profile)
                    } else {
                        Alert.showDialog(this@Profile,response.body()!!.message!!)
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

    class Base64Converter : AsyncTask<Void, Void, String>() {
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

        override fun onPreExecute() {
            super.onPreExecute()
            Loader.showLoader(thisProfileActivity)
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

        fun isfilesizelow(fileUri: Uri): Boolean {
            val selectedFilePath: String = fileUri.path.toString()
            val file = File(selectedFilePath)
            selFileSize = getFolderSizeLabel(file)
            return selFileSize < 5.0
        }

        override fun doInBackground(vararg params: Void?): String? {

            if (isfilesizelow(selUri)) {
                val `in` = thisProfileActivity.contentResolver.openInputStream(selUri)
                val bytes: ByteArray = getBytes(`in`!!)!!
                imageString = Base64.encodeToString(bytes, Base64.DEFAULT)
            } else {
                val bitmapImage = BitmapFactory.decodeFile(selUri.path)
                val nh = (bitmapImage.height * (512.0 / bitmapImage.width)).toInt()
                val scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true)
                val baos = ByteArrayOutputStream()

                scaled.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val imageBytes = baos.toByteArray()
                imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT)
            }
            return ""
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Loader.hideLoader()
        }
    }

    override fun onSelect(calendar: MutableList<Calendar>?) {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val sendSDF = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDateandTime = sdf.format(calendar!![0].time)
        DatePickerVISIBLE = false

        selDate = sendSDF.format(currentDateandTime)
        DB_Profile.txtDob.text = selDate
        Log.e("TAG", "Dateselection")
    }
}