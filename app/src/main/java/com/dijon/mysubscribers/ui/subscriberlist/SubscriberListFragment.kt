package com.dijon.mysubscribers.ui.subscriberlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.dijon.mysubscribers.R
import com.dijon.mysubscribers.data.db.entity.SubscriberEntity
import kotlinx.android.synthetic.main.subscriber_list_fragment.*

class SubscriberListFragment : Fragment(R.layout.subscriber_list_fragment) {

    private lateinit var viewModel: SubscriberListViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val subscriberListAdapter = SubscriberListAdapter(
            listOf(
                SubscriberEntity(1, "Dijon", "dijon@hotmail.com"),
                SubscriberEntity(2, "Braga", "braga@hotmail.com")
            )
        )

        //otimização do kotlin para renomear apenas uma vez, caso o nome do recyclerview mudar
        recycler_subscribers.run {
            setHasFixedSize(true)//todos os itens irão ser do mesmo tamanho
            adapter = subscriberListAdapter
        }
    }
}