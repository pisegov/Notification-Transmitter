package com.myaxa.notificationtransmitter.data.repository

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.myaxa.notificationtransmitter.R
import com.myaxa.notificationtransmitter.data.models.NotificationModel

class NotificationsRepositoryImpl(private val context: Context) : NotificationsRepository {
    private val nReceiver = NotificationReceiver()

    private var mutableNotificationLiveData = MutableLiveData<NotificationModel>()

//    init {
//        registerReceiver()
//    }
//
//    private fun registerReceiver() {
//        val intentFilter = IntentFilter()
//        intentFilter.addAction(context.getString(R.string.intent_action))
//        context.registerReceiver(nReceiver, intentFilter)
//    }

    override fun newNotification(): LiveData<NotificationModel> =
        mutableNotificationLiveData

    override fun getActiveNotifications(): List<NotificationModel>? {
        TODO("Not yet implemented")
    }

    private fun saveNotification() {
        TODO("Not yet implemented")
    }

    override fun startNotificationListening() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(context.getString(R.string.intent_action))
        context.registerReceiver(nReceiver, intentFilter)
    }

    override fun stopNotificationListening() {
        context.unregisterReceiver(nReceiver)
    }

    private inner class NotificationReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val notificationTextString =
                this@NotificationsRepositoryImpl.context.getString(R.string.notification_text)
            val notificationPackageString =
                this@NotificationsRepositoryImpl.context.getString(R.string.notification_package)

            val text = intent?.extras?.get(notificationTextString).toString()
            val packageName = intent?.extras?.get(notificationPackageString).toString()

            mutableNotificationLiveData.value =
                NotificationModel(packageName, text)
        }
    }
}