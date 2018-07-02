package com.qingmei2.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.qingmei2.rxdialog.RxDialog
import com.qingmei2.rxdialog.entity.Event
import com.qingmei2.rxdialog.entity.EventType
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var holders: RxDialogHolders = RxDialog.create(RxDialogHolders::class.java)

    val eventObserver: Consumer<Event> = object : Consumer<Event> {
        override fun accept(event: Event) {
            when (event.button) {
                EventType.CALLBACK_TYPE_POSITIVE -> {
                    toast("我点击了确认按钮")
                }
                EventType.CALLBACK_TYPE_NEGATIVE -> {
                    toast("我点击了取消按钮")
                }
                EventType.CALLBACK_TYPE_DISMISS -> {
                    toast("监听到Dialog.dismiss")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSimple.setOnClickListener {
            holders.simpleDialog(this)
                    .subscribe(eventObserver)
        }
        btnAlert.setOnClickListener {
            holders.alertDialog(this)
                    .subscribe(eventObserver)
        }
    }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}
