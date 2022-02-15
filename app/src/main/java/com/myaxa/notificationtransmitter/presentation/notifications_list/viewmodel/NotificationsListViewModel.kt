package com.myaxa.notificationtransmitter.presentation.notifications_list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myaxa.notificationtransmitter.data.models.NotificationModel
import com.myaxa.notificationtransmitter.data.repository.NotificationsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotificationsListViewModel(private val repository: NotificationsRepository) : ViewModel() {

    fun newNotification(): LiveData<NotificationModel> = repository.newNotificationSubscription()

    private var _activeNotifications = MutableLiveData<List<NotificationModel>>()
    val activeNotifications: LiveData<List<NotificationModel>> = _activeNotifications

//    init {
//        viewModelScope.launch {
//            loadActiveNotifications()
//        }
//    }

    suspend fun loadActiveNotifications() = withContext(Dispatchers.IO) {
        val receivedList = repository.getActiveNotifications() ?: return@withContext

        _activeNotifications.postValue(receivedList)
    }
}