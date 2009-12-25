<#if user?exists>
<div class="side_user_info">
	<div class="side_user_img_div">
		<img alt="${user.screenName}" src="${user.profileImageURL}" class="usr_img" />
	</div>
	<div class="side_user_name">
		<span id="side_user_screenname">${user.screenName}<a href="javascript:;" id="reflesh_profile">刷新</a></span><br />
		<span id="side_user_name">(${user.name})-${user.location}</span>
	</div>

</div>
<div class="fixed"></div>
<div class="side_user_count">
	<a href="/following" class="side_user_link"><span class="side_count" id="side_count_friends">${user.friendsCount}</span><br/><span class="side_tip">朋友</span></a>
	<a href="/follower" class="side_user_link"><span class="side_count" id="side_count_followers">${user.followersCount}</span><br/><span class="side_tip">关注者</span></a>
	<a href="/@${user.screenName}" class="side_user_link side_user_link_last"><span class="side_count" id="side_count_statuses">${user.statusesCount}</span><br/><span class="side_tip">推</span></a>
</div>
<div class="fixed"></div>
<div class="side_link_content">
	<a href="/home" class="side_link">首页</a>
	<a href="/@${user.screenName}" class="side_link">我的主页</a>
	<a href="/reply" class="side_link">@${user.screenName}</a>
	<a href="/inbox" class="side_link">私信</a>
	<a href="/favor" class="side_link side_link_last">收藏</a>
</div>
<div class="fixed"></div>
</#if>
<hr>
<form action="/search" method="get" id="searchform">
		<input type="text" name="s" class="searchtext" size=15 value="<#if search?exists>${search}</#if>"/>
		<button type="submit">搜索</button>
	</form>

<div class="fixed"></div>
<#if searches?exists>
<div id="saved_searches">
<h3>保存的搜索<a id="show_add_search" href="javascript:;">+</a></h3>
<form method="POST" action="/action" onsubmit="return onAddSearch(this);" id="add_search_form" style="display:none;"><input type="hidden" name="type" value="addquery"><input size="8" id="add_search_query" name="query"/><input id="btn_add_search" type="submit" value="添加"></form>
<#list searches as s>
<p id="saved_search_${s.id?c}" class="search"><a  href="/search?s=${s.name?url('utf-8')}">#${s.name}</a><a href="javascript:;" class="del_saved_search" style="display:none;" rel="${s.id?c}">X</a><p>
</#list>
</div>
</#if>
	<div class="fixed"></div>
	<hr>
请在推中添加<a href="/search?s=freeliuxiaobo">#freeliuxiaobo</a>

<div class="fixed"></div>
<div class="tweet_tip">
<hr>
<span>提示</span>
<p>请收藏<a href="http://jteet.com" target="_blank">http://jteet.com</a>或关注我的帐号（<a href="/@sospartan" title="/@sospartan">@sospartan</a>），我会通过以上方式提供最新的使用方法，避免GFW带来不便</p>
<p>如果发现问题或者大家有什么建议，请<a href="http://code.google.com/p/javatweet/issues/list" target="_blank" title="new issue">及时反馈给我们</a></p>
</div>
