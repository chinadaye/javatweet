<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>${title}@${user.screenName}-Jteet</title>
	<link type="text/css" href="/img/style-all20091030.css" rel="stylesheet" />
	<!--<link href="/template/style2.css" media="screen" rel="stylesheet" type="text/css" />
	<link href="/template/style3.css" media="screen" rel="stylesheet" type="text/css" />-->
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js" ></script>
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
	<#include "statusform.ftl" />
		<div class="fixed"></div>
		<#if page == 1><div id="action_div"><a href="javascript:;" id="income_alert" style="display:none;">有新消息</a></div></#if>
		<div class="fixed"></div>
		<div id="tweet_warp">
			<#include "status_element.ftl" /> 
		</div>
		<div class="fixed"></div>
		<div id="tweet_page">
			<#if page gt 1><a href="${uri}?page=${page - 1}" class="pre_page">⇐上一页</a></#if>
			<#if status?size gt 1><a href="${uri}?page=${page + 1}" class="next_page">下一页⇒</a></#if>
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
<script type="text/javascript" src="/js/jquery.ajaxupload.js"></script>
<script type="text/javascript" src="/js/func.js?20100105"></script>
<#if addjs?exists>
	<script type="text/javascript" src="${addjs}?091225"></script>
</#if>
</body>
</html>