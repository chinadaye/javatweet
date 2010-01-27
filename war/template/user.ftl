<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#setting time_zone="GMT+8">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>@${user_show.screenName}-Jteet</title>
	<link type="text/css" href="/img/style-all20091030.css" rel="stylesheet" />
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js" ></script>
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
					<#if user?exists&&user.screenName!=user_show.screenName>
					<span class="user_action">
						<#if is_follew?exists&&is_follew=='1'>
						<a href="javascript:void(0);" class="user_action_msg">发送私信</a>
						<#else>
						<a href="javascript:void(0);" >发送私信（对方还未关注你不能发送私信)</a>
						</#if>
						<#if is_follow?exists&&is_follow=='1'>
							<a href="javascript:void(0);" class="user_action_unfollow">取消关注</a>
						<#else>
							<a href="javascript:void(0);" class="user_action_follow">关注</a>
						</#if>
						<#if is_block?exists&&is_block=='1'>
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
				<span class="tweet_tip">你在做什么?&nbsp;&nbsp;<a id="btn_shorturl" href="javascript:;">缩短链接</a>&nbsp;&nbsp;<span id="uploadimg">上传图片</span>(<a href="javascript:;" id="uploadimgly">img.ly</a>&nbsp;&nbsp;<a href="javascript:;" id="uploadtwicli">twic.li</a>)</span>
				
				<span class="tweet_count_info">剩余：<span id="tweet_count" class="tweet_count_green">140</span>字</span><br />
				<textarea rows="5" cols="20" id="tweet_msg" name="tweet_msg">@${user_show.screenName}</textarea><br />
				<button id="tweet_submit" title="按Ctrl+Enter快捷发布">我推！</button>
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
							<span class="tweet_link"><a href="/status?id=${s.id?c}">${s.createdTimeago}</a></span>
							<#if s.inReplyToScreenName?trim != "">
									<span class="tweet_reply_to"><a href="/status?id=${s.inReplyToStatusId?c}">对${s.inReplyToScreenName}的回复</a></span>
								</#if>
								<span class="tweet_source">通过${s.source}</span>
							</span>
							<span class="tweet_id">${s.id?c}</span>
							<span class="tweet_action">
							<#if user?exists>
								<a href="javascript:void(0);" class="tweet_action_reply">@</a>
								<a href="javascript:void(0);" class="tweet_action_rt">RT</a>
								<#if s.favorited>
									<a href="javascript:void(0);" class="tweet_action_unfavor">★</a>			
								<#else>
									<a href="javascript:void(0);" class="tweet_action_favor">☆</a>
								</#if>
								<#if user?exists&&user.screenName?lower_case == s.user.screenName?lower_case>
									<a href="javascript:void(0);" class="tweet_action_del">✗</a>
								</#if>
							</#if>
							</span>
						</div>
						<div class="fixed"></div>
					</#list>
					<div class="fixed"></div>
					<div id="tweet_page">
						<#if page gt 1><a href="${uri}?page=${page - 1}" class="pre_page">上一页</a></#if>
						<#if status?size gt 1><a href="${uri}?page=${page + 1}" class="next_page">下一页</a></#if>
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
<script type="text/javascript" >
var is_updatecount = <#if user?exists>true<#else>false</#if>;
</script>
<script type="text/javascript" src="/js/jquery.ajaxupload.js"></script>
<script type="text/javascript" src="/js/func.js?20100105"></script>
<script type="text/javascript" src="/js/user.js"></script>
</body>
</html>