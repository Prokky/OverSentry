package com.prokkypew.oversentry.di

import com.prokkypew.oversentry.model.parser.BlizzParser
import com.prokkypew.oversentry.model.parser.BlizzParserImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by alexander.roman on 23.01.2017.
 */
@Module
class DataModule {

    @Provides
    @Singleton
    internal fun provideBlizzParser(): BlizzParser {
        return BlizzParserImpl()
    }
}