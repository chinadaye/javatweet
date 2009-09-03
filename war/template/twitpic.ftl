<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,HH:m:s">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>JTweet - Twitpic - ${user.screenName}</title>
	<link type="text/css" href="/template/style.css" rel="stylesheet" />
	<script type="text/javascript" src="/js/jquery-1.3.2.min.js" ></script>
</head>
<body>
<div id="warp">
	<div id="head_warp">
		<#include "head.ftl" /> 
	</div>
	<div class="fixed"></div>
	<div id="main_warp">
		<#if msg?exists><center><div id="setting_rst">${msg}</div></center></#if>
		<#if imgurl?exists><img src="${imgurl}" class="twitpic_post"/></#if>
		<div id="twitpic_warp">
			<form action="/twitpic" method="post" enctype="multipart/form-data" id="twitpic_form">
				<div class="img_tip">必须为GIF，JPG或PNG格式，大小小于1MB。 </div>
				<div class="img_input_div">选择图片：<input type="file" name="media" class="img_input"/><span class="img_tip">必须 </span></div>
				<div class="img_input_div">附加消息：<input type="text" name="message" maxlength="120" class="img_input"/><span class="img_tip">可选</span></div>
				<center><button type="submit" onclick="javascript:return checkext(this.form.media.value);">上传图片</button></center>
			</form>
		</div>
	</div>
	<div id="side_warp">
		<#include "side.ftl" />
	</div>
	<div class="fixed"></div>
	<div id="foot_warp">
		<#include "foot.ftl" />
	</div>
	<div class="fixed"></div>
</div>
<script type="text/javascript" src="/js/func.js" ></script>
<script type="text/javascript" src="/js/checkext.js" ></script>
</body>
</html>