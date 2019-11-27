package com.xyarim.users.ui.fragment.user_detail

import androidx.lifecycle.*
import com.xyarim.users.R
import com.xyarim.users.api.ApiService
import com.xyarim.users.api.UpdateUserRequest
import com.xyarim.users.api.User
import com.xyarim.users.utils.Event
import kotlinx.coroutines.launch

class UserDetailViewModel(private val apiService: ApiService) : ViewModel() {

    private val _userSavedEvent = MutableLiveData<Event<User>>()
    val userSavedEvent: LiveData<Event<User>> = _userSavedEvent

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    // Two-way databinding, exposing MutableLiveData
    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val email = MutableLiveData<String>()

    val validationMediator = MediatorLiveData<Boolean>()

    var user: User? = null

    init {
        val observer = Observer<String> { validationMediator.postValue(validateUser()) }
        validationMediator.apply {
            addSource(firstName, observer)
            addSource(lastName, observer)
            addSource(email, observer)
        }
    }

    fun setupUser(user: User) {
        firstName.value = user.firstName
        lastName.value = user.lastName
        email.value = user.email
        this.user = user
        validationMediator.postValue(true)
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
        _dataLoading.value = true
        viewModelScope.launch {
            val user = this@UserDetailViewModel.user!!.apply {
                this.firstName = this@UserDetailViewModel.firstName.value
                this.lastName = this@UserDetailViewModel.lastName.value
                this.email = this@UserDetailViewModel.email.value
            }
            try {

                apiService.updateUserAsync(user.id!!, UpdateUserRequest(user))
                _userSavedEvent.postValue(Event(user))

            } catch (e: Exception) {
                _snackbarText.postValue(Event(R.string.error_saving_user))
            } finally {
                _dataLoading.postValue(false)
            }
        }
    }

    private fun createUser() {
        _dataLoading.value = true
        viewModelScope.launch {
            try {
                val user =
                    User(
                        firstName = firstName.value,
                        lastName = lastName.value,
                        email = email.value
                    )
                apiService.createUserAsync(UpdateUserRequest(user))
                _userSavedEvent.postValue(Event(user))

            } catch (e: Exception) {
                _snackbarText.postValue(Event(R.string.error_saving_user))
            } finally {
                _dataLoading.postValue(false)
            }
        }
    }

    private fun validateUser(): Boolean {
        return when {
            firstName.value.isNullOrEmpty() -> false
            lastName.value.isNullOrEmpty() -> false
            !isValidEmail(email.value) -> false
            else -> true
        }
    }

}