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
<div class="fixed">
</div>
<div>
<p>吾家表妹（学生）新开滴店，全场包邮！大家多多捧场，就说表哥介绍来的，就有额外优惠！！</p>
<a href="http://idreams.taobao.com" target="_blank"><img src="/img/idream.png" width='190'></a>
</div>
<form action="/search" method="get" id="searchform">
		<input type="text" name="s" class="searchtext" size=15 value="<#if search?exists>${search}</#if>"/>
		<button type="submit">搜索</button>
		<label for="search_people" style="display:block;"><input type="checkbox" name="search_people" id="search_people" value="1">搜人</label>
	</form>

<div class="fixed"></div>
<#if searches?exists>
<div id="saved_searches">
<h3>保存的搜索<a id="show_add_search" href="javascript:;">⊕</a></h3>
<form method="POST" action="/action" onsubmit="return onAddSearch(this);" id="add_search_form" style="display:none;"><input type="hidden" name="type" value="addquery"><input size="8" id="add_search_query" name="query"/><input id="btn_add_search" type="submit" value="添加"></form>
<#list searches as s>
<p id="saved_search_${s.id?c}" class="search"><a  href="/search?s=${s.name?url('utf-8')}">#${s.name}</a><a href="javascript:;" class="del_saved_search" style="display:none;" rel="${s.id?c}">✗</a><p>
</#list>
</div>
</#if>

<#if trends?exists>
<div class="fixed"></div>
<div class="tweet_tip">
<h3>twitter 趋势</h3>
<#list trends as t>
<p><a href="/search?s=${t.getUrlName()}" name="搜索${t.getName()}">${t.getName()}</a></p>
</#list>
</div>
</#if>

<#if rebang?exists>
<div class="fixed"></div>
<div class="tweet_tip">
<h3>谷歌热榜上升最快</h3>
<#list rebang as r>
<p><a href="/search?s=${r?url('utf-8')}" name="搜索${r?url('utf-8')}">${r}</a></p>
</#list>
</div>
</#if>

<div class="fixed"></div>
<div class="tweet_tip">
☞<a href="/search?s=freeliuxiaobo">#freeliuxiaobo</a>
<br>
</div>
<div class="fixed">
<a href="http://code.google.com/p/thestoryofmalegebi/" target="_blank"><img src="/img/malegebitandegushi.gif" width='200'></a>
</div>

