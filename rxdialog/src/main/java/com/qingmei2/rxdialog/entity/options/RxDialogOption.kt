package com.qingmei2.rxdialog.entity.options

import com.qingmei2.rxdialog.entity.factory.RxDialogFactory

interface RxDialogOption {

    fun seekDialogFactory(): RxDialogFactory

    interface Factory {

        fun buildOption(): RxDialogOption
    }
}