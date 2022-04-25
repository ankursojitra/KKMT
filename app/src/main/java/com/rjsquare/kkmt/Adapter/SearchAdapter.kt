package com.rjsquare.kkmt.Adapter

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.rjsquare.kkmt.Activity.HomeActivity
import com.rjsquare.kkmt.AppConstant.GlobalUsage
import com.rjsquare.kkmt.Fragment.search
import com.rjsquare.kkmt.Model.SearchModel
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.RawSearchFrameBinding

class SearchAdapter(
    var moContext: Context,
    var moArrayList: ArrayList<SearchModel>
) : RecyclerView.Adapter<SearchAdapter.View_holder>() {
    var mSearchModel: SearchModel? = null
    var Width = 0
    private var layoutInflater: LayoutInflater? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): View_holder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val binding: RawSearchFrameBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.raw_search_frame, parent, false)
        val height = parent.measuredHeight
        val width = parent.measuredWidth
        Width = width
        return View_holder(binding)

//        val view: View =
//            LayoutInflater.from(parent.context).inflate(R.layout.raw_search_frame, parent, false)
//        val height = view.measuredHeight
//
//        HomeActivity.YposKeyOn =
//            ((HomeActivity.height - HomeActivity.KeyboardHeight))
//        HomeActivity.YposKeyOff = (HomeActivity.height.toFloat())
//
//        return View_holder(view)
    }

    override fun onBindViewHolder(holder: View_holder, position: Int) {
        try {
            var mSearchModel = moArrayList[position]
            var Per_Value_old = 0.0
            val mSearchModel_old: SearchModel
            holder.lSearchModelSelected = mSearchModel
            holder.DB_RawSearchFrameBinding.idFrameconstraint.background = mSearchModel.BackColor
            holder.DB_RawSearchFrameBinding.imgSearch.setImageDrawable(mSearchModel.ImgLink)
            holder.DB_RawSearchFrameBinding.txtSearchTitle.text = mSearchModel.TxtTitle
            holder.DB_RawSearchFrameBinding.txtSearchAdd.text = mSearchModel.TxtAddress


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

    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager: InputMethodManager = activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus!!.windowToken, 0
        )
    }

    inner class View_holder(itemBinding: RawSearchFrameBinding) :
        RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {


        //        lateinit var mImgSearch: RoundedImageView
//        lateinit var mTxtSearchTitle: TextView
//        lateinit var mTxtSearchAdd: TextView
//        lateinit var mIdFrameconstraint: ConstraintLayout
        var lSearchModelSelected: SearchModel? = null
        lateinit var DB_RawSearchFrameBinding: RawSearchFrameBinding

        init {
            try {
                DB_RawSearchFrameBinding = itemBinding
//                mImgSearch = itemView.findViewById<RoundedImageView>(R.id.img_search)
//                mTxtSearchTitle = itemView.findViewById<TextView>(R.id.txt_search_title)
//                mTxtSearchAdd = itemView.findViewById<TextView>(R.id.txt_search_add)
//                mIdFrameconstraint =
//                    itemView.findViewById<ConstraintLayout>(R.id.id_frameconstraint)
                DB_RawSearchFrameBinding.idFrameconstraint.setOnClickListener(this)

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
                    if (view == DB_RawSearchFrameBinding.idFrameconstraint) {
                        GlobalUsage.mSearchModelSelected = lSearchModelSelected
                        if (HomeActivity.KeyBoardOpen) hideSoftKeyboard(moContext as HomeActivity)
                        search.DB_FSearch.edtSearchbar.clearFocus()
                        search.PrepareBottomSheetView()
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