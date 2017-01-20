package com.prokkypew.oversentry.di

import com.prokkypew.oversentry.model.ParserModel
import com.prokkypew.oversentry.model.ParserModelImpl
import dagger.Module
import dagger.Provides
import rx.subscriptions.CompositeSubscription
import javax.inject.Singleton


/**
 * Created by alexander.roman on 19.01.2017.
 */
@Module
class PresenterModule {

    @Provides
    @Singleton
    internal fun providesParserModel(): ParserModel {
        return ParserModelImpl()
    }

    @Provides
    internal fun provideCompositeSubscription(): CompositeSubscription {
        return CompositeSubscription()
    }

}