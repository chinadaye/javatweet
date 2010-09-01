<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<#global ii=1/>
<#list tweets as t>
<#if ii % 2 == 0>
<tr class="odd">
<#else>
<tr class="even">
</#if>
<#global ii = ii + 1/>
<td>
	<img src="${GetMiniPic(t.getProfileImageUrl())}" height="24" width="24" />
</td>
<td>
	<b><a href="/m/user/${t.getFromUser()}">${t.getFromUser()}</a></b> 
	<a href="/m/re?u=${t.getFromUser()}&id=${t.getId()?c}"><img src="/img/reply.png" /></a> 
	<a href="/m/dm?u=${t.getFromUser()}"><img src="/img/dm.png" /></a> 
	<a href="/m/rt?id=${t.getId()?c}"><img src="/img/retweet.png" /></a> 
	<#if t.getFromUser()?lower_case == login_user.getScreenName()?lower_case>
	<a href="/m/delstatus?id=${t.getId()?c}"><img src="/img/trash.gif" /></a> 
	</#if>
	<small><a href="/m/status/${t.getId()?c}">${t.getCreatedAt()?datetime}</a></small>
	<br />
	${TexttoHTML(t.getText())}
	<small> from ${t.getSource()}<#if t.getToUser()?exists> in reply to ${t.getToUser()}</#if></small>
</td>
</tr>
</#list>