package com.joshtalks.task.inject

import com.joshtalks.task.ViewModalFactory
import com.joshtalks.task.database.AppDataBase
import com.joshtalks.task.repositories.MainActivityRepository
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val applicationModule=Kodein.Module("BASE_APP_MODULE"){
    bind<MainActivityRepository>() with singleton { MainActivityRepository() }
    bind<ViewModalFactory>() with singleton { ViewModalFactory(instance()) }
    bind<AppDataBase>() with singleton { AppDataBase.getInMemoryDatabase(instance()) }
}