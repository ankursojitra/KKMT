package com.rjsquare.kkmt.Helpers

import com.rjsquare.kkmt.AppConstant.GlobalUsage

class Preferences {

    companion object {
        //Store Preferences Clear
        fun Cleardata() {
            GlobalUsage.prefEditor.clear()
            GlobalUsage.prefEditor.commit()
        }

        //Store Preferences
        fun StoreString(Key: String, Value: String) {
            GlobalUsage.prefEditor.putString(Key, Value)
            GlobalUsage.prefEditor.commit()
        }

        fun StoreBoolean(Key: String, Value: Boolean) {
            GlobalUsage.prefEditor.putBoolean(Key, Value)
            GlobalUsage.prefEditor.commit()
        }

        fun StoreInt(Key: String, Value: Int) {
            GlobalUsage.prefEditor.putInt(Key, Value)
            GlobalUsage.prefEditor.commit()
        }

        //Read Preferences
        fun ReadString(Key: String, Value: String): String {
            return GlobalUsage.sharedPref.getString(Key, Value) as String
        }

        fun ReadBoolean(Key: String, Value: Boolean): Boolean {
            return GlobalUsage.sharedPref.getBoolean(Key, Value)
        }

        fun ReadInt(Key: String, Value: Int): Int {
            return GlobalUsage.sharedPref.getInt(Key, Value)

        }
    }
}