package com.rjsquare.kkmt.Fragment

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.rjsquare.kkmt.Activity.Events.Events
import com.rjsquare.kkmt.Activity.HomeActivity
import com.rjsquare.kkmt.Activity.LuckyDraw.LuckyDraw
import com.rjsquare.kkmt.Activity.Notifications.NotificationList
import com.rjsquare.kkmt.Activity.Profile.Profile
import com.rjsquare.kkmt.Activity.Store.Store
import com.rjsquare.kkmt.Activity.Video.Video
import com.rjsquare.kkmt.Activity.commanUtils
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.FragmentHomeBinding
import com.squareup.picasso.Picasso


class Home : Fragment(), View.OnClickListener {
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
            reqActivity = requireActivity()
            DB_FHome.cardViewStore.setOnClickListener(this)
            DB_FHome.cardViewEvent.setOnClickListener(this)
            DB_FHome.cardViewNotify.setOnClickListener(this)
            DB_FHome.cardViewLuckydraw.setOnClickListener(this)
            DB_FHome.cardViewVideos.setOnClickListener(this)
            DB_FHome.crdProfile.setOnClickListener(this)
            DB_FHome.txtLogin.setOnClickListener(this)

            SetupUserLogInViewAndData()
            SetUpUserVerified()

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
            ApplicationClass.isHomeScreenVisible = true
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


    companion object {

        lateinit var reqActivity:Activity
        lateinit var DB_FHome: FragmentHomeBinding
        var HomeView = false

        fun UserInfoUpdateUI() {
            SetupUserLogInViewAndData()
            SetUpUserVerified()

        }

        private fun SetUpUserVerified() {
            if (ApplicationClass.isApprove) {
                DB_FHome.imgVerified.setImageDrawable(
                    ContextCompat.getDrawable(
                        reqActivity,
                        R.drawable.ic_verified
                    )
                )
                DB_FHome.txtVerified.text = reqActivity.getString(R.string.verified)

            } else {
                DB_FHome.imgVerified.setImageDrawable(
                    ContextCompat.getDrawable(
                        reqActivity,
                        R.drawable.ic_nonverified
                    )
                )
                DB_FHome.txtVerified.text = reqActivity.getString(R.string.unverified)
            }
        }

        private fun SetupUserLogInViewAndData() {
            if (ApplicationClass.userLogedIn) {
                DB_FHome.cntLogin.visibility = View.GONE
                DB_FHome.cntProfileView.visibility = View.VISIBLE
                var UserImage = ""
                if (ApplicationClass.userInfoModel.data!!.userimage != null
                    && !ApplicationClass.userInfoModel.data!!.userimage.equals("")
                ) {
                    UserImage = ApplicationClass.userInfoModel.data!!.userimage!!
                    Picasso.with(reqActivity).load(UserImage)
                        .placeholder(R.drawable.expe_logo).into(DB_FHome.imgProfile)
                }
                DB_FHome.txtProfName.text = ApplicationClass.userInfoModel.data!!.username

                DB_FHome.txtLevel.text =
                    "Level : " + ApplicationClass.userInfoModel.data!!.credit_details!!.level

                DB_FHome.txtTotalCredits.text =
                    commanUtils.formatNumber(ApplicationClass.userInfoModel.data!!.credit_details!!.credit!!.toInt())


            } else {
                DB_FHome.cntLogin.visibility = View.VISIBLE
                DB_FHome.cntProfileView.visibility = View.GONE
            }
        }


        @JvmStatic
        fun newInstance() =
            Home().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onClick(view: View?) {
        try {
            if (System.currentTimeMillis() < ApplicationClass.lastClick) return else {
                ApplicationClass.lastClick =
                    System.currentTimeMillis() + ApplicationClass.clickInterval
                if (ApplicationClass.userLogedIn) {
                    if (view == DB_FHome.cardViewStore) {
                        commanUtils.NextScreen(
                            requireActivity(),
                            Intent(requireActivity(), Store::class.java)
                        )
                    } else if (view == DB_FHome.cardViewEvent) {
                        commanUtils.NextScreen(
                            requireActivity(),
                            Intent(requireActivity(), Events::class.java)
                        )
                    } else if (view == DB_FHome.cardViewNotify) {
                        ApplicationClass.NextScreen(
                            requireActivity(),
                            Intent(requireActivity(), NotificationList::class.java)
                        )
                    } else if (view == DB_FHome.cardViewLuckydraw) {
                        commanUtils.NextScreen(
                            requireActivity(),
                            Intent(requireActivity(), LuckyDraw::class.java)
                        )
                    } else if (view == DB_FHome.cardViewVideos) {
                        commanUtils.NextScreen(
                            requireActivity(),
                            Intent(requireActivity(), Video::class.java)
                        )
                    } else if (view == DB_FHome.crdProfile) {
                        commanUtils.NextScreen(
                            requireActivity(),
                            Intent(requireActivity(), Profile::class.java)
                        )
                    }
                } else {
                    if (view == DB_FHome.cardViewEvent) {
                        commanUtils.NextScreen(
                            requireActivity(),
                            Intent(requireActivity(), Events::class.java)
                        )
                    } else if (view == DB_FHome.cardViewStore) {
                        commanUtils.NextScreen(
                            requireActivity(),
                            Intent(requireActivity(), Store::class.java)
                        )
                    } else if (view == DB_FHome.txtLogin) {
                        ApplicationClass.UserLogIn(requireActivity())
                    }
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