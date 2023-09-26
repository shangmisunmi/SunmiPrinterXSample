package com.sunmi.samples.printerx.ui.vm

import android.graphics.BitmapFactory
import android.view.View
import androidx.lifecycle.ViewModel
import com.sunmi.printerx.enums.Command
import com.sunmi.samples.printerx.R

class LcdViewModel: ViewModel(){

    /**
     * 初始化并清屏操作
     * 其他操作可参考文档中Command命令
     * 注意在不支持客显的设备上调用接口将返回异常
     */
    fun lcdCtrl() {
        try {
            selectPrinter?.lcdApi()?.config(Command.INIT)
            selectPrinter?.lcdApi()?.config(Command.CLEAR)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    /**
     * 显示单行内容(T1mini\T2mini)
     * 可根据具体情况设置字体大小
     * 可根据显示情况决定是否拉伸字体到屏幕高度
     */
    fun lcdLine() {
        try {
            selectPrinter?.lcdApi()?.config(Command.CLEAR)
            selectPrinter?.lcdApi()?.showText("SUNMI", 32, true)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    /**
     * 显示多行内容(T1mini\T2mini)
     * 多行内容展示字体大小固定由所在行比例控制
     */
    fun lcdLines() {
        try {
            selectPrinter?.lcdApi()?.config(Command.CLEAR)
            selectPrinter?.lcdApi()?.showTexts(arrayOf("ABCDEF", "123456"), intArrayOf(1, 1))
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    /**
     * 显示位图(T1mini\T2mini)
     * 位图大小需要限制在128*40像素内
     */
    fun lcdLogo(view:View) {
        try {
            val option: BitmapFactory.Options = BitmapFactory.Options().apply {
                inScaled = false
            }
            val bitmap = BitmapFactory.decodeResource(view.context.resources, R.drawable.test, option)
            selectPrinter?.lcdApi()?.config(Command.CLEAR)
            selectPrinter?.lcdApi()?.showBitmap(bitmap)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    /**
     * 显示价格（D3mini)
     * showDigital可以显示7位的数字，并可在任意数字后插入位数分割符
     */
    fun lcdDigital(view: View) {
        try {
            selectPrinter?.lcdApi()?.showDigital("1.3.5.7.9.0.2")
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}