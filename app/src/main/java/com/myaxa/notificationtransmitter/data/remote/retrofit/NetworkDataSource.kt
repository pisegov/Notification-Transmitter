package com.myaxa.notificationtransmitter.data.remote.retrofit

import com.myaxa.notificationtransmitter.data.models.NotificationModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class NetworkDataSource {

    suspend fun postNotification(notification: NotificationModel): NotificationModel =
        withContext(Dispatchers.IO) {
            val response = RetrofitModule.notificationsApi.postNotification(notification)
            return@withContext NotificationModel(
                packageName = response.packageName,
                text = response.text
            )
        }
}
