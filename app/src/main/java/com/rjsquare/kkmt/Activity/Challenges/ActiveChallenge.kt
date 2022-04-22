package com.rjsquare.kkmt.Activity.Challenges

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.rjsquare.kkmt.Activity.Challenges.ui.mainActiveChallenges.SectionsPagerAdapter
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.ActivityActiveChallengeBinding

class ActiveChallenge : AppCompatActivity(), View.OnClickListener {

    lateinit var DB_ActiveChallenge: ActivityActiveChallengeBinding
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_ActiveChallenge =
            DataBindingUtil.setContentView(this, R.layout.activity_active_challenge)
//        setContentView(R.layout.activity_active_challenge)
        try {
            ApplicationClass.StatusTextWhite(this, true)
            val sectionsPagerAdapter =
                SectionsPagerAdapter(this, supportFragmentManager)

            DB_ActiveChallenge.viewPager.adapter = sectionsPagerAdapter
            DB_ActiveChallenge.viewPager.addOnPageChangeListener(object : OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {}
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    // Check if this is the page you want.

                    if (position == 0) {
                        DB_ActiveChallenge.txtAchallenge.background =
                            ContextCompat.getDrawable(
                                this@ActiveChallenge,
                                R.drawable.tab_selection
                            )
                        DB_ActiveChallenge.txtCchallenge.background = null
                        DB_ActiveChallenge.txtAchallenge.setTextColor(
                            ContextCompat.getColor(
                                this@ActiveChallenge,
                                R.color.black
                            )
                        )
                        DB_ActiveChallenge.txtCchallenge.setTextColor(
                            ContextCompat.getColor(
                                this@ActiveChallenge,
                                R.color.white
                            )
                        )
                    } else if (position == 1) {
                        DB_ActiveChallenge.txtCchallenge.background =
                            ContextCompat.getDrawable(
                                this@ActiveChallenge,
                                R.drawable.tab_selection
                            )
                        DB_ActiveChallenge.txtAchallenge.background = null
                        DB_ActiveChallenge.txtCchallenge.setTextColor(
                            ContextCompat.getColor(
                                this@ActiveChallenge,
                                R.color.black
                            )
                        )
                        DB_ActiveChallenge.txtAchallenge.setTextColor(
                            ContextCompat.getColor(
                                this@ActiveChallenge,
                                R.color.white
                            )
                        )
                    }
                }
            })

            DB_ActiveChallenge.txtAchallenge.setOnClickListener(this)
            DB_ActiveChallenge.txtCchallenge.setOnClickListener(this)
            DB_ActiveChallenge.imgBack.setOnClickListener(this)

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
                if (view == DB_ActiveChallenge.txtAchallenge) {
                    DB_ActiveChallenge.viewPager.currentItem = 0
                    DB_ActiveChallenge.txtAchallenge.background =
                        ContextCompat.getDrawable(this, R.drawable.tab_selection)
                    DB_ActiveChallenge.txtCchallenge.background = null
                    DB_ActiveChallenge.txtAchallenge.setTextColor(
                        ContextCompat.getColor(
                            this,
                            R.color.black
                        )
                    )
                    DB_ActiveChallenge.txtCchallenge.setTextColor(
                        ContextCompat.getColor(
                            this,
                            R.color.white
                        )
                    )
                } else if (view == DB_ActiveChallenge.txtCchallenge) {
                    DB_ActiveChallenge.viewPager.currentItem = 1
                    DB_ActiveChallenge.txtCchallenge.background =
                        ContextCompat.getDrawable(this, R.drawable.tab_selection)
                    DB_ActiveChallenge.txtAchallenge.background = null
                    DB_ActiveChallenge.txtCchallenge.setTextColor(
                        ContextCompat.getColor(
                            this,
                            R.color.black
                        )
                    )
                    DB_ActiveChallenge.txtAchallenge.setTextColor(
                        ContextCompat.getColor(
                            this,
                            R.color.white
                        )
                    )
                } else if (view == DB_ActiveChallenge.imgBack) {
                    onBackPressed()
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