package com.sunmi.samples.printerx.ui.vm

import android.net.Uri

import androidx.lifecycle.ViewModel
import com.sunmi.printerx.api.PrintResult


class FileViewModel : ViewModel() {

    /**
     * 指定地址文件打印，可打印pdf、图片
     */
    fun testUrl() {
        selectPrinter?.fileApi()
            ?.printFile("https://img0.baidu.com/it/u=285137529,1114434781&fm=253&fmt=auto&app=138&f=JPEG?w=489&h=396", object: PrintResult() {
                override fun onResult(resultCode: Int, message: String?) {
                }

            })
    }

    /**
     * 通过选择文件打印，可打印pdf、图片
     */
    fun testFile(uri: Uri) {
        selectPrinter?.fileApi()?.printFile(uri.toString(), object : PrintResult(){
            override fun onResult(resultCode: Int, message: String?) {
            }

        })

    }



}