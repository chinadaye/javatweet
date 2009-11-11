<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<#list status as s>
	<div id="tweet_${s.id?c}" class="tweets<#if addclass?exists> ${addclass}</#if>">
	<div class="user_img_div"><a href="/@${s.user.screenName}"><img src="${s.user.profileImageURL}" class="user_img" alt="${s.user.screenName}"/></a></div>
	<div class="tweet_content">
		<span class="tweet_user"><a href="/@${s.user.screenName}">${s.user.screenName}</a></span>
		<span class="tweet_text">${s.html}</span>
		<div class="fixed"></div>
		<span class="tweet_info">
			<span class="tweet_source">通过${s.source}</span>
			<#if s.inReplyToScreenName?trim != "">
				<span class="tweet_reply_to"><a href="/status?id=${s.inReplyToStatusId?c}">对${s.inReplyToScreenName}的回复</a></span>
			</#if>
			<span class="tweet_link">发表于<a href="/status?id=${s.id?c}">${s.createdTimeago}</a></span>
		</span>
		<span class="tweet_id">${s.id?c}</span>
		<!--<div class="fixed"></div>-->
		<span class="tweet_action">
			<a href="javascript:void(0);" class="tweet_action_reply">回复</a>
			<a href="javascript:void(0);" class="tweet_action_rt">锐推</a>
			<#if s.favorited>
				<a href="javascript:void(0);" class="tweet_action_unfavor">取消收藏</a>			
			<#else>
				<a href="javascript:void(0);" class="tweet_action_favor">收藏</a>
			</#if>
			<#if user.screenName?lower_case == s.user.screenName?lower_case>
				<a href="javascript:void(0);" class="tweet_action_del">删除</a>
			</#if>
		</span>
	</div>
	</div>
	<div class="fixed"></div>
</#list>