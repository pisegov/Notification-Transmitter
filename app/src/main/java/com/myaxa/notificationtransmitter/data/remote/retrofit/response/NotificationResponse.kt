package com.myaxa.notificationtransmitter.data.remote.retrofit.response

import kotlinx.serialization.Serializable

@Serializable
data class NotificationResponse(
    val packageName: String = "",
    val text: String = ""
)