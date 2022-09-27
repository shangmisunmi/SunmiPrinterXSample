package com.sunmi.samples.printerx.utils

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.util.Log
import android.widget.Toast
import java.io.IOException
import java.io.OutputStream
import java.util.*

/**
 * 内置虚拟蓝牙默认配对
 */
object BluetoothUtil {

    private val PRINTER_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    private const val Innerprinter_Address = "00:11:22:33:44:55"
    private var bluetoothSocket: BluetoothSocket? = null
    private val bTAdapter: BluetoothAdapter?
        private get() = BluetoothAdapter.getDefaultAdapter()

    private fun getDevice(bluetoothAdapter: BluetoothAdapter?): BluetoothDevice? {
        var innerprinter_device: BluetoothDevice? = null
        val devices = bluetoothAdapter!!.bondedDevices
        for (device in devices) {
            if (device.address == Innerprinter_Address) {
                innerprinter_device = device
                break
            }
        }
        return innerprinter_device
    }

    @Throws(IOException::class)
    private fun getSocket(device: BluetoothDevice): BluetoothSocket? {
        var socket: BluetoothSocket? = null
        socket = device.createRfcommSocketToServiceRecord(PRINTER_UUID)
        socket.connect()
        return socket
    }

    /**
     * 连接蓝牙
     *
     * @param context
     * @return
     */
    fun connectBlueTooth(context: Context?): Boolean {
        if (bluetoothSocket == null) {
            if (bTAdapter == null) {
                Toast.makeText(context, "蓝牙设备不可用", Toast.LENGTH_SHORT).show()
                return false
            }
            if (!bTAdapter!!.isEnabled) {
                Toast.makeText(context, "未检测到蓝牙设备，请打开蓝牙", Toast.LENGTH_SHORT).show()
                return false
            }
            var device: BluetoothDevice
            if (getDevice(bTAdapter).also { device = it!! } == null) {
                Toast.makeText(context, "未发现InnterPrinter!", Toast.LENGTH_SHORT).show()
                return false
            }
            try {
                bluetoothSocket = getSocket(device)
            } catch (e: IOException) {
                Toast.makeText(context, "蓝牙连接失败!", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }

    /**
     * 断开蓝牙
     */
    fun disconnectBlueTooth(context: Context?) {
        if (bluetoothSocket != null) {
            try {
                val out = bluetoothSocket!!.outputStream
                out.close()
                bluetoothSocket!!.close()
                bluetoothSocket = null
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 蓝牙方式打印均使用epson指令
     *
     * @param bytes
     * @throws IOException
     */
    fun sendData(bytes: ByteArray) {
        if (bluetoothSocket != null) {
            var out: OutputStream? = null
            try {
                out = bluetoothSocket!!.outputStream
                out.write(bytes, 0, bytes.size)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            Log.i("kaltin", "bluetoothSocketttt null")
        }
    }
}