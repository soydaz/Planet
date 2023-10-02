package com.example.planet.modules.user_registration.data.datasource

import com.example.planet.App
import com.example.planet.core.storage.local.room.entitys.User

class RegisterUserDataSource {

    private var dao = App.getRoomInstance().getUsersDao()

    suspend fun add(user: User) {
        dao.insert(user)
    }

}