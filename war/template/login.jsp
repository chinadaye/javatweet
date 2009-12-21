<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="java.util.List"%>
<%@page import="twitter4j.Trend"%>
<%@page import="java.net.URLEncoder"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>          
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<meta content="t.jteet.com-为中文用户打造的twitter客户端" name="description" />
<meta content="width = 780" name="viewport" />
<meta content="NOODP" name="robots" />
<meta content="" name="keywords"/>
<title id="page_title">JTeet -  欢迎光临 t.jteet.com-为中文用户打造的twitter客户端</title>
<link type="text/css" href="/img/style-all20091030.css" rel="stylesheet" />
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js" ></script>
<style type="text/css">
      
	body { background: #C0DEED url(/img/bg-clouds.jpg) repeat-x; }
	#login_warp
	{
		padding:30px;
		text-align:center;
		font-size:12px;
		background-color:#fff;
		margin-top:10px;
	
	}
	.login_tip
	{
		word-break:normal;
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
	#search_trend
	{
	font-size: 2em;
	}
	#search_trend a
	{
	font-weight: bold;
	text-decoration:underline;
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
		margin:0 20px 0 0;
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
 			 <ul class="top-navigation round">
				<li><a href="/" accesskey="l">首页</a></li>
				</ul>
		</div>
  
			<div id="login_warp" class="round">
				<div class="login_tip">
				<p><img alt="Jteet" src="/img/jteet_logo.png" /></p>
				<p>欢迎光临 jteet 为中文用户打造的twitter客户端</p>
				</div>
				<div >
				<% String error = (String)request.getAttribute("error"); 
				String rdt = (String)request.getAttribute("rdt");
				if(error!=null){
				%>
				<p class="error_tip">请检查用户名和密码（<%=error %>）</p>
				<%} %>
				<form action="/login" id="loginform" method="post">
				<input type="hidden" name="rdt" value="<%=rdt==null?"":rdt %>"/>
				<p>
					<label>用户名:</label>
					<input type="text" id="username" name="username" tabindex="1"/>
				</p>
				<p>
					<label>密码:</label>
					<input type="password" id="passwd" name="passwd" tabindex="2"/>
				</p>
				<p id="btn_login">
					<button type="submit" id="sub_button" class="login_button" tabindex="4">登录</button><label ><input type="checkbox" id="stay" name="stayin" value="1" tabindex="3" checked="checked"/>保持登录</label>
				</p>
				<p id="cookie_tip" style="display:none;">
				请检查您的浏览器设置，需要启用cookie才能正常登录。
				</p>
				<!--<p>没有帐号？<a href="http://www.power.com/Pub/CreateAccount/CreateAccountTwitter.aspx" target="_blank">注册</a></p>
				--></form>
				</div>
				<% List<Trend> trendlist = (List<Trend>)request.getAttribute("trends"); 
					if(trendlist!=null){
				%>
				<div id="search_trend" >
				热门：
				<%for(Trend trend:trendlist){ %>
				<!-- <%=trend.getName()+"+"+trend.getQuery()+"+"+trend.getUrl() %> -->
				<a href="/search?s=<%=trend.getUrlName() %>" name="搜索<%=trend.getName() %>"><%=trend.getName() %></a>
				<%} %>
				</div>
				<%} %>
			</div>
			
			<div class="fixed"></div>
		
		<div id="footer" class="round">
			<ul>
				<li class="first">©2009 by  <a href="/@sospartan" title="/@sospartan">@sospartan</a></li>
				<li>powered by<a href="http://code.google.com/p/javatweet/" target="_blank">javatweet</a>&<a href="http://code.google.com/appengine/" target="_blank">GAE</a>&<a href="http://yusuke.homeip.net/twitter4j/en/index.html" title="twitter4j" target="_blank">twitter4j</a></li>
				<li><a href="http://code.google.com/p/javatweet/issues/list" target="_blank" title="new issue">反馈问题</a></li>
			</ul>
<script type="text/javascript" src="/js/jquery.cookie.js" ></script>
<script type="text/javascript">
$.cookie('testcookiesenabled', null);
$.cookie('testcookiesenabled', 'enabled');
if (!$.cookie('testcookiesenabled')) {
	$("#btn_login").slideUp(function(){$("#cookie_tip").slideDown();});
} 
</script>
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
