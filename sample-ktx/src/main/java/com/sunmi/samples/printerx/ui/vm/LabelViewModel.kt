package com.sunmi.samples.printerx.ui.vm

import android.graphics.BitmapFactory
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sunmi.printerx.api.PrintResult
import com.sunmi.printerx.enums.HumanReadable
import com.sunmi.printerx.enums.ImageAlgorithm
import com.sunmi.printerx.enums.Rotate
import com.sunmi.printerx.enums.Shape
import com.sunmi.printerx.style.*
import com.sunmi.samples.printerx.R

class LabelViewModel : ViewModel() {

    var count = MutableLiveData("1")

    /**
     * 打印一张30*20mm 只包含内容的小标签
     * 商米打印机可根据1mm = 8像素的关系构建标签内容
     */
    fun printLabel1() {
        selectPrinter?.canvasApi()?.run {
            initCanvas(BaseStyle.getStyle().setWidth(240).setHeight(160))
            renderArea(AreaStyle.getStyle().setStyle(Shape.BOX).setPosX(0).setPosY(0).setWidth(240).setHeight(159))
            renderText("品牌：SUNMI", TextStyle.getStyle().setTextSize(32).enableBold(true).setPosX(10).setPosY(10))
            renderText("商米是一家以“利他心”为核心价值观的物联网科技公司", TextStyle.getStyle().setTextSize(20).setPosX(60)
                .setPosY(50).setWidth(160).setHeight(100))
            printCanvas(count.value?.toInt()?:1, object : PrintResult() {
                override fun onResult(resultCode: Int, message: String?) {
                    if(resultCode == 0) {
                        //打印完成
                    } else {
                        //打印失败
                        println(selectPrinter?.queryApi()?.status)
                    }
                }
            })
        }
    }

    /**
     * 打印一张商品信息标签
     */
    fun printLabel2() {
        selectPrinter?.canvasApi()?.run {
            initCanvas(BaseStyle.getStyle().setWidth(384).setHeight(220))
            renderArea(AreaStyle.getStyle().setStyle(Shape.BOX).setPosX(0).setPosY(0).setWidth(384).setHeight(219))
            renderText("可口可乐(2L)", TextStyle.getStyle().setTextSize(30).enableBold(true)
                .setPosX(10).setPosY(20))
            renderText("2L", TextStyle.getStyle().setTextSize(20)
                .setPosX(10).setPosY(60))
            renderText("200000", TextStyle.getStyle().setTextSize(20)
                .setPosX(10).setPosY(85))
            renderText("瓶", TextStyle.getStyle().setTextSize(24)
                .setPosX(10).setPosY(130))
            renderBarCode("12345678", BarcodeStyle.getStyle().setPosX(200).setPosY(60)
                .setReadable(HumanReadable.POS_TWO).setDotWidth(2).setBarHeight(60).setWidth(160))
            renderText("￥ 7.8", TextStyle.getStyle().setTextSize(16).setTextWidthRatio(1).setTextHeightRatio(1)
                .enableBold(true).setPosX(190).setPosY(160))
            printCanvas(count.value?.toInt()?:1, object : PrintResult() {
                override fun onResult(resultCode: Int, message: String?) {
                    if(resultCode == 0) {
                        //打印完成
                    } else {
                        //打印失败
                        println(selectPrinter?.queryApi()?.status)
                    }
                }
            })
        }
    }

    /**
     * 打印一张品牌说明标签
     */
    fun printLabel3(view: View) {
        selectPrinter?.canvasApi()?.run {
            initCanvas(BaseStyle.getStyle().setWidth(420).setHeight(220))
            renderArea(AreaStyle.getStyle().setStyle(Shape.BOX).setPosX(0).setPosY(0).setWidth(420).setHeight(219))
            renderQrCode("www.sunmi.com", QrStyle.getStyle().setDot(3).setPosX(20).setPosY(20).setWidth(120).setHeight(120))
            renderText("SUNMITECH", TextStyle().setPosX(140).setPosY(20).setRotate(Rotate.ROTATE_90))
            renderBarCode("4006666509", BarcodeStyle.getStyle().setPosX(180).setPosY(50)
                .setReadable(HumanReadable.POS_TWO).setWidth(220).setHeight(90))
            val option: BitmapFactory.Options = BitmapFactory.Options().apply {
                inScaled = false
            }
            var bitmap = BitmapFactory.decodeResource(view.context.resources, R.drawable.sunmi, option)
            renderBitmap(bitmap, BitmapStyle.getStyle().setAlgorithm(ImageAlgorithm.DITHERING)
                .setPosX(20).setPosY(150).setWidth(100).setHeight(60))
            printCanvas(count.value?.toInt()?:1, object : PrintResult() {
                override fun onResult(resultCode: Int, message: String?) {
                    if(resultCode == 0) {
                        //打印完成
                    } else {
                        //打印失败
                        println(selectPrinter?.queryApi()?.status)
                    }
                }
            })
        }
    }
}