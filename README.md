# simple_server
用于Xbot的文件传输处理程序
## 使用步骤
### 1.将java文件编译成class文件(需要安装JDK)：
```
javac Server.java 
```

### 2.运行：
```
java Server <path>
```

最后一个参数`<path>` 指定了接收文件存储路径。如`java Server /home/download` 表示将下载的文件存储到/home/download这个文件夹下(文件夹要存在)



---
还可以使用另外两个文件进行文件上传测试：

1.修改FakeAndroidClient中的所有如下这样的代码：
```
list.add("/home/lee/Bye Bye Bye - Lovestoned.mp3");
```
用测试的文件替换掉原来旧文件的代码。

2.编译
```
javac FakeAndroidClient.java
```

3.运行
```
java FakeAndroidClient
```
