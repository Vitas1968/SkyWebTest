package com.vitaly.skywebtest.ui.fragments.baseframent

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

abstract class BaseFragment<T : ViewModel> : Fragment() {
    protected abstract val viewModel: T
}