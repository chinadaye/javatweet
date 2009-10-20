<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>JTweet - Status - ${status.id}</title>
	<link type="text/css" href="template/style.css" rel="stylesheet" />
	<link href="/template/style2.css" media="screen" rel="stylesheet" type="text/css" />
	<link href="/template/style3.css" media="screen" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="/js/jquery-1.3.2.min.js" ></script>
<style type="text/css">
      
        body { background: #C0DEED url(/img/bg-clouds.jpg) repeat-x; }

a,
#content tr.hentry:hover a,
body#profile #content div.hentry:hover a,
#side .stats a:hover span.stats_count,
#side div.user_icon a:hover,
li.verified-profile a:hover,
#side .promotion .definition strong,
p.list-numbers a:hover,
#side div.user_icon a:hover span {
  color: #0099B9;
}

body,
ul#tabMenu li a, #side .section h1,
#side .stat a,
#side .stats a span.stats_count,
#side div.section-header h1,
#side div.user_icon a,
#side div.user_icon a:hover,
#side div.section-header h3.faq-header,
ul.sidebar-menu li.active a,
li.verified-profile a,
#side .promotion a,
body #content .list-header h2,
p.list-numbers a,
.bar h3 label,
body.timeline #content h1,
.list-header h2 a span {
  color: #3C3940;
}

#side_base {
  border-left:1px solid #5ED4DC;
  background-color: #95E8EC;
}

ul.sidebar-menu li.active a,
ul.sidebar-menu li a:hover,
#side div#custom_search.active,
#side .promotion,
.notify div {
  background-color: #A9FCFF;
}

.list-header,
.list-controls,
ul.sidebar-list li.active a,
ul.sidebar-list li a:hover {
  background-color: #95E8EC !important;
}

#side .actions,
#side .promo {
  border: 1px solid #5ED4DC;
}

#side div.section-header h3 {
  border-bottom: 1px solid #5ED4DC;
}

#side hr {
  background: #5ED4DC;
  color: #5ED4DC;
}

#side span.view-all {
  border-left:1px solid #5ED4DC;
}

#list_subscriptions span.view-all,
#list_memberships span.view-all,
#profile span.view-all,
#profile_favorites span.view-all,
#following span.view-all,
#followers span.view-all {
  border-left: 0;
}      
    
	</style>
</head>
<body class="sessions" id="new">
	<div id="container" class="subpage">
		<#include "head.ftl" /> 
	
	<table cellspacing="0" class="columns">
          <tbody>
            <tr>
              <td id="content" class="round-left column">
		<div id="status_warp">
			<span class="status_text">${status.html}</span>	
			<div class="fixed"></div>
			<span class="status_source">通过${status.source}</span>
			<#if status.inReplyToScreenName?trim != "">
				<a href="/status?id=${status.inReplyToStatusId?c}" class="status_reply_to">对${status.inReplyToScreenName}的回复</a>
			</#if>
			<span class="status_time">发表于${status.createdAt?datetime}</span>
		</div>
		<div id="status_user_warp">
			<img src="${status.user.biggerImageURL}" alt="${status.user.screenName}" class="user_img_big" />
			<div class="user_name_div">
				<a href="/user?id=${status.user.screenName}" class="status_user_screenname">${status.user.screenName}</a>
				<span class="status_user_name">${status.user.name}</span>
			</div>
		</div>
		<div class="fixed"></div>
	</td>

              
            </tr>
          </tbody>
        </table>


  <div id="footer" class="round">
		<#include "foot.ftl" />
	</div>
	<div class="fixed"></div>
</div>
<SCRIPT>
	if (window.navigator.userAgent.indexOf("MSIE 6.0")>=1){
		$("body").addClass("ie ie6");
	}else{
	    if (window.navigator.userAgent.indexOf("MSIE 7.0")>=1){
	    	$("body").addClass("ie ie7");
	    }else{
	    	$("body").addClass("firefox-windows");
	    }
	};
</SCRIPT>
</body>
</html>