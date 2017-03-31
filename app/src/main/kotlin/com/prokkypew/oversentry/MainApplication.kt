package com.prokkypew.oversentry

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.facebook.drawee.backends.pipeline.Fresco
import com.prokkypew.oversentry.di.AppComponent
import com.prokkypew.oversentry.di.DaggerAppComponent
import io.fabric.sdk.android.Fabric
import io.realm.Realm


/**
 * Created by alexander.roman on 16.01.2017.
 */
open class MainApplication : Application() {
    companion object {
        @JvmStatic private lateinit var appComponent: AppComponent
    }

    fun getComponent(): AppComponent {
        return appComponent
    }

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())
        Fresco.initialize(this)
        initRealm()
        appComponent = buildComponent()
    }

    open fun initRealm() {
        Realm.init(this)
    }

    open fun buildComponent(): AppComponent {
        return DaggerAppComponent.builder()
                .build()
    }
}