<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JTweetMobile - 设置 - ${login_user.getScreenName()}</title>
<link href="/css/mobile.css" rel="stylesheet" type="text/css" />
</head>

<body class="session">
<#include "menu_top.ftl" />
<#if msg?exists><p><b>${msg}</b></p></#if>
<table id="img_table">
<tr>
	<td>
		<img src="${login_user.getProfileImageURL()}" alt="${login_user.getScreenName()}" width ="48" height="48" id="setting_img"/>
	</td>
	<td>
	<form action="/m/setimg" method="post" enctype="multipart/form-data" id="img_form">
		<div class="img_tip">必须为GIF，JPG或PNG格式，大小小于700KB。 </div>
		<div class="img_tip">由于GAE URLFetch的缓存机制，新上传的头像并不会在JTweet上马上显示。 </div>
		<input type="file" name="image"/>
		<button type="submit">上传图片</button>
	</form>
	</td>
</tr>
</table>
				
<form action="/m/setting" method="post" id="info_form">
	<table id="info_table">
	<tr>
		<td>昵称：</td>
		<td><input type="text" id="name" name="name" class="info_input" value="${login_user.getName()?default("")}" maxlength="20" /></td>
		<td><span class="setting_tip">最多20字</span></td>
	</tr>
	<tr>
		<td>网址：</td>
		<td><input type="text" id="url" name="url" class="info_input" value="${login_user.getURL()?default("")}" maxlength="100" /></td>
		<td><span class="setting_tip">最多100字</span></td>
	</tr>
	<tr>
		<td>位置：</td>
		<td><input type="text" id="location" name="location" class="info_input" value="${login_user.getLocation()?default("")}" maxlength="30" /></td>
	<td><span class="setting_tip">最多30字</span></td>
	</tr>
	<tr>
		<td>简介：</td>
		<td><textarea id="desc" name="desc" class="info_text" >${login_user.getDescription()?default("")}</textarea></td>
		<td><span class="setting_tip">最多160字</span></td>
	</tr>
	</table>
	<button type="reset" class="setting_btn">重置</button> <button type="submit" class="setting_btn">修改</button>
</form>
<#include "menu_bottom.ftl" />
<p><a href="http://code.google.com/p/javatweet">JavaTweet Mobile</a></p>
</body>
</html>
