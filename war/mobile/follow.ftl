<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JTweetMobile - ${title} - ${user.getScreenName()}</title>
<link href="/css/mobile.css" rel="stylesheet" type="text/css" />
</head>

<body class="session">
<#include "menu_top.ftl" />
<table class="followers">
<tbody>
	<#include "follow_element.ftl" />
</tbody>
</table>
<div id="page">
<#if page?exists>
	<#if page gt 1><a href="?p=${page - 1}">上一页</a></#if>
	<a href="?p=${page + 1}">下一页</a>
<#else>
	<#if previouscursor != 0><a href="?c=${previouscursor?c}">上一页</a></#if>
	<#if nextcursor != 0><a href="?c=${nextcursor?c}">下一页</a></#if>
</#if>	
</div>
<#include "menu_bottom.ftl" />
<p><a href="http://code.google.com/p/javatweet">JavaTweet Mobile</a></p>
</body>
</html>
