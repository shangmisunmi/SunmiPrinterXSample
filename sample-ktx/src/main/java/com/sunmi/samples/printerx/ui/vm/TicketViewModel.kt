package com.sunmi.samples.printerx.ui.vm

import android.graphics.BitmapFactory
import android.view.View
import androidx.lifecycle.ViewModel
import com.sunmi.printerx.api.PrintResult
import com.sunmi.printerx.enums.*
import com.sunmi.printerx.style.*
import com.sunmi.samples.printerx.R

class TicketViewModel : ViewModel(){

    /**
     * 文本打印例子
     * 通过initLine设置行内大小和行内文本对齐方式
     * 通过printText直接打印整行内容
     * 通过addText控制每次打印的内容
     * 局部反白和文本倒置需要Sunmi 3.0字体支持
     */
    fun printText() {
        selectPrinter?.lineApi()?.run {
            initLine(BaseStyle.getStyle())
            printText("这行内容将直接打印出", TextStyle.getStyle())
            addText("不同风格的内容:", TextStyle.getStyle())
            addText("加粗", TextStyle.getStyle().enableBold(true))
            addText("下划线", TextStyle.getStyle().enableUnderline(true))
            addText("删除线", TextStyle.getStyle().enableStrikethrough(true))
            addText("倾斜", TextStyle.getStyle().enableItalics(true))
            addText("\n", TextStyle.getStyle())
            autoOut()
        }
    }

    /**
     * 按列打印文本例子
     *  | * | * | * |
     *  |*  | * |  *|
     *  |*  |*  |*  |
     */
    fun printTexts() {
        selectPrinter?.lineApi()?.run {
            val textStyle = TextStyle.getStyle().setAlign(Align.CENTER)
            printTexts(arrayOf("第一列","第二列","第三列"), intArrayOf(1, 1, 1), arrayOf(textStyle, textStyle, textStyle))
            printTexts(arrayOf("第一列","第二列","第三列"), intArrayOf(1, 1, 1),
                arrayOf(TextStyle.getStyle().setAlign(Align.LEFT),
                    TextStyle.getStyle().setAlign(Align.CENTER),
                    TextStyle.getStyle().setAlign(Align.RIGHT)))
            val textStyle1 = TextStyle.getStyle().setAlign(Align.LEFT)
            printTexts(arrayOf("第一列","第二列","第三列"), intArrayOf(1, 1, 1), arrayOf(textStyle1, textStyle1, textStyle1))
            autoOut()
        }
    }

    /**
     * 打印条码例子
     * 演示打印几段条码内容
     * 如果条码内容过长会导致显示异常，此时需要根据需要自定义条码的宽度
     * （自定义条码宽度可能影响条码的识别效果）
     */
    fun printBar() {
        selectPrinter?.lineApi()?.run {
            val barcodeStyle = BarcodeStyle.getStyle().setAlign(Align.CENTER).setDotWidth(2).setBarHeight(100).setReadable(HumanReadable.POS_TWO)
            printBarCode("0123456789", barcodeStyle)
            printBarCode("0123456789ABCDEFG", barcodeStyle)
            barcodeStyle.setWidth(384)
            printBarCode("0123456789ABCDEFG", barcodeStyle)
            autoOut()
        }
    }

    /**
     * 打印Qr码例子
     */
    fun printQr() {
        selectPrinter?.lineApi()?.run {
            printQrCode("http://www.sunmi.com", QrStyle.getStyle().setAlign(Align.CENTER)
                .setDot(9).setErrorLevel(ErrorLevel.L))
            initLine(BaseStyle.getStyle().setAlign(Align.CENTER))
            printText("http://www.sunmi.com", TextStyle.getStyle().enableBold(true))
            autoOut()
        }
    }

    /**
     * 打印Logo的例子
     * 分别使用二值化处理图片和抖动算法处理图片
     * 由于logo本身透明背景橙色字体，使用二值化处理后通过设置阈值呈现不同效果
     */
    fun printBitmap(view: View) {
        selectPrinter?.lineApi()?.run {
            val option: BitmapFactory.Options = BitmapFactory.Options().apply {
                inScaled = false
            }
            val bitmap = BitmapFactory.decodeResource(view.context.resources, R.drawable.sunmi, option)
            printBitmap(bitmap, BitmapStyle.getStyle().setAlign(Align.CENTER).setAlgorithm(ImageAlgorithm.BINARIZATION).setValue(120).setWidth(384).setHeight(150))
            printBitmap(bitmap, BitmapStyle.getStyle().setAlign(Align.CENTER).setAlgorithm(ImageAlgorithm.DITHERING).setWidth(384).setHeight(150))
            autoOut()
        }
    }

