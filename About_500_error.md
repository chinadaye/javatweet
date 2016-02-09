# Introduction #

关于上传出现500问题


# Details #

为了减小压缩包的体积，我把编译出来的class文件都删了，因为有源代码可以重新编译的。但是发现很多用户装的是jre，有一些第三方上传工具也不预先编译工程就直接上传，所以导致找不到class文件，500错误。

现在新版已经保留了class文件，大家可以再试试。

还有就是大家最好安装jdk，而不是光装个jre。。。
然后将上传工具的java路径指向jdk的bin文件夹

<blockquote>
Reported by xym.yan, Aug 17, 2010<br>
<br>
<blockquote>遇到命令行上传报错，看日志，appcfg把JDK路径指定到了JRE的路径上C:\Program Files\Java\jre6。所以报错。</blockquote>

<blockquote>通过搜索找到一种解决办法，修改appcfg.cmd，把@java 改成@"C:\Program Files\Java\jdk1.6.0_10\bin\java" 每个人的安装路径不一样，原理一样，手动指定jdk的bin文件夹，现在上传不报错了。希望有用。<br>
</blockquote>