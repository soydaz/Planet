package com.example.planet.modules.splash.data.presenter

import com.example.planet.modules.splash.data.contract.SplashContract
import com.example.planet.modules.splash.data.datasource.SplashDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class SplashPresenter(private var mViewInterface: SplashContract.ViewInterface, private var mDataSource: SplashDataSource) : SplashContract.PresenterInterface {

    private val mScope = CoroutineScope(Dispatchers.Main)

    override fun getUsers() {
        mScope.launch {
            mViewInterface.getUserList(mDataSource.getUsersList())
        }
    }

    override fun cleanUp() {
        mScope.cancel()
    }

}