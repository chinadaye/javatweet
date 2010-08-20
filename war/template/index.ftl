<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JTweet - 请登陆</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
</head>

<body class="session">

<div id="container">
	<div id="header">
   	  <a href="/home" title="JTweet"  id="logo" class="left"><img alt="JTweet" height="36" src="/img/jtweet.gif" width="115" /></a>
    	<ul class="top-navigation round right">
        	<li><a href="https://twitter.com/signup" target="_blank">加入Twitter!</a></li>
        </ul>
    <!--#header--></div>
	<div class="content-bubble-arrow clear"></div>
    <div id="login-wraper" class="round">
        <div class="login_tip">
            <p>JTweet 为 Java 编写的 Twitter 在线客户端，采用Twitter4J作为API库，运行于 GAE 之上， 支持OAuth以及OAuth Proxy（！请选您择信任的搭建者！）。</p>
            <p>本程序为开源程序，关于安全性请前往<a href="http://code.google.com/p/javatweet/" target="_blank">项目主页</a>围观。</p>
            <p>JTweet 同时提供一个 API Proxy，可用于其他客户端：<b><a href="${baseurl}/api" >${baseurl}/api</a></b>。</p>
        </div>
        <form action="/oauthoffical" id="loginform" method="get">
            <button type="submit" id="offical_btn" class="login_button" onclick="this.form.action='/oauthoffical';return true;">使用OAuth登陆</button>
            <button type="submit" id="proxy_btn" class="login_button" onClick="this.form.action='/oauthproxy';return true;">使用OAuth Proxy登陆</button>
            <center><label><input type="checkbox" id="stay" name="stayin" value="1"/>保持登录(两周)</label></center>
        </form>
    </div>
<div class="fixed"></div>
<div id="footer" class="round">
	<ul class="footer-nav">
		<li >&copy; 2010 @Yulei666</li>
		<li><a href="http://code.google.com/p/javatweet" target="_blank">JaveTweet with OAuth</a></li>
	</ul>
</div>
<div id="footer-icon">
	<a href="http://code.google.com/appengine/" target="_blank" title="Google AppEngine"><img src="img/appengine-silver-120x30.gif" alt="Google AppEngine"/></a>
    <a href="http://twitter4j.org/" target="_blank" title="Twitter4J"><img src="img/powered-by-twitter4j-138x30.gif" alt="Twitter4J" /></a>
</div>
<!-- #container --></div>
</body>
</html>
