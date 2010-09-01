<div class="menu menu-bottom">
	<#if login_user?exists>
	<b><a href="/m/user/${login_user.getScreenName()}">${login_user.getScreenName()}</a></b> | 
	</#if>
	<a href="/m/home">首页</a> | 
	<a href="/m/replies">回复</a> | 
	<a href="/m/inbox">私信</a> | 
	<a href="/m/favorites">收藏</a> | 
	<a href="/m/retweets_by_me">RT by Me</a> | 
	<a href="/m/retweets_to_me">RT to Me</a> | 
	<a href="/m/follower">关注者</a> | 
	<a href="/m/following">朋友</a> |
	<a href="/m/blocking">屏蔽列表</a> |  
	<a href="/m/public">公共页面</a> | 
	<a href="/m/search">搜索</a> | 
	<a href="/m/setting">设置</a> | 
	<a href="/m/logout">退出</a> | 
	<a href='' accesskey='5'>刷新</a> 5
</div>