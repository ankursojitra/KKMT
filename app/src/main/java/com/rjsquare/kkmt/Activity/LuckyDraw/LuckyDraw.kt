package com.rjsquare.kkmt.Activity.LuckyDraw

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable.ApiCallingInstance
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.Events.LuckyDrawCheckService
import com.rjsquare.kkmt.RetrofitInstance.Events.LuckyDrawCheck_Model
import com.rjsquare.kkmt.RetrofitInstance.Events.LuckyDrawService
import com.rjsquare.kkmt.RetrofitInstance.Events.LuckyDraw_Model
import com.rjsquare.kkmt.databinding.ActivityLuckyDrawBinding
import retrofit2.Call
import retrofit2.Callback
import rubikstudio.library.LuckyWheelView
import rubikstudio.library.model.LuckyItem

class LuckyDraw : AppCompatActivity(), View.OnClickListener {
    lateinit var data: ArrayList<LuckyItem>
    var Spinning = false
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    companion object {
        lateinit var DB_LuckyDraw: ActivityLuckyDrawBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_LuckyDraw = DataBindingUtil.setContentView(this, R.layout.activity_lucky_draw)
//        setContentView(R.layout.activity_lucky_draw)
        try {
            ApplicationClass.StatusTextWhite(this, true)

            DB_LuckyDraw.luckyWheel.setLuckyWheelCenterImage(resources.getDrawable(R.drawable.blank))
            DB_LuckyDraw.luckyWheel.setLuckyWheelCursorImage(R.drawable.spin_pin)

            DB_LuckyDraw.txtSpin.setOnClickListener(this)
            DB_LuckyDraw.imgBack.setOnClickListener(this)

            LodaLuckyDrawItems()
            DB_LuckyDraw.txtUnauthOk.setOnClickListener(this)
            DB_LuckyDraw.txtAlertok.setOnClickListener(this)
            DB_LuckyDraw.cntBacktoHome.setOnClickListener(this)
            DB_LuckyDraw.luckyWheel.setData(data)
            DB_LuckyDraw.luckyWheel.setRound(10)

            DB_LuckyDraw.luckyWheel.isTouchEnabled = false
            DB_LuckyDraw.luckyWheel.isEnabled = false
            DB_LuckyDraw.luckyWheel.setLuckyRoundItemSelectedListener(object :
                LuckyWheelView.LuckyRoundItemSelectedListener {
                override fun LuckyRoundItemSelected(index: Int) {
//                    Log.e("TAG", "Check Item Selected : " + data.get(index).topText)
//                    Log.e("TAG", "Check Item Selected : " + GetCredit(data.get(index).topText))

//                    LuckyDrawCreditAdd(GetCredit(data.get(index).topText))
                }
            })
//            DB_LuckyDraw.cntLoader.visibility = View.VISIBLE
//            CheckLuckyDrawAvailable()
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

    private fun GetCredit(CreditText: String): String {
        if (CreditText.equals("5 0", true)) {
            return "50"
        } else if (CreditText.equals("1 0 0", true)) {
            return "100"
        } else if (CreditText.equals("5 0 0", true)) {
            return "500"
        } else if (CreditText.equals("1 0 0 0", true)) {
            return "1000"
        } else if (CreditText.equals("1 2 0 0", true)) {
            return "1200"
        } else if (CreditText.equals("2 0 0 0", true)) {
            return "2000"
        } else if (CreditText.equals("5 0 0 0", true)) {
            return "5000"
        } else if (CreditText.equals("1 0 0 0 0", true)) {
            return "10000"
        } else {
            return "0"
        }
    }

    private fun CheckLuckyDrawAvailable() {
        try {

            //Here the json data is add to a hash map with key data
            val params: MutableMap<String, String> =
                HashMap()
            params[ApplicationClass.paramKey_UserId] =
                ApplicationClass.userInfoModel.data!!.userid!!

            val service =
                ApiCallingInstance.retrofitInstance.create<LuckyDrawCheckService>(
                    LuckyDrawCheckService::class.java
                )
            val call = service.GetLuckyDrawCheckData(
                params,
                ApplicationClass.userInfoModel.data!!.access_token!!
            )
            call.enqueue(object : Callback<LuckyDrawCheck_Model> {
                override fun onFailure(call: Call<LuckyDrawCheck_Model>, t: Throwable) {
                    DB_LuckyDraw.cntLoader.visibility = View.GONE
                }

                override fun onResponse(
                    call: Call<LuckyDrawCheck_Model>,
                    response: retrofit2.Response<LuckyDrawCheck_Model>
                ) {
                    DB_LuckyDraw.cntLoader.visibility = View.GONE
                    if (response.body()!!.status.equals(ApplicationClass.ResponseSucess)) {
                        if (response.body()!!.data!!.is_eligible.equals("No", true)) {
                            DB_LuckyDraw.txtAlertmsg.text = response.body()!!.message
                            DB_LuckyDraw.cntAlert.visibility = View.VISIBLE
                        } else {
                            Log.e("TAG", "Spin available")
                        }
                    } else if (response.body()!!.status.equals(ApplicationClass.ResponseUnauthorized)) {
                        DB_LuckyDraw.cntUnAuthorized.visibility = View.VISIBLE
                    } else {

                    }
                }
            })
        } catch (E: Exception) {
            print(E)
        } catch (NE: NullPointerException) {
            print(NE)
        } catch (IE: IndexOutOfBoundsException) {
            print(IE)
        } catch (IE: IllegalStateException) {
            print(IE)
        } catch (AE: ActivityNotFoundException) {
            print(AE)
        } catch (KNE: KotlinNullPointerException) {
            print(KNE)
        } catch (CE: ClassNotFoundException) {
            print(CE)
        }
    }

    private fun LuckyDrawCreditAdd(getCredit: String) {
        try {

            //Here the json data is add to a hash map with key data
            val params: MutableMap<String, String> =
                HashMap()
            params[ApplicationClass.paramKey_UserId] =
                ApplicationClass.userInfoModel.data!!.userid!!

            params[ApplicationClass.paramKey_Credit] = getCredit

            val service =
                ApiCallingInstance.retrofitInstance.create<LuckyDrawService>(
                    LuckyDrawService::class.java
                )
            val call = service.GetLuckyDrawData(
                params,
                ApplicationClass.userInfoModel.data!!.access_token!!
            )
            call.enqueue(object : Callback<LuckyDraw_Model> {
                override fun onFailure(call: Call<LuckyDraw_Model>, t: Throwable) {
                    DB_LuckyDraw.cntLoader.visibility = View.GONE
                }

                override fun onResponse(
                    call: Call<LuckyDraw_Model>,
                    response: retrofit2.Response<LuckyDraw_Model>
                ) {
                    DB_LuckyDraw.cntLoader.visibility = View.GONE
                    if (response.body()!!.status.equals(ApplicationClass.ResponseSucess)) {
                        DB_LuckyDraw.txtCreditEarn.text = getCredit
                        DB_LuckyDraw.cntConfirmation.visibility = View.VISIBLE
                    } else if (response.body()!!.status.equals(ApplicationClass.ResponseUnauthorized)) {
                        DB_LuckyDraw.cntUnAuthorized.visibility = View.VISIBLE
                    } else {
                        DB_LuckyDraw.txtAlertmsg.text = response.body()!!.message
                        DB_LuckyDraw.cntAlert.visibility = View.VISIBLE
                    }
                }

            })
        } catch (E: Exception) {
            print(E)
        } catch (NE: NullPointerException) {
            print(NE)
        } catch (IE: IndexOutOfBoundsException) {
            print(IE)
        } catch (IE: IllegalStateException) {
            print(IE)
        } catch (AE: ActivityNotFoundException) {
            print(AE)
        } catch (KNE: KotlinNullPointerException) {
            print(KNE)
        } catch (CE: ClassNotFoundException) {
            print(CE)
        }
    }

    private fun LodaLuckyDrawItems() {
        data = ArrayList()

        val luckyItem1 = LuckyItem()
        luckyItem1.topText = "5 0"
//        luckyItem1.icon = R.drawable.fifty
        luckyItem1.color = ContextCompat.getColor(this, R.color.wheel1)
        data.add(luckyItem1)

        val luckyItem2 = LuckyItem()
        luckyItem2.topText = "1 0 0"
//        luckyItem2.icon = R.drawable.hundred
        luckyItem2.color = ContextCompat.getColor(this, R.color.light_purple)
        data.add(luckyItem2)

        val luckyItem3 = LuckyItem()
        luckyItem3.topText = "5 0 0"
//        luckyItem3.icon = R.drawable.fifty
        luckyItem3.color = ContextCompat.getColor(this, R.color.wheel3)
        data.add(luckyItem3)

        val luckyItem4 = LuckyItem()
        luckyItem4.topText = "1 0 0 0"
//        luckyItem4.icon = R.drawable.fifty
        luckyItem4.color = ContextCompat.getColor(this, R.color.wheel4)
        data.add(luckyItem4)

        val luckyItem5 = LuckyItem()
        luckyItem5.topText = "1 2 0 0"
//        luckyItem5.icon = R.drawable.fifty
        luckyItem5.color = ContextCompat.getColor(this, R.color.wheel1)
        data.add(luckyItem5)

        val luckyItem6 = LuckyItem()
        luckyItem6.topText = "2 0 0 0"
//        luckyItem6.icon = R.drawable.fifty
        luckyItem6.color = ContextCompat.getColor(this, R.color.light_purple)
        data.add(luckyItem6)

        val luckyItem7 = LuckyItem()
        luckyItem7.topText = "5 0 0 0"
//        luckyItem7.icon = R.drawable.fifty
        luckyItem7.color = ContextCompat.getColor(this, R.color.wheel3)
        data.add(luckyItem7)

        val luckyItem8 = LuckyItem()
        luckyItem8.topText = "1 0 0 0 0"
//        luckyItem8.icon = R.drawable.tenthousand
        luckyItem8.color = ContextCompat.getColor(this, R.color.wheel4)
        data.add(luckyItem8)

    }

    override fun onClick(view: View?) {
        try {
            if (view == DB_LuckyDraw.txtSpin) {
                if (!Spinning) {
                    var SpinTimeL =
                        ApplicationClass.SharedPref.getLong(ApplicationClass.SpinTime, 0)
                    Log.e("TAG", "SpinTimeL : " + SpinTimeL)
                    var lSpinTime = System.currentTimeMillis() + (1000 * 60 * 60 * 24)
                    ApplicationClass.PrefEditor.putLong(ApplicationClass.SpinTime, lSpinTime)
                    ApplicationClass.PrefEditor.commit()
                    Spinning = true
                }
                DB_LuckyDraw.luckyWheel.startLuckyWheelWithRandomTarget()
            } else if (view == DB_LuckyDraw.imgBack) {
                onBackPressed()
            } else if (view == DB_LuckyDraw.cntBacktoHome) {
                DB_LuckyDraw.cntConfirmation.visibility = View.GONE
                finish()
                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
            } else if (view == DB_LuckyDraw.txtAlertok) {
                DB_LuckyDraw.cntAlert.visibility = View.GONE
                finish()
                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
            } else if (view == DB_LuckyDraw.txtUnauthOk) {
                DB_LuckyDraw.cntUnAuthorized.visibility = View.GONE
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
}