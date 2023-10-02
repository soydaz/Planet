package com.example.planet.modules.user_registration.data.contract

import com.example.planet.core.storage.local.room.entitys.User


class RegisterUserContract {

    interface PresenterInterface {
        fun addNewUser(user: User)
        fun cleanUp()
    }

    interface ViewInterface {
        fun registeredUserSuccess()
        fun progressMessage(message: String?)
        fun notifyError(message: String)
    }

}