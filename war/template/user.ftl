<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JTweet - ${title} - ${user.getScreenName()}</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<#if css?exists>
<#list css as c>
<link href="${c}" rel="stylesheet" type="text/css" />
</#list>
</#if>
<script type="text/javascript" src="/js/jquery-1.4.2.min.js" ></script>
<script type="text/javascript" src="/js/jquery.cookie.js" ></script>
</head>

<body class="session">

<div id="container">
	<div id="header">
    	<#include "header.ftl" />
    <!--#header--></div>
	<div class="content-bubble-arrow clear"></div>
    <table cellspacing="0" class="columns">
    <tbody>
    	<tr>
			<td id="content" class="round-left column">
            	<div class="wrapper">
                	<div id="user_info" class="clear">
                    	<img src="${getbigpic(user.getProfileImageURL())}" alt="${user.getName()}" class="user_big_pic left" width="73" height="73"/>
                        <div id="user_name">${user.getScreenName()}(${user.getName()})</div>
                        <ul id="user_action">
                        	<li><a href="/outbox?action=re&u=${user.getScreenName()}" title="发送消息" onclick="return onsendmsg('${user.getScreenName()}');" class="a_sendmsg">发送消息</a></li>
                            <#if user.isFollowing()>
                            <li><a href="/following?action=unfo&u=${user.getScreenName()}" title="取消关注" onclick="return onunfo('${user.getScreenName()}');" class="a_fo">取消关注</a></li>
                            <#else>
                            <li><a href="/following?action=fo&u=${user.getScreenName()}" title="关注" onclick="return onfo('${user.getScreenName()}');" class="a_fo">关注</a></li>
                            </#if>
                            <#if blocked>
                            <li><a href="/blocking?action=unb&u=${user.getScreenName()}" title="取消屏蔽" onclick="return onunb('${user.getScreenName()}');" class="a_b">取消屏蔽</a></li>
                            <#else>
                            <li><a href="/blocking?action=b&u=${user.getScreenName()}" title="屏蔽" onclick="return onb('${user.getScreenName()}');" class="a_b">屏蔽</a></li>
                            </#if>
                        </ul>
                    </div>
                    <div class="fixed"></div>
                	<form action="/home" class="status-update-form" id="status_update_form" method="post">
                    	<input id="in_reply_to_status_id" name="in_reply_to_status_id" type="hidden" value="" />
                      <div class="bar">
							<h4><label for="status" class="doing left">告诉大家你在干啥？按Ctrl+Enter快捷发布</label></h4>
							<span class="numeric right">剩余：<strong><span id="status-field-char-counter" class="char-counter green">140</span></strong>字</span>
						</div>
                      <textarea id="status" name="status" ></textarea>
                      <br />
                        <span id="ajax_msg_status" class="ajax_msg left"></span>
                        <input type="submit" name="update" value="我推！" id="update-submit" class="status-btn round right" onclick="return onupdate_submit();"/>
                        <input type="reset" name="reset" value="清除" id="update-reset" class="status-btn round right" onclick="return onupdate_reset();"/>
                    </form>
                    <div class="fixed"></div>
                    <form action="/outbox" class="msg-update-form hide" id="msg_update_form" method="post">
                    	<input id="send_to" name="send_to" type="hidden" value="" />
                      <div class="bar">
							<h4><label for="msg" class="to left">TO:<span id="send_to_span"></span>。按Ctrl+Enter快捷发布</label></h4>
							<span class="numeric right">剩余：<strong><span id="status-field-char-counter" class="char-counter green">140</span></strong>字</span>
						</div>
                      <textarea id="msg" name="msg" ></textarea>
                      <br />
                        <span id="ajax_msg_msg" class="ajax_msg left"></span>
                        <input type="submit" name="update" value="发送！" id="send-submit" class="msg-btn round right" onclick="return onsend_submit();"/>
                        <input type="reset" name="reset" value="清除" id="send-reset" class="msg-btn round right" onclick="return onsend_reset();"/>
                    </form>
                    <div class="fixed"></div>
                	<div id="timeline_heading">
                		<h2 id="heading"><span id="title">${title_en}</span></h2>
                    <!--#timeline_heading--></div>
                    <#if status?exists>
                    <ol id='timeline' class='statuses'>
                    	<#include "status_element.ftl" />
                    </ol>
                    <a href="${morefunction}" class="round more" id="more" rel="next"><div id="pagination"><h3>more</h3></div></a>
                    <#else>
                    <div id="user_protected">对不起，该用户已设置保护。</div>
                    </#if>
                <!--#wrapper--></div>
			<!--#content--></td>
			<td id="side_base" class="column round-right">
            	<div id="side">
                	<#include "side_otheruser.ftl" />
                <!--#side_base--></div>
			<!--side_base--></td>
		</tr>
	</tbody>
	</table>
<div class="fixed"></div>
<#include "footer.ftl" />
<!-- #container --></div>
<script type="text/javascript" src="/js/func.js" ></script>
<#if js?exists>
<#list js as j>
<script type="text/javascript" src="${j}" ></script>
</#list>
</#if>
</body>
</html>
