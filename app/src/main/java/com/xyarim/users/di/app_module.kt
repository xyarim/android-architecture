package com.xyarim.users.di

import com.xyarim.users.ui.fragment.users.UsersViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
   viewModel { UsersViewModel(get()) }
}

val appModule = listOf(viewModelsModule)
