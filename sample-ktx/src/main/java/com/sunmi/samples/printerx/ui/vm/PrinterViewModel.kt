package com.sunmi.samples.printerx.ui.vm

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunmi.printerx.PrinterSdk
import com.sunmi.printerx.PrinterSdk.Printer
import com.sunmi.printerx.enums.PrinterInfo
import com.sunmi.printerx.enums.PrinterType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

var selectPrinter: Printer? = null

class PrinterViewModel : ViewModel() {

    private var printer: PrinterSdk.Printer? = null

    var showPrinters = MutableLiveData<MutableList<PrinterSdk.Printer>?>()

    var printerStatus = MutableLiveData<String>()

    var printerName = MutableLiveData<String>()

    var printerType = MutableLiveData<String>()

    var printerPaper = MutableLiveData<String>()

    fun initPrinter(context: Context) {
        PrinterSdk.getInstance().getPrinter(context, object : PrinterSdk.PrinterListen {

            override fun onDefPrinter(printer: PrinterSdk.Printer?) {
                if(selectPrinter == null) {
                    selectPrinter = printer
                }
            }

            override fun onPrinters(printers: MutableList<PrinterSdk.Printer>?) {
                showPrinters.postValue(printers)
            }

        })
    }

    fun changeSelectPrinter() {
        selectPrinter = printer
    }

    /**
     * 获取状态等信息
     * 因为是阻塞操作最好在协程或线程中获取
     */
    fun showPrinter(printer: Printer) {
        this.printer = printer
        viewModelScope.launch(Dispatchers.IO) {
            printerStatus.postValue(printer.queryApi().status.name)
            printerName.postValue(printer.queryApi().getInfo(PrinterInfo.NAME))
            printerType.postValue(printer.queryApi().getInfo(PrinterInfo.TYPE))
            printerPaper.postValue(printer.queryApi().getInfo(PrinterInfo.PAPER))
            //如需要对打印纸类型做判断可以参考以下方法
            //checkPrinterPaper(printer)
            //其他信息仅商米内置打印机支持不展示在UI上，可根据业务需要选择展示
            //如 printer.queryApi().getInfo(PrinterInfo.DENSITY) ...
        }
    }

    /**
     * 一般打印小票可能需要打印纸张判断用来决定小票布局
     * 可参考此方法进行判断
     */
    fun checkPrinterPaper(printer: Printer) {
        printer?.let {
            val paper = it.queryApi().getInfo(PrinterInfo.PAPER)
            val printerType = it.queryApi().getInfo(PrinterInfo.TYPE)
            when(paper) {
                "58mm" -> println("打印机纸张58mm")
                "80mm" -> println("打印机纸张80mm")
                else -> {
                    if(printerType == PrinterType.THERMAL.toString())  {
                        println("打印机纸张58mm")
                    } else {
                        println("非热敏打印机")
                    }
                }
            }
        }
    }

    fun releaseSdk() {
        PrinterSdk.getInstance().destroy()
    }

}

