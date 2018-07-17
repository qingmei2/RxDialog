package com.qingmei2.rxdialog.entity

import android.content.DialogInterface
import com.qingmei2.rxdialog.RxDialog

data class RxEvent<E>(val dialog: DialogInterface,
                      val button: E)