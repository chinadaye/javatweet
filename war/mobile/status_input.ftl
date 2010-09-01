<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JTweetMobile - ${title}</title>
<link href="/css/mobile.css" rel="stylesheet" type="text/css" />
</head>

<body class="session">
<#include "menu_top.ftl" />
<#if reply_to_status?exists>
<p>回复：<br />${reply_to_status.getText()}</p>
</#if>
<form method="post" action="/m/post">
  <textarea id="status" name="status" rows="3" style="width:100%; max-width: 400px;">${text?default("")}</textarea>
  <div><input name="in_reply_to_id" value="${reply_to_id?default("")}" type="hidden" /><input type="submit" value="我推！" /> <span id="remaining">140</span></div>
</form>
<script type="text/javascript">
	function updateCount() {
		document.getElementById("remaining").innerHTML = 140 - document.getElementById("status").value.length;
		setTimeout(updateCount, 400);
	}
	updateCount();
</script>
<#if rt_id?exists>
<p>
<form method="post" action="/m/rt_offical">
  <div><input name="id" value="${rt_id?c}" type="hidden" /><input type="submit" value="或者官方RT" /></div>
</form>
</p>
</#if>
<#include "menu_bottom.ftl" />
<p><a href="http://code.google.com/p/javatweet">JavaTweet Mobile</a></p>
</body>
</html>
