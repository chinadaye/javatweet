<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#setting time_zone="GMT+8">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>JTweet - ${title} - ${user_show.screenName}</title>
	<link type="text/css" href="template/style.css" rel="stylesheet" />
	<script type="text/javascript" src="/js/jquery-1.3.2.min.js" ></script>
	<script type="text/javascript" src="/js/jquery.cookie.js" ></script> 
	<link href="/template/style2.css" media="screen" rel="stylesheet" type="text/css" />
	<link href="/template/style3.css" media="screen" rel="stylesheet" type="text/css" />
<style type="text/css">
      
        body { background: #C0DEED url(/img/bg-clouds.jpg) repeat-x; }

a,
#content tr.hentry:hover a,
body#profile #content div.hentry:hover a,
#side .stats a:hover span.stats_count,
#side div.user_icon a:hover,
li.verified-profile a:hover,
#side .promotion .definition strong,
p.list-numbers a:hover,
#side div.user_icon a:hover span {
  color: #0099B9;
}

body,
ul#tabMenu li a, #side .section h1,
#side .stat a,
#side .stats a span.stats_count,
#side div.section-header h1,
#side div.user_icon a,
#side div.user_icon a:hover,
#side div.section-header h3.faq-header,
ul.sidebar-menu li.active a,
li.verified-profile a,
#side .promotion a,
body #content .list-header h2,
p.list-numbers a,
.bar h3 label,
body.timeline #content h1,
.list-header h2 a span {
  color: #3C3940;
}

#side_base {
  border-left:1px solid #5ED4DC;
  background-color: #95E8EC;
}

ul.sidebar-menu li.active a,
ul.sidebar-menu li a:hover,
#side div#custom_search.active,
#side .promotion,
.notify div {
  background-color: #A9FCFF;
}

.list-header,
.list-controls,
ul.sidebar-list li.active a,
ul.sidebar-list li a:hover {
  background-color: #95E8EC !important;
}

#side .actions,
#side .promo {
  border: 1px solid #5ED4DC;
}

#side div.section-header h3 {
  border-bottom: 1px solid #5ED4DC;
}

#side hr {
  background: #5ED4DC;
  color: #5ED4DC;
}

#side span.view-all {
  border-left:1px solid #5ED4DC;
}

#list_subscriptions span.view-all,
#list_memberships span.view-all,
#profile span.view-all,
#profile_favorites span.view-all,
#following span.view-all,
#followers span.view-all {
  border-left: 0;
}      
    
	</style>
</head>
<body class="sessions" id="new">
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
					<span class="user_action">
						<a href="javascript:void(0);" class="user_action_msg">发送消息</a>
						<#if user_show.following>
							<a href="javascript:void(0);" class="user_action_unfollow">取消跟踪</a>
						<#else>
							<a href="javascript:void(0);" class="user_action_follow">跟踪</a>
						</#if>
						<#if user_show.blocked>
							<a href="javascript:void(0);" class="user_action_unblock">取消屏蔽</a>
						<#else>
							<a href="javascript:void(0);" class="user_action_block">屏蔽</a>
						</#if>
					</span>	
				</div>
			</div>
			<div class="fixed"></div>
			<div id="form_warp" class="user_form">
				<span class="tweet_tip">你在做什么？ 按Ctrl+Enter快捷发布</span><span class="tweet_count_info">剩余：<span id="tweet_count" class="tweet_count_green">140</span>字</span><br />
				<textarea rows="5" cols="20" id="tweet_msg" name="tweet_msg"></textarea><br />
				<button id="tweet_submit">我推！</button>
			</div>
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
								<span class="tweet_link"><a href="/status?id=${s.id?c}">发表于${s.createdAt?datetime}</a></span>
							</span>
							<span class="tweet_id">${s.id?c}</span>
							<div class="fixed"></div>
							<span class="tweet_action">
								<a href="javascript:void(0);" class="tweet_action_reply">回复</a>
								<a href="javascript:void(0);" class="tweet_action_rt">锐推</a>
								<#if s.favorited>
									<a href="javascript:void(0);" class="tweet_action_unfavor">取消收藏</a>			
								<#else>
									<a href="javascript:void(0);" class="tweet_action_favor">收藏</a>
								</#if>
								<#if user.screenName?lower_case == s.user.screenName?lower_case>
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
				<span class="user_protected">对不起，该用户已保密。</span>
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