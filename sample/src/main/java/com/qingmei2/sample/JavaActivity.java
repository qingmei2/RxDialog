package com.qingmei2.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.qingmei2.rxdialog.entity.RxEvent;
import com.qingmei2.rxdialog.entity.SystemEvent;
import com.qingmei2.rxdialog.core.RxAlertDialog;

import io.reactivex.functions.Consumer;

public class JavaActivity extends AppCompatActivity {

    private Consumer<RxEvent<SystemEvent>> consumer = event -> {
        switch (event.getButton()) {
            case CALLBACK_TYPE_POSITIVE:
                toast("click the OK");
                break;
            case CALLBACK_TYPE_NEGATIVE:
                toast("click the CANCEL");
                break;
            case CALLBACK_TYPE_DISMISS:
                toast("dismiss...");
                break;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);

        FloatingActionButton fabAlert = findViewById(R.id.btnAlert);
        FloatingActionButton fabSimple = findViewById(R.id.btnSimple);

        fabSimple.setOnClickListener(v ->
                RxAlertDialog
                        .Builder
                        .build(builder -> {
                            builder.withTitle("I am title")
                                    .withMessage("I am message")
                                    .withButtons(new SystemEvent[]{
                                            SystemEvent.CALLBACK_TYPE_DISMISS,
                                            SystemEvent.CALLBACK_TYPE_NEGATIVE,
                                            SystemEvent.CALLBACK_TYPE_POSITIVE
                                    })
                                    .withNegativeText("CANCEL")
                                    .withNegativeTextColor(ContextCompat.getColor(this, R.color.negative_color))
                                    .withPositiveTextColor(ContextCompat.getColor(this, R.color.positive_color))
                                    .withPositiveText(getString(R.string.static_dialog_button_ok));
                            return null;
                        })
                        .create()
                        .showDialog(this)
                        .subscribe(consumer)
        );
    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
