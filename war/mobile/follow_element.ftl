<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<#global ii=1/>
<#list follows as f>
<#if ii % 2 == 0>
<tr class="odd">
<#else>
<tr class="even">
</#if>
<#global ii = ii + 1/>
	<td><img src='${GetMiniPic(f.getProfileImageURL())}' height='24' width='24' /></td>
	<td>
		<a href='/m/user/${f.getScreenName()}'>${f.getScreenName()}</a> - ${f.getLocation()?default("")}
		<br />
		<small>
			简介：${f.getDescription()?default("")}
			<br />
			<a href="/m/user/${f.getScreenName()}">${f.getStatusesCount()}条推</a>, 
			<a href="/m/user/${f.getScreenName()}/following">${f.getFriendsCount()}朋友</a>, 
			<a href="/m/user/${f.getScreenName()}/follower">${f.getFollowersCount()}关注者</a>, 
			<a href="/m/user/${f.getScreenName()}/favorites">${f.getFavouritesCount()}收藏</a>
		</small>
	</td>
</tr>
</#list>