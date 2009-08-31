<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>JTweet - 请登录</title>
	<link type="text/css" href="/template/style.css" rel="stylesheet" />
</head>
<body>
<div id="warp">
	<div id="head_warp">
		<img src="/img/jtweet.png" alt="JTweet" class="logo_img"/>
		<div class="fixed"></div>
		<img src="/img/jiantou.png" class="head_jiantou" />
	</div>
	<div class="fixed"></div>
	<div id="login_warp">
		<div class="login_tip">
		<p>JTweet 为 Java 编写的 Twitter 在线客户端，运行于 GAE 之上。暂不支持 OAuth，所以请使用 Twitter 的用户名和密码登录。</p>
		<p>JTweet 同时提供一个 API Proxy，可用于其他客户端。请在将客户端的 API BaseURL 设置为<b><a href="http://<% out.print(request.getServerName()); %>/api" > http://<% out.print(request.getServerName()); %>/api</a></b>。</p>
		</div>
		<form action="/login" id="loginform" method="post">
			<div class="input_div"><span class="login_text">用户名:</span><input type="text" id="username" name="username"/></div>
			<div class="input_div"><span class="login_text">密码:</span><input type="password" id="passwd" name="passwd"/></div>
		<button type="submit" id="sub_button" class="login_button">登录</button>
		<button type="reset" id="reset_button" class="login_button">重置</button>
		</form>
	</div>
	<div class="fixed"></div>
	<div id="foot_warp">
		<div class="foot">
			<span class="foot_author">由<a href="/user?id=yulei666" title="@yulei666">@yulei666</a>制作</span>
			<span class="foot_web"><a href="http://www.yulei666.com" title="导弹基地" target="_blank">www.yulei666.com</a></span>
			<span class="foot_twitter4j">基于<a href="http://yusuke.homeip.net/twitter4j/en/index.html" title="twitter4j" target="_blank">twitter4j</a></span>
			<span class="foot_gae">运行在<a href="http://code.google.com/appengine/" target="_blank">Google AppEngine</a>上</span>
		</div>
	</div>
	<div class="fixed"></div>
</body>
</html>