<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<#list follows as f>
                    	<li class="hentry follow" id="follow_${f.getId()?c}">
                        	<div class="fixed hentry_id" id="follow_id${f.getId()?c}"></div>
                        	<span class="thumb vcard author left"><img alt="${f.getScreenName()}" class="photo fn" height="48" src="${f.getProfileImageURL()}" width="48" /></span>
                            <span class="follow-body">
                                <ul class="follow-action">
                                	<li><strong><a href="/user/${f.getScreenName()}" class="tweet-url screen-name">${f.getScreenName()}</a></strong></li>
                                    <li><a href="/outbox?action=re&u=${f.getScreenName()}" title="发送消息" onclick="return onsendmsg('${f.getScreenName()}');" class="a_sendmsg">发送消息</a></li>
                                    <#if f.isFollowRequestSent()>
                                    <li><a href="/following?action=unfo&u=${f.getScreenName()}" title="取消关注" onclick="return onunfo('${f.getId()?c}');" class="a_fo">取消关注</a></li>
                                    <#else>
                                    <li><a href="/following?action=fo&u=${f.getScreenName()}" title="关注" onclick="return onfo('${f.getId()?c}');" class="a_fo">关注</a></li>
                                    </#if>
                                </ul>
                                <ul class="follow-info">
                                	<li><a href="/user/${f.getScreenName()}" class="tweet-url">${f.getStatusesCount()}条推</a></li>
                                	<li><a href="/user/${f.getScreenName()}/favorites" class="tweet-url">${f.getFavouritesCount()}收藏</a></li>
                                	<li><a href="/user/${f.getScreenName()}/following" class="tweet-url">${f.getFriendsCount()}朋友</a></li>
                                	<li><a href="/user/${f.getScreenName()}/follower" class="tweet-url">${f.getFollowersCount()}关注者</a></li>
                                </ul>
                                <div class="follow-description">简介：${f.getDescription()?default("")}</div>
                            </span>
                            <div class="fixed"></div>
                        </li>
</#list>