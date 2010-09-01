<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JTweetMobile - 错误</title>
<link href="/css/mobile.css" rel="stylesheet" type="text/css" />
</head>

<body class="session">
<#include "menu_top.ftl" />
<div id="errmsg" style="height:50px;">
错误：${err}
</div>
<#include "menu_bottom.ftl" />
<p><a href="http://code.google.com/p/javatweet">JavaTweet Mobile</a></p>
</body>
</html>