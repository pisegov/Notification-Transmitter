package com.myaxa.notificationtransmitter.data.notification_listener

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.myaxa.notificationtransmitter.R

class NLService : NotificationListenerService() {

    private val receiver = CommandReceiver()
    private lateinit var intentActionString: String

    override fun onCreate() {
        intentActionString = getString(R.string.intent_action)
        val filter = IntentFilter()
        filter.addAction(intentActionString)
        registerReceiver(receiver, filter)
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        if (
            sbn?.packageName == "com.instagram.lite" ||
            sbn?.packageName == "com.instagram.android" ||
            sbn?.packageName == "com.zhiliaoapp.musically" ||
            sbn?.packageName == "com.google.android.dialer" ||
            sbn?.packageName == "com.android.messaging"
        ) {
            fetchNotification(sbn)
        }
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
    }

    private fun fetchNotification(sbn: StatusBarNotification?) {
        val notificationTextString = getString(R.string.notification_text)
        val notificationPackageNameString = getString(R.string.notification_package)

        val notificationText = sbn?.notification?.extras?.getCharSequence(Notification.EXTRA_TEXT)
        val packageName = sbn?.packageName
        val intent = Intent(intentActionString)
        intent.putExtra(notificationTextString, notificationText)
        intent.putExtra(notificationPackageNameString, packageName)
        sendBroadcast(intent)
    }

    private inner class CommandReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val commandString = getString(R.string.listener_service_command)
            when (intent?.extras?.get(commandString)) {
                "stop_service" -> {
                    stopSelf()
                }
            }
        }

        fun getAll(): Nothing = TODO("not yet implemented")
    }
}