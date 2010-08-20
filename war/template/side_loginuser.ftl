<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
					<div id="profile" class="section">
                    	<div class="user_icon">
                        	<a href="/user/${login_user.getScreenName()}" class="url" title="${login_user.getName()}"><img alt="${login_user.getName()}" class="side_thumb left" height="48" src="${login_user.getProfileImageURL()}" width="48" /><div id="me_name">${login_user.getScreenName()}</div><div id="me_tweets"><span id="update_count">${login_user.getStatusesCount()?c}</span>推</div></a>
                        	<a href="javascript:reloadprofile();" id="reload_a"><img src="/img/reload.png"/></a>
                        </div>
                        <div class="fixed"></div>
                        <div class="stats">
                        	<table>
                            	<tr>
                                	<td width="60">
                                    	<a href="/following" id="following_count_link" class="link-following_page" title="朋友"><div id="following_count" class="stats_count numeric">${login_user.getFriendsCount()?c}</div><div class="label">朋友</div></a>
                                    </td>
                                    <td width="60">
                                    	<a href="/follower" id="follower_count_link" class="link-followers_page" rel="me" title="关注者"><div id="follower_count" class="stats_count numeric">${login_user.getFollowersCount()?c}</div><div class="label">关注者</div></a>
                                    </td>
                                    <td width="60"><a href="/favorites" id="favs_count_link" class="link-favs_page" rel="me" title="收藏"><div id="favs_count" class="stats_count numeric">${login_user.getFavouritesCount()?c}</div><div class="label">收藏</div></a>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    <!--#profile--></div>
                    <ul id="primary_nav" class="sidebar-menu">
                    	<li id="home_tab"><a href="/home" class="in-page-link"><span>首页</span></a></li>
                        <li id="replies_tab"><a href="/replies" class="in-page-link"><span>@${login_user.getScreenName()}</span></a></li>
                        <li id="inbox_tab"><a href="/inbox" class="in-page-link"><span>消息</span></a></li>
                        <li id="outbox_tab"><a href="/outbox" class="in-page-link"><span>发件箱</span></a></li>
                        <li id="favorites_tab"><a href="/favorites"  class="in-page-link"><span>收藏</span></a></li>
                        <li id="retweets_by_me_tab"><a href="/retweets_by_me" class="in-page-link"><span>RT by me</span></a></li>
                        <li id="retweets_to_me_tab"><a href="/retweets_to_me" class="in-page-link"><span>RT to me</span></a></li>
                        <li id="blocking_tab"><a href="/blocking" class="in-page-link"><span>屏蔽列表</span></a></li>
                    </ul>
					<form action="/search" method="get" id="searchform">
						<input type="text" name="s" id="searchtext"/>
						<button type="submit" class="round">搜索</button>
					</form>