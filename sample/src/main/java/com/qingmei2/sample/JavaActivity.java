package com.qingmei2.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.qingmei2.rxdialog.RxDialog;
import com.qingmei2.rxdialog.entity.Event;
import com.qingmei2.rxdialog.entity.EventType;

import io.reactivex.functions.Consumer;

public class JavaActivity extends AppCompatActivity {

    private Consumer<Event> consumer = event -> {
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
                RxDialog
                        .Companion
                        .build(this, builder -> {
                            builder.withTitle(R.string.static_dialog_title)
                                    .withMessage(R.string.static_dialog_message)
                                    .withButtons(new EventType[]{
                                            EventType.CALLBACK_TYPE_DISMISS,
                                            EventType.CALLBACK_TYPE_NEGATIVE,
                                            EventType.CALLBACK_TYPE_POSITIVE
                                    })
                                    .withNegativeText(R.string.static_dialog_button_cancel)
                                    .withNegativeTextColor(R.color.negative_color)
                                    .withPositiveTextColor(R.color.positive_color)
                                    .withPositiveText(R.string.static_dialog_button_ok);
                            return null;
                        })
                        .observable()
                        .subscribe(consumer)
        );
    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
