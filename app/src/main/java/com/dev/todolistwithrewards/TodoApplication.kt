package com.dev.todolistwithrewards

import android.app.Application
import com.facebook.stetho.Stetho

class TodoApplication : Application() {
    private val database by lazy { TaskItemDatabase.getDatabase(this) }
    val repository by lazy { TaskItemRepository(database.taskItemDao()) }
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}