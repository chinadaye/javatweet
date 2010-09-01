<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JTweetMobile - 搜索 - ${s?default("")}</title>
<link href="/css/mobile.css" rel="stylesheet" type="text/css" />
</head>

<body class="session">
<#include "menu_top.ftl" />
<form action="search" method="get">
	<input name="s" value="${s?default("")}" style="width:100%; max-width: 300px" />
	<input type="submit" value="搜索" />
</form>
<#if tweets?exists>
<table class="timeline">
<tbody>
	<#include "tweet_element.ftl" />
</tbody>
</table>
<div id="page">
<#if page gt 1><a href="?s=${s}&p=${page - 1}">上一页</a> </#if>
<a href="?s=${s}&p=${page + 1}">下一页</a>
</div>
</#if>
<#include "menu_bottom.ftl" />
<p><a href="http://code.google.com/p/javatweet">JavaTweet Mobile</a></p>
</body>
</html>
