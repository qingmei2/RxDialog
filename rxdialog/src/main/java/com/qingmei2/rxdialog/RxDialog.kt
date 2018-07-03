package com.qingmei2.rxdialog

import com.qingmei2.rxdialog.core.ServiceMethod
import com.qingmei2.rxdialog.util.Utils
import java.lang.reflect.Proxy

@Suppress("UNCHECKED_CAST")
object RxDialog {

    fun <T> create(service: Class<T>): T {
        // check service class correct
        Utils.validateServiceInterface(service)

        return Proxy.newProxyInstance(
                service.classLoader,
                arrayOf(service)
        ) { _, method, args ->
            // every time call the method, instance a new ServiceMethod object.
            ServiceMethod(method, args)
                    .buildOption()
                    .seekDialogFactory()
                    .observable()
        } as T
    }
}