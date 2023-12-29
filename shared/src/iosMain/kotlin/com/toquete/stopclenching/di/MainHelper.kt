package com.toquete.stopclenching.di

import com.toquete.stopclenching.presentation.main.MainViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainHelper : KoinComponent {

    private val mainViewModel: MainViewModel by inject()

    fun getMainViewModel(): MainViewModel = mainViewModel
}