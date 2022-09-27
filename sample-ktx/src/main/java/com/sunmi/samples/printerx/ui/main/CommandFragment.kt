package com.sunmi.samples.printerx.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sunmi.samples.printerx.R
import com.sunmi.samples.printerx.databinding.FragmentCmdBinding
import com.sunmi.samples.printerx.ui.vm.CmdViewModel
import com.sunmi.samples.printerx.ui.vm.LabelViewModel

/**
 *   指令打印样例
 *   针对了解打印指令并已经设计过打印指令的开发者通过发送指令打印
 *   有两种发送方式：
 *   1、通过SDK提供的SDK接口发送
 *   2、仅针对商米内置打印机还可通过连接设备中内置虚拟蓝牙发送
 */
class CommandFragment: Fragment() {

    private lateinit var dataBinding : FragmentCmdBinding

    companion object {
        fun newInstance() = CommandFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cmd, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: CmdViewModel by viewModels()
        dataBinding.model = viewModel
    }
}