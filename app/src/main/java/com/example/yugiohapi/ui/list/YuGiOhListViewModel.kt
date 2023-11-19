package com.example.yugiohapi.ui.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yugiohapi.data.api.CardListApiModel
import com.example.yugiohapi.data.api.CardRepository
import com.example.yugiohapi.data.model.Card
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class YuGiOhListViewModel(): ViewModel() {

    private val repository = CardRepository.getInstance()
    private val _cardUi = MutableLiveData<List<Card>>()
    val cardUi: LiveData<List<Card>>
        get() = _cardUi

    private val observer = Observer<CardListApiModel> {
        val list:MutableList<Card> = mutableListOf()
        it.list?.forEach {
            val card = Card(it.id, it.name)
            list.add(card)
        }
        _cardUi.value = list

    }

    init {
        fetch()
    }

    private fun fetch() {
        repository.card.observeForever(observer)
        viewModelScope.launch {
            repository.fetch()
        }
    }

    override fun onCleared() {
        super.onCleared()
        repository
            .card
            .removeObserver(observer)
    }
}