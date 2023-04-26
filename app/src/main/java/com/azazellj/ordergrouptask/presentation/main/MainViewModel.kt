package com.azazellj.ordergrouptask.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azazellj.ordergrouptask.domain.model.Query
import com.azazellj.ordergrouptask.domain.repository.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val mainRepository: MainRepository,
) : ViewModel() {

    private val _currentQuery: MutableStateFlow<Query?> = MutableStateFlow(null)
    val currentQuery: StateFlow<Query?> = _currentQuery

    fun fetchData(
        query: String,
    ) {
        viewModelScope.launch {
            mainRepository.fetchQuery(
                query = query,
            ).onSuccess { query ->
                _currentQuery.tryEmit(query)
            }.onFailure { t: Throwable ->
                // log error?
                // need clear?
                _currentQuery.tryEmit(null)
            }
        }
    }
}