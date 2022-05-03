package com.rjsquare.kkmt.Activity.Video

import android.R.attr.bitmap
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.MediaMetadataRetriever
import android.media.ThumbnailUtils
import android.os.Bundle
import android.provider.MediaStore.Video.Thumbnails.MINI_KIND
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener
import com.google.gson.Gson
import com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable.ApiCallingInstance
import com.rjsquare.kkmt.Activity.Dialog.Alert
import com.rjsquare.kkmt.Activity.Dialog.Loader
import com.rjsquare.kkmt.Activity.Dialog.Network
import com.rjsquare.kkmt.AppConstant.Constants
import com.rjsquare.kkmt.AppConstant.GlobalUsage
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.Events.NetworkServices
import com.rjsquare.kkmt.RetrofitInstance.Events.VideoDetail_Model
import com.rjsquare.kkmt.databinding.ActivityVideoPlayerBinding
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Matcher
import java.util.regex.Pattern


class VideoPlayer : YouTubeBaseActivity(), View.OnClickListener,
    YouTubePlayer.OnInitializedListener {
    private var playWhenReady = false
    private var currentWindow = 0
    private var playbackPosition = 0L
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

//            GlobalUsage.SetLayoutHeight(DB_VideoPlayer.VideoPlayer, playerHeight.toInt())

            Picasso.with(this).load("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4")
                .into(object : com.squareup.picasso.Target {
                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                        Log.e("TAG", "onBitmapLoaded : ")
//                        val d: Drawable = BitmapDrawable(attr.bitmap)

                    }

                    override fun onBitmapFailed(errorDrawable: Drawable?) {
                        Log.e("TAG", "onBitmapFailed : ")

                    }

                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                        Log.e("TAG", "onPrepareLoad : ")

                    }

                })

           var player = SimpleExoPlayer.Builder(this)
                .build()
                .also { exoPlayer ->
                    DB_VideoPlayer.videoView.player = exoPlayer
                    val mediaItem = MediaItem.fromUri("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4")
                    exoPlayer.setMediaItem(mediaItem)
                    exoPlayer.playWhenReady = playWhenReady
                    exoPlayer.seekTo(currentWindow, playbackPosition)
                    exoPlayer.prepare()
                }

            try {
                val bitmap = ThumbnailUtils.createVideoThumbnail("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4", MINI_KIND);
//                    retriveVideoFrameFromVideo("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4")
                if (bitmap != null) {
//                    DB_VideoPlayer.videoView.defaultArtwork = BitmapDrawable(bitmap)
                    DB_VideoPlayer.videoView.defaultArtwork = ContextCompat.getDrawable(this,R.drawable.expe_logo)
                }
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
            }
//            try {
//                val mediacontroller = MediaController(this)
//                mediacontroller.setAnchorView(DB_VideoPlayer.VideoPlayer)
//                val video: Uri =
////                    Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4")
//                    Uri.parse("https://www.youtube.com/watch?v=QnOcXQL2wDA&t=18s")
//                DB_VideoPlayer.VideoPlayer.setMediaController(mediacontroller)
//                DB_VideoPlayer.VideoPlayer.setVideoURI(video)
//            } catch (e: java.lang.Exception) {
//                Log.e("Error", e.message!!)
//                e.printStackTrace()
//            }
//
//            DB_VideoPlayer.VideoPlayer.requestFocus()
//            DB_VideoPlayer.VideoPlayer.setOnPreparedListener(OnPreparedListener {
////                this@VideoPlayer.pDialog.dismiss()
//                DB_VideoPlayer.VideoPlayer.start()
//
//            })
//            DB_VideoPlayer.VideoPlayer.setOnCompletionListener(OnCompletionListener {
//                finish()
//            })

//            DB_VideoPlayer.andExoPlayerView.setSource("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4")
//            DB_VideoPlayer.andExoPlayerView.setPlayWhenReady(true)

//            val youTubePlayerView = findViewById<View>(R.id.youtube_player) as YouTubePlayerView

