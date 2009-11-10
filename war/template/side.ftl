<#if user?exists>
<div class="side_user_info">
	<div class="side_user_img_div">
		<img alt="${user.screenName}" src="${user.profileImageURL}" class="usr_img" />
	</div>
	<div class="side_user_name">
		<span id="side_user_screenname">${user.screenName}</span><br />
		<span id="side_user_name">（${user.name}）</span>
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
	<a href="/home" class="side_link">首页</a>
	<a href="/user?id=${user.screenName}" class="side_link">我的主页</a>
	<a href="/reply" class="side_link">@${user.screenName}</a>
	<a href="/inbox" class="side_link">私信</a>
	<a href="/favor" class="side_link side_link_last">收藏</a>
</div>
<div class="fixed"></div>
</#if>
<form action="/search" method="get" id="searchform">
		<input type="text" name="s" class="searchtext" size=15 value="<#if search?exists>${search}</#if>"/>
		<button type="submit">搜索</button>
	</form>
<div class="fixed"></div>
<#if searches?exists>
<div id="saved_searches">
<h3>保存的搜索</h3>
<#list searches as s>
<a href="/search?s=${s.urlName}">#${s.name}</a>
</#list>
</div>
</#if>
<div class="tweet_tip">
<span>提示</span>
<p>点击短链接将自动进行还原</p>
</div>
<#-- 
<#if rate?exists>
	<div id="side_rate_div">
		<#include "rate.ftl" />
	</div>
	<div class="fixed"></div>
</#if>
-->
