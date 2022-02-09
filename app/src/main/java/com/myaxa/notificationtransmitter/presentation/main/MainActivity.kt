package com.myaxa.notificationtransmitter.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.myaxa.notificationtransmitter.R
import com.myaxa.notificationtransmitter.data.repository.NotificationsRepository
import com.myaxa.notificationtransmitter.data.repository.NotificationsRepositoryImpl
import com.myaxa.notificationtransmitter.data.repository.NotificationsRepositoryProvider
import com.myaxa.notificationtransmitter.presentation.notifications_list.views.FragmentNotificationsList

class MainActivity : AppCompatActivity(), NotificationsRepositoryProvider,
    FragmentNotificationsList.ExecuteButtonListener {

    private lateinit var notificationsRepository: NotificationsRepository
    private var notificationsListFragment: FragmentNotificationsList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationsRepository = NotificationsRepositoryImpl(this)

        goToListPage(savedInstanceState)
    }

    override fun getNotificationsRepository(): NotificationsRepository = notificationsRepository

    private fun goToListPage(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            notificationsListFragment = FragmentNotificationsList.newInstance()
            notificationsListFragment?.apply {
                supportFragmentManager.beginTransaction()
                    .add(R.id.main_frame_layout, this, NOTIFICATIONS_LIST_FRAGMENT_TAG)
                    .commit()
            }
        } else {
            notificationsListFragment =
                supportFragmentManager
                    .findFragmentByTag(NOTIFICATIONS_LIST_FRAGMENT_TAG) as? FragmentNotificationsList
        }
    }

    companion object {
        const val NOTIFICATIONS_LIST_FRAGMENT_TAG = "NotificationsList"
    }

    override fun onStartButtonClicked() {
        notificationsRepository.startNotificationListening()
    }

    override fun onStopButtonClicked() {
        notificationsRepository.stopNotificationListening()
    }

}