# TVEasyKeyboard
Android TV custom keyboard,suport digits and letters input

![](https://github.com/laoxiao79/TVEasyKeyboard/blob/master/demo.jpg)

Support letters and numbers input, for TV, TV box and other equipment.
There is a need to pay attention to, when the content of the input text box beyond visual range, the need to display the ellipsis, while using traditional android:ellipsize= "start" attribute is not effective. Here, a small trick is used to handle the actual stored string and the displayed string. With the aid of the TextUtils.ellipsize method, the actual text is converted into a format similar to "...ADGAG".

支持字母和数字输入，适用于TV，电视盒子等设备。
这里有个需要注意的地方，输入的内容当超出文本框可视范围之后，左边需要以省略号进行显示，而使用传统的android:ellipsize="start"属性是不起作用的。这里使用了一个小技巧进行了处理，利用实际存储的字符串和显示的字符串进行转化来解决的。借助了TextUtils.ellipsize 方法进行转换，将实际文本转换成类似“...ADGAG”的格式进行显示。

