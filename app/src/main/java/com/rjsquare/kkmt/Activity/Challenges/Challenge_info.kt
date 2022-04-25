package com.rjsquare.kkmt.Activity.Challenges

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

import com.rjsquare.kkmt.AppConstant.GlobalUsage
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.ActivityChallengeInfoBinding

class Challenge_info : AppCompatActivity(), View.OnClickListener {
    private lateinit var mImgBack: ImageView
    lateinit var DB_ChallengeInfo: ActivityChallengeInfoBinding
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_ChallengeInfo = DataBindingUtil.setContentView(this, R.layout.activity_challenge_info)
//        setContentView(R.layout.activity_challenge_info)
        GlobalUsage.StatusTextWhite(this, true)

        try {
            DB_ChallengeInfo.imgBack.setOnClickListener(this)
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

    override fun onClick(v: View?) {
        try {
            if (v == DB_ChallengeInfo.imgBack) {
                onBackPressed()
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