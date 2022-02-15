package com.myaxa.notificationtransmitter.data.models

import kotlinx.serialization.Serializable

@Serializable
data class NotificationModel(
    val packageName: String,
    val text: String
)