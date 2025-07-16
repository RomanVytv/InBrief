 package com.romanvytv.inbrief.di

import com.romanvytv.inbrief.data.repo.BookSummaryRepository
import com.romanvytv.inbrief.data.repo.IBookSummaryRepository
import com.romanvytv.inbrief.service.MediaPlayerServiceConnection
import com.romanvytv.inbrief.ui.feature.SummaryViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<IBookSummaryRepository> { BookSummaryRepository(androidContext()) }
    single<MediaPlayerServiceConnection> { MediaPlayerServiceConnection(androidContext()) }
    viewModel { SummaryViewModel() }
}
