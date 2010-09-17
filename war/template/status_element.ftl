<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<#list status as s>
<#assign u=s.getUser()/>
<#if s.isRetweet()>
<#assign s_rt=s.getRetweetedJStatus()/>
<#assign u_rt=s_rt.getUser()/>
                    	<li class="hentry status <#if newcome?exists> newcome<#if (u_rt.getScreenName()?lower_case != login_user.getScreenName()?lower_case) && (u.getScreenName()?lower_case != login_user.getScreenName()?lower_case)> unread</#if></#if>" id="status_${s_rt.getId()?c}">
                        	<div class="fixed hentry_id" id="status_id${s.getId()?c}"></div>
                        	<span class="thumb vcard author left"><img alt="${u_rt.getScreenName()}" class="photo fn" height="48" src="${u_rt.getProfileImageURL()}" width="48" /></span>
                            <span class="status-body">
                            	<div class="status-content">
                                	<strong>RT <a href="/user/${u_rt.getScreenName()}" class="tweet-url screen-name">${u_rt.getScreenName()}</a></strong>
                                	<span class="entry-content">${texttohtml(s_rt.getText())}</span>
                            	</div>
                                <div class="meta entry-meta">
                                	<span>ReTweet to you by</span>
                                    <span><a href="/user/${u.getScreenName()}" target="_blank">${u.getScreenName()}</a></span>
                                    <span class="published timestamp" ><a class="entry-date" href="/status/${s_rt.getId()?c}" target="_blank">大约发表于 ${s_rt.getHumanTime()}</a></span>
                                </div>
                                <ul class="actions-hover right">
                                	<li><a href="${uri}?action=re&id=${s_rt.getId()?c}&u=${u_rt.getScreenName()}" title="回复" onclick="return onreply('${s_rt.getId()?c}');" class="a_re">回复</a></li>
                                    <li><a href="${uri}?action=rt_t&id=${s_rt.getId()?c}&u=${u_rt.getScreenName()}" title="传统RT" onclick="return onrt_t('${s_rt.getId()?c}');" class="a_rt_t">传统RT</a></li>
                                    <li><a href="/retweets_by_me?action=rt&id=${s_rt.getId()?c}" title="官方RT" onclick="return onrt('${s_rt.getId()?c}');" class="a_rt">官方RT</a></li>
                                    <#if s_rt.isFavorited()>
                                    <li><a href="$/favorites?action=unfav&id=${s_rt.getId()?c}" title="取消收藏" onclick="return onunfav('${s_rt.getId()?c}');" class="a_fav">取消收藏</a></li>
                                    <#else>
                                    <li><a href="/favorites?action=fav&id=${s_rt.getId()?c}" title="收藏" onclick="return onfav('${s_rt.getId()?c}');" class="a_fav">收藏</a></li>
                                    </#if>
                                    <#if u.getScreenName()?lower_case == login_user.getScreenName()?lower_case>
                                    <li><a href="${uri}?action=del&id=${s.getId()?c}" title="删除" onclick="return ondel('${s_rt.getId()?c}');" class="a_del">删除</a></li>
                                    </#if>
                                </ul>
                            </span>
                            <div class="fixed"></div>
                        </li>
<#else>
                    	<li class="hentry status <#if newcome?exists> newcome<#if u.getScreenName()?lower_case != login_user.getScreenName()?lower_case> unread</#if></#if>" id="status_${s.getId()?c}">
                        	<div class="fixed hentry_id" id="status_id${s.getId()?c}"></div>
                        	<span class="thumb vcard author left"><img alt="${u.getScreenName()}" class="photo fn" height="48" src="${u.getProfileImageURL()}" width="48" /></span>
                            <span class="status-body">
                            	<div class="status-content">
                                	<strong><a href="/user/${u.getScreenName()}" class="tweet-url screen-name">${u.getScreenName()}</a></strong>
                                	<span class="entry-content">${texttohtml(s.getText())}</span>
                            	</div>
                                <div class="meta entry-meta">
                                	<span class="published timestamp" ><a class="entry-date" href="/status/${s.getId()?c}" target="_blank">大约在 ${s.getHumanTime()}</a></span>
                                	<span>通过 ${s.getSource()} 发表</span>
                                	<#if s.getInReplyToScreenName()?exists>
                                    <span>对<a href="/status/${s.getInReplyToStatusId()?c}" target="_blank">${s.getInReplyToScreenName()}</a>的回复</span>
                                    </#if>
                                </div>
                                <ul class="actions-hover right">
                                	<li><a href="${uri}?action=re&id=${s.getId()?c}&u=${u.getScreenName()}" title="回复" onclick="return onreply('${s.getId()?c}');" class="a_re">回复</a></li>
                                    <li><a href="${uri}?action=rt_t&id=${s.getId()?c}&u=${u.getScreenName()}" title="传统RT" onclick="return onrt_t('${s.getId()?c}');" class="a_rt_t">传统RT</a></li>
                                    <#if !u.isProtected()>
                                    <li><a href="/retweets_by_me?action=rt&id=${s.getId()?c}" title="官方RT" onclick="return onrt('${s.getId()?c}');" class="a_rt">官方RT</a></li>
                                    </#if>
                                    <#if s.isFavorited()>
                                    <li><a href="/favorites?action=unfav&id=${s.getId()?c}" title="取消收藏" onclick="return onunfav('${s.getId()?c}');" class="a_fav">取消收藏</a></li>
                                    <#else>
                                    <li><a href="/favorites?action=fav&id=${s.getId()?c}" title="收藏" onclick="return onfav('${s.getId()?c}');" class="a_fav">收藏</a></li>
                                    </#if>
                                    <#if u.getScreenName()?lower_case == login_user.getScreenName()?lower_case>
                                    <li><a href="${uri}?action=del&id=${s.getId()?c}" title="删除" onclick="return ondel('${s.getId()?c}');" class="a_del">删除</a></li>
                                    </#if>
                                </ul>
                            </span>
                            <div class="fixed"></div>
                        </li>
</#if>                                
</#list>