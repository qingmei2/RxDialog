package com.qingmei2.rxdialog.core.options

import android.content.Context
import android.support.v4.content.ContextCompat
import com.qingmei2.rxdialog.core.factory.DialogFactory
import com.qingmei2.rxdialog.core.factory.SimpleDialogFactory
import com.qingmei2.rxdialog.entity.*

internal class SimpleDialogOption(val context: Context) : DialogOption {

    private val factory: DialogFactory by lazy {
        SimpleDialogFactory(this)
    }

    var title: String = DEFAULT_DIALOG_STRING
    var message: String = DEFAULT_DIALOG_STRING
    var positiveText: String = DEFAULT_DIALOG_STRING
    var positiveTextColor: Int = DEFAULT_DIALOG_COLOR_RES
    var negativeText: String = DEFAULT_DIALOG_STRING
    var negativeTextColor: Int = DEFAULT_DIALOG_COLOR_RES
    var cancelable: Boolean = DEFAULT_DIALOG_CANCELABLE
    var buttons: Array<EventType> = arrayOf()

    override fun seekDialogFactory(): DialogFactory = factory

    companion object {

        fun build(context: Context, dialog: Dialog) =
                SimpleDialogOption(context).apply {
                    this.title = parseStringRes(dialog.title)
                    this.message = parseStringRes(dialog.message)
                    this.positiveText = parseStringRes(dialog.positiveText)
                    this.negativeText = parseStringRes(dialog.negativeText)

                    this.positiveTextColor = parseColorRes(dialog.positiveTextColor)
                    this.negativeTextColor = parseColorRes(dialog.negativeTextColor)

                    this.cancelable = dialog.cancelable
                    this.buttons = dialog.buttons
                }
    }

    private fun parseStringRes(stringRes: Int): String =
            if (stringRes == DEFAULT_DIALOG_STRING_RES)
                DEFAULT_DIALOG_STRING
            else
                context.getString(stringRes)

    private fun parseColorRes(colorRes: Int): Int =
            if (colorRes == DEFAULT_DIALOG_COLOR_RES)
                DEFAULT_DIALOG_COLOR_RES
            else
                ContextCompat.getColor(context, colorRes)
}

