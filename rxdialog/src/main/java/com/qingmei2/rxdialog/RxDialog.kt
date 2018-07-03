package com.qingmei2.rxdialog

import com.qingmei2.rxdialog.core.ServiceMethod
import com.qingmei2.rxdialog.util.Utils
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.concurrent.ConcurrentHashMap

@Suppress("UNCHECKED_CAST")
object RxDialog {

    private val serviceMethodCache = ConcurrentHashMap<Method, ServiceMethod>()

    fun <T> create(service: Class<T>): T {
        // check service class correct
        Utils.validateServiceInterface(service)

        return Proxy.newProxyInstance(
                service.classLoader,
                arrayOf(service)
        ) { _, method, args ->
            loadServiceMethod(method, args)
                    .buildOption()
                    .seekDialogFactory()
                    .observable()
        } as T
    }

    private fun loadServiceMethod(method: Method,
                                  args: Array<Any>): ServiceMethod {
        return serviceMethodCache[method] ?: synchronized(serviceMethodCache) {
            ServiceMethod(method, args).also {
                serviceMethodCache[method] = it
            }
        }
    }
}