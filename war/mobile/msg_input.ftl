<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JTweetMobile - 发送消息</title>
<link href="/css/mobile.css" rel="stylesheet" type="text/css" />
</head>

<body class="session">
<#include "menu_top.ftl" />
<form action="/m/senddm" method="post">
	发送至：	<input name="to" value="${send_to?default("")}" type="text">
	<br />
	<textarea name="message" cols="50" rows="3" id="message"></textarea>
	<br>
	<input type="submit" value="发送">
	<span id="remaining">140</span>
</form>
<script type="text/javascript">
	function updateCount() {
		document.getElementById("remaining").innerHTML = 140 - document.getElementById("message").value.length;
		setTimeout(updateCount, 400);
	}
	updateCount();
</script>
<#include "menu_bottom.ftl" />
<p><a href="http://code.google.com/p/javatweet">JavaTweet Mobile</a></p>
</body>
</html>
