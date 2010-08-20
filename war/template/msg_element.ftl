<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<#list msgs as m>
<#assign u=m.getSender()/>
                    	<li class="hentry msg" id="msg_${m.getId()?c}">
                        	<div class="fixed hentry_id" id="msg_id${m.getId()?c}"></div>
                        	<span class="thumb vcard author left"><img alt="${m.getSenderScreenName()}" class="photo fn" height="48" src="${u.getProfileImageURL()}" width="48" /></span>
                            <span class="msg-body">
                            	<div class="msg-content">
                                	<strong><a href="/user/${m.getSenderScreenName()}" class="tweet-url screen-name">${m.getSenderScreenName()}</a></strong>
                                	<span class="entry-content">${texttohtml(m.getText())}</span>
                            	</div>
                                <div class="meta entry-meta">
                                    <span>发送至<a href="/user/${m.getRecipientScreenName()}" target="_blank">${m.getRecipientScreenName()}</a></span>
                                    <span class="published timestamp" >发送于${m.getCreatedAt()?datetime}</span>
                                </div>
                                <ul class="actions-hover right">
                                    <#if u.getScreenName()?lower_case != login_user.getScreenName()?lower_case>
                                    <li><a href="${uri}?action=re&u=${m.getSenderScreenName()}" title="回复" onclick="return onreplymsg('${m.getSenderScreenName()}')" class="a_remsg">回复</a></li>
                                    </#if>
                                    <li><a href="${uri}?action=del&id=${m.getId()?c}" title="删除" onclick="return ondelmsg('${m.getId()?c}')" class="a_delmsg">删除</a></li>
                                </ul>
                            </span>
                            <div class="fixed"></div>
                        </li>
</#list>