package com.qingmei2.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.qingmei2.rxdialog.EventType
import com.qingmei2.rxdialog.RxDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var dialogComponent: RxDialogComponent = RxDialog.create(RxDialogComponent::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAlert.setOnClickListener {
            dialogComponent.instance(this)
                    .subscribe { event ->
                        when (event.button) {
                            EventType.CALLBACK_TYPE_OK -> {
                                toast("我点击了确认按钮")
                            }
                            EventType.CALLBACK_TYPE_CANCEL -> {
                                toast("我点击了取消按钮")
                            }
                            EventType.CALLBACK_TYPE_DISMISS -> {
                                toast("监听到Dialog.dismiss")
                            }
                            else -> {
                                toast("其他事件")
                            }
                        }
                    }
        }
    }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}
