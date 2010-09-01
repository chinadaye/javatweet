<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JTweetMobile - ${title} - ${login_user.getScreenName()}</title>
<link href="/css/mobile.css" rel="stylesheet" type="text/css" />
</head>

<body class="session">
<#include "menu_top.ftl" />
<form method="post" action="/m/post">
  <textarea id="status" name="status" rows="3" style="width:100%; max-width: 400px;"></textarea>
  <div><input name="in_reply_to_id" value="" type="hidden" /><input type="submit" value="我推！" /> <span id="remaining">140</span></div>
</form>
<script type="text/javascript">
	function updateCount() {
		document.getElementById("remaining").innerHTML = 140 - document.getElementById("status").value.length;
		setTimeout(updateCount, 400);
	}
	updateCount();
</script>
<table class="timeline">
<tbody>
	<#include "status_element.ftl" />
</tbody>
</table>
<#if page?exists>
<div id="page">
<#if page gt 1><a href="?p=${page - 1}">上一页</a> </#if>
<a href="?p=${page + 1}">下一页</a>
</div>
</#if>
<#include "menu_bottom.ftl" />
<p><a href="http://code.google.com/p/javatweet">JavaTweet Mobile</a></p>
</body>
</html>
