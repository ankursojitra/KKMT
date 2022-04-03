package com.rjsquare.kkmt.Helpers

import com.rjsquare.kkmt.AppConstant.ApplicationClass

class Preferences {

    companion object {
        //Store Preferences Clear
        fun Cleardata() {
            ApplicationClass.PrefEditor.clear()
            ApplicationClass.PrefEditor.commit()
        }

        //Store Preferences
        fun StoreString(Key: String, Value: String) {
            ApplicationClass.PrefEditor.putString(Key, Value)
            ApplicationClass.PrefEditor.commit()
        }

        fun StoreBoolean(Key: String, Value: Boolean) {
            ApplicationClass.PrefEditor.putBoolean(Key, Value)
            ApplicationClass.PrefEditor.commit()
        }

        fun StoreInt(Key: String, Value: Int) {
            ApplicationClass.PrefEditor.putInt(Key, Value)
            ApplicationClass.PrefEditor.commit()
        }

        //Read Preferences
        fun ReadString(Key: String, Value: String): String {
            return ApplicationClass.SharedPref.getString(Key, Value) as String
        }

        fun ReadBoolean(Key: String, Value: Boolean): Boolean {
            return ApplicationClass.SharedPref.getBoolean(Key, Value)
        }

        fun ReadInt(Key: String, Value: Int): Int {
            return ApplicationClass.SharedPref.getInt(Key, Value)

        }
    }
}