package com.myaxa.notificationtransmitter.presentation.notifications_list.views

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myaxa.notificationtransmitter.R
import com.myaxa.notificationtransmitter.data.models.NotificationModel
import com.myaxa.notificationtransmitter.data.repository.NotificationsRepositoryProvider
import com.myaxa.notificationtransmitter.presentation.notifications_list.viewmodel.NotificationsListViewModel
import com.myaxa.notificationtransmitter.presentation.notifications_list.viewmodel.NotificationsListViewModelFactory
import kotlinx.coroutines.launch

class FragmentNotificationsList : Fragment(R.layout.fragment_notifications_list) {
    private var recycler: RecyclerView? = null
    private lateinit var adapter: NotificationAdapter
    private var executeButton: Button? = null
    private var executeButtonListener: ExecuteButtonListener? = null
    private var listenerStartupState = false

    private var notificationsList: MutableList<NotificationModel> = mutableListOf()

    private val viewModel: NotificationsListViewModel by viewModels {
        NotificationsListViewModelFactory((requireActivity() as NotificationsRepositoryProvider).getNotificationsRepository())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler(view)

        executeButton = view.findViewById(R.id.btn_refresh)
        executeButton?.text = "Start"
        executeButton?.setOnClickListener {
//            loadActiveNotifications()
//            updateData()
            when (listenerStartupState) {
                false -> {
                    executeButtonListener?.onStartButtonClicked()
                    executeButton?.text = "Stop"
                    listenerStartupState = true
                }
                true -> {
                    executeButtonListener?.onStopButtonClicked()
                    executeButton?.text = "Start"
                    listenerStartupState = false
                }
            }
        }

        viewModel.newNotification()?.observe(viewLifecycleOwner) { notification ->
            notificationsList.add(notification)
            updateData()
        }
//        viewModel.activeNotifications.observe(viewLifecycleOwner) { newNotificationsList ->
//            notificationsList = newNotificationsList.toMutableList()
//            updateData()
//        }
    }

    private fun setupRecycler(view: View) {
        adapter = NotificationAdapter()
        adapter.setHasStableIds(true)

        recycler = view.findViewById(R.id.rv_notifications_list)
        recycler?.adapter = adapter
        recycler?.layoutManager =
            LinearLayoutManager(requireContext()).apply {
                reverseLayout = true
                stackFromEnd = true
            }
    }

    private fun updateData() {
        (recycler?.adapter as? NotificationAdapter)?.apply {
            bindItems(notificationsList)
        }
    }

    private fun loadActiveNotifications() {
        lifecycleScope.launch {
            viewModel.loadActiveNotifications()
        }
    }

    override fun onDetach() {
        super.onDetach()
        recycler = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is ExecuteButtonListener) {
            executeButtonListener = context
        }
    }

    interface ExecuteButtonListener {
        fun onStartButtonClicked()
        fun onStopButtonClicked()
    }

    companion object {
        fun newInstance() = FragmentNotificationsList()
    }
}