package com.myaxa.notificationtransmitter.data

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.myaxa.notificationtransmitter.R

class NLService : NotificationListenerService() {

    private val receiver = CommandReceiver()
    private lateinit var loggingTag: String
    private lateinit var intentActionString: String

    override fun onCreate() {
        intentActionString = getString(R.string.intent_action)
        loggingTag = getString(R.string.nls_logging_tag)
        val filter = IntentFilter()
        filter.addAction(intentActionString)
        registerReceiver(receiver, filter)
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        if (sbn?.notification?.category == "msg") {
            Log.d(loggingTag, "posted")
            fetchNotification(sbn)
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        Log.d(loggingTag, "removed")
        fetchNotification(sbn)
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

        Log.d(loggingTag, notificationText.toString())
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