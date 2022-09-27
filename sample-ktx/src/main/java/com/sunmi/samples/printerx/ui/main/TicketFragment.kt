package com.sunmi.samples.printerx.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sunmi.samples.printerx.R
import com.sunmi.samples.printerx.databinding.FragmentTicketBinding
import com.sunmi.samples.printerx.ui.vm.TicketViewModel

/**
 *   票据打印样例
 */
class TicketFragment: Fragment() {

    private lateinit var dataBinding : FragmentTicketBinding

    companion object {
        fun newInstance() = TicketFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_ticket, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: TicketViewModel by viewModels()
        dataBinding.model = viewModel
    }
}