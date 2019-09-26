package com.xyarim.users.ui.fragment.users

import androidx.lifecycle.*
import com.xyarim.users.api.ApiService
import com.xyarim.users.api.User
import com.xyarim.users.utils.Event
import kotlinx.coroutines.launch

class UsersViewModel(val apiService: ApiService) : ViewModel() {

    private val _items = MutableLiveData<List<User>>().apply { value = emptyList() }
    val items: LiveData<List<User>> = _items

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _openUserEvent = MutableLiveData<Event<Int>>()
    val openUserEvent: LiveData<Event<Int>> = _openUserEvent


    // This LiveData depends on another so we can use a transformation.
    val empty: LiveData<Boolean> = Transformations.map(_items) {
        it.isEmpty()
    }

    fun getUsers() {
        viewModelScope.launch {
            try {
                val response = apiService.getUsers().await()
                if (response.isSuccessful) {
                    _items.postValue(response.body())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {

            }
        }
    }

    /**
     * Called by Data Binding.
     */
    fun openUser(taskId: Int) {
        _openUserEvent.value = Event(taskId)
    }

    fun refresh() {
        getUsers()
    }
}