<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>私信 -Jteet</title>
	<link type="text/css" href="/template/style-all20091030.css" rel="stylesheet" />
	<link type="text/css" href="/js/jquery.autocomplete.css" rel="stylesheet"/>
	<script type="text/javascript" src="/js/jquery-1.3.2.min.js" ></script>
	<script type="text/javascript" src="/js/jquery.cookie.js" ></script>
<#include "user_css.ftl">
	<script type="text/javascript">
	<!--
	<#if page == 1>
		autofresh = true;
	<#else>
		autofresh = false;
	</#if>
	-->
	</script>
</head>
<body class="sessions" id="new">
<div id="ajax_loader" >
<img alt="loader" src="/img/ajax-loader-big.gif"/>
</div>
	<div id="container" class="subpage">
		<#include "head.ftl" /> 
	
	<table cellspacing="0" class="columns">
          <tbody>
            <tr>
              <td id="content" class="round-left column">
                                <div class="wrapper">
	<div id="statuses" class="statuses">
			<form action="/send" method="POST">
			<span class="tweet_tip">给<input id="user_followers" type="text" name="user_id" size="15" >发送私信</span><span class="tweet_count_info">剩余：<span id="tweet_count" class="tweet_count_green">140</span>字</span><br />
			<textarea id="tweet_msg" name="tweet_msg"></textarea><br />
			<input type="button" id="btn_shorturl" value="缩短链接">
			<input type="submit" id="tweet_submit" value="发送！">
			</form>
		</div>
		<div class="fixed"></div><!--
		<div id="action_div">[<a href="javascript:markallread();">标记全部为已读</a>]</div>
		-->
		<div id="timeline_heading">
	    <ul id="dm_tabs" class="tabMenu">
	      <li id="inbox_tab" class="active"><a href="/inbox" class="in-page-link" ><span>收件箱</span></a></li>
	      <li id="sent_tab" ><a href="/sent" class="in-page-link" ><span>发件箱</span></a></li>
	    </ul>
      </div>
		<div class="fixed"></div>
		<div id="msg_warp">
			<#include "message_element.ftl" /> 
		</div>
		<div class="fixed"></div>
		<div id="msg_page">
			<#if page gt 1><a href="${uri}?page=${page - 1}" class="pre_page">上一页</a></#if>
			<#if msg?size gte 19><a href="${uri}?page=${page + 1}" class="next_page">下一页</a></#if>
		</div>
	</div>
	</td>
              
                <td id="side_base" class="column round-right">
                                  
                  <div id="side">
		<#include "side.ftl" />
	</div>
	</td>

              
            </tr>
          </tbody>
        </table>


  <div id="footer" class="round">
		<#include "foot.ftl" />
	</div>
	<div class="fixed"></div>
</div>
<script type="text/javascript" src="/js/jquery.bgiframe.min.js"></script>
<script type="text/javascript" src="/js/jquery.ajaxQueue.js"></script>
<script type="text/javascript" src="/js/jquery.autocomplete.min.js"></script>
<script type="text/javascript" src="/js/func.js"></script>
<script type="text/javascript" src="/js/message.js"></script>
<script type="text/javascript" src="/js_follower_autocomplete"></script>
</body>
</html>