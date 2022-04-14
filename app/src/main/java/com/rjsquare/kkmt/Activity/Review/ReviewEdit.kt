package com.rjsquare.kkmt.Activity.Review

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import com.rjsquare.kkmt.Activity.commanUtils
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.ActivityReviewScreenBinding
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream

class ReviewEdit : AppCompatActivity(), View.OnClickListener {

    var PDFString: String = ""
    val PICK_IMAGE = 1
    var photoFileName = "photo.jpg"
    var photoFile: File? = null


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
        ApplicationClass.StatusTextWhite(this, true)
        thisReviewEdit = this

        DB_ReviewEdit.txtSubmit.setOnClickListener(this)
        DB_ReviewEdit.imgBack.setOnClickListener(this)
        DB_ReviewEdit.cnt1star.setOnClickListener(this)
        DB_ReviewEdit.cntBad.setOnClickListener(this)
        DB_ReviewEdit.cntGood.setOnClickListener(this)
        DB_ReviewEdit.cnt5star.setOnClickListener(this)
        DB_ReviewEdit.cntReceiptUpload.setOnClickListener(this)
        DB_ReviewEdit.cntVoice.setOnClickListener(this)

        SetUpFields()
        SetUpReviewData()
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

        DB_ReviewEdit.edtReceiptAmount.setOnFocusChangeListener(
            @RequiresApi(Build.VERSION_CODES.M)
            object : View.OnFocusChangeListener {
                override fun onFocusChange(p0: View?, focused: Boolean) {
                    if (!focused) {
                        if (!DB_ReviewEdit.edtReceiptAmount.text.toString().equals("")) {
                            DB_ReviewEdit.edtReceiptAmount.setText(
                                commanUtils.formatNumber(
                                    DB_ReviewEdit.edtReceiptAmount.text.toString().toInt()
                                )
                            )
                        }
                    } else {
                        if (!DB_ReviewEdit.edtReceiptAmount.text.toString().equals("")) {
                            var value = DB_ReviewEdit.edtReceiptAmount.text.toString().replace(",","")
                            DB_ReviewEdit.edtReceiptAmount.setText(value)
                        }
                    }
                }
            })

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
        PDFString = ""
    }

    private fun SetUpReviewData() {
        if (ApplicationClass.isReviewNew) {
            //Setup New review data
//            UncheckedReview()
            DB_ReviewEdit.cnt1star.performClick()

            DB_ReviewEdit.txtReviewName.text = ApplicationClass.Selected_ReviewEmp_Model.EmpName

            var UserImage = ""
            if (ApplicationClass.userInfoModel.data!!.userimage != null && !ApplicationClass.userInfoModel.data!!.userimage.equals(
                    ""
                )
            ) {
                UserImage = ApplicationClass.userInfoModel.data!!.userimage!!
                Picasso.with(this).load(UserImage)
                    .placeholder(R.drawable.ic_expe_logo).into(DB_ReviewEdit.imgProfile)
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
            val baos = ByteArrayOutputStream()
            val `in` = contentResolver.openInputStream(sUri)
            val bytes: ByteArray = getBytes(`in`!!)!!

            PDFString = Base64.encodeToString(bytes, Base64.DEFAULT)

            Log.e("TAG", "ActivityResult: " + PDFString)
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
        DB_ReviewEdit.txtUploadReceipt.setText(getString(R.string.uploacreceipt))
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
            if (view == DB_ReviewEdit.txtSubmit) {
                var HelperIntent = Intent(this, ReviewDisplay::class.java)
                startActivity(HelperIntent)
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
            } else if (view == DB_ReviewEdit.imgBack) {
                onBackPressed()
            } else if (view == DB_ReviewEdit.cnt1star) {
                UncheckedReview()
                DB_ReviewEdit.cnt1star.setBackgroundResource(R.drawable.review_selection)
            } else if (view == DB_ReviewEdit.cntBad) {
                UncheckedReview()
                DB_ReviewEdit.cntBad.setBackgroundResource(R.drawable.review_selection)
            } else if (view == DB_ReviewEdit.cntGood) {
                UncheckedReview()
                DB_ReviewEdit.cntGood.setBackgroundResource(R.drawable.review_selection)
            } else if (view == DB_ReviewEdit.cnt5star) {
                UncheckedReview()
                DB_ReviewEdit.cnt5star.setBackgroundResource(R.drawable.review_selection)
            }  else if (view == DB_ReviewEdit.cntVoice) {
                VoiceRecordingSetup()
            } else if (view == DB_ReviewEdit.txtUploadReceipt) {
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

    private fun VoiceRecordingSetup() {

    }
}