# 环境配置 #

## Java环境配置 ##
首先必须安装JDK，没有的同学，可以到[官方站点下载](http://goo.gl/WWHV)。安装就是一路下一步。

安装完之后，在命令行下运行`java -version`，如果显示类似下面的结果，表示配置没问题。

![http://javatweet.googlecode.com/svn/wiki/images/manual/snapshot_000.png](http://javatweet.googlecode.com/svn/wiki/images/manual/snapshot_000.png)

## GAE SDK配置 ##

在[官方下载页面](http://code.google.com/appengine/downloads.html)下载，下载完毕后，解压到任一目录，尽量不要在路径中包含中文字符，防止出现意外的问题

_注：使用eclipse的同学推荐安装GAE的eclipse插件，详细步骤[见此](http://code.google.com/eclipse/docs/getting_started.html)。使用eclipse的同学，应该都是有一定开发知识的，在此就不罗嗦了。_

# 注册Twitter consumer key #

注：此过程中有好几个步骤需要访问的地址在墙外，请确保能访问墙外地址

在[Twitter官方申请页面](https://twitter.com/oauth_clients/new)填写注册信息

  * Application Icon:程序图标，可换可不换
  * Application Name:保证唯一就可以了
  * Description:描述，可以为空
  * Application Website:程序网站，没有的，可以填本项目地址http://code.google.com/p/javatweet
  * Organization:组织，可以填http://code.google.com/p/javatweet
  * Website:组织的网站，可以填http://code.google.com/p/javatweet
  * Application Type:选Browser
  * Callback URL:随便填，但是一定要填。实际的callback url授权的时候，程序会自动生成
  * Default Access type:选Read & Write
  * Use Twitter for login:可选可不选

在注册成功页面，你会得到Consumer key和Consumer secret

# 配置Jtweet #

  1. 在[项目下载页面](http://code.google.com/p/javatweet/downloads/list)下载部署包，解压到任一目录，目前的命名规则是和svn的版本同步的，推荐安装最新版
  1. 找到`\war\WEB-INF`路径下的`appengine-web.xml`文件，文本编辑器打开
  1. 替换`<application>jtweet</application>`中的`jtweet`为你自己的application id
  1. 替换`<property name="twitter.api.key" value="[consumer key]"/>`中`[consumer key]`为前面获得的Consumer key
  1. 替换`<property name="twitter.api.secret" value="[consumer key secret]"/>` 中`[consumer key secret]`为前面获得的Consumer secret
  1. 保存修改后的文件

# 上传程序 #

> 进入GAE SDK的目录，我直接用eclipse的插件了，所以目录比较深
![http://javatweet.googlecode.com/svn/wiki/images/manual/snapshot_001.png](http://javatweet.googlecode.com/svn/wiki/images/manual/snapshot_001.png)

> 使用`appcfg.cmd update "%jtweet的war路径%"` 如下图中的橙色框中所示
![http://javatweet.googlecode.com/svn/wiki/images/manual/snapshot_002.png](http://javatweet.googlecode.com/svn/wiki/images/manual/snapshot_002.png)

> 如果你前面的配置无问题的话，会出现成功的提示。此过程中会提示你输入你GAE的帐号和密码，这里省去了。
![http://javatweet.googlecode.com/svn/wiki/images/manual/snapshot_003.png](http://javatweet.googlecode.com/svn/wiki/images/manual/snapshot_003.png)

_注：使用eclipse的话，直接点击工具栏的部署按钮就可以了，具体步骤请参考google code的[帮助文档](http://goo.gl/4ABo)。_

# 使用 #

> 登录你jtweet的部署地址，形如`http(s)://your-api.appspot.com/`，推荐使用OAuth Proxy登录，省去翻墙的麻烦
![http://javatweet.googlecode.com/svn/wiki/images/manual/snapshot_004.png](http://javatweet.googlecode.com/svn/wiki/images/manual/snapshot_004.png)

> 按照向导输入你的twitter ID和密码
![http://javatweet.googlecode.com/svn/wiki/images/manual/snapshot_006.png](http://javatweet.googlecode.com/svn/wiki/images/manual/snapshot_006.png)

> 成功之后，会自动跳转到jtweet的主页
![http://javatweet.googlecode.com/svn/wiki/images/manual/snapshot_005.png](http://javatweet.googlecode.com/svn/wiki/images/manual/snapshot_005.png)

# 问题？ #
如果你在使用过程中碰到问题，可以在twitter上[@gythialy](https://twitter.com/gythialy)联系我，或者在项目的[Issues页面](http://code.google.com/p/javatweet/issues/list)提交issue

如果你是一个懒人呢，那就使用我的[演示站点](https://jtwitter-demo.appspot.com/)吧，基本都会部署最新开发版

# 补充 #
update:2010.08.07
  * 添加了OAuth Proxy功能，连唯一的一次登陆Twitter网站都可以省略了。当然，如果担心你密码的安全性，可以下载代码自行架设。
  * 关于程序中的统计代码，那个代码的统计结果只有我(@yulei666)能看见，不公开。如果各位有疑问，可以将“war\template\analytics.ftl”文件中的内容删除，注意是删除内容不是删除文件，否则会出错的。
  * 对于javatweet.appspot.com这个域名被定点掉的问题，其实对付起来很简单。用Hosts文件把这个域名强行指定到g.cn的ip上，万事大吉。
  * 由于大部分图床都杯具了，所以暂时去掉了缩略图的预览功能。
  * 在OAuth下twipic的功能也不好用了，所以也去掉了，以后研究出方法再加。