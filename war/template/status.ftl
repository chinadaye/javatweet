<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JTweet - Status - ${title}</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
</head>

<body class="session">

<div id="container">
	<div id="header">
    	<#include "header.ftl" />
    <!--#header--></div>
	<div class="content-bubble-arrow clear"></div>
	<div id="status_wrap" class="round">
		<#if status?exists>
		<div class="status_text">${texttohtml(status.getText())}</div>
		<div class="status_info">	
			<span class="published timestamp" ><a class="entry-date" href="/status/${status.getId()?c}" target="_blank">大约在 ${status.getHumanTime()}</a></span>
			<span class="status_source">通过 ${status.getSource()} 发表</span>
			<#if status.getInReplyToScreenName()?exists>
			<a href="/status/${status.getInReplyToStatusId()?c}" class="status_reply_to">对${status.getInReplyToScreenName()}的回复</a>
			</#if>
		</div>
		<div id="status_user_warp">
			<#assign u=status.getUser()> 
			<img src="${getbigpic(u.getProfileImageURL())}" alt="${u.getScreenName()}" class="user_img_big left" with="73" height="73"/>
			<span class="user_name_div">
				<a href="/user/${u.getScreenName()}" class="status_user_screenname">${u.getScreenName()}</a>
				<div class="status_user_name">${u.getName()}</div>
			</span>
			<div class="fixed"></div>
		</div>
		<#else>
		<div class="status_protected">对不起，该用户已设置保护！</div>
		</#if>
	</div>
<#include "footer.ftl" />
<!-- #container --></div>
</body>
</html>
