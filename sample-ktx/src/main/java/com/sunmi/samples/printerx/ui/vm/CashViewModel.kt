package com.sunmi.samples.printerx.ui.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CashViewModel: ViewModel(){

    var cashStatus = MutableLiveData<String>()

    /**
     * 控制打开钱箱
     * 监听钱箱打开返回接口执行情况，并不是实际前向开启状态，所以可以考虑不传入监听
     */
    fun cashSwitch() {
        try {
            selectPrinter?.cashDrawerApi()?.open(null)
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 钱箱状态更新
     */
    fun status() {
        try {
            if(selectPrinter?.cashDrawerApi()?.isOpen == true){
                cashStatus.value = "on"
            } else {
                cashStatus.value = "off"
            }
        } catch (e : Exception) {
            cashStatus.value = "none"
        }
    }
}