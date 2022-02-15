package com.myaxa.notificationtransmitter.data.repository

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.myaxa.notificationtransmitter.R
import com.myaxa.notificationtransmitter.data.models.NotificationModel
import com.myaxa.notificationtransmitter.data.remote.retrofit.NetworkDataSource
import com.myaxa.notificationtransmitter.data.remote.retrofit.NetworkResult
import kotlinx.coroutines.*
import kotlinx.serialization.ExperimentalSerializationApi
import retrofit2.HttpException
import java.io.IOException

@ExperimentalSerializationApi
class NotificationsRepositoryImpl(private val context: Context) : NotificationsRepository {
    private val nReceiver = NotificationReceiver()
    private var mutableNotificationLiveData = MutableLiveData<NotificationModel>()

    private val networkDataSource = NetworkDataSource()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)


//    init {
//        registerReceiver()
//    }
//
//    private fun registerReceiver() {
//        val intentFilter = IntentFilter()
//        intentFilter.addAction(context.getString(R.string.intent_action))
//        context.registerReceiver(nReceiver, intentFilter)
//    }

    override fun newNotificationSubscription(): LiveData<NotificationModel> =
        mutableNotificationLiveData

    override fun getActiveNotifications(): List<NotificationModel>? {
        TODO("Not yet implemented")
    }

    override suspend fun postNotification(notification: NotificationModel): NetworkResult<NotificationModel> =
        safeApiCall { networkDataSource.postNotification(notification) }

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

            val notification = NotificationModel(
                packageName = intent?.extras?.get(notificationPackageString).toString(),
                text = intent?.extras?.get(notificationTextString).toString()
            )

            mutableNotificationLiveData.value = notification

            coroutineScope.launch {
                when (postNotification(notification)) {
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