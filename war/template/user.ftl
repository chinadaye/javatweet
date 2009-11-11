<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#setting time_zone="GMT+8">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>@${user_show.screenName}-Jteet</title>
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
			<div id="info_warp">
				<img src="${user_show.biggerImageURL}" alt="${user_show.screenName}" class="user_img_big"/>
				<div class="user_div">
					<span class="user_name">${user_show.screenName}</span>
					<#if user?exists>
					<span class="user_action">
						<a href="javascript:void(0);" class="user_action_msg">发送私信</a>
						<#if user_show.following>
							<a href="javascript:void(0);" class="user_action_unfollow">取消关注</a>
						<#else>
							<a href="javascript:void(0);" class="user_action_follow">关注</a>
						</#if>
						<#if user_show.blocked>
							<a href="javascript:void(0);" class="user_action_unblock">取消屏蔽</a>
						<#else>
							<a href="javascript:void(0);" class="user_action_block">屏蔽</a>
						</#if>
					</span>	
					</#if>
				</div>
			</div>
			<div class="fixed"></div>
			<#if user?exists>
			<div id="form_warp" class="user_form">
				<span class="tweet_tip">你在做什么？ 按Ctrl+Enter快捷发布</span><span class="tweet_count_info">剩余：<span id="tweet_count" class="tweet_count_green">140</span>字</span><br />
				<textarea rows="5" cols="20" id="tweet_msg" name="tweet_msg"></textarea><br />
				<button id="btn_shorturl">缩短链接</button>
				<button id="tweet_submit">我推！</button>
			</div>
			</#if>
			<div class="fixed"></div>
			<div id="tweet_warp">
				<#if status?exists>
					<#list status as s>
						<div id="tweet_${s.id?c}" class="tweets">
							<span class="tweet_user hide">${s.user.screenName}</span>
							<span class="tweet_text">${s.html}</span>
							<div class="fixed"></div>
							<span class="tweet_info">
								<span class="tweet_source">通过${s.source}</span>
								<#if s.inReplyToScreenName?trim != "">
									<span class="tweet_reply_to"><a href="/status?id=${s.inReplyToStatusId?c}">对${s.inReplyToScreenName}的回复</a></span>
								</#if>
								<span class="tweet_link"><a href="/status?id=${s.id?c}">发表于${s.createdTimeago}</a></span>
							</span>
							<span class="tweet_id">${s.id?c}</span>
							<!--<div class="fixed"></div>-->
							<span class="tweet_action">
								<a href="javascript:void(0);" class="tweet_action_reply">回复</a>
								<a href="javascript:void(0);" class="tweet_action_rt">锐推</a>
								<#if s.favorited>
									<a href="javascript:void(0);" class="tweet_action_unfavor">取消收藏</a>			
								<#else>
									<a href="javascript:void(0);" class="tweet_action_favor">收藏</a>
								</#if>
								<#if user?exists&&user.screenName?lower_case == s.user.screenName?lower_case>
									<a href="javascript:void(0);" class="tweet_action_del">删除</a>
								</#if>
							</span>
						</div>
						<div class="fixed"></div>
					</#list>
					<div class="fixed"></div>
					<div id="tweet_page">
						<#if page gt 1><a href="${uri}&page=${page - 1}" class="pre_page">上一页</a></#if>
						<#if status?size gt 1><a href="${uri}&page=${page + 1}" class="next_page">下一页</a></#if>
					</div>
				<#else>
				<span class="user_protected">对不起，该用户已设置消息保护。</span>
				</#if>
			</div>
		</div>
	</td>
              
                <td id="side_base" class="column round-right">
                                  
                  <div id="side">
			<#if user?exists&&user.screenName?lower_case == user_show.screenName?lower_case>
				<#include "side.ftl" />
			<#else>
				<#include "side_other.ftl" />
			</#if>
		</div>
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
<script type="text/javascript" src="/js/user.js"></script>
</body>
</html>