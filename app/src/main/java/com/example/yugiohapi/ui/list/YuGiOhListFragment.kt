package com.example.yugiohapi.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.yugiohapi.R
import com.example.yugiohapi.databinding.FragmentYuGiOhListBinding
import com.example.yugiohapi.ui.adapter.CardAdapter


class YuGiOhListFragment : Fragment() {
    private val viewModel:YuGiOhListViewModel by viewModels()
    private lateinit var binding: FragmentYuGiOhListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentYuGiOhListBinding.inflate(inflater,
            container,
            false

        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.cardList
        val adapter = CardAdapter()
        recyclerView.adapter = adapter

        viewModel.cardUi.observe(viewLifecycleOwner) { cardList ->

            adapter.submitList(cardList)
        }
    }
}