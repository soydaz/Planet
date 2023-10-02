package com.example.planet.modules.user_registration.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.planet.R
import com.example.planet.core.arch.BaseActivity
import com.example.planet.core.message.MessageUtils
import com.example.planet.core.storage.local.room.entitys.User
import com.example.planet.databinding.ActivityRegisterUserBinding
import com.example.planet.modules.catalogue.ui.CatalogueActivity
import com.example.planet.modules.user_registration.data.contract.RegisterUserContract
import com.example.planet.modules.user_registration.data.datasource.RegisterUserDataSource
import com.example.planet.modules.user_registration.data.presenter.RegisterUserPresenter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RegisterUserActivity : BaseActivity(), RegisterUserContract.ViewInterface, DatePickerDialog.OnDateSetListener {

    private lateinit var mBinding: ActivityRegisterUserBinding
    private lateinit var mPresenter: RegisterUserPresenter
    private val mCalendar = Calendar.getInstance()
    private var mSelectedCountry: String = ""
    private var mSelectedBirthdayDate: String = ""
    private val mSimpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    companion object {
        fun launch(appCompatActivity: AppCompatActivity) {
            val intent = Intent(appCompatActivity, RegisterUserActivity::class.java)
            appCompatActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register_user)
        mPresenter = RegisterUserPresenter(this, RegisterUserDataSource())

        mBinding.uiCountrySpinner.setSelection(0)
        mBinding.uiCountrySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                mSelectedCountry = resources.getStringArray(R.array.country)[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        mBinding.uiBirthdayInput.setOnClickListener {
            showDatePickerDialog()
        }

        mBinding.actionRegisterUser.setOnClickListener {
            if (validateInfo()) {
                val user = User(nameUser = mBinding.uiNameUserInput.text.toString(), lastNameUser = mBinding.uiLastNameInput.text.toString(), motherLastNameUser = mBinding.uiMotherLastNameInput.text.toString(), birthdayDateUser = mSelectedBirthdayDate, countryUser = mSelectedCountry)
                mPresenter.addNewUser(user)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.cleanUp()
    }

    override fun registeredUserSuccess() {
        this@RegisterUserActivity.finish()
        CatalogueActivity.launch(this)
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

    private fun validateInfo(): Boolean {
        return validateName() && validateLastName() && validateMotherLastName() && validateCountry() && validateBirthday()
    }

    private fun validateName(): Boolean {
        val text: String = mBinding.uiNameUserInput.text.toString().trim()
        return if (text.isEmpty()) {
            notifyError(getString(R.string.error_field_name_empty))
            false
        } else if (text.length > 15) {
            notifyError(getString(R.string.error_field_name_too_long))
            false
        } else {
            true
        }
    }

    private fun validateLastName(): Boolean {
        val text: String = mBinding.uiLastNameInput.text.toString().trim()
        return if (text.isEmpty()) {
            notifyError(getString(R.string.error_field_last_name_empty))
            false
        } else if (text.length > 20) {
            notifyError(getString(R.string.error_field_last_too_long))
            false
        } else {
            true
        }
    }

    private fun validateMotherLastName(): Boolean {
        val text: String = mBinding.uiMotherLastNameInput.text.toString().trim()
        return if (text.isEmpty()) {
            notifyError(getString(R.string.error_field_mother_last_name_empty))
            false
        } else if (text.length > 20) {
            notifyError(getString(R.string.error_field_mother_last_name_too_long))
            false
        } else {
            true
        }
    }

    private fun validateCountry(): Boolean {
        val text: String = mSelectedCountry
        return if (text.isEmpty() || text == "Elige una opción") {
            notifyError(getString(R.string.error_field_contry_empty))
            false
        } else {
            true
        }
    }

    private fun validateBirthday(): Boolean {
        val text: String = mSelectedBirthdayDate
        return if (text.isEmpty()) {
            notifyError(getString(R.string.error_field_birthday_empty))
            false
        } else {
            true
        }
    }

    private fun showDatePickerDialog() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            DatePickerDialog(this, this, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        mCalendar.set(year, month, dayOfMonth)
        mSelectedBirthdayDate = mSimpleDateFormat.format(mCalendar.time)
        mBinding.uiBirthdayInput.text = mSelectedBirthdayDate
    }
}