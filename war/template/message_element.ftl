<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<#list msg as m>
	<div id="msg_${m.id?c}" class="msgs<#if addclass?exists> ${addclass}</#if>">
	<div class="user_img_div"><img src="${m.sender.profileImageURL}" class="user_img" alt="${m.senderScreenName}"/></div>
	<div class="msg_content">
		<span class="msg_user"><a href="/user?id=${m.senderScreenName}">${m.senderScreenName}</a></span>
		<span class="msg_text">${m.html}</span>
		<div class="fixed"></div>
		<span class="tweet_info">发表于${m.createdTimeago}</span>
		<span class="msg_id">${m.id?c}</span>
		<span class="msg_action">
			<a href="javascript:void(0);" class="msg_action_reply">回复</a>
			<a href="javascript:void(0);" class="msg_action_del">删除</a>
		</span>
	</div>
	</div>
	<div class="fixed"></div>
</#list>