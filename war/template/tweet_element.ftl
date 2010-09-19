<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<#list tweets as t>
                    	<li class="hentry status" id="status_${t.getId()?c}">
                        	<div class="fixed hentry_id" id="status_id${t.getId()?c}"></div>
                        	<span class="thumb vcard author left"><img alt="${t.getFromUser()}" class="photo fn" height="48" src="${t.getProfileImageUrl()}" width="48" /></span>
                            <span class="status-body">
                            	<div class="status-content">
                                	<strong><a href="/user/${t.getFromUser()}" class="tweet-url screen-name">${t.getFromUser()}</a></strong>
                                	<span class="entry-content">${texttohtml(t.getText())}</span>
                            	</div>
                                <div class="meta entry-meta">
                                	<span class="published timestamp" ><a class="entry-date" href="/status/${t.getId()?c}" target="_blank">大约在${t.getHumanTime()}</a></span>
                                	<span>通过 ${t.getSource()} 发表</span>
                                	<#if t.getToUser()?exists>
                                    <span>对${t.getToUser()}的回复</span>
                                    </#if>
                                </div>
                                <ul class="actions-hover right">
                                	<li><a href="/home?action=re&id=${t.getId()?c}&u=${t.getFromUser()}" title="回复" onclick="return onreply('${t.getId()?c}');" class="a_re">回复</a></li>
                                    <li><a href="/home?action=rt_t&id=${t.getId()?c}&u=${t.getFromUser()}" title="传统RT" onclick="return onrt_t('${t.getId()?c}');" class="a_rt_t">传统RT</a></li>
                                    <li><a href="/retweets_by_me?action=rt&id=${t.getId()?c}" title="官方RT" onclick="return onrt('${t.getId()?c}');" class="a_rt">官方RT</a></li>
                                    <li><a href="/favorites?action=fav&id=${t.getId()?c}" title="收藏" onclick="return onunfav('${t.getId()?c}');" class="a_fav">收藏</a></li>
                                    <#if t.getFromUser()?lower_case == login_user.getScreenName()?lower_case>
                                    <li><a href="/home?action=del&id=${t.getId()?c}" title="删除" onclick="return ondel('${t.getId()?c}');" class="a_del">删除</a></li>
                                    </#if>
                                </ul>
                            </span>
                            <div class="fixed"></div>
                        </li>                              
</#list>