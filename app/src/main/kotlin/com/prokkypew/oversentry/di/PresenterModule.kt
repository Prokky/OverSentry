package com.prokkypew.oversentry.di

import com.prokkypew.oversentry.model.BlizzParser
import com.prokkypew.oversentry.model.BlizzParserImpl
import dagger.Module
import dagger.Provides
import io.realm.Realm
import rx.subscriptions.CompositeSubscription
import javax.inject.Singleton


/**
 * Created by alexander.roman on 19.01.2017.
 */
@Module
class PresenterModule {

    @Provides
    @Singleton
    internal fun provideParser(): BlizzParser {
        return BlizzParserImpl()
    }

    @Provides
    internal fun provideCompositeSubscription(): CompositeSubscription {
        return CompositeSubscription()
    }

    @Provides
    internal fun provideRealm(): Realm {
        return Realm.getDefaultInstance()
    }
}