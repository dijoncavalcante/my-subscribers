package com.dijon.mysubscribers.ui.subscriberlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dijon.mysubscribers.R
import com.dijon.mysubscribers.data.db.AppDataBase
import com.dijon.mysubscribers.data.db.dao.SubscriberDAO
import com.dijon.mysubscribers.extension.navigateWithAnimations
import com.dijon.mysubscribers.repository.DatabaseDataSource
import com.dijon.mysubscribers.repository.SubscriberRepository
import kotlinx.android.synthetic.main.subscriber_list_fragment.*

class SubscriberListFragment : Fragment(R.layout.subscriber_list_fragment) {

    //implementação do factory
    private val viewModel: SubscriberListViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val subscriberDAO: SubscriberDAO =
                    AppDataBase.getInstance(requireContext()).subscriberDAO

                val repository: SubscriberRepository = DatabaseDataSource(subscriberDAO)
                return SubscriberListViewModel(repository) as T
            }
        }
    }

    //ponto de partida do fragment
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModelEvents()
        configureViewListeners()
    }

    private fun observeViewModelEvents() {
        viewModel.allSubscribersEvent.observe(viewLifecycleOwner) { allSubscribers ->
            val subscriberListAdapter = SubscriberListAdapter(allSubscribers)

            //otimização do kotlin para renomear apenas uma vez, caso o nome do recyclerview mudar
            with(recycler_subscribers) {
                setHasFixedSize(true)//todos os itens irão ser do mesmo tamanho
                adapter = subscriberListAdapter
            }
        }
    }

    private fun configureViewListeners(){
        fabAddSubscriber.setOnClickListener{
            findNavController().navigateWithAnimations(R.id.subscriberFragment)
        }
    }
}