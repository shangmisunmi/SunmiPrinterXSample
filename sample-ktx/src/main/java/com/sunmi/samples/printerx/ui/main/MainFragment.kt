package com.sunmi.samples.printerx.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.sunmi.samples.printerx.MainActivity
import com.sunmi.samples.printerx.R
import com.sunmi.samples.printerx.databinding.FragmentMainBinding
import com.sunmi.samples.printerx.ui.vm.PrinterViewModel

/**
 * 功能展示页
 */
class MainFragment : Fragment() {

    private lateinit var dataBinding : FragmentMainBinding

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.info.setOnClickListener {
            (activity as MainActivity).switchFragment(InfoFragment.newInstance(),
                addToBackStack = true
            )
        }
        dataBinding.ticket.setOnClickListener {
            (activity as MainActivity).switchFragment(TicketFragment.newInstance(),
                addToBackStack = true
            )
        }
        dataBinding.label.setOnClickListener {
            (activity as MainActivity).switchFragment(LabelFragment.newInstance(),
                addToBackStack = true
            )
        }
        dataBinding.cmd.setOnClickListener {
            (activity as MainActivity).switchFragment(CommandFragment.newInstance(),
                addToBackStack = true
            )
        }
        dataBinding.file.setOnClickListener {
            (activity as MainActivity).switchFragment(FileFragment.newInstance(),
                addToBackStack = true
            )
        }
        dataBinding.cash.setOnClickListener {
            (activity as MainActivity).switchFragment(CashFragment.newInstance(),
                addToBackStack = true
            )
        }
        dataBinding.lcd.setOnClickListener {
            (activity as MainActivity).switchFragment(LcdFragment.newInstance(),
                addToBackStack = true
            )
        }
        val viewModel: PrinterViewModel by viewModels()
        context?.let { viewModel.initPrinter(it) }
    }

}