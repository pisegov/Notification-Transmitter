package com.myaxa.notificationtransmitter.data.notification_listener

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.myaxa.notificationtransmitter.R
import com.myaxa.notificationtransmitter.data.models.NotificationModel
import com.myaxa.notificationtransmitter.data.remote.retrofit.NetworkResult
import com.myaxa.notificationtransmitter.data.repository.NotificationsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NLServiceController(private val context: Context, val repository: NotificationsRepository) {
    private val nReceiver = NotificationReceiver()
    private var mutableNotificationLiveData = MutableLiveData<NotificationModel>()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun newNotificationLiveData() = mutableNotificationLiveData

    fun startListening() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(context.getString(R.string.intent_action))
        context.registerReceiver(nReceiver, intentFilter)
    }

    fun stopListening() = context.unregisterReceiver(nReceiver)

    private inner class NotificationReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val notificationTextString =
                context?.getString(R.string.notification_text)
            val notificationPackageString =
                context?.getString(R.string.notification_package)

            val notification = NotificationModel(
                packageName = intent?.extras?.get(notificationPackageString).toString(),
                text = intent?.extras?.get(notificationTextString).toString()
            )

            mutableNotificationLiveData.value = notification

            coroutineScope.launch {
                when (repository.postNotification(notification)) {
                    is NetworkResult.GenericError -> {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                context,
                                "Generic Error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    is NetworkResult.NetworkError -> {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                context,
                                "Network Error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    else -> {}

                }
            }
        }
    }
}