package com.rjsquare.kkmt.Activity

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Rect
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.Menu
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.rjsquare.kkmt.Activity.Bussiness.Bussiness_Location
import com.rjsquare.kkmt.Activity.Challenges.ActiveChallenge
import com.rjsquare.kkmt.Activity.Login.Login
import com.rjsquare.kkmt.Activity.Notifications.NotificationList
import com.rjsquare.kkmt.Activity.Profile.Profile
import com.rjsquare.kkmt.Activity.Register.upload_doc
import com.rjsquare.kkmt.Activity.Setting.Settings
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.Fragment.*
import com.rjsquare.kkmt.IMMResult
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mTxtTitle: TextView
    private lateinit var mImgHomeback: ImageView

    private var heightDiff = 0
    private lateinit var mCntHomemain: ConstraintLayout

    private var wasOpened = false
    private val DefaultKeyboardDP = 100

    companion object {
        var TestVar = 0
        lateinit var thisHomeActivity: Activity
        lateinit var DB_HomeActivity: ActivityHomeBinding
        lateinit var mCrdLoader: CardView
        lateinit var mCntLoader: ConstraintLayout
        var height: Int = 0
        var ismEdtSearchbarInit = false
        lateinit var mFragmentManager: FragmentManager

        // Lollipop includes button bar in the root. Add height of button bar (48dp) to maxDiff
        var EstimatedKeyboardDP = 0f
        var width: Int = 0
        var keyBoardOn = 825f
        var keyBoardOff = 1350f
        var durationTime = 400L
        var KeyboardHeight = 0f
        var RecyclerviEWHeight = 0
        var KeyBoardOpen = false

        //Transection of Fragment
        lateinit var HomefragmentTransaction: FragmentTransaction
        lateinit var searchfragmentTransaction: FragmentTransaction
        lateinit var LeaderBoardfragmentTransaction: FragmentTransaction
        lateinit var HistoryfragmentTransaction: FragmentTransaction

        //Fragments
        var HomeFr: Fragment? = null
        var HistoryFr: Fragment? = null
        var searchFr: Fragment? = null
        var LeaderBoardFr: Fragment? = null

        fun SetUpEmployeeUI() {
            if (ApplicationClass.IsUserEmployee) {
                HistoryFr = EmployeeHistory()
            } else {
                HistoryFr = History()
            }
        }

        fun UnauthorizedUser(){
            DB_HomeActivity.cntUnAuthorized.visibility = View.VISIBLE
        }

    }

    fun isSoftKeyboardShown(
        imm: InputMethodManager,
        v: View?
    ): Boolean {
        val result = IMMResult()
        val res: Int
        imm.showSoftInput(v, 0, result)

        // if keyboard doesn't change, handle the keypress
        res = result.getResult()
        return res == InputMethodManager.RESULT_UNCHANGED_SHOWN ||
                res == InputMethodManager.RESULT_UNCHANGED_HIDDEN
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    fun setKeyboardListener() {
//        val activityRootView: View =
//            (findViewById<View>(R.id.cnt_homemain) as ViewGroup).getChildAt(0)
        DB_HomeActivity.HomeScreen.cntHomeMain.viewTreeObserver
            .addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                private val r: Rect = Rect()
                override fun onGlobalLayout() {
                    // Convert the dp to pixels.
                    val estimatedKeyboardHeight = TypedValue
                        .applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            EstimatedKeyboardDP,
                            DB_HomeActivity.HomeScreen.cntHomeMain.resources.displayMetrics
                        )
                    KeyboardHeight = estimatedKeyboardHeight
                    // Conclude whether the keyboard is shown or not.
                    DB_HomeActivity.HomeScreen.cntHomeMain.getWindowVisibleDisplayFrame(r)
                    heightDiff =
                        DB_HomeActivity.HomeScreen.cntHomeMain.rootView.height - (r.bottom - r.top)
                    val isShown: Boolean = heightDiff >= estimatedKeyboardHeight
                    wasOpened = isShown
                    if (isShown) {
                        Log.w("Keyboard state", "Ignoring global layout change...CO")
                    } else {
                        Log.e("Keyboard state", "Ignoring global layout change...OP")
                    }
                }
            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_HomeActivity = DataBindingUtil.setContentView(this, R.layout.activity_home)
        try {
            thisHomeActivity = this
            mFragmentManager = supportFragmentManager
            ApplicationClass.IsRegisterFlow = false
            EstimatedKeyboardDP =
                DefaultKeyboardDP + if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) 48f else 0f

            DB_HomeActivity.txtUnauthOk.setOnClickListener(this)
            if (!ApplicationClass.AutorisedUser){
                DB_HomeActivity.cntUnAuthorized.visibility = View.VISIBLE
            }

            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            height = displayMetrics.heightPixels
            width = displayMetrics.widthPixels

            setKeyboardListener()

            ApplicationClass.StatusTextWhite(this, true)

            mCrdLoader = findViewById<CardView>(R.id.crd_loader)
            mCntLoader = findViewById<ConstraintLayout>(R.id.cnt_loader)

            UncheckedAll()
            mTxtTitle = findViewById<TextView>(R.id.txt_title)
            mImgHomeback = findViewById<ImageView>(R.id.img_homeback)


            mCntLoader.setOnClickListener(this)
            DB_HomeActivity.HomeScreen.ContentView.cntHome.setOnClickListener(this)
            DB_HomeActivity.HomeScreen.ContentView.cntSearch.setOnClickListener(this)
            DB_HomeActivity.HomeScreen.ContentView.cntLeaderboard.setOnClickListener(this)
            DB_HomeActivity.HomeScreen.ContentView.cntHistory.setOnClickListener(this)
            DB_HomeActivity.HomeScreen.ContentView.cntStar.setOnClickListener(this)
            DB_HomeActivity.HomeScreen.ContentView.cntNewreview.setOnClickListener(this)
            DB_HomeActivity.nevigationMenuview.cntHomeMenu.setOnClickListener(this)
            DB_HomeActivity.nevigationMenuview.cntProfileMenu.setOnClickListener(this)
            DB_HomeActivity.nevigationMenuview.cntReviewsMenu.setOnClickListener(this)
            DB_HomeActivity.nevigationMenuview.cntNotificationMenu.setOnClickListener(this)
            DB_HomeActivity.nevigationMenuview.cntMyhistoryMenu.setOnClickListener(this)
            DB_HomeActivity.nevigationMenuview.cntChallangesMenu.setOnClickListener(this)
            DB_HomeActivity.nevigationMenuview.cntSettingMenu.setOnClickListener(this)
            DB_HomeActivity.nevigationMenuview.cntUploadMenu.setOnClickListener(this)
            DB_HomeActivity.nevigationMenuview.cntLogoutMenu.setOnClickListener(this)
            DB_HomeActivity.HomeScreen.ContentView.imgMenu.setOnClickListener(this)

            DB_HomeActivity.HomeScreen.ContentView.imgHome.visibility = View.INVISIBLE
            DB_HomeActivity.HomeScreen.ContentView.cntSelHome.visibility = View.VISIBLE
            mCntLoader.visibility = View.VISIBLE

            Log.e("TAG", "CheckEmployeeUser : " + ApplicationClass.IsUserEmployee)
            //setup Employee new Review
            DB_HomeActivity.HomeScreen.ContentView.cntNewreview.visibility =
                if (ApplicationClass.IsUserEmployee) View.GONE else View.VISIBLE
            DB_HomeActivity.HomeScreen.ContentView.cntSelStar.visibility =
                if (ApplicationClass.IsUserEmployee) View.GONE else View.VISIBLE
            DB_HomeActivity.nevigationMenuview.cntReviewsMenu.visibility =
                if (ApplicationClass.IsUserEmployee) View.GONE else View.VISIBLE
            DB_HomeActivity.nevigationMenuview.cntUploadMenu.visibility =
                if (ApplicationClass.IsUserEmployee) View.GONE else View.VISIBLE

            LoadFragmentsPages().execute()

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

    private fun SelectBottomTabImage() {
        DB_HomeActivity.HomeScreen.ContentView.imgHome.visibility = View.VISIBLE
        DB_HomeActivity.HomeScreen.ContentView.imgSearch.visibility = View.VISIBLE
        DB_HomeActivity.HomeScreen.ContentView.imgLeader.visibility = View.VISIBLE
        DB_HomeActivity.HomeScreen.ContentView.imgHistory.visibility = View.VISIBLE
    }

    private fun UncheckedAll() {
        DB_HomeActivity.HomeScreen.ContentView.cntSelHome.visibility = View.INVISIBLE
        DB_HomeActivity.HomeScreen.ContentView.cntSelSearch.visibility = View.INVISIBLE
        DB_HomeActivity.HomeScreen.ContentView.cntSelLeader.visibility = View.INVISIBLE
        DB_HomeActivity.HomeScreen.ContentView.cntSelHistory.visibility = View.INVISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onClick(view: View?) {
        try {
            if (view == DB_HomeActivity.HomeScreen.ContentView.cntHome || view == DB_HomeActivity.nevigationMenuview.cntHomeMenu) {
                ApplicationClass.StatusTextWhite(this, true)

                SelectBottomTabImage()
                DB_HomeActivity.HomeScreen.ContentView.imgHome.visibility = View.INVISIBLE

                UncheckedAll()
                DB_HomeActivity.HomeScreen.ContentView.cntSelHome.visibility = View.VISIBLE

                HideAllFragment()
                DB_HomeActivity.HomeScreen.ContentView.navHomeFragment.visibility = View.VISIBLE

                if (Home.HomeView) {
                    mCntLoader.visibility = View.GONE
                } else {
                    mCntLoader.visibility = View.VISIBLE
                }

                SetTitleBarView(false, resources.getString(R.string.home))
                mImgHomeback.visibility = View.VISIBLE

                if (DB_HomeActivity.drawerLayout.isDrawerOpen(GravityCompat.START))
                    DB_HomeActivity.drawerLayout.closeDrawer(GravityCompat.START)

            } else if (view == DB_HomeActivity.HomeScreen.ContentView.cntSearch) {

                ApplicationClass.StatusTextWhite(this, false)

                SelectBottomTabImage()
                DB_HomeActivity.HomeScreen.ContentView.imgSearch.visibility = View.INVISIBLE

                UncheckedAll()
                DB_HomeActivity.HomeScreen.ContentView.cntSelSearch.visibility = View.VISIBLE

                HideAllFragment()
                DB_HomeActivity.HomeScreen.ContentView.navSearchFragment.visibility = View.VISIBLE

                if (search.searchView) {
                    mCntLoader.visibility = View.GONE
                } else {
                    mCntLoader.visibility = View.VISIBLE
                }

                SetTitleBarView(true, resources.getString(R.string.search))

                mImgHomeback.visibility = View.VISIBLE
            } else if (view == DB_HomeActivity.HomeScreen.ContentView.cntLeaderboard) {
                ApplicationClass.StatusTextWhite(this, true)

                SelectBottomTabImage()
                DB_HomeActivity.HomeScreen.ContentView.imgLeader.visibility = View.INVISIBLE

                UncheckedAll()
                DB_HomeActivity.HomeScreen.ContentView.cntSelLeader.visibility = View.VISIBLE

                HideAllFragment()
                DB_HomeActivity.HomeScreen.ContentView.navLeaderFragment.visibility = View.VISIBLE

                if (LeaderBoard.leaderBoardView) {
                    mCntLoader.visibility = View.GONE
                } else {
                    mCntLoader.visibility = View.VISIBLE
                }

                SetTitleBarView(false, resources.getString(R.string.leaderboard))
                mImgHomeback.visibility = View.VISIBLE
            } else if (view == DB_HomeActivity.HomeScreen.ContentView.cntHistory ||
                view == DB_HomeActivity.nevigationMenuview.cntMyhistoryMenu
            ) {
                ApplicationClass.StatusTextWhite(this, true)
                SelectBottomTabImage()
                DB_HomeActivity.HomeScreen.ContentView.imgHistory.visibility = View.INVISIBLE

                UncheckedAll()
                DB_HomeActivity.HomeScreen.ContentView.cntSelHistory.visibility = View.VISIBLE

                HideAllFragment()
                DB_HomeActivity.HomeScreen.ContentView.navHistoryFragment.visibility = View.VISIBLE

                var LoadView = false
                if (ApplicationClass.IsUserEmployee) {
                    LoadView = EmployeeHistory.EmpHistoryView
                } else {
                    LoadView = History.HistoryView
                }

                if (LoadView) {
                    mCntLoader.visibility = View.GONE
                } else {
                    mCntLoader.visibility = View.VISIBLE
                }
                SetTitleBarView(false, resources.getString(R.string.history))

                mImgHomeback.visibility = View.INVISIBLE

                if (DB_HomeActivity.drawerLayout.isDrawerOpen(GravityCompat.START))
                    DB_HomeActivity.drawerLayout.closeDrawer(GravityCompat.START)

            } else if (view == DB_HomeActivity.HomeScreen.ContentView.imgMenu) {
                if (!DB_HomeActivity.drawerLayout.isDrawerOpen(GravityCompat.START))
                    DB_HomeActivity.drawerLayout.openDrawer(GravityCompat.START)
            } else if (view == DB_HomeActivity.nevigationMenuview.cntProfileMenu) {
                var ProfileIntent = Intent(this, Profile::class.java)
                startActivity(ProfileIntent)
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
                DB_HomeActivity.drawerLayout.closeDrawer(GravityCompat.START)
            } else if (view == DB_HomeActivity.nevigationMenuview.cntNotificationMenu) {
                var NotifyIntent = Intent(this, NotificationList::class.java)
                startActivity(NotifyIntent)
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
                DB_HomeActivity.drawerLayout.closeDrawer(GravityCompat.START)
            } else if (view == DB_HomeActivity.nevigationMenuview.cntChallangesMenu) {
                var NotifyIntent = Intent(this, ActiveChallenge::class.java)
                startActivity(NotifyIntent)
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
                DB_HomeActivity.drawerLayout.closeDrawer(GravityCompat.START)
            } else if (view == DB_HomeActivity.nevigationMenuview.cntSettingMenu) {
                var SettingIntent = Intent(this, Settings::class.java)
                startActivity(SettingIntent)
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
                DB_HomeActivity.drawerLayout.closeDrawer(GravityCompat.START)
            } else if (view == DB_HomeActivity.nevigationMenuview.cntUploadMenu) {
                var SettingIntent = Intent(this, upload_doc::class.java)
                startActivity(SettingIntent)
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
                DB_HomeActivity.drawerLayout.closeDrawer(GravityCompat.START)
            } else if (view == DB_HomeActivity.nevigationMenuview.cntLogoutMenu) {
                DB_HomeActivity.drawerLayout.closeDrawer(GravityCompat.START)
                ApplicationClass.UserLogout(this)
            } else if (view == DB_HomeActivity.HomeScreen.ContentView.cntNewreview || view == DB_HomeActivity.nevigationMenuview.cntReviewsMenu) {
                var StarIntent = Intent(this, Bussiness_Location::class.java)
                startActivity(StarIntent)
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
            } else if (view == DB_HomeActivity.txtUnauthOk) {
                DB_HomeActivity.cntUnAuthorized.visibility = View.GONE
                ApplicationClass.UserLogout(this)
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

    private fun HideAllFragment() {
        DB_HomeActivity.HomeScreen.ContentView.navHomeFragment.visibility = View.GONE
        DB_HomeActivity.HomeScreen.ContentView.navSearchFragment.visibility = View.GONE
        DB_HomeActivity.HomeScreen.ContentView.navLeaderFragment.visibility = View.GONE
        DB_HomeActivity.HomeScreen.ContentView.navHistoryFragment.visibility = View.GONE
    }

    private fun SetTitleBarView(darkColor: Boolean, Title: String) {
        if (darkColor) {
            mTxtTitle.text = Title
            mTxtTitle.setTextColor(ContextCompat.getColor(this, R.color.blue_darklight))
            DB_HomeActivity.HomeScreen.ContentView.ll.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.blue_darklight
                )
            )
            DB_HomeActivity.HomeScreen.ContentView.imgMenu.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_dark_menu
                )
            )
        } else {
            mTxtTitle.text = Title
            mTxtTitle.setTextColor(ContextCompat.getColor(this, R.color.white))
            DB_HomeActivity.HomeScreen.ContentView.ll.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
            DB_HomeActivity.HomeScreen.ContentView.imgMenu.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_menu
                )
            )
        }
    }

    class LoadFragmentsPages : AsyncTask<Void, Void, String>() {
        override fun doInBackground(vararg params: Void?): String? {

            HomefragmentTransaction.replace(R.id.nav_home_fragment, HomeFr as Home)
            searchfragmentTransaction.replace(R.id.nav_search_fragment, searchFr as search)
            LeaderBoardfragmentTransaction.replace(
                R.id.nav_leader_fragment,
                LeaderBoardFr as LeaderBoard
            )
            if (ApplicationClass.IsUserEmployee) {
                HistoryfragmentTransaction.replace(
                    R.id.nav_history_fragment,
                    HistoryFr as EmployeeHistory
                )
            } else {
                HistoryfragmentTransaction.replace(
                    R.id.nav_history_fragment,
                    HistoryFr as History
                )
            }
            return ""
        }

        override fun onPreExecute() {
            super.onPreExecute()
            HomefragmentTransaction = mFragmentManager.beginTransaction()
            searchfragmentTransaction = mFragmentManager.beginTransaction()
            LeaderBoardfragmentTransaction = mFragmentManager.beginTransaction()
            HistoryfragmentTransaction = mFragmentManager.beginTransaction()
            SetUpEmployeeUI()

            HomeFr = Home()
            LeaderBoardFr = LeaderBoard()
            searchFr = search()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            HomefragmentTransaction.commit()
            HistoryfragmentTransaction.commit()
            LeaderBoardfragmentTransaction.commit()
            searchfragmentTransaction.commit()
            ApplicationClass.HomeScreenLoad = true
        }
    }
}