package com.example.yugiohapi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yugiohapi.data.model.Card
import com.example.yugiohapi.databinding.CardItemBinding

class CardAdapter()
    : RecyclerView.Adapter<CardAdapter.CardItemViewHolder>() {

    private var cardList: List<Card> = emptyList()

    inner class CardItemViewHolder(private val binding: CardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(p: Card) {
            binding.nameText.text = p.name ?: "Nombre no disponible"

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardItemViewHolder {
        val binding = CardItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CardItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardItemViewHolder, position: Int) {
        val card = cardList[position]
        holder.bind(card)
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    fun submitList(list: List<Card>) {
        cardList = list
        notifyDataSetChanged()
    }
}