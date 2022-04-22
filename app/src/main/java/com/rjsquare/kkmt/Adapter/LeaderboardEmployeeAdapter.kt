package com.rjsquare.kkmt.Adapter

import android.content.ActivityNotFoundException
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.Model.LeaderBoardModel
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.Leaderboard.EmpInfo
import com.rjsquare.kkmt.databinding.RawLeaderboardFrameBinding
import com.squareup.picasso.Picasso

class LeaderboardEmployeeAdapter(
    var moContext: Context,
    var moArrayList: ArrayList<EmpInfo>
) : RecyclerView.Adapter<LeaderboardEmployeeAdapter.View_holder>() {
    var mLeaderBoardModel: LeaderBoardModel? = null
    var Width = 0
    private var layoutInflater: LayoutInflater? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): View_holder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val binding: RawLeaderboardFrameBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.raw_leaderboard_frame, parent, false)
        val height = parent.measuredHeight
        val width = parent.measuredWidth
        Width = width
        return View_holder(binding)


//        val DB_RawLeaderboardFrameBinding = RawLeaderboardFrameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        val view: View =DB_RawLeaderboardFrameBinding.root
////            LayoutInflater.from(parent.context)
////                .inflate(R.layout.raw_leaderboard_frame, parent, false)
//        val height = parent.measuredHeight
//        val width = parent.measuredWidth
//        Width = width
//        return View_holder(view)
    }

    override fun onBindViewHolder(holder: View_holder, position: Int) {
        try {
            var mLeaderBoardModel = moArrayList[position]
            var Per_Value_old = 0.0
            val mLeaderBoardModel_old: LeaderBoardModel
            holder.employeeInfoSelected = mLeaderBoardModel
            holder.DB_RawLeaderboardFrameBinding.txtName.text =
                holder.employeeInfoSelected!!.username
            holder.DB_RawLeaderboardFrameBinding.txtCredits.text =
                holder.employeeInfoSelected!!.credit
            holder.DB_RawLeaderboardFrameBinding.txtRank.text = holder.employeeInfoSelected!!.rank

            Picasso.with(moContext).load(holder.employeeInfoSelected!!.userimage)
                .placeholder(R.drawable.ic_expe_logo)
                .into(holder.DB_RawLeaderboardFrameBinding.imgProfile)
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
//        Log.e("TAG", "SizeOfLeaderBoard : " + moArrayList.size)
        return moArrayList.size
    }

    inner class View_holder(itemBinding: RawLeaderboardFrameBinding) :
        RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        private lateinit var mImgNotify: ImageView
        private lateinit var mTxtNotifyTitle: TextView
        private lateinit var mTxtNotifyAmt: TextView
        private lateinit var mCntAmt: ConstraintLayout
        private lateinit var mIdFrameconstraint: ConstraintLayout

        var employeeInfoSelected: EmpInfo? = null

        lateinit var DB_RawLeaderboardFrameBinding: RawLeaderboardFrameBinding

        init {
            try {
                DB_RawLeaderboardFrameBinding = itemBinding
//                mIdFrameconstraint =
//                    itemView.findViewById<ConstraintLayout>(R.id.id_frameconstraint)
//                mIdFrameconstraint.setOnClickListener(this)

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
                    if (view == mIdFrameconstraint) {
                        Toast.makeText(moContext, "Comming soon...", Toast.LENGTH_SHORT).show()
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