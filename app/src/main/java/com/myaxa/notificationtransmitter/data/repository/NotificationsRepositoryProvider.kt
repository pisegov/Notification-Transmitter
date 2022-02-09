package com.myaxa.notificationtransmitter.data.repository

interface NotificationsRepositoryProvider {
    fun getNotificationsRepository(): NotificationsRepository
}