package com.qingmei2.rxdialog.entity

import android.content.DialogInterface

data class Event(val dialog: DialogInterface,
                 val button: EventType)