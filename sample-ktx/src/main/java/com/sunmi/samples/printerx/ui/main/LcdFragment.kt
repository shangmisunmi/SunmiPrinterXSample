package com.sunmi.samples.printerx.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sunmi.samples.printerx.R
import com.sunmi.samples.printerx.databinding.FragmentLcdBinding
import com.sunmi.samples.printerx.ui.vm.CashViewModel
import com.sunmi.samples.printerx.ui.vm.LcdViewModel

/**
 *   客显使用样例
 *   客显仅指商米T1mini、T2mini设备上的液晶显示屏
 */
class LcdFragment: Fragment() {

    private lateinit var dataBinding : FragmentLcdBinding

    companion object {
        fun newInstance() = LcdFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_lcd, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: LcdViewModel by viewModels()
        dataBinding.model = viewModel
    }
}