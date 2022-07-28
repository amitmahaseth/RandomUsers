package com.example.randomusers.Util

import android.app.Dialog
import android.content.Context
import com.example.randomusers.R
import com.example.randomusers.model.ErrorResponse
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import java.io.IOException

object Extensions {
    private var dialog: Dialog? = null

    fun errorMessage(error: ResponseBody): String {
        try {

            val gson = GsonBuilder().create()
            val mError: ErrorResponse
            try {
                mError = gson.fromJson(
                    error.string(),
                    ErrorResponse::class.java
                )
                return mError.message
            } catch (e: IOException) {
                // handle failure to read error
                return Constant.SOMETHING_WENT_WRONG
            }

        } catch (e: Exception) {
            return Constant.SOMETHING_WENT_WRONG
        }
    }


    fun showProgess(context: Context) {
        dialog = Dialog(context)
        dialog!!.setContentView(R.layout.dialog_progress)
        dialog!!.setCancelable(false)
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog!!.show()
    }

    fun stopProgress() {
        if (dialog != null)
            dialog!!.cancel()

    }

}