//            DB_VideoPlayer.youtubePlayerView.setvi
//            getLifecycle().addObserver(DB_VideoPlayer.youtubePlayerView)
//            DB_VideoPlayer.youtubePlayerView.lis(object : AbstractYouTubePlayerListener() {
//                override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
//                    val videoId = "J1rOfVst-EQ"
//                    youTubePlayer.loadVideo(videoId, 0f)
//                }
//            })

            DB_VideoPlayer.imgVideoCredit.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_video_credit
                )
            )
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

    @Throws(Throwable::class)
    fun retriveVideoFrameFromVideo(videoPath: String?): Bitmap? {
        var bitmap: Bitmap? = null
        var mediaMetadataRetriever: MediaMetadataRetriever? = null
        try {
            mediaMetadataRetriever = MediaMetadataRetriever()
            mediaMetadataRetriever.setDataSource(videoPath, HashMap())
            //   mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.frameAtTime
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            throw Throwable("Exception in retriveVideoFrameFromVideo(String videoPath)" + e.message)
        } finally {
            mediaMetadataRetriever?.release()
        }
        return bitmap
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
                    Log.e("TAG", "URLPLAYER : " + Gson().toJson(response.body()))
                    Loader.hideLoader()
                    if (response.body()!!.status.equals(Constants.ResponseSucess)) {
                        VideoData = response.body()!!.data!!
                        setUpUI()
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

    private fun setUpUI() {
        Log.e("TAG", "CHEcKURI : " + VideoData.video)
//        if (VideoData.video!!.contains("youtube.com", true)
//            || VideoData.video!!.contains("youtu.be", true)
//        ) {
//            Log.e("TAG", "Do CHEcKURI : " + VideoData.video)
//            DB_VideoPlayer.youtubePlayerView.initialize(getString(R.string.youtubeAPIKey), this)
//            DB_VideoPlayer.youtubePlayerView.visibility = View.VISIBLE
//            DB_VideoPlayer.videoView.visibility = View.GONE
//        } else {

            Log.e("TAG", "Else CHEcKURI : " + VideoData.video)
            VideoData.video =
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
            Picasso.with(this).load(VideoData.video)
                .into(object : com.squareup.picasso.Target {
                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                        Log.e("TAG", "onBitmapLoaded : ")
//                        val d: Drawable = BitmapDrawable(attr.bitmap)
                        DB_VideoPlayer.videoView.defaultArtwork = BitmapDrawable(bitmap)
                    }

                    override fun onBitmapFailed(errorDrawable: Drawable?) {
                        Log.e("TAG", "onBitmapFailed : ")

                    }

                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                        Log.e("TAG", "onPrepareLoad : ")

                    }

                })

            DB_VideoPlayer.youtubePlayerView.visibility = View.GONE
            DB_VideoPlayer.videoView.visibility = View.VISIBLE

//            http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4
            if (!VideoData.video.contentEquals("http://", true)) {
//                DB_VideoPlayer.andExoPlayerView.setSource("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4")
//                DB_VideoPlayer.andExoPlayerView.setSource(VideoData.video)
            } else {
                Alert.showDialog(this, "There is an issue on video, Please try again leter.")
            }
//        }

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

    private val playbackEventListener: PlaybackEventListener = object : PlaybackEventListener {
        override fun onBuffering(arg0: Boolean) {}
        override fun onPaused() {}
        override fun onPlaying() {}
        override fun onSeekTo(arg0: Int) {}
        override fun onStopped() {}
    }

    private val playerStateChangeListener: PlayerStateChangeListener =
        object : PlayerStateChangeListener {
            override fun onAdStarted() {}
            override fun onError(arg0: YouTubePlayer.ErrorReason?) {}
            override fun onLoaded(arg0: String) {}
            override fun onLoading() {}
            override fun onVideoEnded() {}
            override fun onVideoStarted() {}
        }

    fun getYoutubeVideoId(ytUrl: String?): String? {
        var vId: String? = null
        val pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*"
        val compiledPattern: Pattern = Pattern.compile(pattern)
        val matcher: Matcher = compiledPattern.matcher(ytUrl)
        if (matcher.find()) {
            vId = matcher.group()
        }
        return vId
    }

    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        player: YouTubePlayer?,
        wasRestored: Boolean
    ) {
        Log.e(
            "TAG",
            "YouTubePlayer.onInitializationSuccess : " + getYoutubeVideoId(VideoData.video)
        )
        player!!.setPlayerStateChangeListener(playerStateChangeListener)
        player.setPlaybackEventListener(playbackEventListener)

        /** Start buffering **/
        if (!wasRestored) {

            player.cueVideo(getYoutubeVideoId(VideoData.video))
//            player.cueVideo(VideoData.video)
        }
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {
        Log.e("TAG", "YouTubePlayer.onInitializationFailure")
    }
}