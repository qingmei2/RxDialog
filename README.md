
## RxDialog

An easy-to-use Dialog management tool.

## Usage

This tool is still in development and we don't provide its dependencies by the Gradle compile.  

Show your dialog at somewhere:

```Kotlin
button.setOnClickListener {
            RxDialog
                    .build(this) {
                        title = "I am title"
                        message = "I am message"
                        buttons = arrayOf(
                                EventType.CALLBACK_TYPE_POSITIVE,
                                EventType.CALLBACK_TYPE_NEGATIVE,
                                EventType.CALLBACK_TYPE_DISMISS
                        )
                        positiveText = getString(R.string.static_dialog_button_ok)
                        negativeText = getString(R.string.static_dialog_button_cancel)
                    }
                    .observable()
                    .subscribe{ event ->
                          when (event.button) {
                              EventType.CALLBACK_TYPE_POSITIVE -> {
                                  toast("我点击了确认按钮")
                              }
                              EventType.CALLBACK_TYPE_NEGATIVE -> {
                                  toast("我点击了取消按钮")
                              }
                              EventType.CALLBACK_TYPE_DISMISS -> {
                                  toast("监听到Dialog.dismiss")
                              }
                          }
                    }
}
```

## Another author's libraries using RxJava:

* **[RxImagePicker](https://github.com/qingmei2/RxImagePicker)**:  The library which choosing pictures from camera or gallery in Android.
* **[RxSchedulers](https://github.com/qingmei2/RxSchedulers)**: The schedulers tools for RxJava2 in Android.   

License
-------

    The RxDialog License

    Copyright (c) 2018 qingmei2

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
