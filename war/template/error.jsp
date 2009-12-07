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
<title id="page_title">JTeet </title>
<link href="/template/style2.css" media="screen" rel="stylesheet" type="text/css" />
<link href="/template/style3.css" media="screen" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js" ></script>
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
	.error_tip
	{
		text-align:center;
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
				<div class="error_tip">
				<p><img alt="error"  src="/img/error.jpg" width="400"></p>
				<p>发生了未知的异常情况，请稍候再试。<% String error = (String)request.getAttribute("error"); %><%=error!=null?error:"" %></p>
				</div>
				
			</div>
			<div class="fixed"></div>
		
		<div id="footer" class="round">
			<ul>
				<li class="first">©2009 by  <a href="/@sospartan" title="/@sospartan">@sospartan</a></li>
				<li>powered by<a href="http://code.google.com/p/javatweet/" target="_blank">javatweet</a>&<a href="http://code.google.com/appengine/" target="_blank">GAE</a>&<a href="http://yusuke.homeip.net/twitter4j/en/index.html" title="twitter4j" target="_blank">twitter4j</a></li>
				<li><a href="http://code.google.com/p/javatweet/issues/list" target="_blank" title="new issue">反馈问题</a></li>
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
