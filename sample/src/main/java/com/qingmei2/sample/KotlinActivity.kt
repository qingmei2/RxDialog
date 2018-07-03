package com.qingmei2.sample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.qingmei2.rxdialog.entity.Event
import com.qingmei2.rxdialog.entity.EventType
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_kt.*

class KotlinActivity : AppCompatActivity() {

    private val eventObserver: Consumer<Event> = Consumer { event ->
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kt)

        val sampleApplication = application as SampleApplication
        val holders = sampleApplication.dialogHolders

        btnJava.setOnClickListener {
            startActivity(Intent(this, JavaActivity::class.java))
        }
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
