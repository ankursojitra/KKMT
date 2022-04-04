package com.rjsquare.kkmt.Adapter

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.media.MediaMetadataRetriever
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.rjsquare.kkmt.Activity.Video.Video
import com.rjsquare.kkmt.Activity.Video.VideoPlayer
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.Model.VideoesModel
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.Events.Videos_Model
import com.rjsquare.kkmt.databinding.RawVideoFrameBinding
import java.util.*


class VideosAdapter(
    var moContext: Context,
    var moArrayList: ArrayList<Videos_Model.VideoData>
) : RecyclerView.Adapter<VideosAdapter.View_holder>() {

    var Width = 0

    private var layoutInflater: LayoutInflater? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): View_holder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val binding: RawVideoFrameBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.raw_video_frame, parent, false)
        val height = parent.measuredHeight
        val width = parent.measuredWidth
        Width = width
        return View_holder(binding)

//        val view: View =
//            LayoutInflater.from(parent.context).inflate(R.layout.raw_video_frame, parent, false)
//        val height = parent.measuredHeight
//        val width = parent.measuredWidth
//        Width = width
////        view.layoutParams = RecyclerView.LayoutParams(width, height)
//        return View_holder(view)
    }

    override fun onBindViewHolder(holder: View_holder, position: Int) {
        try {
            var mVideoesModel = moArrayList[position]

            holder.lVideoesModelSelected = mVideoesModel

            holder.DB_RawVideoFrameBinding.txtVideoTitle.text = mVideoesModel.title
            holder.DB_RawVideoFrameBinding.txtPoints.text = mVideoesModel.credit
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

    override fun getItemCount(): Int {
        return moArrayList.size
    }

    inner class View_holder(itemBinding: RawVideoFrameBinding) : RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {
        var lVideoesModelSelected: Videos_Model.VideoData? = null

        lateinit var DB_RawVideoFrameBinding: RawVideoFrameBinding
        init {
            try {
                DB_RawVideoFrameBinding = itemBinding
                DB_RawVideoFrameBinding.idFrameconstraintX.setOnClickListener(this)

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

        override fun onClick(view: View?) {try{
            if (view == DB_RawVideoFrameBinding.idFrameconstraintX) {
                ApplicationClass.mVideoesModelSelected = lVideoesModelSelected
                var ShowVideoIntent = Intent(moContext, VideoPlayer::class.java)
                (moContext as Video).startActivityForResult(ShowVideoIntent, Video.CompleteQuestionCall)
                (moContext as Video).overridePendingTransition(
                    R.anim.activity_in,
                    R.anim.activity_out
                )
            } } catch (NE: NullPointerException) {
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

}