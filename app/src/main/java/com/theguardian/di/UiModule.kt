package com.theguardian.di

import android.content.Context
import android.content.res.Resources
import com.theguardian.base.error.BaseErrorStateModelMapper
import com.theguardian.base.statemodel.ErrorStateModel
import com.theguardian.entity.error.CallException
import com.theguardian.ui.fragment.favoritearticlesfragment.FavoriteArticlesFragmentViewModel
import com.theguardian.ui.fragment.allarticlesfragment.AllArticlesFragmentViewModel
import com.theguardian.ui.fragment.detailsfragment.DetailsFragmentViewModel
import com.theguardian.ui.fragment.homefragment.HomeFragmentViewModel
import com.theguardian.ui.fragment.splashfragment.SplashFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module
internal val QualifierBaseErrorMapper = StringQualifier("BaseErrorMapper")


val viewModelModule = module {
    viewModel { HomeFragmentViewModel() }
    viewModel { AllArticlesFragmentViewModel(get(), get(),baseErrorStateModelMapper = get(QualifierBaseErrorMapper)) }
    viewModel { FavoriteArticlesFragmentViewModel(get()) }
    viewModel { DetailsFragmentViewModel() }
    viewModel { SplashFragmentViewModel(get()) }
}

val appModule = module {
    factory<(CallException) -> ErrorStateModel>(QualifierBaseErrorMapper) { BaseErrorStateModelMapper(get()) }
    single<Resources> { get<Context>().resources }
}

val appModuleList = listOf(
    viewModelModule,
    appModule
)