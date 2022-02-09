package com.myaxa.notificationtransmitter.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.myaxa.notificationtransmitter.data.models.NotificationModel

class NotificationsRepositoryImpl(private val context: Context) : NotificationsRepository {
    override fun newNotification(): LiveData<NotificationModel>? {
        TODO("Not yet implemented")
    }

    override fun getActiveNotifications(): List<NotificationModel>? {
        TODO("Not yet implemented")
    }

    override fun startNotificationListening() {
        TODO("Not yet implemented")
    }

    override fun stopNotificationListening() {
        TODO("Not yet implemented")
    }

}