<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<#global ii=1/>
<#list msgs as m>
<#assign u=m.getSender()/>
<#if ii % 2 == 0>
<tr class="odd">
<#else>
<tr class="even">
</#if>
<#global ii = ii + 1/>
<td>
	<img src="${GetMiniPic(u.getProfileImageURL())}" height="24" width="24" />
</td>
<td>
	<b><a href="/m/user/${m.getSenderScreenName()}">${m.getSenderScreenName()}</a></b> 
	<a href="/m/dm?u=${m.getSenderScreenName()}"><img src="/img/dm.png" /></a>
	<a href="/m/delmsg?id=${m.getId()?c}"><img src="/img/trash.gif" /></a> 
	<small>${m.getCreatedAt()?datetime}</small>
	<br />
	${TexttoHTML(m.getText())}
</td>
</tr>
</#list>