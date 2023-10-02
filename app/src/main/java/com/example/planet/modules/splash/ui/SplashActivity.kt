package com.example.planet.modules.splash.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.planet.R
import com.example.planet.core.storage.local.room.entitys.User
import com.example.planet.databinding.ActivityLauncherBinding
import com.example.planet.modules.catalogue.ui.CatalogueActivity
import com.example.planet.modules.splash.data.contract.SplashContract
import com.example.planet.modules.splash.data.datasource.SplashDataSource
import com.example.planet.modules.splash.data.presenter.SplashPresenter
import com.example.planet.modules.user_registration.ui.RegisterUserActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity(), SplashContract.ViewInterface {

    private lateinit var mBinding: ActivityLauncherBinding
    private lateinit var mPresenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_launcher)
        mPresenter = SplashPresenter(this, SplashDataSource())
        val runnable = Runnable { startFlow() }
        Handler(Looper.getMainLooper()).postDelayed( runnable,5_000L)
    }

    override fun getUserList(users: List<User>) {
        if (users.isEmpty()) {
            this@SplashActivity.finish()
            RegisterUserActivity.launch(this)
        } else {
            this@SplashActivity.finish()
            CatalogueActivity.launch(this)
        }
    }

    override fun notifyError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.cleanUp()
    }

    private fun startFlow() {
        mPresenter.getUsers()
    }

}