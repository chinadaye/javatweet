<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JTweet - 设置 - ${login_user.getScreenName()}</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/js/jquery-1.4.2.min.js" ></script>
<script type="text/javascript" src="/js/jquery.cookie.js" ></script>
<script type="text/javascript" src="/js/setting.js" ></script>
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
            		<h3>${login_user.getScreenName()}设置：</h3>
            		<#if msg?exists><center><div id="setting_rst">${msg}</div></center></#if>
					<img src="${getbigpic(login_user.getProfileImageURL())}" alt="${login_user.getScreenName()}" class="user_img_big left" width ="73" height="73" id="setting_img"/>
					<form action="/setimg" method="post" enctype="multipart/form-data" id="img_form" class="right">
						<div class="img_tip">必须为GIF，JPG或PNG格式，大小小于700KB。 </div>
						<div class="img_tip">由于GAE URLFetch的缓存机制，新上传的头像并不会在JTweet上马上显示。 </div>
						<input type="file" name="image"/>
						<button type="submit" onclick="return checkext(this.form.image.value);">上传图片</button>
					</form>
					<div class="fixed"></div>
				
					<form action="/setting" method="post" id="info_form">
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
					<center><button type="reset" class="setting_btn">重置</button> <button type="submit" class="setting_btn">修改</button></center>
					</form>
					<div id="jtweet_setting">
					<div class="setting_div">
						新消息声音提醒:
						<input type="radio" value="true" name="ring">是</input>
						<input type="radio" value="false" name="ring">否</input>
						<br /><br />
						闪动标题：
						<input type="radio" value="true" name="flash">是</input>
						<input type="radio" value="false" name="flash">否</input>
					</div>
					<div class="setting_tip">Cookie保存，有效期14天。</div>
					<center><button class="settin_btn" onclick="javascript:onCookieSetting();">保存</button></center>
					</div>
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
</body>
</html>
