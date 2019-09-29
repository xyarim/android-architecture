package com.xyarim.users.ui.fragment.user_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xyarim.users.R
import com.xyarim.users.api.ApiService
import com.xyarim.users.api.UpdateUserRequest
import com.xyarim.users.api.User
import com.xyarim.users.utils.Event
import kotlinx.coroutines.launch

class UserDetailViewModel(val apiService: ApiService) : ViewModel() {
    private val _userSavedEvent = MutableLiveData<Event<User>>()
    val userSavedEvent: LiveData<Event<User>> = _userSavedEvent

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    // Two-way databinding, exposing MutableLiveData
    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    var user: User? = null

    fun setupUser(user: User) {
        firstName.value = user.firstName
        lastName.value = user.lastName
        email.value = user.email
        this.user = user
    }

    fun saveUser() {
        if (validateUser())
            if (user == null) {
                createUser()
            } else {
                updateUser()
            }
    }

    private fun updateUser() {
        viewModelScope.launch {
            val user = this@UserDetailViewModel.user!!.apply {
                this.firstName = this@UserDetailViewModel.firstName.value
                this.lastName = this@UserDetailViewModel.lastName.value
                this.email = this@UserDetailViewModel.email.value
            }
            val response = apiService.updateUser(user.id!!, UpdateUserRequest(user)).await()
            Log.d("updateUser", response.toString())
            _userSavedEvent.postValue(Event(user))
        }
    }

    private fun createUser() {
        viewModelScope.launch {
            val user = User(firstName = firstName.value, lastName = lastName.value, email = email.value)
            val response = apiService.createUser(UpdateUserRequest(user)).await()
            Log.d("createUser", response.toString())
            _userSavedEvent.postValue(Event(user))
        }
    }


    private fun validateUser(): Boolean {
        if (firstName.value.isNullOrEmpty()) {
            _snackbarText.value = Event(R.string.empty_name_message)
            return false
        } else if (lastName.value.isNullOrEmpty()) {
            _snackbarText.value = Event(R.string.empty_last_name_message)
            return false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
            _snackbarText.value = Event(R.string.empty_email_message)
            return false
        }
        return true
    }

}