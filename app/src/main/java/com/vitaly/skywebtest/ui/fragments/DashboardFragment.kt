package com.vitaly.skywebtest.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.vitaly.skywebtest.R
import com.vitaly.skywebtest.ui.adapters.ImageListAdapter
import com.vitaly.skywebtest.ui.fragments.baseframent.BaseFragment
import com.vitaly.skywebtest.utils.State
import com.vitaly.skywebtest.viewmodel.DashboardViewModel
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : BaseFragment<DashboardViewModel>() {


    override val viewModel: DashboardViewModel by viewModel()
    private lateinit var photosAdapter: ImageListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        initState()
    }

    private fun initRecycler() {
        photosAdapter = ImageListAdapter { viewModel.retry() }
        rv_images.adapter = photosAdapter
        viewModel.imageList.observe(viewLifecycleOwner, {
            photosAdapter.submitList(it)
        })
    }

    private fun initState() {
        main_txt_error.setOnClickListener { viewModel.retry() }
        viewModel.getState().observe(viewLifecycleOwner, Observer { state ->
            progress_bar.visibility =
                if (viewModel.listIsEmpty() && state == State.LOADING) View.VISIBLE else View.GONE
            main_txt_error.visibility =
                if (viewModel.listIsEmpty() && state == State.ERROR) View.VISIBLE else View.GONE
            if (!viewModel.listIsEmpty()) {
                photosAdapter.setState(state ?: State.DONE)
            }
        })
    }
}