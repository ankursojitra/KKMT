package com.rjsquare.kkmt.Helpers

import com.rjsquare.kkmt.AppConstant.ApplicationClass

class Preferences {

    companion object {
        //Store Preferences Clear
        fun Cleardata() {
            ApplicationClass.prefEditor.clear()
            ApplicationClass.prefEditor.commit()
        }

        //Store Preferences
        fun StoreString(Key: String, Value: String) {
            ApplicationClass.prefEditor.putString(Key, Value)
            ApplicationClass.prefEditor.commit()
        }

        fun StoreBoolean(Key: String, Value: Boolean) {
            ApplicationClass.prefEditor.putBoolean(Key, Value)
            ApplicationClass.prefEditor.commit()
        }

        fun StoreInt(Key: String, Value: Int) {
            ApplicationClass.prefEditor.putInt(Key, Value)
            ApplicationClass.prefEditor.commit()
        }

        //Read Preferences
        fun ReadString(Key: String, Value: String): String {
            return ApplicationClass.sharedPref.getString(Key, Value) as String
        }

        fun ReadBoolean(Key: String, Value: Boolean): Boolean {
            return ApplicationClass.sharedPref.getBoolean(Key, Value)
        }

        fun ReadInt(Key: String, Value: Int): Int {
            return ApplicationClass.sharedPref.getInt(Key, Value)

        }
    }
}