    /**
     * 打印分割线的例子
     * 分割线包括空白线，每行打印内容可以通过空白线进行分割
     */
    fun printLine() {
        selectPrinter?.lineApi()?.run {
            printDividingLine(DividingLine.EMPTY, 20)
            printDividingLine(DividingLine.DOTTED, 5)
            printDividingLine(DividingLine.EMPTY, 20)
            printDividingLine(DividingLine.SOLID, 10)
            autoOut()
        }
    }

    /**
     * 结合上面构建一个标准的小票内容
     */
    fun printTicket(view: View) {
        selectPrinter?.lineApi()?.run {
            initLine(BaseStyle.getStyle().setAlign(Align.CENTER))
            addText("******", TextStyle.getStyle())
            addText("商米之家", TextStyle.getStyle().enableBold(true).setTextWidthRatio(1).setTextHeightRatio(1))
            printText( "******", TextStyle.getStyle())
            val option: BitmapFactory.Options = BitmapFactory.Options().apply {
                inScaled = false
            }
            val bitmap = BitmapFactory.decodeResource(view.context.resources, R.drawable.sunmi, option)
            printBitmap(bitmap, BitmapStyle.getStyle().setAlign(Align.CENTER).setAlgorithm(ImageAlgorithm.BINARIZATION).setValue(120).setWidth(384).setHeight(150))
            printDividingLine(DividingLine.EMPTY, 30)
            printDividingLine(DividingLine.DOTTED, 2)
            printDividingLine(DividingLine.EMPTY, 30)
            val textStyle = TextStyle.getStyle().setAlign(Align.LEFT)
            printTexts(arrayOf("商品1","商品12","商品13"), intArrayOf(1, 1, 1), arrayOf(textStyle, textStyle, textStyle))
            printTexts(arrayOf("商品21","商品22","商品23"), intArrayOf(1, 1, 1), arrayOf(textStyle, textStyle, textStyle))
            printTexts(arrayOf("商品31","商品32","商品33"), intArrayOf(1, 1, 1), arrayOf(textStyle, textStyle, textStyle))
            printText("商品信息条码信息:", TextStyle.getStyle())
            printBarCode("1234567890", BarcodeStyle.getStyle().setAlign(Align.CENTER).setReadable(HumanReadable.POS_TWO))
            printDividingLine(DividingLine.EMPTY, 30)
            printDividingLine(DividingLine.DOTTED, 2)
            initLine(BaseStyle.getStyle().setAlign(Align.CENTER))
            printText("--已支付--", TextStyle.getStyle().setTextSize(48))
            printText( "预计19：00送达", TextStyle.getStyle().setTextSize(48))
            initLine(BaseStyle.getStyle().setAlign(Align.LEFT))
            printText( "【下单时间】2021-8-1 12:00", TextStyle.getStyle())
            addText("【备注】", TextStyle.getStyle())
            printText("尽快送达", TextStyle.getStyle().setTextHeightRatio(2))
            printDividingLine(DividingLine.EMPTY, 30)
            printDividingLine(DividingLine.DOTTED, 2)
            printText("【发票抬头】", TextStyle.getStyle().setTextSize(16))
            printQrCode("1234567890", QrStyle.getStyle().setDot(9).setAlign(Align.CENTER))
            autoOut()
        }
    }

    /**
     * 需要监听打印结果时可以开启事务模式
     * 开启后每次打印内容时通过printTrans监听回调即可获取结果
     */
    fun printTicketAndListen(view: View) {
        //需要监听打印结果时可以开启事务模式
        selectPrinter?.lineApi()?.enableTransMode(true)

        //正常打印票据
        printTicket(view)
        //事务监听结果
        selectPrinter?.lineApi()?.printTrans(object : PrintResult() {
            override fun onResult(resultCode: Int, message: String?) {
                if(resultCode == 0) {
                    //打印完成
                } else {
                    //打印失败
                    println(selectPrinter?.queryApi()?.status)
                }
            }

        })
        selectPrinter?.lineApi()?.enableTransMode(false)
    }


}