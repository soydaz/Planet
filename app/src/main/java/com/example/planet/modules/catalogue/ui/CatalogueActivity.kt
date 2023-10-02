package com.example.planet.modules.catalogue.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.planet.R
import com.example.planet.core.arch.BaseActivity
import com.example.planet.core.message.MessageUtils
import com.example.planet.databinding.ActivityCatalogueBinding
import com.example.planet.modules.catalogue.data.contract.CatalogueContract
import com.example.planet.modules.catalogue.data.datasource.CatalogueDataSource
import com.example.planet.modules.catalogue.data.model.Planet
import com.example.planet.modules.catalogue.data.presenter.CataloguePresenter
import com.example.planet.modules.catalogue.ui.adapter.CatalogueAdapter
import com.example.planet.modules.detail.ui.PlanetDetailActivity

class CatalogueActivity : BaseActivity(), CatalogueContract.ViewInterface {

    private lateinit var mBinding: ActivityCatalogueBinding
    private lateinit var mPresenter: CatalogueContract.PresenterInterface
    private lateinit var mAdapter: CatalogueAdapter

    companion object {
        fun launch(appCompatActivity: AppCompatActivity) {
            val intent = Intent(appCompatActivity, CatalogueActivity::class.java)
            appCompatActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_catalogue)
        mPresenter = CataloguePresenter(this, CatalogueDataSource())
        mAdapter = CatalogueAdapter(this, CatalogueAdapter.EventListener { item ->
            val id = item.getPlanetId()
            if (id.isEmpty())
                notifyError(getString(R.string.sorry_planet_invalid))
            else
                PlanetDetailActivity.launch(this, item.getPlanetId())
        })
        mBinding.bindAdapter(mAdapter)
    }

    override fun onStart() {
        super.onStart()
        mPresenter.getPlanetsList()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.cleanUp()
    }

    override fun notifyPlanets(planetsList: ArrayList<Planet>) {
        mAdapter.submitList(planetsList)
    }

    override fun progressMessage(message: String?) {
        if (!message.isNullOrEmpty())
            MessageUtils.progress(this, message)
        else
            MessageUtils.stopProgress()
    }

    override fun notifyError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun ActivityCatalogueBinding.bindAdapter(adapter: CatalogueAdapter) {
        recycler.adapter = adapter
    }
}