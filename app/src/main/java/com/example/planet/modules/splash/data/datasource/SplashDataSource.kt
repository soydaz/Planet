package com.example.planet.modules.splash.data.datasource

import com.example.planet.App
import com.example.planet.core.storage.local.room.entitys.User

class SplashDataSource {

    private var dao = App.getRoomInstance().getUsersDao()

    suspend fun getUsersList(): List<User> {
        return dao.getUsersList()
    }
}