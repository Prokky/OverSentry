package com.prokkypew.oversentry

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.prokkypew.oversentry.di.AppComponent
import com.prokkypew.oversentry.di.DaggerAppComponent
import com.prokkypew.oversentry.di.PresenterModule
import io.realm.Realm


/**
 * Created by alexander.roman on 16.01.2017.
 */
class MainApplication : Application() {
    companion object {
        @JvmStatic private lateinit var appComponent: AppComponent
    }

    fun getComponent(): AppComponent {
        return appComponent
    }

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        Realm.init(this)
        appComponent = DaggerAppComponent.builder()
                .presenterModule(PresenterModule())
                .build()
    }
}