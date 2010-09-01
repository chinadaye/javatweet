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
<form method="post" action="/m/post">
  <textarea id="status" name="status" rows="3" style="width:100%; max-width: 400px;">@${user.getScreenName()} </textarea>
  <div><input name="in_reply_to_id" value="" type="hidden" /><input type="submit" value="我推！" /> <span id="remaining">140</span></div>
</form>
<script type="text/javascript">
	function updateCount() {
		document.getElementById("remaining").innerHTML = 140 - document.getElementById("status").value.length;
		setTimeout(updateCount, 400);
	}
	updateCount();
</script>
<table>
<tr>
	<td><img src="${user.getProfileImageURL()}" height="48" width="48" /></td>
	<td>
		<b><a href="/m/user/${user.getScreenName()}">${user.getScreenName()}</a> (${user.getName()})</b>
		<small>
			<br />简介: ${user.getDescription()?default("")}
			<br />链接: <#if user.getURL()?exists><a href="${user.getURL()}" target="_blank">${user.getURL()}</a></#if>
			<br />位置: ${user.getLocation()?default("")}
		</small>
			<br />
			<a href="/m/user/${user.getScreenName()}">${user.getStatusesCount()?c}推 </a>
			| <a href="/m/user/${user.getScreenName()}/follower">${user.getFollowersCount()?c}关注者</a> 
			| <a href="/m/user/${user.getScreenName()}/following">${user.getFriendsCount()?c}朋友</a>
			| <a href="/m/user/${user.getScreenName()}/favorites">${user.getFavouritesCount()?c}收藏</a>	
			| <a href="/m/dm?u=${user.getScreenName()}">发送消息</a> 
			| <#if user.isFollowing()><a href="/m/unfo?u=${user.getScreenName()}">取消关注</a><#else><a href="/m/fo?u=${user.getScreenName()}">关注</a></#if> 
			| <#if blocked><a href="/m/unb?u=${user.getScreenName()}">取消屏蔽</a><#else><a href="/m/b?u=${user.getScreenName()}">屏蔽</a></#if>
	
	</td>
</tr>	
</table>
<#if status?exists>
<table class="timeline">
<tbody>
	<#include "status_element.ftl" />
</tbody>
</table>
<#if page?exists>
<div id="page">
<#if page gt 1><a href="?p=${page - 1}">上一页</a> </#if>
<a href="?p=${page + 1}">下一页</a>
</div>
</#if>
<#else>
<div>该用户已设置保护。</div>
</#if>
<#include "menu_bottom.ftl" />
<p><a href="http://code.google.com/p/javatweet">JavaTweet Mobile</a></p>
</body>
</html>