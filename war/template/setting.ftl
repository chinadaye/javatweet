<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>JTweet / 设置 / ${user.screenName}</title>
	<link type="text/css" href="/template/style-all20091030.css" rel="stylesheet" />
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js" ></script>
	<script type="text/javascript" src="/js/jquery.cookie.js" ></script>
<style type="text/css">
	  body {
  background: #${user.profileBackgroundColor} url('${user.profileBackgroundImageUrl}') fixed <#if user.profileBackgroundTile == 'false'>no-repeat<#else>repeat</#if> top left;

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
  color: #${user.profileLinkColor};
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
  color: #${user.profileTextColor};
}

#side_base {
  border-left:1px solid #${user.profileSidebarBorderColor};
  background-color: #${user.profileSidebarFillColor};
}
.side_user_link_last,.side_user_link,.side_link
{
	border-color:#${user.profileSidebarFillColor};
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
	
	<script type="text/javascript">
	<!--
	 if (window.navigator.userAgent.indexOf("MSIE 6.0")>=1){
	        document.write('<body class="sessions ie ie6" id="new">')
	}else{
	        if (window.navigator.userAgent.indexOf("MSIE 7.0")>=1){
	        document.write('<body class="sessions ie ie7" id="new">')
	        }else{
				document.write('<body class="sessions firefox-windows" id="new">')
	        }
	 }
	 -->
	</script>
</head>
<body class="sessions firefox-windows" id="new">
	<div id="container" class="subpage">
		<#include "head.ftl" /> 
	
	<table cellspacing="0" class="columns">
          <tbody>
            <tr>
              <td id="content" class="round-left column">
                                <div class="wrapper">
		<#if msg?exists><center><div id="setting_rst">${msg}</div></center></#if>
		<div id="setting_img">
			<img src="${user.biggerImageURL}" alt="${user.screenName}" class="user_img_big"/>
			<form action="/setimg" method="post" enctype="multipart/form-data" id="img_form">
				<div class="img_tip">必须为GIF，JPG或PNG格式，大小小于700KB。 </div>
				<div class="img_tip">由于GAE URLFetch的缓存机制，新上传的头像并不会在JTweet上马上显示。 </div>
				<input type="file" name="image"/>
				<button type="submit" onclick="javascript:return checkext(this.form.image.value);">上传图片</button>
			</form>
		</div>
		<div id="setting_info">
			<form action="/setting" method="post" id="info_form">
				<div class="setting_div">昵称：<input type="text" id="name" name="name" class="info_input" value="${user.name}" maxlength="20" /><span class="setting_tip">最多20字</span></div>
				<div class="setting_div">网址：<input type="text" id="url" name="url" class="info_input" value="${user.URL?default("")}" maxlength="100" /><span class="setting_tip">最多100字</span></div>
				<div class="setting_div">位置：<input type="text" id="location" name="location" class="info_input" value="${user.location}" maxlength="30" /><span class="setting_tip">最多30字</span></div>
				<div class="setting_div">简介：<textarea id="description" name="description" class="info_text" >${user.description}</textarea><span class="setting_tip">最多160字</span></div>
				<center><button type="submit" id="setting_sub">修改</button></center>
			</form>
		</div>
		<!--<div id="jtweet_setting">
			<div class="setting_div">
				新消息声音提醒:
				<input type="radio" value="true" name="ring">是</input>
				<input type="radio" value="false" name="ring">否</input>
				<span class="setting_tip">Cookie保存，有效期7天。</span>
			</div>
			<center><button id="jtweet_sub" onclick="javascript:onJTweetSetting();">保存</button></center>
		</div>-->
	</div>
	</td>
              
                <td id="side_base" class="column round-right">
                                  
                  <div id="side">
		<#include "side.ftl" />
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
<script type="text/javascript" src="/js/func.js" ></script>
<script type="text/javascript" src="/js/setting.js" ></script>
<script type="text/javascript" src="/js/checkext.js" ></script>
</body>
</html>