<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#setting time_zone="GMT+8">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>JTeet /${title} /${user_show.screenName}</title>
	<link type="text/css" href="template/style-all20091030.css" rel="stylesheet" />
	<script type="text/javascript" src="/js/jquery-1.3.2.min.js" ></script>
	<script type="text/javascript" src="/js/jquery.cookie.js" ></script>
<style type="text/css">
	  body {
  background: #${user_show.profileBackgroundColor} url('${user_show.profileBackgroundImageUrl}') fixed <#if user_show.profileBackgroundTile == 'false'>no-repeat<#else>repeat-x</#if> top left;

}


a,
#content tr.hentry:hover a,
body#profile #content div.hentry:hover a,
#side .stats a:hover span.stats_count,
#side div.user_icon a:hover,
li.verified-profile a:hover,
#side .promotion .definition strong,
p.list-numbers a:hover,
#side div.user_icon a:hover span,
#content .tabMenu li a,
.translator-profile a:hover {
  color: #${user_show.profileLinkColor};
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
.list-header h2 a span,
#content .tabMenu li.active a,
body#direct_messages #content .tabMenu #inbox_tab a,
body#inbox #content .tabMenu #inbox_tab a,
body#sent #content .tabMenu #sent_tab a,
body#direct_messages #content .tabMenu #inbox_tab a,
body#retweets_by_others #content .tabMenu #retweets_by_others_tab a,
body#retweets #content .tabMenu #retweets_tab a,
body#retweeted_by_others #content .tabMenu #retweeted_by_others_tab a,
body#retweeted_of_mine #content .tabMenu #retweeted_of_mine_tab a,
.translator-profile a {
  color: #${user_show.profileTextColor};
}

#side_base {
  border-left:1px solid #${user_show.profileSidebarBorderColor};
  background-color: #${user_show.profileSidebarFillColor};
}
.side_user_link_last,.side_user_link,.side_link
{
	border-color:#${user_show.profileSidebarFillColor};
}
ul.sidebar-menu li.active a,
ul.sidebar-menu li a:hover,
#side div#custom_search.active,
#side .promotion,
.notify div {
  background-color: #1B201F;
}

.list-header,
.list-controls,
ul.sidebar-list li.active a,
ul.sidebar-list li a:hover {
  background-color: #030807 !important;
}

#side .actions,
#side .promo {
  border: 1px solid #051A17;
}

#side div.section-header h3 {
  border-bottom: 1px solid #051A17;
}

#side hr {
  background: #051A17;
  color: #051A17;
}

ul.sidebar-menu li.loading a {
  background: #1B201F url('http://a1.twimg.com/a/1256237813/images/spinner.gif') no-repeat 171px 0.5em !important;
}

#side .collapsible h2.sidebar-title {
  background: transparent url('http://a2.twimg.com/a/1256237813/images/toggle_up_light.png') no-repeat center right !important;
}

#side .collapsible.collapsed h2.sidebar-title {
  background: transparent url('http://a1.twimg.com/a/1256237813/images/toggle_down_light.png') no-repeat center right !important;
}

#side ul.lists-links li a em {
  background: url('http://a1.twimg.com/a/1256237813/images/arrow_right_light.png') no-repeat left top;
}

#side span.pipe {
  border-left:1px solid #051A17;
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
				<button id="tweet_submit">发送！</button>
			</div>
			<div class="fixed"></div>
			<div id="follow_warp">
				<span class="name">${user_show.screenName}的${title}:</span><br />
				<#list follow as f>
				<div id="follow_${f.id}" class="follow_status">
					<div class="user_img_div"><img src="${f.profileImageURL}" alt="${f.screenName}" class="user_img"/></div>
					<div class="follow_content">
						<span class="follow_name"><a href="/user?id=${f.screenName}">${f.screenName}</a></span>
						<span class="follow_action"><a href="javascript:void(0);" class="follow_action_msg">发送消息</a></span>
						<div class="fixed"></div>
						<span class="follow_info">
							<a href="/following?id=${f.screenName}" class="follow_info_following">${f.friendsCount}个朋友</a>
							<a href="/follower?id=${f.screenName}" class="follow_info_follower">${f.followersCount}个关注者</a>
							<a href="/user?id=${f.screenName}" class="follow_info_status">${f.statusesCount}条推</a>
							<a href="/user?id=${f.screenName}&show=favor" class="follow_info_favor">${f.favouritesCount}条收藏</a>
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