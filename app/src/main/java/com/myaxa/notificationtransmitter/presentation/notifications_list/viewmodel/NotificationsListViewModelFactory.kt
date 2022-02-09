package com.myaxa.notificationtransmitter.presentation.notifications_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myaxa.notificationtransmitter.data.repository.NotificationsRepository

class NotificationsListViewModelFactory(private val repository: NotificationsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotificationsListViewModel(repository) as T
    }
}