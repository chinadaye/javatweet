<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>          
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<meta content="Twitter is without a doubt the best way to share and discover what is happening right now." name="description" />
<meta content="width = 780" name="viewport" />
<meta content="NOODP" name="robots" />
<meta content="n" name="session-loggedin" />
<title id="page_title">JTeet - 请登录</title>
<link href="/template/style2.css" media="screen" rel="stylesheet" type="text/css" />
<link href="/template/style3.css" media="screen" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/js/jquery-1.3.2.min.js" ></script>
<style type="text/css">
      
	body { background: #C0DEED url(/img/bg-clouds.jpg) repeat-x; }
	#login_warp
	{
		padding:30px;
		text-align:center;
		font-size:12px;
		background-color:#fff;
	
	}
	.login_tip
	{
		word-break:normal;
		text-align:left;
		font-weight:bold;
		font-size:14px;
		padding: 0px 30px 30px 30px;
	}
	#loginform
	{
		margin:0px auto;
		width:300px;
		text-align:left;
	}
	#loginform p
	{
	padding:5px;
	}
	#loginform input[type="password"],#loginform input[type="text"]
	{
	display:block;
	width: 200px;
	border: 1px solid #AACCEE;
	margin: 0 0 5px;
	padding: 5px;
	}
	.input_div
	{
		padding:5px;
		text-align:left;
		overflow:auto;
		vertical-align:top;
	}
	
	
	.login_button
	{
		margin:0 5px 0 0;
		font-size:12px;
	}
     .error_tip
     {
     	color: red;
     } 
</style>
</head>
<body class="sessions" id="new">
	<div id="container" class="subpage">
		<div id="header">
			<a href="/" title=" Home" accesskey="1" id="logo"> <img alt="Jteet" height="40" src="/img/jteet_logo.png" /> </a>
 			 <ul class="top-navigation round">
				<li><a href="/" accesskey="l">首页</a></li>
				<!--<li class="signup-link"><a href="https://twitter.com/signup">加入 Twitter!</a></li>
			--></ul>
		</div>
  
		<div class="content-bubble-arrow"></div>
			<div id="login_warp" class="round">
				<div class="login_tip">
				<p>JTweet 为 Java 编写的 Twitter 在线客户端，运行于 GAE 之上。暂不支持 OAuth，所以请使用 Twitter 的用户名和密码登录。</p><br />
				<p>JTweet 同时提供一个 API Proxy，可用于其他客户端。请在将客户端的 API BaseURL 设置为<b><a href="http://<% out.print(request.getServerName()); %>/api" > http://<% out.print(request.getServerName()); %>/api</a></b>。</p>
				</div>
				<div >
				<% String error = (String)request.getAttribute("error"); 
				if(error!=null){
				%>
				<p class="error_tip">请检查用户名和密码（<%=error %>）</p>
				<%} %>
				<form action="/login" id="loginform" method="post">
				<p>
					<label>用户名:</label>
					<input type="text" id="username" name="username"/>
				</p>
				<p>
					<label>密码:</label>
					<input type="password" id="passwd" name="passwd"/>
				</p>
				<p>
					<button type="submit" id="sub_button" class="login_button">登录</button><label ><input type="checkbox" id="stay" name="stayin" value="1"/>保持登录</label>
				</p>
				</form>
				</div>
			</div>
			<div class="fixed"></div>
		
		<div id="footer" class="round">
			<ul>
				<li class="first">©2009 by  <a href="/user?id=sospartan" title="@sospartan">@sospartan</a>,<a href="/user?id=yulei666" title="@yulei666">@yulei666</a>,<a href="/user?id=gowers" title="@gowers">@gowers</a></li>
				<li>powered by<a href="http://code.google.com/p/javatweet/">javatweet</a>&<a href="http://code.google.com/appengine/" target="_blank">GAE</a>&<a href="http://yusuke.homeip.net/twitter4j/en/index.html" title="twitter4j" target="_blank">twitter4j</a></li>
			</ul>
<script type="text/javascript">
var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
try {
var pageTracker = _gat._getTracker("UA-11204156-1");
pageTracker._trackPageview();
} catch(err) {}</script>
		</div>
		<hr />
	</div>

<!-- BEGIN google analytics -->

 
<!-- END google analytics -->

	<div id="notifications"></div>
	
<SCRIPT>
	if (window.navigator.userAgent.indexOf("MSIE 6.0")>=1){
		$("body").addClass("ie ie6");
	}else{
	    if (window.navigator.userAgent.indexOf("MSIE 7.0")>=1){
	    	$("body").addClass("ie ie7");
	    }else{
	    	$("body").addClass("firefox-windows");
	    }
	};
</SCRIPT>
    
</body>

</html>
