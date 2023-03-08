package com.sunmi.samples.printerx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import com.sunmi.printerx.PrinterSdk
import com.sunmi.samples.printerx.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    private val handler by lazy {
        Handler(mainLooper)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_printer)
        if (savedInstanceState == null) {
            switchFragment(MainFragment.newInstance(), false)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.log_swtich, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * 调试期间可通过开启log检查接口调用不打印或失败的原因
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_log_off-> PrinterSdk.getInstance().log(false, null)
            R.id.action_log_on-> PrinterSdk.getInstance().log(true, null)
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    fun switchFragment(
        fragment: Fragment,
        addToBackStack: Boolean = true,
        clearStack: Boolean = true,
    ) {
        handler.post {
            if (clearStack) {
                if (lifecycle.currentState != Lifecycle.State.RESUMED) {
                    fixBug()
                }
                var r = supportFragmentManager.popBackStackImmediate(
                    null,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
            }
            var transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, fragment, fragment::class.java.name)
            if (addToBackStack) {
                transaction.addToBackStack(fragment::class.java.name)
            }
            transaction.commitAllowingStateLoss()
            supportFragmentManager.executePendingTransactions()
        }

    }

    fun fixBug() {
        try {
            var aClass = FragmentManager::class.java
            var method = aClass.getDeclaredMethod("noteStateNotSaved")
            method.setAccessible(true)
            method.invoke(supportFragmentManager)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}