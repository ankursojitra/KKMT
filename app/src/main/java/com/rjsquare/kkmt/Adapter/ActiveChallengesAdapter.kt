package com.rjsquare.kkmt.Adapter

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.rjsquare.kkmt.Activity.Challenges.ActiveChallenge
import com.rjsquare.kkmt.Activity.Challenges.Challenge_info
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.Model.ActiveChallengesModel
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.RawChallengesFrameBinding

class ActiveChallengesAdapter(
    var moContext: Context,
    var moArrayList: ArrayList<ActiveChallengesModel>
) : RecyclerView.Adapter<ActiveChallengesAdapter.View_holder>() {
    var mActiveChallengesModel: ActiveChallengesModel? = null
    var Width = 0
    private var layoutInflater: LayoutInflater? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): View_holder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val binding: RawChallengesFrameBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.raw_challenges_frame, parent, false)
        val height = parent.measuredHeight
        val width = parent.measuredWidth
        Width = width
        return View_holder(binding)

//        val view: View =
//            LayoutInflater.from(parent.context)
//                .inflate(R.layout.raw_challenges_frame, parent, false)
//
////        view.layoutParams = RecyclerView.LayoutParams(width, height)
//        return View_holder(view)
    }

    override fun onBindViewHolder(holder: View_holder, position: Int) {
        try {
            var mActiveChallengesModel = moArrayList[position]
            var Per_Value_old = 0.0
            val mActiveChallengesModel_old: ActiveChallengesModel
            holder.lActiveChallengesModelSelected = mActiveChallengesModel
//            holder.mImg1.visibility = View.VISIBLE
//            holder.mImg2.visibility = View.VISIBLE
//            holder.mImg3.visibility = View.VISIBLE
//            holder.mTxt1.visibility = View.VISIBLE
//            holder.mTxt2.visibility = View.VISIBLE
//            holder.mTxt3.visibility = View.VISIBLE
//            holder.mTxt2.text="Check-in into 1 location"
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

    inner class View_holder(itemBinding: RawChallengesFrameBinding) :
        RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        lateinit var DB_RawChallengesFrameBinding: RawChallengesFrameBinding

//        lateinit var mIdFrameconstraint: ConstraintLayout
//        lateinit var mImg1: ImageView
//        lateinit var mImg2: ImageView
//        lateinit var mImg3: ImageView
//        lateinit var mTxt1: TextView
//        lateinit var mTxt2: TextView
//        lateinit var mTxt3: TextView

        var lActiveChallengesModelSelected: ActiveChallengesModel? = null

        init {
            try {
                DB_RawChallengesFrameBinding = itemBinding

//               DB_RawChallengesFrameBinding.img1 mImg1 = itemView.findViewById<ImageView>(R.id.img_1)
//               DB_RawChallengesFrameBinding.img2 mImg2 = itemView.findViewById<ImageView>(R.id.img_2)
//               DB_RawChallengesFrameBinding.img3 mImg3 = itemView.findViewById<ImageView>(R.id.img_3)
//               DB_RawChallengesFrameBinding.txt1 mTxt1 = itemView.findViewById<TextView>(R.id.txt_1)
//               DB_RawChallengesFrameBinding.txt2 mTxt2 = itemView.findViewById<TextView>(R.id.txt_2)
//               DB_RawChallengesFrameBinding.txt3 mTxt3 = itemView.findViewById<TextView>(R.id.txt_3)

//                mIdFrameconstraint =
//                    itemView.findViewById<ConstraintLayout>(R.id.id_frameconstraintX)
                DB_RawChallengesFrameBinding.idFrameconstraintX.setOnClickListener(this)


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
                if (System.currentTimeMillis() < ApplicationClass.lastClick) return else {
                    ApplicationClass.lastClick =
                        System.currentTimeMillis() + ApplicationClass.clickInterval
                    if (view == DB_RawChallengesFrameBinding.idFrameconstraintX) {
                        var ChallengesInfoIntent = Intent(moContext, Challenge_info::class.java)
                        moContext.startActivity(ChallengesInfoIntent)
                        (moContext as ActiveChallenge).overridePendingTransition(
                            R.anim.activity_in,
                            R.anim.activity_out
                        )
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

}