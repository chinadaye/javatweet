<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>JTweet - 搜索 ${search} - ${user.screenName}</title>
	<link type="text/css" href="/template/style.css" rel="stylesheet" />
	<script type="text/javascript" src="/js/jquery-1.3.2.min.js" ></script>
	<script type="text/javascript" src="/js/jquery.cookie.js" ></script>
</head>
<body>
<div id="warp">
	<div id="head_warp">
		<#include "head.ftl" /> 
	</div>
	<div class="fixed"></div>
	<div id="main_warp">
		<div id="form_warp">
			<span class="tweet_tip">你在做什么？ 按Ctrl+Enter快捷发布</span><span class="tweet_count_info">剩余：<span id="tweet_count" class="tweet_count_green">140</span>字</span><br />
			<textarea id="tweet_msg" name="tweet_msg"></textarea><br />
			<button id="tweet_submit">我推！</button>
		</div>
		<div class="fixed"></div>
		<div id="action_div"><a href="javascript:markallread();">标记全部为已读</a></div>
		<div class="fixed"></div>
		<div id="tweet_warp">
			<#include "tweet_element.ftl" /> 
		</div>
		<div class="fixed"></div>
		<div id="tweet_page">
			<#if page gt 1><a href="/search?s=${search}&page=${page - 1}" class="pre_page">上一页</a></#if>
			<#if tweets?size gt 14><a href="/search?s=${search}&page=${page + 1}" class="next_page">下一页</a></#if>
		</div>
	</div>
	<div id="side_warp">
		<#include "side.ftl" />
	</div>
	<div class="fixed"></div>
	<div id="foot_warp">
		<#include "foot.ftl" />
	</div>
	<div class="fixed"></div>
</div>
<script type="text/javascript" src="/js/func.js"></script>
<#if addjs?exists>
	<script type="text/javascript" src="${addjs}"></script>
</#if>
</body>
</html>