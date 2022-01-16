package com.bk.learnkit.utilities

import android.content.Context
import android.content.SharedPreferences

class PrefManager(mContext: Context) {

    companion object {
        private const val PREF_NAME = "workPassengerApplication"
        private const val PRIVATE_MODE = 0
        private lateinit var prefs: SharedPreferences
        private var mInstance: PrefManager? = null

        @Synchronized
        fun iniInstance(mContext: Context): PrefManager? {
            if (mInstance == null) {
                mInstance = PrefManager(mContext)
            }
            return mInstance
        }

        fun setPref(key: String?, Values: String?) {
           prefs.edit().putString(key, Values).commit()
        }

        fun getPref(key: String?): String? {
            return prefs.getString(key, "")
        }
    }


    init {
        prefs = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    }

}