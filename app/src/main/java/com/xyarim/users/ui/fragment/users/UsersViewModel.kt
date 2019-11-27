package com.xyarim.users.ui.fragment.users

import androidx.lifecycle.*
import com.xyarim.users.api.ApiService
import com.xyarim.users.api.User
import com.xyarim.users.utils.Event
import kotlinx.coroutines.launch

class UsersViewModel(private val apiService: ApiService) : ViewModel() {

    private val _items = MutableLiveData<List<User>>().apply { value = emptyList() }
    val items: LiveData<List<User>> = _items

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _openUserEvent = MutableLiveData<Event<User>>()
    val openUserDetailEvent: LiveData<Event<User>> = _openUserEvent

    private val _createUserEvent = MutableLiveData<Event<Unit>>()
    val createUserEvent: LiveData<Event<Unit>> = _createUserEvent


    // This LiveData depends on another so we can use a transformation.
    val empty: LiveData<Boolean> = Transformations.map(_items) {
        it.isEmpty()
    }

    fun getUsers() {
        _dataLoading.postValue(true)
        viewModelScope.launch {
            try {
                val response = apiService.getUsersAsync()
                if (response.isSuccessful) {
                    _items.postValue(response.body())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _dataLoading.postValue(false)
            }
        }
    }

    /**
     * Called by Data Binding.
     */
    fun openUser(user: User) {
        _openUserEvent.value = Event(user)
    }

    fun createUser() {
        _createUserEvent.value = Event(Unit)
    }

    fun refresh() {
        getUsers()
    }
}