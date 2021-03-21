package com.dijon.mysubscribers.ui.subscriber

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dijon.mysubscribers.R
import com.dijon.mysubscribers.data.db.AppDataBase
import com.dijon.mysubscribers.data.db.dao.SubscriberDAO
import com.dijon.mysubscribers.repository.DatabaseDataSource
import com.dijon.mysubscribers.repository.SubscriberRepository

class SubscriberFragment : Fragment(R.layout.subscriber_fragment) {

    //implementação do factory
    private val viewModel: SubscriberViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val subscriberDAO: SubscriberDAO =
                    AppDataBase.getInstance(requireContext()).subscriberDAO

                val repository: SubscriberRepository = DatabaseDataSource(subscriberDAO)
                return SubscriberViewModel(repository) as T
            }
        }
    }
}