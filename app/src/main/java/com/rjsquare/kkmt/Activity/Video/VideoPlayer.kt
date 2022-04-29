package com.rjsquare.kkmt.Activity.Video

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable.ApiCallingInstance
import com.rjsquare.kkmt.Activity.Dialog.Loader
import com.rjsquare.kkmt.Activity.Dialog.Network

import com.rjsquare.kkmt.AppConstant.Constants
import com.rjsquare.kkmt.AppConstant.GlobalUsage
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.Events.NetworkServices
import com.rjsquare.kkmt.RetrofitInstance.Events.VideoDetail_Model
import com.rjsquare.kkmt.databinding.ActivityVideoPlayerBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VideoPlayer : AppCompatActivity(), View.OnClickListener {

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    companion object {
        lateinit var VideoPlayerActivity: Activity
        lateinit var DB_VideoPlayer: ActivityVideoPlayerBinding
        lateinit var VideoData: VideoDetail_Model.VideoDetail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_VideoPlayer = DataBindingUtil.setContentView(this, R.layout.activity_video_player)

        try {
            GlobalUsage.StatusTextWhite(this, true)
            VideoPlayerActivity = this
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            val height = displayMetrics.heightPixels
            val width = displayMetrics.widthPixels

            val playerHeight = (width / 1.78)

            GlobalUsage.SetLayoutHeight(DB_VideoPlayer.andExoPlayerView, playerHeight.toInt())

            DB_VideoPlayer.imgBack.setOnClickListener(this)
            DB_VideoPlayer.txtQuestions.setOnClickListener(this)
            if (!GlobalUsage.IsNetworkAvailable(this)) {
                Network.showDialog(this)
                return
            }
            Loader.showLoader(this)
            GetVideoDetails()
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

    private fun GetVideoDetails() {
        try {

            //Here the json data is add to a hash map with key data
            val params: MutableMap<String, String> =
                HashMap()
            params[Constants.paramKey_VideoId] = GlobalUsage.mVideoesModelSelected!!.id!!
            params[Constants.paramKey_UserId] =
                GlobalUsage.userInfoModel.data!!.userid!!
            val service =
                ApiCallingInstance.retrofitInstance.create<NetworkServices.VideosDetailService>(
                    NetworkServices.VideosDetailService::class.java
                )
            val call =
                service.GetVideosDetail(
                    params, GlobalUsage.userInfoModel.data!!.access_token!!
                )

            call.enqueue(object : Callback<VideoDetail_Model> {
                override fun onFailure(call: Call<VideoDetail_Model>, t: Throwable) {
                    Loader.hideLoader()
                }

                override fun onResponse(
                    call: Call<VideoDetail_Model>,
                    response: Response<VideoDetail_Model>
                ) {
                    Loader.hideLoader()
                    if (response.body()!!.status.equals(Constants.ResponseSucess)) {
                        VideoData = response.body()!!.data!!
                    } else {

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


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
            DB_VideoPlayer.cntTool.visibility = View.GONE
        } else {
            DB_VideoPlayer.cntTool.visibility = View.VISIBLE
        }
    }

    override fun onClick(view: View?) {
        try {
            if (System.currentTimeMillis() < GlobalUsage.lastClick) return else {
                GlobalUsage.lastClick =
                    System.currentTimeMillis() + GlobalUsage.clickInterval
                if (view == DB_VideoPlayer.imgBack) {
                    onBackPressed()
                } else if (view == DB_VideoPlayer.txtQuestions) {
                    GlobalUsage.NextScreen(this, Intent(this, Questions::class.java))
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