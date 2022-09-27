package com.sunmi.samples.printerx.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sunmi.samples.printerx.R
import com.sunmi.samples.printerx.databinding.FragmentLabelBinding
import com.sunmi.samples.printerx.ui.vm.LabelViewModel
import com.sunmi.samples.printerx.ui.vm.TicketViewModel

/**
 *   标签打印样例
 *   标签打印和黑标打印的接口使用方式相同，只是需要注意针对标签或黑标大小进行绘制的区域不同
 */
class LabelFragment: Fragment() {

    private lateinit var dataBinding : FragmentLabelBinding

    companion object {
        fun newInstance() = LabelFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_label, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: LabelViewModel by viewModels()
        dataBinding.model = viewModel
        dataBinding.lifecycleOwner = viewLifecycleOwner
    }

}