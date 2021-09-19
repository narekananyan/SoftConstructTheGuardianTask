package com.theguardian.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.theguardian.base.utils.hasNetwork

class NetworkChangeReceiver : BroadcastReceiver() {

    private var isOnlineCallBack: (isOnline: Boolean) -> Unit = {}

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.hasNetwork()?.let { isOnlineCallBack.invoke(it) }
    }

    fun addIsOnLineCallBack(callBack: (isOnline: Boolean) -> Unit) {
        isOnlineCallBack = callBack
    }
}