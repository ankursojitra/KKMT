package com.rjsquare.kkmt.Adapter

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.rjsquare.kkmt.Activity.Challenges.Challenge_info
import com.rjsquare.kkmt.Activity.Challenges.Challenges
import com.rjsquare.kkmt.AppConstant.GlobalUsage
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.OTPCall.ChallangesModel
import com.rjsquare.kkmt.databinding.RawChallengesFrameBinding

class ChallengesAdapter(
    var moContext: Context,
    var moArrayList: ArrayList<ChallangesModel.Challange>
) : RecyclerView.Adapter<ChallengesAdapter.View_holder>() {
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
//        val height = parent.measuredHeight
//        val width = parent.measuredWidth
//        Width = width
////        view.layoutParams = RecyclerView.LayoutParams(width, height)
//        return View_holder(view)
    }

    override fun onBindViewHolder(holder: View_holder, position: Int) {
        try {
            var mChallengesModel = moArrayList[position]
            holder.lChallengesModelSelected = mChallengesModel

            if (!mChallengesModel.sub_challanges.isNullOrEmpty()) {
                holder.DB_RawChallengesFrameBinding.txt1.text =
                    mChallengesModel.sub_challanges!![0].title
                holder.DB_RawChallengesFrameBinding.txt1.visibility = View.VISIBLE
                holder.DB_RawChallengesFrameBinding.img1.visibility = View.VISIBLE

                if (mChallengesModel.sub_challanges!!.size > 1) {
                    holder.DB_RawChallengesFrameBinding.txt2.text = mChallengesModel.sub_challanges!![1].title
                    holder.DB_RawChallengesFrameBinding.txt2.visibility = View.VISIBLE
                    holder.DB_RawChallengesFrameBinding.img2.visibility = View.VISIBLE
                } else {
                    holder.DB_RawChallengesFrameBinding.txt2.visibility = View.GONE
                    holder.DB_RawChallengesFrameBinding.img2.visibility = View.GONE
                }
                if (mChallengesModel.sub_challanges!!.size > 2) {
                    holder.DB_RawChallengesFrameBinding.txt3.text = mChallengesModel.sub_challanges!![2].title
                    holder.DB_RawChallengesFrameBinding.txt3.visibility = View.VISIBLE
                    holder.DB_RawChallengesFrameBinding.img3.visibility = View.VISIBLE
                } else {
                    holder.DB_RawChallengesFrameBinding.txt3.visibility = View.GONE
                    holder.DB_RawChallengesFrameBinding.img3.visibility = View.GONE
                }
            }
            holder.DB_RawChallengesFrameBinding.txtCredit.text = mChallengesModel.total_credit

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
//        lateinit var mTxt1: TextView
//        lateinit var mTxt2: TextView
//        lateinit var mTxt3: TextView
//        lateinit var mImg1: ImageView
//        lateinit var mImg2: ImageView
//        lateinit var mImg3: ImageView
//        private lateinit var mTxtPrice: TextView
//        private lateinit var mIdFrameconstraintX: ConstraintLayout

        lateinit var DB_RawChallengesFrameBinding: RawChallengesFrameBinding

        var lChallengesModelSelected: ChallangesModel.Challange? = null

        init {
            try {

                DB_RawChallengesFrameBinding = itemBinding

//                mTxt1 = itemView.findViewById<TextView>(R.id.txt_1)
//                mTxt2 = itemView.findViewById<TextView>(R.id.txt_2)
//                mTxt3 = itemView.findViewById<TextView>(R.id.txt_3)
//
//                mImg1 = itemView.findViewById<ImageView>(R.id.img_1)
//                mImg2 = itemView.findViewById<ImageView>(R.id.img_2)
//                mImg3 = itemView.findViewById<ImageView>(R.id.img_3)
//
//                mTxtPrice = itemView.findViewById<TextView>(R.id.txt_price)
//                mIdFrameconstraintX =
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
                if (System.currentTimeMillis() < GlobalUsage.lastClick) return else {
                    GlobalUsage.lastClick =
                        System.currentTimeMillis() + GlobalUsage.clickInterval
                    if (view == DB_RawChallengesFrameBinding.idFrameconstraintX) {
                        var ChallengesInfoIntent = Intent(moContext, Challenge_info::class.java)
                        moContext.startActivity(ChallengesInfoIntent)
                        (moContext as Challenges).overridePendingTransition(
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