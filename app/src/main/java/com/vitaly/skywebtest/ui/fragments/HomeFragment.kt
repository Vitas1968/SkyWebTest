package com.vitaly.skywebtest.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.vitaly.skywebtest.R
import com.vitaly.skywebtest.di.injectDependenciesIntoHome
import com.vitaly.skywebtest.ui.fragments.baseframent.BaseFragment
import com.vitaly.skywebtest.utils.ProgressDialogFragment
import com.vitaly.skywebtest.utils.stringBuilder
import com.vitaly.skywebtest.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val FRAGMENT_DIALOG_TAG = "team-5d62-46bf-ab6"

class HomeFragment : BaseFragment<HomeViewModel>() {

    override lateinit var viewModel: HomeViewModel
    private val progressDialog: ProgressDialogFragment by lazy {
        ProgressDialogFragment()
    }
    private var message = "No data"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        injectDependenciesIntoHome()
        initViewModel()
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onStart() {
        super.onStart()
        setOnClickListenerBtnEnter()
        subscribeOnWeather()
        subscribeOnError()
        subscribeOnProgress()
        subscribeOnValidEmail()
        subscribeOnValidPassword()
        subscribeOnValidShared()
        subscribeOnEmailIsNotBlank()
        subscribeOnPasswordIsNotBlank()
    }

    private fun initViewModel() {
        val model: HomeViewModel by viewModel()
        viewModel = model
    }

    private fun setOnClickListenerBtnEnter() {
        enter_btn.setOnClickListener {
            val email = email_field_et.text.toString()
            val password = password_field_et.text.toString()
            viewModel.emailValid(email)
            viewModel.passwordValid(password)
        }
    }

    private fun subscribeOnPasswordIsNotBlank() {
        viewModel.subscribeOnPasswordIsNotBlank().observe(viewLifecycleOwner, { isNotBlank ->
            if (isNotBlank)
                setErrorPasswordField(getStringFromResource(R.string.reset_error))
            else
                setErrorPasswordField(getStringFromResource(R.string.password_is_empty))
        })
    }

    private fun subscribeOnEmailIsNotBlank() {
        viewModel.subscribeOnEmailIsNotBlank().observe(viewLifecycleOwner, { isNotBlank ->
            if (isNotBlank)
                setErrorEmailField(getStringFromResource(R.string.reset_error))
            else
                setErrorEmailField(getStringFromResource(R.string.email_is_empty))
        })
    }


    private fun subscribeOnWeather() {
        viewModel.subscribeOnWeatherReady().observe(viewLifecycleOwner, { weather ->
            val weatherMsg = arrayOf(
                weather.cityName,
                weather.temperature.toString(),
                weather.description,
                weather.humidity.toString()
            )
            message = stringBuilder(weatherMsg)

        })
    }

    private fun subscribeOnError() {
        viewModel.subscribeOnError().observe(viewLifecycleOwner, {
        })
    }

    private fun subscribeOnValidEmail() {
        viewModel.subscribeOnValidEmail().observe(viewLifecycleOwner, { validEmail ->
            if (!validEmail) setErrorEmailField(getStringFromResource(R.string.email_invalid))
            else setErrorEmailField(getStringFromResource(R.string.reset_error))
        })
    }

    private fun subscribeOnValidPassword() {
        viewModel.subscribeOnValidPassword().observe(viewLifecycleOwner, { validPass ->
            if (!validPass) setErrorPasswordField(getStringFromResource(R.string.password_invalid))
            else setErrorPasswordField(getStringFromResource(R.string.reset_error))
        })
    }

    private fun subscribeOnValidShared() {
        viewModel.subscribeOnValidShared().observe(viewLifecycleOwner, { validShared ->
            if (validShared) {
                cleanEditText()
                showSnackBar(message)
            }
        })
    }

    private fun subscribeOnProgress() {
        viewModel.subscribeOnProgress().observe(viewLifecycleOwner, { state ->
            if (state) showDialogProgress() else hideDialogProgress()
        })
    }

    private fun cleanEditText() {
        password_field_et.setText("")
        email_field_et.setText("")
    }

    private fun showSnackBar(message: String) {
        view?.let { view ->
            val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
            snackBar.setAction("Close") { snackBar.dismiss() }.show()
        }
    }
/*
    private fun checkEmailIsNotBlank(email: String) {
        if (email.isNotBlank()) {
            setErrorEmailField(getStringFromResource(R.string.reset_error))
            viewModel.checkValidEmail(email)
        } else {
            setErrorEmailField(getStringFromResource(R.string.email_is_empty))
        }
    }

 */
/*
    private fun checkPasswordIsNotBlank(password: String) {
        if (password.isNotBlank()) {
            setErrorPasswordField(getStringFromResource(R.string.reset_error))
            viewModel.checkValidPassword(password)
        } else {
            setErrorPasswordField(getStringFromResource(R.string.password_is_empty))
        }
    }

 */

    private fun getStringFromResource(resId: Int): String {
        var string = ""
        context?.getString(resId)?.let { string = it }
        return string
    }

    private fun setErrorEmailField(error: String) {
        email_layout.error = error
    }

    private fun setErrorPasswordField(error: String) {
        password_layout.error = error
    }

    private fun showDialogProgress() {
        activity?.supportFragmentManager?.let { fragMan ->
            progressDialog.show(fragMan, FRAGMENT_DIALOG_TAG)
        }
    }

    private fun hideDialogProgress() {
        progressDialog.dismiss()
    }
}