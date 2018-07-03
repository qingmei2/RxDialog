package com.qingmei2.rxdialog.core.options

import com.qingmei2.rxdialog.core.factory.DialogFactory

interface DialogOption {

    fun seekDialogFactory(): DialogFactory

    interface Factory {

        fun buildOption(): DialogOption
    }
}