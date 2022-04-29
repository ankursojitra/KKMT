package com.rjsquare.kkmt.Activity.Video

import android.app.Activity
import android.content.ActivityNotFoundException
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable.ApiCallingInstance
import com.rjsquare.kkmt.Activity.Dialog.Alert
import com.rjsquare.kkmt.Activity.Dialog.Loader
import com.rjsquare.kkmt.Activity.Dialog.Network

import com.rjsquare.kkmt.AppConstant.Constants
import com.rjsquare.kkmt.AppConstant.GlobalUsage
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.Events.NetworkServices
import com.rjsquare.kkmt.RetrofitInstance.Events.VideoQuestionComplete_Model
import com.rjsquare.kkmt.databinding.ActivityResultQuestionBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultQuestion : AppCompatActivity(), View.OnClickListener {
//    private lateinit var mImgBack: ImageView
//    private lateinit var mTxtWatchmore: TextView

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    companion object {
        lateinit var DB_ResultQuestion: ActivityResultQuestionBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_ResultQuestion = DataBindingUtil.setContentView(this, R.layout.activity_result_question)
//        setContentView(R.layout.activity_result_question)
        try {
            GlobalUsage.StatusTextWhite(this, true)

//            mImgBack = findViewById<ImageView>(R.id.img_back)
//            mTxtWatchmore = findViewById<TextView>(R.id.txt_watchmore)

            DB_ResultQuestion.imgBack.setOnClickListener(this)
            DB_ResultQuestion.txtWatchmore.setOnClickListener(this)
            DB_ResultQuestion.cntBacktohome.setOnClickListener(this)

            SetUpUIData()
            if (!GlobalUsage.IsNetworkAvailable(this)) {
                Network.showDialog(this)
                return
            }
            Loader.showLoader(this)
            CompleteQuestion()
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

    private fun SetUpUIData() {
        val ansTotal = VideoPlayer.VideoData.question!!.size
        DB_ResultQuestion.txtCorrect.text = "Correct : $ansTotal Answers"
        DB_ResultQuestion.txtCredit.text = VideoPlayer.VideoData.credit
    }

    private fun CompleteQuestion() {
        try {

            //Here the json data is add to a hash map with key data
            val params: MutableMap<String, String> =
                HashMap()
            params[Constants.paramKey_VideoId] = VideoPlayer.VideoData.id!!
            params[Constants.paramKey_Credit] = VideoPlayer.VideoData.credit!!
            params[Constants.paramKey_Usertype] =
                GlobalUsage.userInfoModel.data!!.usertype!!
            params[Constants.paramKey_UserId] =
                GlobalUsage.userInfoModel.data!!.userid!!

            val service =
                ApiCallingInstance.retrofitInstance.create<NetworkServices.VideoQuestionCompleteService>(
                    NetworkServices.VideoQuestionCompleteService::class.java
                )
            val call =
                service.GetVideoQuestionComplete(
                    params, GlobalUsage.userInfoModel.data!!.access_token!!
                )

            call.enqueue(object : Callback<VideoQuestionComplete_Model> {
                override fun onFailure(call: Call<VideoQuestionComplete_Model>, t: Throwable) {
                    Loader.hideLoader()
                }

                override fun onResponse(
                    call: Call<VideoQuestionComplete_Model>,
                    response: Response<VideoQuestionComplete_Model>
                ) {
                    Loader.hideLoader()
                    if (response.body()!!.status.equals(Constants.ResponseSucess)) {

                    } else if (response.body()!!.status.equals(Constants.ResponseUnauthorized)) {
                        GlobalUsage.UserLogout(this@ResultQuestion)
                    } else {
                        Alert.showDialog(this@ResultQuestion, response.body()!!.message!!)
//                        DB_ResultQuestion.txtAlertmsg.text = response.body()!!.message
//                        DB_ResultQuestion.cntAlert.visibility = View.VISIBLE
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
            if (System.currentTimeMillis() < GlobalUsage.lastClick) return else {
                GlobalUsage.lastClick =
                    System.currentTimeMillis() + GlobalUsage.clickInterval
                if (view == DB_ResultQuestion.imgBack) {
                    Video.thisVideo.finish()
                    onBackPressed()
                } else if (view == DB_ResultQuestion.txtWatchmore) {
                    setResult(Activity.RESULT_OK)
                    onBackPressed()
                } else if (view == DB_ResultQuestion.cntBacktohome) {
                    Video.thisVideo.finish()
                    onBackPressed()
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