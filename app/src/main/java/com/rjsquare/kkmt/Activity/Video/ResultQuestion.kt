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
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.AppConstant.Constants
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
            ApplicationClass.StatusTextWhite(this, true)

//            mImgBack = findViewById<ImageView>(R.id.img_back)
//            mTxtWatchmore = findViewById<TextView>(R.id.txt_watchmore)

            DB_ResultQuestion.imgBack.setOnClickListener(this)
            DB_ResultQuestion.txtWatchmore.setOnClickListener(this)
            DB_ResultQuestion.cntBacktohome.setOnClickListener(this)
            DB_ResultQuestion.txtAlertok.setOnClickListener(this)

            SetUpUIData()
            DB_ResultQuestion.cntLoader.visibility = View.VISIBLE
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
                ApplicationClass.userInfoModel.data!!.usertype!!
            params[Constants.paramKey_UserId] =
                ApplicationClass.userInfoModel.data!!.userid!!

            val service =
                ApiCallingInstance.retrofitInstance.create<NetworkServices.VideoQuestionCompleteService>(
                    NetworkServices.VideoQuestionCompleteService::class.java
                )
            val call =
                service.GetVideoQuestionComplete(
                    params, ApplicationClass.userInfoModel.data!!.access_token!!
                )

            call.enqueue(object : Callback<VideoQuestionComplete_Model> {
                override fun onFailure(call: Call<VideoQuestionComplete_Model>, t: Throwable) {
                    Log.e("GetResponsesasXASX", "Hell: ")
                    DB_ResultQuestion.cntLoader.visibility = View.GONE
                }

                override fun onResponse(
                    call: Call<VideoQuestionComplete_Model>,
                    response: Response<VideoQuestionComplete_Model>
                ) {
                    Log.e("GetResponsesas", "Hell: " + Gson().toJson(response.body()))
                    DB_ResultQuestion.cntLoader.visibility = View.GONE
                    if (response.body()!!.status.equals(Constants.ResponseSucess)) {

                    } else if (response.body()!!.status.equals(Constants.ResponseUnauthorized)) {
                        ApplicationClass.UserLogout(this@ResultQuestion)
                    } else {
                        DB_ResultQuestion.txtAlertmsg.text = response.body()!!.message
                        DB_ResultQuestion.cntAlert.visibility = View.VISIBLE
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
                if (view == DB_ResultQuestion.imgBack) {
                    Video.thisVideo.finish()
                    onBackPressed()
                } else if (view == DB_ResultQuestion.txtWatchmore) {
                    setResult(Activity.RESULT_OK)
                    onBackPressed()
                } else if (view == DB_ResultQuestion.cntBacktohome) {
                    Video.thisVideo.finish()
                    onBackPressed()
                } else if (view == DB_ResultQuestion.txtAlertok) {
                    DB_ResultQuestion.cntAlert.visibility = View.GONE
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