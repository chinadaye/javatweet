<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<#list tweets as s>
	<div id="tweet_${s.id?c}" class="tweets">
	<div class="user_img_div"><img src="${s.profileImageUrl}" class="user_img" alt="${s.fromUser}"/></div>
	<div class="tweet_content">
		<span class="tweet_user"><a href="/@${s.fromUser}">${s.fromUser}</a></span>
		<span class="tweet_text">${s.html}</span>
		<div class="fixed"></div>
		<span class="tweet_info">
		<span class="tweet_link"><a href="/status?id=${s.id?c}" class="status_create_at" rel="${s.createTime}">${s.createdTimeago}</a></span>
		<#if s.toUser?exists>
				<#if s.toUser?trim != "">
					<span class="tweet_reply_to">对<a href="/@${s.toUser}">${s.toUser}</a>的回复</span>
				</#if>
		</#if>
		<span class="tweet_source">通过${s.source}</span>
			
		</span>
		<span class="tweet_id">${s.id?c}</span>
		<#if user?exists>
		<span class="tweet_action">
			<a href="javascript:void(0);" class="tweet_action_reply" title="回复">@</a>
			<a href="javascript:void(0);" class="tweet_action_rt" title="锐推">RT</a>			
			<a href="javascript:void(0);" class="tweet_action_favor" title="收藏">☆</a>
			<#if user.screenName?lower_case == s.fromUser?lower_case>
				<a href="javascript:void(0);" class="tweet_action_del" title="删除">✗</a>
			</#if>
		</span>
		</#if>
	</div>
	</div>
	<div class="fixed"></div>
</#list>