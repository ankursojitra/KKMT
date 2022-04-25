package com.rjsquare.kkmt.AppConstant

import android.app.Application
import android.content.ActivityNotFoundException
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.gson.Gson
import com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable.ApiCallingInstance
import com.rjsquare.kkmt.Activity.HomeActivity
import com.rjsquare.kkmt.Fragment.Home
import com.rjsquare.kkmt.Helpers.Preferences
import com.rjsquare.kkmt.RetrofitInstance.Events.NetworkServices
import com.rjsquare.kkmt.RetrofitInstance.LogInCall.UserLogIn_Model
import com.rjsquare.kkmt.RetrofitInstance.RegisterUserCall.UserInfoData_Model
import retrofit2.Call
import retrofit2.Callback


class ApplicationClass : Application(), LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        //App in background
        GlobalUsage.appBackground = true
//        Log.e("Applicationxx", " On Background")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        // App in foreground
        GlobalUsage.appBackground = false
//        Log.e("Applicationxx", " On Foreground")
    }


    companion object {
        lateinit var ApplicationContext: Application


        private fun GetUserPrefData(): UserInfoData_Model {
            var mUserInfoData_Model = UserInfoData_Model()
            if (!GlobalUsage.sharedPref.getString(Constants.Pref_UserDataModel, "")
                    .equals("", true)
            ) {
                mUserInfoData_Model = Gson().fromJson(
                    GlobalUsage.sharedPref.getString(Constants.Pref_UserDataModel, ""),
                    UserInfoData_Model::class.java
                )
                Log.e("TAG", "ModelCHECK")
            }
            return mUserInfoData_Model
        }

        fun updateUserInfo() {
            try {
                val userModel = GlobalUsage.userInfoModel.data!!
                //Here the json data is add to a hash map with key data
                val params: MutableMap<String, String> =
                    HashMap()

                params[Constants.paramKey_UserId] = userModel.userid.toString()

                val service =
                    ApiCallingInstance.retrofitInstance.create<NetworkServices.AppReopenService>(
                        NetworkServices.AppReopenService::class.java
                    )
                val call = service.GetUserData(
                    params, userModel.access_token.toString()
                )
                call.enqueue(object : Callback<UserInfoData_Model> {
                    override fun onFailure(call: Call<UserInfoData_Model>, t: Throwable) {
                        Log.e("GetResponseXX", ": " + t)
                    }

                    override fun onResponse(
                        call: Call<UserInfoData_Model>,
                        response: retrofit2.Response<UserInfoData_Model>
                    ) {
                        Log.e("GetResponsesas", ": " + Gson().toJson(response.body()!!))

                        if (response.body()!!.status.equals(Constants.ResponseSucess, true)) {

                            GlobalUsage.userInfoModel = response.body()!!
                            Preferences.StoreString(
                                Constants.Pref_UserDataModel,
                                Gson().toJson(GlobalUsage.userInfoModel)
                            )

                            GlobalUsage.isUserEmployee = GlobalUsage.IsEmployee()
                            GlobalUsage.authorisedUser = true
                            GlobalUsage.isApprove = userModel.approve.equals(Constants.YES, true)
                            if (GlobalUsage.isHomeScreenVisible) {
                                HomeActivity.UserVerifiedUpdateUI()
                                Home.UserInfoUpdateUI()
                            }
                        } else if (response.body()!!.status.equals(
                                Constants.ResponseUnauthorized,
                                true
                            )
                        ) {
                            GlobalUsage.authorisedUser = false
                        } else {
                            GlobalUsage.ShowToast(ApplicationContext, response.body()!!.message)
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

    }

    override fun onCreate() {
        super.onCreate()
        try {
            ApplicationContext = this

            ProcessLifecycleOwner.get().lifecycle.addObserver(this)
            GlobalUsage.sharedPref = getSharedPreferences(Constants.PrefName, 0)
            GlobalUsage.prefEditor = GlobalUsage.sharedPref.edit()
            GlobalUsage.userLogedIn = Preferences.ReadBoolean(Constants.Pref_UserLogedIn, false)
            InitModels()


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

    private fun InitModels() {
        GlobalUsage.mLogInInfo_Model = UserLogIn_Model()
        GlobalUsage.userInfoModel = UserInfoData_Model()
        GlobalUsage.userInfoModel = GetUserPrefData()

//        if (userInfoModel != UserInfoData_Model()) {
//            IsUserEmployee = IsEmployee()
//        }

        if (GlobalUsage.userLogedIn) {
            updateUserInfo()
            GlobalUsage.isUserEmployee = GlobalUsage.IsEmployee()
        }

    }
}