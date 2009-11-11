<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#setting time_zone="GMT+8">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>@${user_show.screenName}的${title}-Jteet</title>
	<link type="text/css" href="/template/style-all20091030.css" rel="stylesheet" />
	<script type="text/javascript" src="/js/jquery-1.3.2.min.js" ></script>
	<script type="text/javascript" src="/js/jquery.cookie.js" ></script>
<#include "user_css.ftl">
</head>
<body class="sessions" id="new">
<div id="ajax_loader" >
<img alt="loader" src="/img/ajax-loader-big.gif"/>
</div>
	<div id="container" class="subpage">
		<#include "head.ftl" /> 
	
	<table cellspacing="0" class="columns">
          <tbody>
            <tr>
              <td id="content" class="round-left column">
                                <div class="wrapper">
	<div id="statuses" class="statuses">
				<span class="tweet_tip">你在做什么？ 按Ctrl+Enter快捷发布</span><span class="tweet_count_info">剩余：<span id="tweet_count" class="tweet_count_green">140</span>字</span><br />
				<textarea rows="5" cols="20" id="tweet_msg" name="tweet_msg"></textarea><br />
				<button id="btn_shorturl">缩短链接</button>
				<button id="tweet_submit">发送！</button>
			</div>
			<div class="fixed"></div>
			<div id="follow_warp">
				<span class="name">${user_show.screenName}的${title}:</span><br />
				<#list follow as f>
				<div id="follow_${f.id}" class="follow_status">
					<div class="user_img_div"><img src="${f.profileImageURL}" alt="${f.screenName}" class="user_img"/></div>
					<div class="follow_content">
						<span class="follow_name"><a href="/@${f.screenName}">${f.screenName}</a></span>
						<span class="follow_action"><a href="javascript:void(0);" class="follow_action_msg">发送消息</a></span>
						<div class="fixed"></div>
						<span class="follow_info">
							<a href="/following?id=${f.screenName}" class="follow_info_following">${f.friendsCount}个朋友</a>
							<a href="/follower?id=${f.screenName}" class="follow_info_follower">${f.followersCount}个关注者</a>
							<a href="/@${f.screenName}" class="follow_info_status">${f.statusesCount}条推</a>
							<a href="/@${f.screenName}&show=favor" class="follow_info_favor">${f.favouritesCount}条收藏</a>
						</span>
						<span class="follow_description">简介：${f.description}</span>
					</div>
				</div>
				<div class="fixed"></div>
				</#list>	
			</div>
			<div class="fixed"></div>
			<div id="follow_page">
				<#if user.screenName?lower_case == user_show.screenName?lower_case>
					<#if page gt 1><a href="${uri}?page=${page - 1}" class="pre_page">上一页</a></#if>
					<#if follow?size gte 19><a href="${uri}?page=${page + 1}" class="next_page">下一页</a></#if>
				<#else>
					<#if page gt 1><a href="${uri}?id=${user_show.screenName}&page=${page - 1}" class="pre_page">上一页</a></#if>
					<#if follow?size gt 1><a href="${uri}?id=${user_show.screenName}&page=${page + 1}" class="next_page">下一页</a></#if>
				</#if>
			</div>
		</div>
	</td>
              
                <td id="side_base" class="column round-right">
                                  
                  <div id="side">
			<#if user.screenName?lower_case == user_show.screenName?lower_case>
				<#include "side.ftl" />
			<#else>
				<#include "side_other.ftl" />
			</#if>
		</div>
		<div class="fixed"></div>
	</td>

              
            </tr>
          </tbody>
        </table>


  <div id="footer" class="round">
		<#include "foot.ftl" />
	</div>
		<div class="fixed"></div>
	</div>
<script type="text/javascript" src="/js/func.js"></script>
<script type="text/javascript" src="/js/follow.js"></script>
</body>
</html>