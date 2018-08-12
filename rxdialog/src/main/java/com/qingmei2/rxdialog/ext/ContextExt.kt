package com.qingmei2.rxdialog.ext

import android.content.Context
import android.support.v4.content.ContextCompat

internal fun Context.getColorByResId(colorRes: Int): Int = ContextCompat.getColor(this, colorRes)

internal fun Context.getStringByResId(stringRes: Int): String = getString(stringRes)