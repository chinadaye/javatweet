<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<#global ii=1/>
<#list status as s>
<#assign u=s.getUser()/>
<#if ii % 2 == 0>
<tr class="odd">
<#else>
<tr class="even">
</#if>
<#global ii = ii + 1/>
<#if s.isRetweet()>
<#assign s_rt=s.getRetweetedStatus()/>
<#assign u_rt=s_rt.getUser()/>
<td>
	<img src="${GetMiniPic(u_rt.getProfileImageURL())}" height="24" width="24" />
</td>
<td>
	<b>RT <a href="/m/user/${u_rt.getScreenName()}">${u_rt.getScreenName()}</a></b> 
	<a href="/m/re?u=${u_rt.getScreenName()}&id=${s_rt.getId()?c}"><img src="/img/reply.png" /></a> 
	<a href="/m/dm?u=${u_rt.getScreenName()}"><img src="/img/dm.png" /></a> 
	<#if s_rt.isFavorited()>
	<a href="/m/unfav?id=${s_rt.getId()?c}"><img src="/img/star.png" /></a> 
	<#else>
	<a href="/m/fav?id=${s_rt.getId()?c}"><img src="/img/star_grey.png" /></a> 
	</#if>
	<a href="/m/rt?id=${s_rt.getId()?c}"><img src="/img/retweet.png" /></a> 
	<#if u.getScreenName()?lower_case == login_user.getScreenName()?lower_case>
	<a href="/m/delstatus?id=${s.getId()?c}"><img src="/img/trash.gif" /></a> 
	</#if>
	<small><a href="/m/status/${s.getId()?c}">${s.getCreatedAt()?datetime}</a></small>
	<br />
	${TexttoHTML(s_rt.getText())}
	<small> from ${s_rt.getSource()}<#if s_rt.getInReplyToScreenName()?exists> in reply to <a href="/m/status/${s_rt.getInReplyToStatusId()?c}">${s_rt.getInReplyToScreenName()}</a></#if> retweet to you by <a href="/m/user/${u.getScreenName()}">${u.getScreenName()}</a></small>
</td>
<#else>
<td>
	<img src="${GetMiniPic(u.getProfileImageURL())}" height="24" width="24" />
</td>
<td>
	<b><a href="/m/user/${u.getScreenName()}">${u.getScreenName()}</a></b> 
	<a href="/m/re?u=${u.getScreenName()}&id=${s.getId()?c}"><img src="/img/reply.png" /></a> 
	<a href="/m/dm?u=${u.getScreenName()}"><img src="/img/dm.png" /></a> 
	<#if s.isFavorited()>
	<a href="/m/unfav?id=${s.getId()?c}"><img src="/img/star.png" /></a> 
	<#else>
	<a href="/m/fav?id=${s.getId()?c}"><img src="/img/star_grey.png" /></a> 
	</#if>
	<a href="/m/rt?id=${s.getId()?c}"><img src="/img/retweet.png" /></a> 
	<#if u.getScreenName()?lower_case == login_user.getScreenName()?lower_case>
	<a href="/m/delstatus?id=${s.getId()?c}"><img src="/img/trash.gif" /></a> 
	</#if>
	<small><a href="/m/status/${s.getId()?c}">${s.getCreatedAt()?datetime}</a></small>
	<br />
	${TexttoHTML(s.getText())}
	<small> from ${s.getSource()}<#if s.getInReplyToScreenName()?exists> in reply to <a href="/m/status/${s.getInReplyToStatusId()?c}">${s.getInReplyToScreenName()}</a></#if></small>
</td>
</#if>
</tr>
</#list>