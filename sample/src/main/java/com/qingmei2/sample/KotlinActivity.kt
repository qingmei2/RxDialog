package com.qingmei2.sample

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.qingmei2.rxdialog.entity.RxEvent
import com.qingmei2.rxdialog.entity.SystemEvent
import com.qingmei2.rxdialog.core.RxAlertDialog
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_kt.*

class KotlinActivity : AppCompatActivity() {

    private val eventObserver: Consumer<RxEvent<SystemEvent>> = Consumer { event ->
        when (event.button) {
            SystemEvent.CALLBACK_TYPE_POSITIVE -> {
                toast("click the OK")
            }
            SystemEvent.CALLBACK_TYPE_NEGATIVE -> {
                toast("click the CANCEL")
            }
            SystemEvent.CALLBACK_TYPE_DISMISS -> {
                toast("dismiss...")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kt)

        btnJava.setOnClickListener {
            startActivity(Intent(this, JavaActivity::class.java))
        }
        btnSimple.setOnClickListener {
            RxAlertDialog
                    .build {
                        title = { "I am title" }
                        message = { "I am message" }
                        buttons = arrayOf(
                                SystemEvent.CALLBACK_TYPE_POSITIVE,
                                SystemEvent.CALLBACK_TYPE_NEGATIVE,
                                SystemEvent.CALLBACK_TYPE_DISMISS
                        )
                        positiveText = { getString(R.string.static_dialog_button_ok) }
                        positiveTextColor = { ContextCompat.getColor(this@KotlinActivity, R.color.positive_color) }
                        negativeText = { getString(R.string.static_dialog_button_cancel) }
                        negativeTextColor = { ContextCompat.getColor(this@KotlinActivity, R.color.negative_color) }
                        cancelable = false
                    }
                    .create()
                    .showDialog(this)
                    .subscribe(eventObserver)
        }
    }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}
