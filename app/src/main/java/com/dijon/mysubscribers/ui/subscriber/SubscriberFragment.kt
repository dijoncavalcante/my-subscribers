package com.dijon.mysubscribers.ui.subscriber

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dijon.mysubscribers.R
import com.dijon.mysubscribers.data.db.AppDataBase
import com.dijon.mysubscribers.data.db.dao.SubscriberDAO
import com.dijon.mysubscribers.extension.hideKeyboard
import com.dijon.mysubscribers.repository.DatabaseDataSource
import com.dijon.mysubscribers.repository.SubscriberRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.subscriber_fragment.*

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeEvents()//observar os eventos do view model e escutar os liveData
        setListeners()//escutar os eventos de click da view e outros eventos
    }

    private fun observeEvents() {
        viewModel.subscriberStateEventData.observe(viewLifecycleOwner) { subscriberState ->
            when (subscriberState) {
                is SubscriberViewModel.SubscriberState.Inserted -> {
                    //limpar os campos
                    clearFields()
                    //forçar esconder teclado
                    hideKeyboard()

                    requireView().requestFocus()

                    findNavController().popBackStack()
                }
            }
        }

        viewModel.messageEventData.observe(viewLifecycleOwner) { stringResId ->
            Snackbar.make(requireView(), stringResId, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun clearFields() {
        input_name.text?.clear()
        input_email.text?.clear()
    }

    private fun hideKeyboard() {
        val parentActivity = requireActivity()
        if (parentActivity is AppCompatActivity) {
            parentActivity.hideKeyboard()
        }
    }

    //escutando o botao para criar um subscriber
    private fun setListeners() {
        button_subscriber.setOnClickListener {
            val name = input_name.text.toString()
            val email = input_email.text.toString()
            viewModel.addSubscriber(name, email)
        }
    }
}