package com.rjsquare.kkmt.Activity.Video

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable.ApiCallingInstance
import com.rjsquare.kkmt.Activity.Dialog.Network
import com.rjsquare.kkmt.Activity.Dialog.UnAuthorized
import com.rjsquare.kkmt.Adapter.VideosAdapter

import com.rjsquare.kkmt.AppConstant.Constants
import com.rjsquare.kkmt.AppConstant.GlobalUsage
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.Events.NetworkServices
import com.rjsquare.kkmt.RetrofitInstance.Events.Videos_Model
import com.rjsquare.kkmt.databinding.ActivityVideoBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Video : AppCompatActivity(), View.OnClickListener {
    lateinit var mArray_VideosModel: ArrayList<Videos_Model.VideoData>
    var PageNo = 0
    var dataSize = 0
    var PagePerlimit = 10
    var IsVideoCallavailable = false

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    companion object {
        lateinit var DB_Video: ActivityVideoBinding
        lateinit var thisVideo: Activity
        var CompleteQuestionCall = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_Video = DataBindingUtil.setContentView(this, R.layout.activity_video)
        try {
            GlobalUsage.StatusTextWhite(this, true)
            thisVideo = this
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                DB_Video.txtVideoLbl.text = (Html.fromHtml(
                    "Enhance Your Perspective and <font color='#FC9A1F'>Earn Credits</font>",
                    Html.FROM_HTML_MODE_COMPACT
                ))
            } else {
                DB_Video.txtVideoLbl.text =
                    (Html.fromHtml("Enhance Your Perspective and and <font color='#FC9A1F'>Earn Credits</font>"))
            }


            mArray_VideosModel = ArrayList()
            DB_Video.imgBack.setOnClickListener(this)

            IsVideoCallavailable = false

            framesAdapter()
            if (!GlobalUsage.IsNetworkAvailable(this)) {
                Network.showDialog(this)
                return
            }
            GetLatestVideos((++PageNo).toString(), PagePerlimit.toString())

//            filldata()
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
        if (requestCode == CompleteQuestionCall) {
            if (resultCode == Activity.RESULT_OK) {
                Log.e("TAG", "GetCallFromComplete : ")
                PageNo = 0
                mArray_VideosModel = ArrayList()
                if (!GlobalUsage.IsNetworkAvailable(this)) {
                    Network.showDialog(this)
                    return
                }
                GetLatestVideos((++PageNo).toString(), PagePerlimit.toString())
            }
        }
    }

    private fun filldata() {
        try {
//            mVideosModel = VideoesModel()
//            mVideosModel.EarnPoint = "12300"
//            mVideosModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.video1)!!
//            mVideosModel.VideoTitle = "My Biggest Lesson"
//            mArray_VideosModel.add(mVideosModel)
//            mArray_VideosModel.add(mVideosModel)
//            mArray_VideosModel.add(mVideosModel)
//
//            framesAdapter()
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

    private fun GetLatestVideos(pageNo: String, PagePerLimit: String) {
        try {
            DB_Video.gifLoader.visibility = View.VISIBLE
            //Here the json data is add to a hash map with key data
            val params: MutableMap<String, String> =
                HashMap()
            params[Constants.paramKey_Usertype] =
                GlobalUsage.userInfoModel.data!!.usertype!!
            params[Constants.paramKey_PageNo] = pageNo
            params[Constants.paramKey_limit] = PagePerLimit
            params[Constants.paramKey_UserId] =
                GlobalUsage.userInfoModel.data!!.userid!!

            val service =
                ApiCallingInstance.retrofitInstance.create<NetworkServices.VideosService>(
                    NetworkServices.VideosService::class.java
                )
            val call =
                service.GetVideosData(
                    params, GlobalUsage.userInfoModel.data!!.access_token!!
                )

            call.enqueue(object : Callback<Videos_Model> {
                override fun onFailure(call: Call<Videos_Model>, t: Throwable) {
                    DB_Video.gifLoader.visibility = View.GONE
                }

                override fun onResponse(
                    call: Call<Videos_Model>,
                    response: Response<Videos_Model>
                ) {
                    DB_Video.gifLoader.visibility = View.GONE
                    Log.e("TAG", "VideoResponse : " + Gson().toJson(response.body()!!))
                    if (response.body()!!.status.equals(Constants.ResponseSucess)) {
                        IsVideoCallavailable = response.body()!!.data!!.size >= this@Video.PagePerlimit
                        dataSize = response.body()!!.data!!.size
                        mArray_VideosModel.addAll(response.body()!!.data!!)
                        DB_Video.rrVideos.adapter!!.notifyDataSetChanged()

                        if (mArray_VideosModel.size > 0) {
                            DB_Video.txtNoEvents.visibility = View.GONE
                        } else {
                            DB_Video.txtNoEvents.visibility = View.VISIBLE
                        }
                    } else if (response.body()!!.status.equals(Constants.ResponseUnauthorized)) {
                        UnAuthorized.showDialog(this@Video)
                    } else if (response.body()!!.status.equals(Constants.ResponseEmpltyList)) {
                        IsVideoCallavailable = false
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


    fun framesAdapter() {
        try {
            if (mArray_VideosModel != null && mArray_VideosModel.size > 0) {
                DB_Video.txtNoEvents.visibility = View.GONE
            } else {
                DB_Video.txtNoEvents.visibility = View.VISIBLE
            }

            val loVideosAdapter: VideosAdapter
            loVideosAdapter = VideosAdapter(
                this, mArray_VideosModel
            )

            DB_Video.rrVideos.adapter = loVideosAdapter

            DB_Video.rrVideos.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val layoutManager =
                        LinearLayoutManager::class.java.cast(recyclerView.layoutManager)
                    val totalItemCount = layoutManager.itemCount
                    val lastVisible = layoutManager.findLastVisibleItemPosition()
                    val endHasBeenReached = lastVisible + 5 >= totalItemCount
                    Log.e("TAG", "POSITION : " + totalItemCount)
                    Log.e("TAG", "LastPOSITION : " + lastVisible)
                    if (totalItemCount > 0 && endHasBeenReached) {
                        //you have reached to the bottom of your recycler view
                        Log.e("TAG", "RECYCLERVIEWLASTITEM")
                    }
                    if ((totalItemCount) == lastVisible && IsVideoCallavailable && dataSize == PagePerlimit) {
                        IsVideoCallavailable = false
                        GetLatestVideos(
                            (++PageNo).toString(),
                            PagePerlimit.toString()
                        )
                    }
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

    override fun onClick(view: View?) {
        if (System.currentTimeMillis() < GlobalUsage.lastClick) return else {
            GlobalUsage.lastClick = System.currentTimeMillis() + GlobalUsage.clickInterval
            if (view == DB_Video.imgBack) {
                onBackPressed()
            }
        }
    }
}