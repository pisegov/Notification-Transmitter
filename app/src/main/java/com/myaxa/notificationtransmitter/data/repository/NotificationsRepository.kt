package com.myaxa.notificationtransmitter.data.repository

import androidx.lifecycle.LiveData
import com.myaxa.notificationtransmitter.data.models.NotificationModel

interface NotificationsRepository {
    fun newNotification(): LiveData<NotificationModel>
    fun getActiveNotifications(): List<NotificationModel>?

    fun startNotificationListening()
    fun stopNotificationListening()
}