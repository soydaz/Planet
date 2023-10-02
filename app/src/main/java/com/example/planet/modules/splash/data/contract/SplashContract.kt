package com.example.planet.modules.splash.data.contract

import com.example.planet.core.storage.local.room.entitys.User

class SplashContract {

    interface PresenterInterface {
        fun getUsers()
        fun cleanUp()
    }

    interface ViewInterface {
        fun getUserList(users: List<User>)
        fun notifyError(message: String)
    }

}