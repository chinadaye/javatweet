<div id="header">
	<a href="/" title=" Home" accesskey="1" id="logo"> <img alt="JTweet" height="40" src="/img/jteet_logo.png" /> </a>
	<ul class="top-navigation round">
	 <li><a href="/" >首页</a></li>
	 <#if user?exists>
	 <li><a href="/reply" >回复</a></li>
	 <li><a href="/inbox">私信</a></li>
	 <li><a href="/public">公共页面</a></li>
	 <li><a href="/twitpic">TwitPic</a></li>
	 <li><a href="/twitgoo">Twitgoo</a></li>
	 <li><a href="/setting" >设置</a></li>
	 <li><a href="/logout">退出</a></li>
	 <#else>
	 <li><a href="/login" >登录</a></li>
	 </#if>
	</ul>
	<div class="fixed"></div>
</div>
<div class="fixed"></div>
<div class="content-bubble-arrow"></div>
