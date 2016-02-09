A Twitter Web Client in Java based on Twitter4J run on GAE.

And an API Proxy for Twitter with the support of Twitter search.

基于Twitter4J库的在线Twitter客户端，运行于GAE之上。

以及一个支持搜索的Twitter API Proxy。


**演示站点:http://javatweet.appspot.com**

**手机版:http://javatweet.appspot.com/m/**

**API Proxy:http://javatweet.appspot.com/api**

**对于javatweet.appspot.com这个域名被定点掉的问题，其实对付起来很简单。用Hosts文件把这个域名强行指定到g.cn的ip上，万事大吉。**

由于大部分图床都杯具了，所以暂时去掉了缩略图的预览功能。
在OAuth下twipic的功能也不好用了，所以也去掉了，以后研究出方法再加。


---


### DONE: ###
  * 支持OAuth和OAuth Proxy，方便国内用户使用
  * 发推，回复，锐推，删推，收藏和取消收藏
  * 发送消息
  * 自动更新消息
  * 未读消息提醒(闪动标题；如果浏览器支持flash，则会有声音提醒。)
  * 关注，取消关注，屏蔽，取消屏蔽用户
  * 查看朋友和关注者
  * 查看用户信息
  * 修改个人信息和头像
  * 支持HTTPS安全连接
  * 对消息中长度大于30的网址，自动生成短链接
  * 支持搜索
  * 有对手机友好的类似dabr的页面

### TODO: ###
  * 异常处理和BUG修正
  * ~~搜索支持~~
  * ~~https支持~~
  * ~~Oauth支持~~
  * UI优化
  * 正在考虑。。。。


---


### Screen Shot ###

登录页面

![http://javatweet.googlecode.com/files/screenshot_login.jpg](http://javatweet.googlecode.com/files/screenshot_login.jpg)

时间线

![http://javatweet.googlecode.com/files/screenshot_1.jpg](http://javatweet.googlecode.com/files/screenshot_1.jpg)

单条推

![http://javatweet.googlecode.com/files/screenshot_2.jpg](http://javatweet.googlecode.com/files/screenshot_2.jpg)
