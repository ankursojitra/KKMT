package com.rjsquare.kkmt.Activity.Splash

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.rjsquare.kkmt.AppConstant.Constants
import com.rjsquare.kkmt.AppConstant.GlobalUsage
import com.rjsquare.kkmt.Helpers.Preferences
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.ActivitySplashBinding
import java.io.*


class Splash : AppCompatActivity() {
    companion object {
        lateinit var DB_Splash: ActivitySplashBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_Splash = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        try {
            ConvertAudioToBase64()
            GlobalUsage.StatusTextWhite(this, true)
            GlobalUsage.userLogedIn =
                Preferences.ReadBoolean(Constants.Pref_UserLogedIn, false)
//            DB_Splash.cntStartapp.transitionToStart()
            DB_Splash.cntStartapp.setTransitionListener(object : MotionLayout.TransitionListener {
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
//                    GlobalUsage.NextScreen(
//                        this@Splash,
//                        Intent(this@Splash, HomeActivity::class.java)
//                    )
//                    finish()
                }

                override fun onTransitionTrigger(
                    motionLayout: MotionLayout?,
                    triggerId: Int,
                    positive: Boolean,
                    progress: Float
                ) {

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

    @Throws(IOException::class)
    private fun copyInputStreamToFile(inputStream: InputStream, file: File) {

        // append = false
        FileOutputStream(file, false).use { outputStream ->
            var read: Int
            val bytes = ByteArray(DEFAULT_BUFFER_SIZE)
            while (inputStream.read(bytes).also { read = it } != -1) {
                outputStream.write(bytes, 0, read)
            }
        }
    }

    // Returns the File for a photo stored on disk given the fileName
    fun getAudioFileUri(fileName: String): File {
        val root = Environment.getExternalStorageDirectory()
        val dir = File(root.absolutePath + "/KKMTX/Music")
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

    private fun ConvertAudioToBase64() {

        val audioBytes: ByteArray
        try {
//            val file = File(Uri.parse("android.resource://$packageName/" + R.raw.review).toString())
//            val file = assets.open("review.m4a")
//            val file = resources.openRawResource(R.raw.review)
            var audioFilePath = getAudioFileUri("review.m4a").absolutePath
            val filex = File(audioFilePath)


            copyInputStreamToFile(resources.openRawResource(R.raw.review), filex)
            val bytes: ByteArray = loadFile(filex)!!
            // Here goes the Base64 string
            var voiceNoteString = Base64.encodeToString(bytes, Base64.NO_WRAP)
//            Log.e("TAG", "StringAudio : " + voiceNoteString)

            print(" XX::: " + voiceNoteString)
            System.out.print(" ::: " + Base64.encodeToString(bytes, Base64.NO_WRAP))
            val maxLogSize = 1000
            for (i in 0..voiceNoteString.length / maxLogSize) {
                val start = i * maxLogSize
                var end = (i + 1) * maxLogSize
                end = if (end > voiceNoteString.length) voiceNoteString.length else end
                Log.e("TAG ::: ", voiceNoteString.substring(start, end))
            }
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
//            setVoiceNoteUI()
//            Loader.hideLoader()

        } catch (e: java.lang.Exception) {
//            DiagnosticHelper.writeException(e)
            Log.e("TAG", "StringAudioException : " + Gson().toJson(e))

        }

    }
}