# API OAuth验证 #

部署好Jtweet之后，在浏览器中输入地址`http(s)://your-appid.appspot.com/api`

![http://javatweet.googlecode.com/svn/wiki/images/manual/api.png](http://javatweet.googlecode.com/svn/wiki/images/manual/api.png)

在出现的页面上，根据自己的情况选择登录方式

  1. OAuth登录：需要登录
  1. OAuth Proxy登录：验证过程中需要twitter的ID和密码，存在安全隐患

**如果是自己部署的应用的话，推荐使用OAuth Proxy方式，第三方的请自己判断**

OAuth Proxy过程中的登录并验证

![http://javatweet.googlecode.com/svn/wiki/images/manual/apiproxy.png](http://javatweet.googlecode.com/svn/wiki/images/manual/apiproxy.png)


验证成功后，会自动生成密码，这时，你在客户端设置好API后，**可以直接用twitter ID加此密码登录**。如果你后悔的话，可以直接删除验证信息

![http://javatweet.googlecode.com/svn/wiki/images/manual/apiinfo.png](http://javatweet.googlecode.com/svn/wiki/images/manual/apiinfo.png)

如果生成的密码比较难记的话，可以在输入新密码后，点击修改按钮进行修改，修改成功后，会显示新的登录信息

![http://javatweet.googlecode.com/svn/wiki/images/manual/apimodify.png](http://javatweet.googlecode.com/svn/wiki/images/manual/apimodify.png)

至此，**你可以用API——http(s)://your-appid.appspot.com/api，twitter ID和你最终获得的密码登录**