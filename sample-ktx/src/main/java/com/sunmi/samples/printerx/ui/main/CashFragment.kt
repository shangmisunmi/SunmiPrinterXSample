package com.sunmi.samples.printerx.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sunmi.samples.printerx.R
import com.sunmi.samples.printerx.databinding.FragmentCashBinding
import com.sunmi.samples.printerx.ui.vm.CashViewModel

/**
 *   钱箱使用样例
 *   钱箱仅指具有钱箱接口的商米设备
 */
class CashFragment: Fragment() {

    private lateinit var dataBinding : FragmentCashBinding

    companion object {
        fun newInstance() = CashFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cash, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: CashViewModel by viewModels()
        dataBinding.model = viewModel
        dataBinding.lifecycleOwner = viewLifecycleOwner
        viewModel.status()
    }
}