
## RxDialog

An easy-to-use Dialog management tool.

## Usage

This tool is still in development and we don't provide its dependencies by the Gradle compile.

### 1.Create Interface and configuration it.

```Kotlin
interface RxDialogHolders {

    @Dialog(title = R.string.static_dialog_title,
            message = R.string.static_dialog_message,
            positiveText = R.string.static_dialog_button_ok,
            positiveTextColor = R.color.positive_color,
            negativeText = R.string.static_dialog_button_cancel,
            negativeTextColor = R.color.negative_color,
            buttons = [
                EventType.CALLBACK_TYPE_NEGATIVE,
                EventType.CALLBACK_TYPE_POSITIVE,
                EventType.CALLBACK_TYPE_DISMISS
            ])
    fun simpleDialog(context: Context): Observable<Event>
}
```

it would be like this :

![](https://github.com/qingmei2/RxDialog/blob/master/screenshots/ide_preview.png)

### 2.Init your DialogHolders(as singleton?).

```Kotlin
private var holders: RxDialogHolders = RxDialog.create(RxDialogHolders::class.java)
```

### 3.Show your dialog at somewhere.

```Kotlin
button.setOnClickListener {
      holders.alertDialog(this)
              .subscribe { event ->
                  when (event.button) {
                      EventType.CALLBACK_TYPE_POSITIVE -> {
                          toast("I Click the OK")
                      }
                      EventType.CALLBACK_TYPE_NEGATIVE -> {
                          toast("I Click the Cancel")
                      }
                      EventType.CALLBACK_TYPE_DISMISS -> {
                          toast("dialog is dismiss")
                      }
                  }
              }
}
```


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
