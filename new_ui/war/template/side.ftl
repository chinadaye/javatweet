<div class="side_user_info">
	<div class="side_user_img_div">
		<img alt="${user.screenName}" src="${user.profileImageURL}" class="usr_img" />
	</div>
	<div class="side_user_name">
		<span id="side_user_screenname">${user.screenName}</span><br />
		<span id="side_user_name">${user.name}</span>
	</div>
</div>
<div class="fixed"></div>
<div class="side_user_count">
	<a href="/following" class="side_user_link"><span class="side_count">${user.friendsCount}</span><br/><span class="side_tip">朋友</span></a>
	<a href="/follower" class="side_user_link"><span class="side_count">${user.followersCount}</span><br/><span class="side_tip">关注者</span></a>
	<a href="/user?id=${user.screenName}" class="side_user_link side_user_link_last"><span class="side_count">${user.statusesCount}</span><br/><span class="side_tip">推</span></a>
</div>
<div class="fixed"></div>
<div class="side_link_content">
	<a href="/home" class="side_link">时间线</a>
	<a href="/user?id=${user.screenName}" class="side_link">我的推</a>
	<a href="/reply" class="side_link">@我的回复</a>
	<a href="/message" class="side_link">d我的消息</a>
	<a href="/favor" class="side_link side_link_last">我的收藏</a>
</div>
<div class="fixed"></div>
<#if rate?exists>
	<div id="side_rate_div">
		<#include "rate.ftl" />
	</div>
	<div class="fixed"></div>
</#if>