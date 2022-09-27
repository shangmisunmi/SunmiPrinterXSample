package com.sunmi.samples.printerx.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sunmi.printerx.PrinterSdk
import com.sunmi.printerx.PrinterSdk.Printer
import com.sunmi.samples.printerx.R
import com.sunmi.samples.printerx.databinding.FragmentInfoBinding
import com.sunmi.samples.printerx.ui.vm.PrinterViewModel

/**
 * 打印信息页
 */
class InfoFragment : Fragment() {

    private lateinit var dataBinding : FragmentInfoBinding

    private lateinit var arrayAdapter: ArrayAdapter<PrinterSdk.Printer>

    companion object {
        fun newInstance() = InfoFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            val viewModel: PrinterViewModel by viewModels()
            dataBinding.model = viewModel
            dataBinding.lifecycleOwner = viewLifecycleOwner
            viewModel.showPrinters.observe(viewLifecycleOwner) {
                it?.let {
                    arrayAdapter.clear()
                    arrayAdapter.addAll(it)
                }
            }
            arrayAdapter = ArrayAdapter(it, android.R.layout.simple_spinner_item, arrayListOf())
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            dataBinding.spinner.let {
                it.adapter = arrayAdapter
                it.onItemSelectedListener = object : OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        viewModel.showPrinter(arrayAdapter.getItem(p2) as Printer)
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }

                }
            }
            viewModel.initPrinter(it)
        }
    }


}