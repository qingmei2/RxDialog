package com.qingmei2.sample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.qingmei2.rxdialog.RxDialog
import com.qingmei2.rxdialog.entity.Event
import com.qingmei2.rxdialog.entity.EventType
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_kt.*

class KotlinActivity : AppCompatActivity() {

    private val eventObserver: Consumer<Event> = Consumer { event ->
        when (event.button) {
            EventType.CALLBACK_TYPE_POSITIVE -> {
                toast("click the OK")
            }
            EventType.CALLBACK_TYPE_NEGATIVE -> {
                toast("click the CANCEL")
            }
            EventType.CALLBACK_TYPE_DISMISS -> {
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
            RxDialog
                    .build(this) {
                        title = "I am title"
                        message = "I am message"
                        buttons = arrayOf(
                                EventType.CALLBACK_TYPE_POSITIVE,
                                EventType.CALLBACK_TYPE_NEGATIVE,
                                EventType.CALLBACK_TYPE_DISMISS
                        )
                        positiveText = getString(R.string.static_dialog_button_ok)
                        positiveTextColor = R.color.positive_color
                        negativeText = getString(R.string.static_dialog_button_cancel)
                        negativeTextColor = R.color.negative_color
                        cancelable = false
                    }
                    .observable()
                    .subscribe(eventObserver)
        }
    }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}
