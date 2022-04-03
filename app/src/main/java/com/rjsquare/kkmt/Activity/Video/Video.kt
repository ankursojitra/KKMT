package com.rjsquare.kkmt.Activity.Video

import android.app.Activity
import android.content.ActivityNotFoundException
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable.ApiCallingInstance
import com.rjsquare.kkmt.Adapter.VideosAdapter
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.Model.VideoesModel
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.Events.VideosService
import com.rjsquare.kkmt.RetrofitInstance.Events.Videos_Model
import com.rjsquare.kkmt.databinding.ActivityVideoBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Video : AppCompatActivity(), View.OnClickListener {
    lateinit var mVideosModel: VideoesModel
    lateinit var mArray_VideosModel: ArrayList<VideoesModel>
    var PageNo = 0
    var dataSize = 0
    var IsVideoCallavailable = false


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    companion object {
        lateinit var DB_Video: ActivityVideoBinding
        lateinit var thisVideo: Activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_Video = DataBindingUtil.setContentView(this, R.layout.activity_video)
        try {
            ApplicationClass.StatusTextWhite(this, true)
            thisVideo = this
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                DB_Video.txtVideoLbl.text = (Html.fromHtml(
                    "Enhance Your Perspective and <font color='#FC9A1F'>Earn Credits</font>",
                    Html.FROM_HTML_MODE_COMPACT
                ));
            } else {
                DB_Video.txtVideoLbl.text =
                    (Html.fromHtml("Enhance Your Perspective and and <font color='#FC9A1F'>Earn Credits</font>"));
            }


            mArray_VideosModel = ArrayList()
            DB_Video.imgBack.setOnClickListener(this)

            IsVideoCallavailable = false
//            DB_Video.cntLoader.visibility = View.VISIBLE
//            framesAdapter()
//            GetLatestVideos((++PageNo).toString())

            filldata()
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

    private fun filldata() {
        try {
            mVideosModel = VideoesModel()
            mVideosModel.EarnPoint = "12300"
            mVideosModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.video1)!!
            mVideosModel.VideoTitle = "My Biggest Lesson"
            mArray_VideosModel.add(mVideosModel)
            mArray_VideosModel.add(mVideosModel)
            mArray_VideosModel.add(mVideosModel)

            framesAdapter()
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

    private fun GetLatestVideos(pageNo: String) {
//        try {
//
//            //Here the json data is add to a hash map with key data
//            val params: MutableMap<String, String> =
//                HashMap()
//            params[ApplicationClass.paramKey_Usertype] =
//                ApplicationClass.userInfoModel.data!!.usertype!!
//            params[ApplicationClass.paramKey_PageNo] = pageNo
//            params[ApplicationClass.paramKey_UserId] =
//                ApplicationClass.userInfoModel.data!!.userid!!
//
//            val service =
//                ApiCallingInstance.retrofitInstance.create<VideosService>(
//                    VideosService::class.java
//                )
//            val call =
//                service.GetVideosData(
//                    params, ApplicationClass.userInfoModel.data!!.access_token!!
//                )
//
//            call.enqueue(object : Callback<Videos_Model> {
//                override fun onFailure(call: Call<Videos_Model>, t: Throwable) {
//
//                    DB_Video.cntLoader.visibility = View.GONE
//                    Log.e("GetResponsesasXASX", "Hell: ")
//                }
//
//                override fun onResponse(
//                    call: Call<Videos_Model>,
//                    response: Response<Videos_Model>
//                ) {
//                    Log.e("GetResponsesas", "Hell: " + response.body())
//                    DB_Video.cntLoader.visibility = View.GONE
//                    if (response.body()!!.status.equals(ApplicationClass.ResponseSucess)) {
//                        dataSize = response.body()!!.data!!.size
//                        mArray_VideosModel.addAll(response.body()!!.data!!)
//                        DB_Video.rrVideos.adapter!!.notifyDataSetChanged()
//                        IsVideoCallavailable = true
//                        Log.e("TAG", "Sizeofdata : " + mArray_VideosModel.size)
//                        if (mArray_VideosModel.size > 0) {
//                            DB_Video.txtNoEvents.visibility = View.GONE
//                        } else {
//                            DB_Video.txtNoEvents.visibility = View.VISIBLE
//                        }
//                    } else {
//
//                    }
//                }
//            })
//        } catch (E: Exception) {
//            print(E)
//        } catch (NE: NullPointerException) {
//            print(NE)
//        } catch (IE: IndexOutOfBoundsException) {
//            print(IE)
//        } catch (IE: IllegalStateException) {
//            print(IE)
//        } catch (AE: ActivityNotFoundException) {
//            print(AE)
//        } catch (KNE: KotlinNullPointerException) {
//            print(KNE)
//        } catch (CE: ClassNotFoundException) {
//            print(CE)
//        }
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

            DB_Video.rrVideos.setAdapter(loVideosAdapter)

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
                    if ((totalItemCount) == lastVisible && IsVideoCallavailable && dataSize == 10) {
                        IsVideoCallavailable = false
                        GetLatestVideos((++PageNo).toString())
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
        if (view == DB_Video.imgBack) {
            onBackPressed()
        }
    }
}