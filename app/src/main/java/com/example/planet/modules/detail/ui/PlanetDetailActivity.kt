package com.example.planet.modules.detail.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.planet.R
import com.example.planet.core.arch.BaseActivity
import com.example.planet.core.common.Keys
import com.example.planet.databinding.ActivityPlanetDetailBinding
import com.example.planet.modules.detail.data.contract.DetailContract
import com.example.planet.modules.detail.data.datasource.DetailDataSource
import com.example.planet.modules.detail.data.presenter.DetailPresenter
import com.example.planet.modules.detail.data.response.DetailPlanetResponse

class PlanetDetailActivity : BaseActivity(), DetailContract.ViewInterface {

    private lateinit var mBinding: ActivityPlanetDetailBinding
    private lateinit var mPresenter: DetailContract.PresenterInterface
    private var mPlanetId: String? = null

    companion object {
        fun launch(appCompatActivity: AppCompatActivity, url: String) {
            val intent = Intent(appCompatActivity, PlanetDetailActivity::class.java).apply {
                putExtra(Keys.EXTRA_ID_PLANET, url)
            }
            appCompatActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_planet_detail)
        mPresenter = DetailPresenter(this, DetailDataSource())
        mPlanetId = intent.getStringExtra(Keys.EXTRA_ID_PLANET)
    }

    override fun onStart() {
        super.onStart()
        mPlanetId?.let {
            mPresenter.getDetailPlanetById(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.cleanUp()
    }

    override fun detailPlanet(planet: DetailPlanetResponse) {
        mBinding.item = planet
        mBinding.executePendingBindings()
    }

    override fun notifyError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}