package com.example.planet.core.message

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import com.example.planet.R

class MessageUtils {

    companion object {

        private var mDialog: Dialog? = null

        fun progress(activity: Activity, textShow: String) {
            if (mDialog != null) {
                mDialog!!.dismiss()
            }
            mDialog = Dialog(activity)

            val view = activity.window.decorView.findViewById<ViewGroup>(android.R.id.content)
            val inflate = LayoutInflater.from(activity).inflate(R.layout.dialog_progress_message, view, false)
            mDialog!!.setContentView(inflate)
            mDialog!!.setCancelable(false)
            mDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(mDialog!!.window!!.attributes)
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
            mDialog!!.window!!.attributes = layoutParams
            mDialog!!.window!!.decorView.animate().translationY(-100f).setDuration(300).start()

            inflate.findViewById<TextView>(R.id.progress_text).text = textShow

            mDialog!!.show()
        }

        fun stopProgress() {
            if (mDialog != null) {
                mDialog!!.window!!.decorView.animate().translationY(100f).setDuration(300).start()
                mDialog!!.dismiss()
            }
        }

    }
}