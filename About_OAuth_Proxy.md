# Introduction #

关于 OAuth Proxy


# Details #

  * **r227以后版本支持该特性**
  * 该API Proxy用于进行Basic Auth和OAuth之间的转换，只适用于Basic Auth的客户端(gravity 6375 之前版本，TweetDeck 0.34.2/0.34.1等等)
  * 首先请阅读JTweet搭建方法 http://code.google.com/p/javatweet/wiki/How_to_deploy_jtweet_with_oauth
  * 使用时先用浏览器打开yourappid.appspot.com/api，登陆后系统会给出替代密码
  * 设置客户端的api地址为yourappid.appspot.com/api/，然后用用户名和上面给出的临时密码登陆
  * 如果设置正确，系统会对你的请求添加OAuth信息，已完成认证。
  * **系统会在后台数据库明文保存使用者的用户名和AccessToken，搭建着可以看见。请确认搭建者为您信任的人**
  * 忘记密码的话重新登陆一次即可
  * 如果想删除API上登陆信息，请再登陆一次，点击最后页面中的“删除用户信息”