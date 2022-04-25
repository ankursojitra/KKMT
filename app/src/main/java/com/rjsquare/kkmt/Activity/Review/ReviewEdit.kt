package com.rjsquare.kkmt.Activity.Review

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionLayout.TransitionListener
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
//import cafe.adriel.androidaudiorecorder.AndroidAudioRecorder
import com.google.gson.Gson
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable.ApiCallingInstance

import com.rjsquare.kkmt.AppConstant.Constants
import com.rjsquare.kkmt.AppConstant.GlobalUsage
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.Events.NetworkServices
import com.rjsquare.kkmt.RetrofitInstance.OTPCall.ReviewSubmitModel
import com.rjsquare.kkmt.databinding.ActivityReviewScreenBinding
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.util.*


class ReviewEdit : AppCompatActivity(), View.OnClickListener {

    var receiptImageString: String = ""
    val PICK_IMAGE = 1
    var photoFileName = "photo.jpg"
    var photoFile: File? = null
    var DaudioFilePath = ""
    var audioFilePath = ""
    var isPlaying = false
    var isRecoding = false
    var looped = false
    private var recorder: MediaRecorder? = null
//    lateinit var audioFile: AndroidAudioRecorder
    var player: MediaPlayer? = null

    lateinit var TimerHandler: Handler
    var TimerRunnable: Runnable? = null
    private var seconds = 0
    private var running = false
    private var wasRunning = false
    var receiptno = ""
    var receiptamount = ""
    var star = ""
    var WrittenNote = ""
    var voiceNoteString = ""

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    companion object {
        lateinit var DB_ReviewEdit: ActivityReviewScreenBinding
        lateinit var thisReviewEdit: Activity
        var isVoiceNote = false
        var isWritenNote = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_ReviewEdit = DataBindingUtil.setContentView(this, R.layout.activity_review_screen)
//        try {
        GlobalUsage.StatusTextWhite(this, true)
        thisReviewEdit = this

        DB_ReviewEdit.txtSubmit.setOnClickListener(this)
        DB_ReviewEdit.imgBack.setOnClickListener(this)
        DB_ReviewEdit.cnt1star.setOnClickListener(this)
        DB_ReviewEdit.cntBad.setOnClickListener(this)
        DB_ReviewEdit.cntGood.setOnClickListener(this)
        DB_ReviewEdit.cnt5star.setOnClickListener(this)
        DB_ReviewEdit.cntReceiptUpload.setOnClickListener(this)
        DB_ReviewEdit.cntVoice.setOnClickListener(this)
        DB_ReviewEdit.txtConfirm.setOnClickListener(this)
        DB_ReviewEdit.txtCancel.setOnClickListener(this)
        DB_ReviewEdit.imgDelete.setOnClickListener(this)
        DB_ReviewEdit.imgMic.setOnClickListener(this)
        DB_ReviewEdit.imgPlaypause.setOnClickListener(this)
        DB_ReviewEdit.txtUploadReceipt.setOnClickListener(this)
        DB_ReviewEdit.txtAlertok.setOnClickListener(this)
        TimerHandler = Handler()
        if (savedInstanceState != null) {

            // Get the previous state of the stopwatch
            // if the activity has been
            // destroyed and recreated.
            seconds = savedInstanceState
                .getInt("seconds")
            running = savedInstanceState
                .getBoolean("running")
            wasRunning = savedInstanceState
                .getBoolean("wasRunning")
        }

        SetUpFields()
        SetUpReviewData()

        TimerRunnable = Runnable {
//            fun run() {
            val hours: Int = seconds / 3600
            val minutes: Int = seconds % 3600 / 60
            val secs: Int = seconds % 60

            // Format the seconds into hours, minutes,
            // and seconds.
//            val time: String = java.lang.String
//                .format(
//                    Locale.getDefault(),
//                    "%d:%02d:%02d", hours,
//                    minutes, secs
//                )
            val time: String = java.lang.String
                .format(
                    Locale.getDefault(),
                    "%02d:%02d", minutes, secs
                )

            // Set the text view text.
            DB_ReviewEdit.txtTimer.text = time

            // If running is true, increment the
            // seconds variable.
            if (running) {
                seconds++
            }
            if (running) {
                TimerHandler.postDelayed(TimerRunnable!!, 1000)
            } else {
                TimerHandler.removeCallbacks(TimerRunnable!!)
            }


            // Post the code again
            // with a delay of 1 second.

        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            mTxtAmt.text=(Html.fromHtml("Enter Amount  <font color='#8A4EF2'>&amp; Receipt Number</font>", Html.FROM_HTML_MODE_COMPACT));
//        } else {
//            mTxtAmt .text=(Html.fromHtml("Enter Amount  <font color='#8A4EF2'>&amp; Receipt Number</font>"));
//        }
//        } catch (NE: NullPointerException) {
//            NE.printStackTrace()
//        } catch (IE: IndexOutOfBoundsException) {
//            IE.printStackTrace()
//        } catch (AE: ActivityNotFoundException) {
//            AE.printStackTrace()
//        } catch (E: IllegalArgumentException) {
//            E.printStackTrace()
//        } catch (RE: RuntimeException) {
//            RE.printStackTrace()
//        } catch (E: Exception) {
//            E.printStackTrace()
//        }

    }

    private fun SetUpFields() {
        DB_ReviewEdit.edtReceiptNumber.addTextChangedListener(@RequiresApi(Build.VERSION_CODES.M)
        object : View.OnContextClickListener, TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Set text change
                if (!text.toString().trim().equals("")) {
                    DB_ReviewEdit.imgReceiptNumber.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@ReviewEdit,
                            R.drawable.ic_receipt_number
                        )
                    )
                    DB_ReviewEdit.imgReceiptNumberVerified.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@ReviewEdit,
                            R.drawable.ic_verified
                        )
                    )
                } else {
                    DB_ReviewEdit.imgReceiptNumber.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@ReviewEdit,
                            R.drawable.ic_reciept_none
                        )
                    )
                    DB_ReviewEdit.imgReceiptNumberVerified.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@ReviewEdit,
                            R.drawable.ic_nonverified
                        )
                    )
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

            override fun onContextClick(p0: View?): Boolean {
                return true
            }
        })

        DB_ReviewEdit.edtReceiptAmount.addTextChangedListener(@RequiresApi(Build.VERSION_CODES.M)
        object : View.OnContextClickListener, TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Set text change
                if (!text.toString().trim().equals("")) {
                    DB_ReviewEdit.imgReceiptAmount.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@ReviewEdit,
                            R.drawable.ic_amount
                        )
                    )
                    DB_ReviewEdit.imgReceiptAmountVerified.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@ReviewEdit,
                            R.drawable.ic_verified
                        )
                    )
                } else {
                    DB_ReviewEdit.imgReceiptAmount.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@ReviewEdit,
                            R.drawable.ic_amount_none
                        )
                    )
                    DB_ReviewEdit.imgReceiptAmountVerified.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@ReviewEdit,
                            R.drawable.ic_nonverified
                        )
                    )
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

            override fun onContextClick(p0: View?): Boolean {
                return true
            }

        })

        DB_ReviewEdit.edtReceiptAmount.onFocusChangeListener = @RequiresApi(Build.VERSION_CODES.M)
        object : View.OnFocusChangeListener {
            override fun onFocusChange(p0: View?, focused: Boolean) {
                if (!focused) {
                    if (!DB_ReviewEdit.edtReceiptAmount.text.toString().equals("")) {
                        DB_ReviewEdit.edtReceiptAmount.setText(
                            GlobalUsage.formatNumber(
                                DB_ReviewEdit.edtReceiptAmount.text.toString().toInt()
                            )
                        )
                    }
                } else {
                    if (!DB_ReviewEdit.edtReceiptAmount.text.toString().equals("")) {
                        var value =
                            DB_ReviewEdit.edtReceiptAmount.text.toString().replace(",", "")
                        DB_ReviewEdit.edtReceiptAmount.setText(value)
                    }
                }
            }
        }

        DB_ReviewEdit.edtWrittenNote.addTextChangedListener(@RequiresApi(Build.VERSION_CODES.M)
        object : View.OnContextClickListener, TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Set text change
                if (!text.toString().trim().equals("")) {
                    DB_ReviewEdit.imgWrittenNote.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@ReviewEdit,
                            R.drawable.ic_note
                        )
                    )
                    DB_ReviewEdit.imgWrittenVerified.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@ReviewEdit,
                            R.drawable.ic_verified
                        )
                    )
                } else {
                    DB_ReviewEdit.imgWrittenNote.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@ReviewEdit,
                            R.drawable.ic_notes_none
                        )
                    )
                    DB_ReviewEdit.imgWrittenVerified.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@ReviewEdit,
                            R.drawable.ic_nonverified
                        )
                    )
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

            override fun onContextClick(p0: View?): Boolean {
                return true
            }

        })

    }

    private fun UncheckedReview() {
        DB_ReviewEdit.cnt1star.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent))
        DB_ReviewEdit.cntBad.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent))
        DB_ReviewEdit.cntGood.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent))
        DB_ReviewEdit.cnt5star.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent))
    }

    fun NewReveiwSetup() {


        //Clear fields
        DB_ReviewEdit.imgReceipt.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_upload_none
            )
        )
        DB_ReviewEdit.imgReceiptAmount.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_reciept_none
            )
        )
        DB_ReviewEdit.imgReceiptNumber.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_amount_none
            )
        )
        DB_ReviewEdit.imgWrittenNote.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_notes_none
            )
        )
        DB_ReviewEdit.imgVoiceNote.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_voice_none
            )
        )
        DB_ReviewEdit.imgReceiptVerified.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_nonverified
            )
        )
        DB_ReviewEdit.imgReceiptAmountVerified.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_nonverified
            )
        )
        DB_ReviewEdit.imgReceiptNumberVerified.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_nonverified
            )
        )
        DB_ReviewEdit.imgWrittenVerified.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_nonverified
            )
        )
        DB_ReviewEdit.imgVoiceVerified.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_nonverified
            )
        )

        DB_ReviewEdit.edtReceiptAmount.text.clear()
        DB_ReviewEdit.txtUploadReceipt.text = ""
        DB_ReviewEdit.edtReceiptNumber.text.clear()
        DB_ReviewEdit.edtWrittenNote.text.clear()

        //Image selection nill
        receiptImageString = ""
    }

    private fun SetUpReviewData() {
        if (GlobalUsage.isNewReview) {
            //Setup New review data

            NewReveiwSetup()
            DB_ReviewEdit.cnt1star.performClick()

            DB_ReviewEdit.txtReviewName.text = GlobalUsage.empSlaveModel.username

            var UserImage = ""
            if (GlobalUsage.empSlaveModel.employeeimage!! != null && !GlobalUsage.empSlaveModel.employeeimage!!.equals(
                    ""
                )
            ) {
                UserImage = GlobalUsage.empSlaveModel.employeeimage!!
                Picasso.with(this).load(UserImage).into(DB_ReviewEdit.imgProfile)
            }
        } else {
            //Setup edit review data
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
//            val baos = ByteArrayOutputStream()
//            val `in` = contentResolver.openInputStream(sUri)

//            val bytes: ByteArray = getBytes(`in`!!)!!
            val bitmapImage = BitmapFactory.decodeFile(sUri.path)
            val nh = (bitmapImage.height * (512.0 / bitmapImage.width)).toInt()
            val scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true)
//                your_imageview.setImageBitmap(scaled)

            val baos = ByteArrayOutputStream()

            scaled.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val imageBytes = baos.toByteArray()
            receiptImageString = Base64.encodeToString(imageBytes, Base64.DEFAULT)

//            receiptImageString = Base64.encodeToString(bytes, Base64.DEFAULT)

            Log.e("TAG", "ActivityResult: " + receiptImageString)
            ChangeUploadText()
            DB_ReviewEdit.cntLoader.visibility = View.GONE
        } catch (e: java.lang.Exception) {
            // TODO: handle exception
            e.printStackTrace()
            Log.d("error", "onActivityResult: $e")
            DB_ReviewEdit.cntLoader.visibility = View.GONE
        }
    }

    private fun ChangeUploadText() {
        DB_ReviewEdit.imgReceipt.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_upload_receipt
            )
        )
        DB_ReviewEdit.txtUploadReceipt.text = getString(R.string.uploacreceipt)
        DB_ReviewEdit.imgReceiptVerified.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_verified
            )
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === 1 && resultCode === RESULT_OK) {
            DB_ReviewEdit.cntLoader.visibility = View.VISIBLE
            ConvertFileOrImageToString(Uri.fromFile(photoFile))
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

    // Returns the File for a photo stored on disk given the fileName
    fun getAudioFileUri(fileName: String): File {
        val root = Environment.getExternalStorageDirectory()
        val dir = File(root.absolutePath + "/KKMT/Music")
        dir.mkdirs()

        val file = File(dir, "fileName")
        val mediaStorageDir =
            File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), "KKMT")


        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d("KKMT", "failed to create directory")
        }

        // Return the file target for the photo based on filename
        return File(dir.absolutePath + File.separator + fileName)
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

    override fun onClick(view: View?) {
        try {
            if (System.currentTimeMillis() < GlobalUsage.lastClick) return else {
                GlobalUsage.lastClick =
                    System.currentTimeMillis() + GlobalUsage.clickInterval
                if (view == DB_ReviewEdit.txtSubmit) {
                    if (CheckDataForReview()) SubmitReview()
                    else DB_ReviewEdit.cntAlert.visibility = View.VISIBLE
                } else if (view == DB_ReviewEdit.imgBack) {
                    onBackPressed()
                } else if (view == DB_ReviewEdit.txtAlertok) {
                    DB_ReviewEdit.cntAlert.visibility = View.GONE
                } else if (view == DB_ReviewEdit.cnt1star) {
                    UncheckedReview()
                    star = Constants.onestar
                    DB_ReviewEdit.cnt1star.setBackgroundResource(R.drawable.review_selection)
                } else if (view == DB_ReviewEdit.cntBad) {
                    UncheckedReview()
                    star = Constants.bad
                    DB_ReviewEdit.cntBad.setBackgroundResource(R.drawable.review_selection)
                } else if (view == DB_ReviewEdit.cntGood) {
                    UncheckedReview()
                    star = Constants.good
                    DB_ReviewEdit.cntGood.setBackgroundResource(R.drawable.review_selection)
                } else if (view == DB_ReviewEdit.cnt5star) {
                    UncheckedReview()
                    star = Constants.fivestar
                    DB_ReviewEdit.cnt5star.setBackgroundResource(R.drawable.review_selection)
                } else if (view == DB_ReviewEdit.cntVoice) {
                    VoiceRecordingSetup()
                } else if (view == DB_ReviewEdit.txtConfirm) {
                    DB_ReviewEdit.cntVoiceDialoug.visibility = View.GONE
                    DB_ReviewEdit.cntLoader.visibility = View.VISIBLE
                    ConvertAudioToBase64()
                } else if (view == DB_ReviewEdit.txtCancel) {
                    DB_ReviewEdit.cntVoiceDialoug.visibility = View.GONE
                    ResetRecording()
                } else if (view == DB_ReviewEdit.imgDelete) {
                    ResetRecording()
                } else if (view == DB_ReviewEdit.imgMic) {
                    isRecoding = !isRecoding
                    AudioRecording()
                } else if (view == DB_ReviewEdit.imgPlaypause) {
                    isPlaying = !isPlaying
                    FilePlayPause()
                } else if (view == DB_ReviewEdit.txtUploadReceipt) {
                    Log.e("TAG", "txtUploadReceipt")
                    val permissions =
                        arrayOf(
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    Permissions.check(this, permissions, null, null, object : PermissionHandler() {
                        override fun onGranted() {
                            // do your task.
                            Log.e("TAG", "onGranted")
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

    private fun CheckDataForReview(): Boolean {
        receiptno = DB_ReviewEdit.edtReceiptNumber.text.toString()
        receiptamount = DB_ReviewEdit.edtReceiptAmount.text.toString()
//        receiptImageString
//            star
        WrittenNote = DB_ReviewEdit.edtWrittenNote.text.toString()
//        voiceNoteString

        if (star.equals(Constants.onestar) || star.equals(Constants.fivestar)) {
            if (WrittenNote.trim().length > 0 || voiceNoteString.trim().length > 0) {
                return true
            } else {
                DB_ReviewEdit.txtAlertmsg.text = getString(R.string.reviewnoteserror)
                return false
            }
        } else {
            return true
        }
    }

    private fun SubmitReview() {
        try {
            DB_ReviewEdit.cntLoader.visibility = View.VISIBLE
            //Here the json data is add to a hash map with key data
            val params: MutableMap<String, String> =
                HashMap()

            params[Constants.paramKey_UserId] =
                GlobalUsage.userInfoModel.data!!.userid!!.toString()
            params[Constants.paramKey_EmployeeId] = GlobalUsage.empSlaveModel.employeeid!!
            params[Constants.paramKey_receiptno] = receiptno
            params[Constants.paramKey_receiptamount] = receiptamount
            params[Constants.paramKey_UploadRecipt] = receiptImageString
            params[Constants.paramKey_star] = star
            params[Constants.paramKey_WrittenNote] = WrittenNote
            params[Constants.paramKey_VoiceNote] = voiceNoteString

            Log.e("TAG", "Call Param : " + Gson().toJson(params))
            val service =
                ApiCallingInstance.retrofitInstance.create<NetworkServices.EmployeeReportService>(
                    NetworkServices.EmployeeReportService::class.java
                )
            val call =
                service.EmployeeReportData(
                    params, GlobalUsage.userInfoModel.data!!.access_token!!.toString()
                )

            call.enqueue(object : Callback<ReviewSubmitModel> {
                override fun onFailure(call: Call<ReviewSubmitModel>, t: Throwable) {
                    DB_ReviewEdit.cntLoader.visibility = View.GONE
                    Log.e("GetResponsesasXASX", "Hell: " + t)
                }

                override fun onResponse(
                    call: Call<ReviewSubmitModel>,
                    response: Response<ReviewSubmitModel>
                ) {
                    Log.e("GetResponsesasXASX", "responseHell: " + response.body()!!)
                    DB_ReviewEdit.cntLoader.visibility = View.GONE
                    if (response.body()!!.status.equals(Constants.ResponseSucess)) {
                        GlobalUsage.ReviewInfoModel = response.body()!!.data!!

                        GlobalUsage.NextScreen(
                            this@ReviewEdit,
                            Intent(this@ReviewEdit, ReviewDisplay::class.java)
                        )
                        finish()
                    } else if (response.body()!!.status.equals(Constants.ResponseUnauthorized)) {
                        DB_ReviewEdit.cntUnAuthorized.visibility = View.VISIBLE
                    } else if (response.body()!!.status.equals(Constants.ResponseEmpltyList)) {

                    } else {
                        DB_ReviewEdit.txtAlertmsg.text = response.body()!!.message
                        DB_ReviewEdit.cntAlert.visibility = View.VISIBLE
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

    private fun ConvertAudioToBase64() {

        val audioBytes: ByteArray
        try {
            val file = File(audioFilePath)
            val bytes: ByteArray = loadFile(file)!!

            // Here goes the Base64 string
            voiceNoteString = Base64.encodeToString(bytes, Base64.DEFAULT)
            Log.e("TAG", "StringAudio : " + voiceNoteString)


            //Decode Audio File
//            val fos = FileOutputStream(File(DaudioFilePath))
//            fos.write(Base64.decode(_audioBase64, Base64.DEFAULT))
//            fos.close()
//            try {
//                player = MediaPlayer()
//                player!!.setDataSource(DaudioFilePath)
//                player!!.prepare()
//                player!!.start()
//                Log.e("TAG","Check audio")
//            } catch (e: java.lang.Exception) {
////                DiagnosticHelper.writeException(e)
//            }
            DB_ReviewEdit.cntLoader.visibility = View.GONE

        } catch (e: java.lang.Exception) {
//            DiagnosticHelper.writeException(e)
            Log.e("TAG", "StringAudioException : " + Gson().toJson(e))

        }

//        Log.e("TAG","StringAudio : "+ConvertFileOrImageToString(Uri.parse(audioFilePath)))
    }


    @Throws(IOException::class)
    private fun loadFile(file: File): ByteArray? {
        val `is`: InputStream = FileInputStream(file)
        val length = file.length()
        if (length > Int.MAX_VALUE) {
            // File is too large
        }
        val bytes = ByteArray(length.toInt())
        var offset = 0
        var numRead = 0
        while (offset < bytes.size
            && `is`.read(bytes, offset, bytes.size - offset).also { numRead = it } >= 0
        ) {
            offset += numRead
        }
        if (offset < bytes.size) {
            throw IOException("Could not completely read file " + file.name)
        }
        `is`.close()
        return bytes
    }

    private fun AudioRecording() {
        if (isRecoding) {
            StartRecording()
        } else {
            StopRecording()
        }
    }

    private fun StopCounter() {
        running = false
    }

    private fun ResetCounter() {
        running = false
        seconds = 0
    }

    private fun StartCounter() {
        running = true
        TimerHandler.postDelayed(TimerRunnable!!, 0)
    }

    private fun StopPlaying() {
        if (player != null) {
            player!!.release()
            player = null
            isPlaying = false
            DB_ReviewEdit.imgPlaypause.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_play
                )
            )
            DB_ReviewEdit.imgMic.isClickable = true
            DB_ReviewEdit.imgMic.alpha = 1f
            ResetCounter()
        }
    }

    private fun PlayRecording() {
        player = MediaPlayer()
        try {
            player!!.setDataSource(audioFilePath)
            player!!.setOnCompletionListener(OnCompletionListener { StopPlaying() })
            player!!.prepare()
            player!!.start()
            DB_ReviewEdit.imgPlaypause.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_pause
                )
            )
            DB_ReviewEdit.imgMic.isClickable = false
            DB_ReviewEdit.imgMic.alpha = 0.2f
            StartCounter()
        } catch (e: IOException) {
            Log.e("TAG" + ":playRecording()", "prepare() failed")
        }
    }

    private fun FilePlayPause() {
        if (isPlaying) {
            //File Play run
            PlayRecording()
        } else {
            //File Play Stop
            StopPlaying()
        }
    }

    private fun ResetRecording() {
        StopPlaying()
        DB_ReviewEdit.txtRecodinglbl.visibility = View.INVISIBLE
        DB_ReviewEdit.motMic.transitionToEnd()
        DB_ReviewEdit.crdPlaypause.visibility = View.INVISIBLE
        DB_ReviewEdit.crdDelete.visibility = View.INVISIBLE
        ResetCounter()
        if (File(audioFilePath).exists()) {
            File(audioFilePath).delete()
        }
        DB_ReviewEdit.txtConfirm.isClickable = false
        DB_ReviewEdit.txtConfirm.background =
            ContextCompat.getDrawable(this, R.drawable.proceedback_default)
        DB_ReviewEdit.imgIconMic.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_voice_none
            )
        )
    }

    private fun StopRecording() {
        DB_ReviewEdit.txtRecodinglbl.visibility = View.INVISIBLE
        if (recorder != null) {
            recorder!!.release()
            recorder = null
        }
        ResetCounter()
        DB_ReviewEdit.crdPlaypause.visibility = View.VISIBLE
        DB_ReviewEdit.crdDelete.visibility = View.VISIBLE
        DB_ReviewEdit.txtConfirm.isClickable = true
        DB_ReviewEdit.txtConfirm.background =
            ContextCompat.getDrawable(this, R.drawable.proceedback_orange)
        DB_ReviewEdit.imgIconMic.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_voice_colored
            )
        )
    }

    private fun StartRecording() {
        ResetRecording()
        DB_ReviewEdit.motMic.transitionToStart()
        DB_ReviewEdit.txtRecodinglbl.visibility = View.VISIBLE
//        audioFile.record()
        recorder = MediaRecorder()
        recorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        recorder!!.setOutputFile(audioFilePath)
        recorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

        try {
            recorder!!.prepare()
        } catch (e: IOException) {
            Log.e("TAG" + ":startRecording()", "prepare() failed")
        }
        StartCounter()
        recorder!!.start()
    }


    // Save the state of the stopwatch
    // if it's about to be destroyed.
    override fun onSaveInstanceState(
        savedInstanceState: Bundle
    ) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState
            .putInt("seconds", seconds)
        savedInstanceState
            .putBoolean("running", running)
        savedInstanceState
            .putBoolean("wasRunning", wasRunning)
    }

    // If the activity is paused,
    // stop the stopwatch.
    override fun onPause() {
        super.onPause()
        wasRunning = running
        running = false
    }

    // If the activity is resumed,
    // start the stopwatch
    // again if it was running previously.
    override fun onResume() {
        super.onResume()
        if (wasRunning) {
            running = true
        }
    }

    private fun AudioFileSetUp() {
//        audioFilePath = "${Environment.getExternalStorageDirectory()} /recorded_audio.wav";
        DaudioFilePath = getAudioFileUri("review1.wav").absolutePath
        audioFilePath = getAudioFileUri("review.wav").absolutePath
        Log.e("TAG", "FilePathSaved : " + audioFilePath)

    }

    private fun VoiceRecordingSetup() {
        DB_ReviewEdit.cntVoiceDialoug.visibility = View.VISIBLE
        ResetRecording()
        val permissions =
            arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        Permissions.check(this, permissions, null, null, object : PermissionHandler() {
            override fun onGranted() {
                // do your task.
                AudioFileSetUp()
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

        AudioFileSetUp()
        DB_ReviewEdit.motMic.setTransitionListener(object : TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {

            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {

            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {

                Log.e("TAG", "isRecoding" + isRecoding)
                if (isRecoding) {
                    Log.e("TA", "Recording")
                    if (DB_ReviewEdit.motMic.transitionToStart() == motionLayout!!.transitionToStart()) {
                        motionLayout.transitionToEnd()
                    } else {
                        motionLayout.transitionToStart()
                    }
//                    if (!looped) {
//                        motionLayout!!.transitionToStart();
//                        looped = true
//                    }
//                    else {
//                        motionLayout!!.transitionToEnd();
//                        looped = false
//                    }

                } else {
                    motionLayout!!.transitionToEnd()
                    Log.e("TA", "Recording NOt")
                }
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {

            }

        })
    }
}