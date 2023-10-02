package com.example.planet.modules.user_registration.data.presenter

import com.example.planet.core.storage.local.room.entitys.User
import com.example.planet.modules.user_registration.data.contract.RegisterUserContract
import com.example.planet.modules.user_registration.data.datasource.RegisterUserDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class RegisterUserPresenter(private var mViewInterface: RegisterUserContract.ViewInterface, private var mDataSource: RegisterUserDataSource) : RegisterUserContract.PresenterInterface {

    private val mScope = CoroutineScope(Dispatchers.Main)

    override fun addNewUser(user: User) {
        mScope.launch {
            mDataSource.add(user)
            mViewInterface.registeredUserSuccess()
        }
    }

    override fun cleanUp() {
        mScope.cancel()
    }

}