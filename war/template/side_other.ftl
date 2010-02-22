<div class="side_user_info">
	<div class="side_user_img_div">
		<img alt="${user_show.screenName}" src="${user_show.profileImageURL}" class="usr_img" width="48" heigth="48"/>
	</div>
	<div class="side_user_name">
		<span id="side_user_screenname">${user_show.screenName}</span><br />
		<span id="side_user_name">(${user_show.name})-${user_show.location}</span>
	</div>
	<div class="fixed"></div>
	<#if user_show.URL?exists>
		<div class="side_user_url_div">主页：<a href="${user_show.URL}" target="_blank">${user_show.URL}</a></div>
	</#if>
	<div class="side_user_description">简介：${user_show.description}</div>
</div>
<div class="fixed"></div>
<div class="side_user_count2">
	<a href="/@${user_show.screenName}/following" class="side_user_link"><span class="side_count">${user_show.friendsCount}</span><br/><span class="side_tip">朋友</span></a>
	<a href="/@${user_show.screenName}/follower" class="side_user_link"><span class="side_count">${user_show.followersCount}</span><br/><span class="side_tip">关注者</span></a>
	<a href="/@${user_show.screenName}" class="side_user_link side_user_link_last"><span class="side_count">${user_show.statusesCount}</span><br/><span class="side_tip">推</span></a>
</div>
<div class="fixed"></div>
<div class="side_link_content">
	<a href="/@${user_show.screenName}" class="side_link">TA的推</a>
	<a href="/@${user_show.screenName}/favor" class="side_link side_link_last">TA的收藏</a>
</div>
<div class="fixed"></div>
<form action="/search" method="get" id="searchform">
		<input type="text" name="s" class="searchtext" size=15/>
		<button type="submit">搜索</button>
</form>
	
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
<p><a href="/search?s=${r?url}" name="搜索${r?url}">${r}</a></p>
</#list>
</div>
</#if>

<div class="fixed"></div>
<div class="tweet_tip">

请在推中添加<a href="/search?s=freeliuxiaobo">#freeliuxiaobo</a>
</div>
<div class="fixed"></div>

<div class="">
<script type="text/javascript"> 
alimama_pid="mm_10056631_2166294_8590032"; 
alimama_titlecolor="0000FF"; 
alimama_descolor ="000000"; 
alimama_bgcolor="FFFFFF"; 
alimama_bordercolor="E6E6E6"; 
alimama_linkcolor="008000"; 
alimama_bottomcolor="FFFFFF"; 
alimama_anglesize="0"; 
alimama_bgpic="0"; 
alimama_icon="0"; 
alimama_sizecode="35"; 
alimama_width=200; 
alimama_height=200; 
alimama_type=2; 
</script> 
<script src="http://a.alimama.cn/inf.js" type="text/javascript"> 
</script>
</div>
