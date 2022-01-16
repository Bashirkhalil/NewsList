package com.bk.learnkit.utilities

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import java.io.IOException

class Utils {

    fun setLightStatusBar(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var flags = activity.window.decorView.systemUiVisibility // get current flag
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR // add LIGHT_STATUS_BAR to flag
            activity.window.decorView.systemUiVisibility = flags
            activity.window.statusBarColor = Color.WHITE // optional
        }
    }

    fun getJsonFile(mContext: Context, fileName: String): String? {
        var json: String
        try {
            val `is` = mContext.assets.open("$fileName.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer)//, "UTF-8")
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }


}