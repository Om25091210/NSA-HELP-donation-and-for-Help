package com.aryomtech.nsabilaspur

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class Specific {
    val TAG = "Send"

    fun noti(name:String,phone:String,token:String){
        val title = name
        val message = phone
        if (message != null) {
            if (title != null) {
                if(title.isNotEmpty() && message.isNotEmpty()) {
                    PushNotification(
                            NotificationData(title, message),
                            token
                    ).also {
                        sendNotification(it)
                    }
                }
            }
        }
    }
    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitInstance.api.postNotification(notification)
            if(response.isSuccessful) {
                Log.d(TAG, "Response: ${Gson().toJson(response)}")
            } else {
                Log.e(TAG, response.errorBody().toString())
            }
        } catch(e: Exception) {
            Log.e(TAG, e.toString())
        }
    }

}