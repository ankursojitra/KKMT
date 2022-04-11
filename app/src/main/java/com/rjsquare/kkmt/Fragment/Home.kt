package com.rjsquare.kkmt.Fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.rjsquare.kkmt.Activity.Events.Events
import com.rjsquare.kkmt.Activity.HomeActivity
import com.rjsquare.kkmt.Activity.LuckyDraw.LuckyDraw
import com.rjsquare.kkmt.Activity.Notifications.NotificationList
import com.rjsquare.kkmt.Activity.Profile.Profile
import com.rjsquare.kkmt.Activity.Store.Store
import com.rjsquare.kkmt.Activity.Video.Video
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.FragmentHomeBinding

class Home : Fragment(), View.OnClickListener {
    lateinit var DB_FHome: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        DB_FHome = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
//        var rootView = inflater.inflate(R.layout.fragment_home, container, false)
        try {
            DB_FHome.cardViewStore.setOnClickListener(this)
            DB_FHome.cardViewEvent.setOnClickListener(this)
            DB_FHome.cardViewNotify.setOnClickListener(this)
            DB_FHome.cardViewLuckydraw.setOnClickListener(this)
            DB_FHome.cardViewVideos.setOnClickListener(this)
            DB_FHome.crdProfile.setOnClickListener(this)

            HomeActivity.mCntLoader.visibility = View.GONE

            if (ApplicationClass.isUserEmployee) {
                DB_FHome.txtStoreLbl.text = "GREAT SERVICE DESERVES REWARDS.\n" +
                        "REDEEM CREDITS FOR GOODS & SERVICES"
                DB_FHome.txtEventMsg.text = "YOU’RE INVITED"
                DB_FHome.txtNotifyMsg.text = "PERSONALIZED JUST FOR YOU"
                DB_FHome.txtLuckydrawMsg.text = "SPIN TO WIN FREE CREDITS"
                DB_FHome.txtVideosMsg.text = "EXPAND YOUR KNOWLEDGE"
                DB_FHome.txtVideos.text = "Training Videos"
            } else {
                DB_FHome.txtStoreLbl.text = "YOUR FEEDBACK MATTERS.\n" +
                        "REDEEM CREDITS FOR GOODS & SERVICES"
                DB_FHome.txtEventMsg.text = "YOU’RE INVITED"
                DB_FHome.txtNotifyMsg.text = "PERSONALIZED JUST FOR YOU"
                DB_FHome.txtLuckydrawMsg.text = "SPIN TO WIN FREE CREDITS"
                DB_FHome.txtVideosMsg.text = "EXPAND YOUR KNOWLEDGE"
                DB_FHome.txtVideos.text = "Videos"
            }

            SetUpHomeUIData()
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
        HomeView = true
        return DB_FHome.root
    }

    private fun SetUpHomeUIData() {
        DB_FHome.txtLevel.text = "Level : "+ ApplicationClass.userInfoModel.data!!.credit_details!!.level
        DB_FHome.txtTotalCredits.text = ApplicationClass.userInfoModel.data!!.credit_details!!.credit
        DB_FHome.txtVerified.text = if (ApplicationClass.userInfoModel.data!!.approve.equals("yes",true)) "Verified" else "Unverified"
        DB_FHome.txtProfName.text = ApplicationClass.userInfoModel.data!!.username
    }

    companion object {

        var HomeView = false

        @JvmStatic
        fun newInstance() =
            Home().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onClick(view: View?) {
        try {
            if (view == DB_FHome.cardViewStore) {
                var StoreIntent = Intent(requireActivity(), Store::class.java)
                startActivity(StoreIntent)
                requireActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
            } else if (view == DB_FHome.cardViewEvent) {
                var EventIntent = Intent(requireActivity(), Events::class.java)
                startActivity(EventIntent)
                requireActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
            } else if (view == DB_FHome.cardViewNotify) {
                var NotifyIntent = Intent(requireActivity(), NotificationList::class.java)
                startActivity(NotifyIntent)
                requireActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
            } else if (view == DB_FHome.cardViewLuckydraw) {
                var LuckyDrawIntent = Intent(requireActivity(), LuckyDraw::class.java)
                startActivity(LuckyDrawIntent)
                requireActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
            } else if (view == DB_FHome.cardViewVideos) {
                var VideosIntent = Intent(requireActivity(), Video::class.java)
                startActivity(VideosIntent)
                requireActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
            } else if (view == DB_FHome.crdProfile) {
                var VideosIntent = Intent(requireActivity(), Profile::class.java)
                startActivity(VideosIntent)
                requireActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
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