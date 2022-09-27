package com.sunmi.samples.printerx.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sunmi.samples.printerx.R
import com.sunmi.samples.printerx.databinding.FragmentFileBinding
import com.sunmi.samples.printerx.ui.vm.FileViewModel

/**
 *   文件打印样例
 *   介绍如何通过SDK打印图片文件或PDF文件
 */
class FileFragment: Fragment() {

    private lateinit var dataBinding : FragmentFileBinding

    companion object {
        fun newInstance() = FileFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_file, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: FileViewModel by viewModels()
        dataBinding.model = viewModel
        val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == Activity.RESULT_OK){
                result.data?.data?.let { viewModel.testFile(it) }
            }
        }
        dataBinding.fileLocal.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "*/*"
            }
            launcher.launch(intent)
        }
    }
}