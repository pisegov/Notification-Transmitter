package com.myaxa.notificationtransmitter.data.remote.retrofit

import com.myaxa.notificationtransmitter.data.models.NotificationModel
import com.myaxa.notificationtransmitter.data.remote.retrofit.response.NotificationResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface NotificationsApi {

    @POST("/notification")
    suspend fun postNotification(
        @Body notification: NotificationModel
    ): NotificationResponse
}