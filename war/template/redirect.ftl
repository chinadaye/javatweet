<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>登录成功！-Jteet.com</title>
	<link type="text/css" href="/img/style-all20091030.css" rel="stylesheet" />
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js" ></script>
	<script type="text/javascript" src="/js/jquery.cookie.js" ></script>
<#include "user_css.ftl">
</head>
<body class="sessions" id="new">
	<div id="container" class="subpage">
		<#include "head.ftl" /> 
	
	<table cellspacing="0" class="columns">
          <tbody>
            <tr>
              <td id="content" class="round-left column">
		<div id="status_warp">
			<span class="status_text">登录成功！<img class="small_loader" src="/img/ajax-loader.gif" /></span>	
			<div class="fixed"></div>
			
		</div>
		<div class="fixed"></div>
	</td>

              
            </tr>
          </tbody>
        </table>
<script type="text/javascript">
var rdt = '${to}';
$(function(){
setTimeout(function(){window.location.href=rdt},250);
});

</script>
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