package com.joshtalks.task

import android.app.Application
import com.joshtalks.task.database.GlobalSharedPreferance
import com.joshtalks.task.inject.applicationModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule

class JoshTalkTaskApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(applicationModule.copy ( "BASE_APP_MODULE" ))
        import(androidModule(this@JoshTalkTaskApplication))
        GlobalSharedPreferance(this@JoshTalkTaskApplication)
    }

}