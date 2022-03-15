package com.myaxa.notificationtransmitter.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.myaxa.notificationtransmitter.data.models.NotificationModel
import com.myaxa.notificationtransmitter.data.notification_listener.NLServiceController
import com.myaxa.notificationtransmitter.data.remote.retrofit.NetworkDataSource
import com.myaxa.notificationtransmitter.data.remote.retrofit.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import retrofit2.HttpException
import java.io.IOException

@ExperimentalSerializationApi
class NotificationsRepositoryImpl(context: Context) : NotificationsRepository {

    private val networkDataSource = NetworkDataSource()

    private val listenerController = NLServiceController(context, this)

    override fun newNotificationSubscription(): LiveData<NotificationModel> =
        listenerController.newNotificationLiveData()

    override fun getActiveNotifications(): List<NotificationModel>? {
        TODO("Not yet implemented")
    }

    override suspend fun postNotification(notification: NotificationModel): NetworkResult<NotificationModel> =
        safeApiCall { networkDataSource.postNotification(notification) }

    private fun saveNotification() {
        TODO("Not yet implemented")
    }

    override fun startNotificationListening() {
        listenerController.startListening()
    }

    override fun stopNotificationListening() {
        listenerController.stopListening()
    }

    private suspend fun <T : Any> safeApiCall(
        apiCall: suspend () -> T
    ): NetworkResult<T> {
        return withContext(Dispatchers.IO) {
            try {
                NetworkResult.Success(apiCall.invoke())
            } catch (error: Throwable) {
                when (error) {
                    is IOException ->
                        NetworkResult.NetworkError
                    is HttpException -> {
                        val response = error.response()?.errorBody()?.source().toString()
                        NetworkResult.GenericError(response)
                    }
                    else -> NetworkResult.GenericError("Undefined error")
                }
            }
        }
    }
}