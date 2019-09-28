package com.xyarim.users.di

import com.xyarim.users.ui.fragment.user_detail.UserDetailViewModel
import com.xyarim.users.ui.fragment.users.UsersViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { UsersViewModel(get()) }
    viewModel { UserDetailViewModel(get()) }
}

val appModule = listOf(viewModelsModule)
