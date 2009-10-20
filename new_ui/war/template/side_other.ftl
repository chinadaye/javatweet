<div class="side_user_info">
	<div class="side_user_img_div">
		<img alt="${user_show.screenName}" src="${user_show.profileImageURL}" class="usr_img" />
	</div>
	<div class="side_user_name">
		<span id="side_user_screenname">${user_show.screenName}</span><br />
		<span id="side_user_name">（${user_show.name}）</span>
	</div>
	<div class="fixed"></div>
	<div class="side_user_location">位置:${user_show.location}</div>
	<#if user_show.URL?exists>
		<div class="side_user_url_div">主页：<a href="${user_show.URL}" target="_blank">${user_show.URL}</a></div>
	</#if>
	<div class="side_user_description">简介：${user_show.description}</div>
</div>
<div class="fixed"></div>
<div class="side_user_count2">
	<a href="/following?id=${user_show.screenName}" class="side_user_link"><span class="side_count">${user_show.friendsCount}</span><br/><span class="side_tip">朋友</span></a>
	<a href="/follower?id=${user_show.screenName}" class="side_user_link"><span class="side_count">${user_show.followersCount}</span><br/><span class="side_tip">关注者</span></a>
	<a href="/user?id=${user_show.screenName}" class="side_user_link side_user_link_last"><span class="side_count">${user_show.statusesCount}</span><br/><span class="side_tip">推</span></a>
</div>
<div class="fixed"></div>
<div class="side_link_content">
	<a href="/user?id=${user_show.screenName}" class="side_link">TA的推</a>
	<a href="/user?show=favor&id=${user_show.screenName}" class="side_link side_link_last">TA的收藏</a>
</div>
<div class="fixed"></div>
<#if rate?exists>
	<div id="side_rate_div">
		<#include "rate.ftl" />
	</div>
	<div class="fixed"></div>
</#if>
