<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JTweet - ${title} - ${login_user.getScreenName()}</title>
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
                	<form action="${uri}" class="msg-update-form" id="msg_update_form" method="post">
                    	<input id="send_to" name="send_to" type="hidden" value="${send_to}" />
                      <div class="bar">
							<h4><label for="msg" class="to left">TO:<span id="send_to_span">${send_to}</span>。按Ctrl+Enter快捷发布</label></h4>
							<span class="numeric right">剩余：<strong><span id="msg-field-char-counter" class="char-counter green">140</span></strong>字</span>
						</div>
                      <textarea id="msg" name="msg" >${text}</textarea>
                      <br />
                        <span id="ajax_msg_msg" class="ajax_msg left">${action_result}</span>
                        <input type="submit" name="update" value="发送！" id="send-submit" class="msg-btn round right" onclick="return onsend_submit();"/>
                        <input type="reset" name="reset" value="清除" id="send-reset" class="msg-btn round right" onclick="return onsend_reset();"/>
                    </form>
                	<div id="timeline_action" class="bar clear">[<a href="javascript:markallread();">标记全部为已读</a>]</div>
                	<div id="timeline_heading">
                		<h2 id="heading"><span id="title">${title_en}</span></h2>
                    <!--#timeline_heading--></div>
                    <ol id='timeline' class='msgs'>
                    	<#include "msg_element.ftl" />
                    </ol>
                    <a href="javascript:ongetmoremsg('${uri}')" class="round more" id="more" rel="next"><div id="pagination"><h3>more</h3></div></a>
                <!--#wrapper--></div>
			<!--#content--></td>
			<td id="side_base" class="column round-right">
            	<div id="side">
                	<#include "side_loginuser.ftl" />
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
