package com.myaxa.notificationtransmitter.presentation.notifications_list.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myaxa.notificationtransmitter.R
import com.myaxa.notificationtransmitter.data.models.NotificationModel

class NotificationAdapter : RecyclerView.Adapter<NotificationsViewHolder>() {
    private var notifications = listOf<NotificationModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationsViewHolder {
        return NotificationsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_notification, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return notifications.size
    }

    override fun onBindViewHolder(holder: NotificationsViewHolder, position: Int) {
        holder.onBind(notifications[position])
    }

    fun bindItems(newNotifications: List<NotificationModel>) {
        notifications = newNotifications
        notifyDataSetChanged()
    }

}

class NotificationsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val packageName = itemView.findViewById<TextView>(R.id.tv_package)
    private val message = itemView.findViewById<TextView>(R.id.tv_message)

    fun onBind(notification: NotificationModel) {
        packageName.text = notification.packageName
        message.text = notification.text
    }

}