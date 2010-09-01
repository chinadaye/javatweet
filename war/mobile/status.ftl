<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JTweetMobile - Status - ${id?c}</title>
<link href="/css/mobile.css" rel="stylesheet" type="text/css" />
</head>

<body class="session">
<#include "menu_top.ftl" />
<#if status?exists>
<#assign u=status.getUser()> 
<form method="post" action="/m/post">
  <textarea id="status" name="status" rows="3" style="width:100%; max-width: 400px;">@${u.getScreenName()} </textarea>
  <div><input name="in_reply_to_id" value="${id?c}" type="hidden" /><input type="submit" value="我推" /> <span id="remaining">140</span></div>
</form>
<script type="text/javascript">
	function updateCount() {
		document.getElementById("remaining").innerHTML = 140 - document.getElementById("status").value.length;
		setTimeout(updateCount, 400);
	}
	updateCount();
</script>
<p>${TexttoHTML(status.getText())}</p>
<table>
<tr>
	<td><img src='${u.getProfileImageURL()}' height='48' width='48' /></td>
	<td>
		<a href='/m/user/${u.getScreenName()}'>${u.getScreenName()}</a>
		<br />
		<small>
			<a href='/m/status/${id?c}'>${status.getCreatedAt()?datetime}</a>
		</small>
	</td>
</tr>
</table>
<#else>
<p>该用户已设置保护。</p>
</#if>
<#include "menu_bottom.ftl" />
<p><a href="http://code.google.com/p/javatweet">JavaTweet Mobile</a></p>
</body>
</html>
