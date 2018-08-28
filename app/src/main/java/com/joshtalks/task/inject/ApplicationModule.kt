package com.joshtalks.task.inject

import com.joshtalks.task.ViewModalFactory
import com.joshtalks.task.dao.DatabaseManager
import com.joshtalks.task.dao.ItemBoundaryCallback
import com.joshtalks.task.database.AppDataBase
import com.joshtalks.task.database.GlobalSharedPreferance
import com.joshtalks.task.repositories.MainActivityRepository
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val applicationModule = Kodein.Module("BASE_APP_MODULE") {
    bind<MainActivityRepository>() with singleton { MainActivityRepository() }
    bind<DatabaseManager>() with singleton { DatabaseManager(instance()) }
    bind<ItemBoundaryCallback>() with singleton { ItemBoundaryCallback(instance(), instance()) }
    bind<AppDataBase>() with singleton { AppDataBase.getInMemoryDatabase(instance()) }
    bind<ViewModalFactory>() with singleton { ViewModalFactory(instance(), instance()) }
    bind<GlobalSharedPreferance>() with singleton { GlobalSharedPreferance(instance()) }
}