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
<hr>
请在推中添加<a href="/search?s=freeliuxiaobo">#freeliuxiaobo</a>
</div>
<div class="fixed"></div>
<div class="tweet_tip">
<span>提示</span>
<p>点击短链接将自动进行还原</p>
<p>本客户端还处在开发和大规模的修改中，如果发现问题或者大家有什么建议，请<a href="http://code.google.com/p/javatweet/issues/list" target="_blank" title="new issue">及时反馈给我们</a></p>
</div>
<div class="fixed"></div>
<hr>
<div class="tweet_tip">
<span>给洒家买碗酒?</span>
<form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_blank">
<input type="hidden" name="cmd" value="_s-xclick">
<input type="hidden" name="encrypted" value="-----BEGIN PKCS7-----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-----END PKCS7-----
">
<input type="image" src="https://www.paypal.com/zh_XC/i/btn/btn_donateCC_LG.gif" border="0" name="submit" alt="PayPal — 最安全便捷的在线支付方式！">
<img alt="" border="0" src="https://www.paypal.com/zh_XC/i/scr/pixel.gif" width="1" height="1">
</form>
</div>
