<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
					<div id="profile" class="section">
                    	<div class="user_icon">
                        	<a href="/user/${user.getScreenName()}" class="url" title="${user.getName()}"><img alt="${user.getName()}" class="side_thumb left" height="48" src="${user.getProfileImageURL()}" width="48" /><div id="me_name">${user.getScreenName()}</div><div id="me_tweets"><span id="update_count">${login_user.getStatusesCount()?c}</span>推</div></a>
                        </div>
                        <div class="fixed"></div>
						<div id="side_user_location">位置:${user.getLocation()}</div>
						<#if user.getURL()?exists>
						<div id="side_user_url_div">主页：<a href="${user.getURL()}" target="_blank">${user.getURL()}</a></div>
						</#if>
						<div id="side_user_description">简介：<#if user.getDescription()?exists>${user.getDescription()}</#if></div>
                        <div class="fixed"></div>
                        <div class="stats">
                        	<table>
                            	<tr>
                                	<td width="60">
                                    	<a href="/user/${user.getScreenName()}/following" id="following_count_link" class="link-following_page" title="朋友"><div id="following_count" class="stats_count numeric">${user.getFriendsCount()?c}</div><div class="label">朋友</div></a>
                                    </td>
                                    <td width="60">
                                    	<a href="/user/${user.getScreenName()}/follower" id="follower_count_link" class="link-followers_page" rel="me" title="关注者"><div id="follower_count" class="stats_count numeric">${user.getFollowersCount()?c}</div><div class="label">关注者</div></a>
                                    </td>
                                    <td width="60"><a href="/user/${user.getScreenName()}/favorites" id="favs_count_link" class="link-favs_page" rel="me" title="收藏"><div id="favs_count" class="stats_count numeric">${user.getFavouritesCount()?c}</div><div class="label">收藏</div></a>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    <!--#profile--></div>
                    <ul id="primary_nav" class="sidebar-menu">
                    	<li id="home_tab"><a href="/user/${user.getScreenName()}" class="in-page-link"><span>TA的推</span></a></li>
                        <li id="favorites_tab"><a href="/user/${user.getScreenName()}/favorites"  class="in-page-link"><span>TA的收藏</span></a></li>
                    </ul>
					<form action="/search" method="get" id="searchform">
						<input type="text" name="s" id="searchtext"/>
						<button type="submit" class="round">搜索</button>
					</form>