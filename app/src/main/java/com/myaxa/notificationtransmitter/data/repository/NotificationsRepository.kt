package com.myaxa.notificationtransmitter.data.repository

import androidx.lifecycle.LiveData
import com.myaxa.notificationtransmitter.data.models.NotificationModel
import com.myaxa.notificationtransmitter.data.remote.retrofit.NetworkResult

interface NotificationsRepository {
    fun newNotificationSubscription(): LiveData<NotificationModel>
    fun getActiveNotifications(): List<NotificationModel>?

    suspend fun postNotification(notification: NotificationModel): NetworkResult<NotificationModel>

    fun startNotificationListening()
    fun stopNotificationListening()
}