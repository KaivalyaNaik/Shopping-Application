package com.example.shoppingapplication.ui

import androidx.lifecycle.*
import com.example.shoppingapplication.data.Item
import com.example.shoppingapplication.repository.ItemRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ItemViewModel(private val itemRepository: ItemRepository):ViewModel() {
    val allItems:LiveData<List<Item>> =itemRepository.allItems.asLiveData()

    fun delete(item: Item)=viewModelScope.launch {
        itemRepository.delete(item)
    }

    fun insert(item: Item)=viewModelScope.launch{
        itemRepository.insert(item)
    }
}

class ItemViewModelFactory(private val repository: ItemRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ItemViewModel::class.java))
            return ItemViewModel(repository) as T

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}