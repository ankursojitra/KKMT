package com.rjsquare.kkmt.Activity.Challenges

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.rjsquare.kkmt.Adapter.ChallengesAdapter
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.Model.ChallengesModel
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.ActivityChallengesBinding

class Challenges : AppCompatActivity(), View.OnClickListener {
    private lateinit var mImgBack: ImageView
    private lateinit var mRrChallenges: RecyclerView
    private lateinit var mTxtNoChallenges: TextView
    lateinit var mArray_ChallengesModel: ArrayList<ChallengesModel>
    private lateinit var mTxtDaily: TextView
    private lateinit var mLlDaily: LinearLayout
    private lateinit var mTxtWeekly: TextView
    private lateinit var mLlWeekly: LinearLayout
    private lateinit var mTxtMonthly: TextView
    private lateinit var mLlMonthly: LinearLayout
    lateinit var DB_Challenges: ActivityChallengesBinding

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_Challenges = DataBindingUtil.setContentView(this, R.layout.activity_challenges)
//        setContentView(R.layout.activity_challenges)
        try {
            ApplicationClass.StatusTextWhite(this, true)

            mImgBack = findViewById<ImageView>(R.id.img_back)
            mRrChallenges = findViewById<RecyclerView>(R.id.rr_challenges)
            mTxtNoChallenges = findViewById<TextView>(R.id.txt_no_challenges)
            mTxtDaily = findViewById<TextView>(R.id.txt_daily)
            mLlDaily = findViewById<LinearLayout>(R.id.ll_daily)
            mTxtWeekly = findViewById<TextView>(R.id.txt_weekly)
            mLlWeekly = findViewById<LinearLayout>(R.id.ll_weekly)
            mTxtMonthly = findViewById<TextView>(R.id.txt_monthly)
            mLlMonthly = findViewById<LinearLayout>(R.id.ll_monthly)

            mArray_ChallengesModel = ArrayList()


            DB_Challenges.imgBack.setOnClickListener(this)
            DB_Challenges.txtDaily.setOnClickListener(this)
            DB_Challenges.txtWeekly.setOnClickListener(this)
            DB_Challenges.txtMonthly.setOnClickListener(this)

            fillData()
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

    private fun fillData() {
        try {
            var mChallengesModel = ChallengesModel()
            mChallengesModel.Txt1 = "Watch 3 Videos"
            mChallengesModel.Txt2 = "Check-in into 1 location"
            mChallengesModel.Txt3 = "Leave 1 Verified Reviews"
            mArray_ChallengesModel.add(mChallengesModel)

            var mChallengesModelx = ChallengesModel()
            mChallengesModelx.Txt1 = ""
            mChallengesModelx.Txt2 = "Complete 1 Survey"
            mChallengesModelx.Txt3 = ""
            mArray_ChallengesModel.add(mChallengesModelx)
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

    fun framesAdapter() {
        try {

            if (mArray_ChallengesModel != null && mArray_ChallengesModel.size > 0) {
                DB_Challenges.txtNoChallenges.visibility = View.GONE
            } else {
                DB_Challenges.txtNoChallenges.visibility = View.VISIBLE
            }

            val loChallengesAdapter: ChallengesAdapter
//                if (mHomeModelArrayList_old == null) {
            loChallengesAdapter = ChallengesAdapter(
                this, mArray_ChallengesModel
            )

            DB_Challenges.rrChallenges.adapter = loChallengesAdapter


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
        try {
            if (view == DB_Challenges.imgBack) {
                onBackPressed()
            } else if (view == DB_Challenges.txtDaily) {
                Toast.makeText(this, "Coming soon...", Toast.LENGTH_SHORT).show()
                DB_Challenges.txtDaily.setTextColor(ContextCompat.getColor(this, R.color.blue_dark))
                DB_Challenges.llDaily.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.blue_darklight
                    )
                )
                DB_Challenges.txtWeekly.setTextColor(ContextCompat.getColor(this, R.color.white))
                DB_Challenges.llWeekly.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.transparent
                    )
                )
                DB_Challenges.txtMonthly.setTextColor(ContextCompat.getColor(this, R.color.white))
                DB_Challenges.llMonthly.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.transparent
                    )
                )
            } else if (view == DB_Challenges.txtWeekly) {
                Toast.makeText(this, "Coming soon...", Toast.LENGTH_SHORT).show()
                DB_Challenges.txtWeekly.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.blue_dark
                    )
                )
                DB_Challenges.llWeekly.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.blue_darklight
                    )
                )
                DB_Challenges.txtDaily.setTextColor(ContextCompat.getColor(this, R.color.white))
                DB_Challenges.llDaily.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.transparent
                    )
                )
                DB_Challenges.txtMonthly.setTextColor(ContextCompat.getColor(this, R.color.white))
                DB_Challenges.llMonthly.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.transparent
                    )
                )
            } else if (view == DB_Challenges.txtMonthly) {
                Toast.makeText(this, "Coming soon...", Toast.LENGTH_SHORT).show()
                DB_Challenges.txtMonthly.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.blue_dark
                    )
                )
                DB_Challenges.llMonthly.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.blue_darklight
                    )
                )

                DB_Challenges.txtWeekly.setTextColor(ContextCompat.getColor(this, R.color.white))
                DB_Challenges.llWeekly.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.transparent
                    )
                )


                DB_Challenges.txtDaily.setTextColor(ContextCompat.getColor(this, R.color.white))
                DB_Challenges.llDaily.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.transparent
                    )
                )
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