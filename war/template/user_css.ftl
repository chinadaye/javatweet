<#if user_show?exists>
<style type="text/css">
	  body {
  background: #<#if user_show.profileBackgroundColor =='9ae4e8'>C0DEED<#else>${user_show.profileBackgroundColor}</#if> url('${user_show.profileBackgroundImageUrl}') fixed <#if user_show.profileBackgroundTile == 'false'>no-repeat<#else>repeat</#if> top left;

}


a,
#content tr.hentry:hover a,
body#profile #content div.hentry:hover a,
#side .stats a:hover span.stats_count,
#side div.user_icon a:hover,
li.verified-profile a:hover,
#side .promotion .definition strong,
p.list-numbers a:hover,
#side div.user_icon a:hover span,
#content .tabMenu li a,
.translator-profile a:hover {
  color: #${user_show.profileLinkColor};
}

body,
ul#tabMenu li a, #side .section h1,
#side .stat a,
#side .stats a span.stats_count,
#side div.section-header h1,
#side div.user_icon a,
#side div.user_icon a:hover,
#side div.section-header h3.faq-header,
ul.sidebar-menu li.active a,
li.verified-profile a,
#side .promotion a,
body #content .list-header h2,
p.list-numbers a,
.bar h3 label,
body.timeline #content h1,
.list-header h2 a span,
#content .tabMenu li.active a,
body#direct_messages #content .tabMenu #inbox_tab a,
body#inbox #content .tabMenu #inbox_tab a,
body#sent #content .tabMenu #sent_tab a,
body#direct_messages #content .tabMenu #inbox_tab a,
body#retweets_by_others #content .tabMenu #retweets_by_others_tab a,
body#retweets #content .tabMenu #retweets_tab a,
body#retweeted_by_others #content .tabMenu #retweeted_by_others_tab a,
body#retweeted_of_mine #content .tabMenu #retweeted_of_mine_tab a,
.translator-profile a {
  color: #${user_show.profileTextColor};
}

#side_base {
  border-left:1px solid #${user_show.profileSidebarBorderColor};
  background-color: #${user_show.profileSidebarFillColor};
}
.side_user_link_last,.side_user_link,.side_link
{
	border-color:#${user_show.profileSidebarFillColor};
}
ul.sidebar-menu li.active a,
ul.sidebar-menu li a:hover,
#side div#custom_search.active,
#side .promotion,
.notify div {
  background-color: #1B201F;
}

.list-header,
.list-controls,
ul.sidebar-list li.active a,
ul.sidebar-list li a:hover {
  background-color: #030807 !important;
}

#side .actions,
#side .promo {
  border: 1px solid #051A17;
}

#side div.section-header h3 {
  border-bottom: 1px solid #051A17;
}

#side hr {
  background: #051A17;
  color: #051A17;
}

ul.sidebar-menu li.loading a {
  background: #1B201F url('http://a1.twimg.com/a/1256237813/images/spinner.gif') no-repeat 171px 0.5em !important;
}

#side .collapsible h2.sidebar-title {
  background: transparent url('http://a2.twimg.com/a/1256237813/images/toggle_up_light.png') no-repeat center right !important;
}

#side .collapsible.collapsed h2.sidebar-title {
  background: transparent url('http://a1.twimg.com/a/1256237813/images/toggle_down_light.png') no-repeat center right !important;
}

#side ul.lists-links li a em {
  background: url('http://a1.twimg.com/a/1256237813/images/arrow_right_light.png') no-repeat left top;
}

#side span.pipe {
  border-left:1px solid #051A17;
}

#list_subscriptions span.view-all,
#list_memberships span.view-all,
#profile span.view-all,
#profile_favorites span.view-all,
#following span.view-all,
#followers span.view-all {
  border-left: 0;
}   
	</style>
<#elseif user?exists>

<style type="text/css">
	  body {
  background: #<#if user.profileBackgroundColor =='9ae4e8'>C0DEED<#else>${user.profileBackgroundColor}</#if> url('${user.profileBackgroundImageUrl}') fixed <#if user.profileBackgroundTile == 'false'>no-repeat<#else>repeat</#if> top left;

}


a,
#content tr.hentry:hover a,
body#profile #content div.hentry:hover a,
#side .stats a:hover span.stats_count,
#side div.user_icon a:hover,
li.verified-profile a:hover,
#side .promotion .definition strong,
p.list-numbers a:hover,
#side div.user_icon a:hover span,
#content .tabMenu li a,
.translator-profile a:hover {
  color: #${user.profileLinkColor};
}

body,
ul#tabMenu li a, #side .section h1,
#side .stat a,
#side .stats a span.stats_count,
#side div.section-header h1,
#side div.user_icon a,
#side div.user_icon a:hover,
#side div.section-header h3.faq-header,
ul.sidebar-menu li.active a,
li.verified-profile a,
#side .promotion a,
body #content .list-header h2,
p.list-numbers a,
.bar h3 label,
body.timeline #content h1,
.list-header h2 a span,
#content .tabMenu li.active a,
body#direct_messages #content .tabMenu #inbox_tab a,
body#inbox #content .tabMenu #inbox_tab a,
body#sent #content .tabMenu #sent_tab a,
body#direct_messages #content .tabMenu #inbox_tab a,
body#retweets_by_others #content .tabMenu #retweets_by_others_tab a,
body#retweets #content .tabMenu #retweets_tab a,
body#retweeted_by_others #content .tabMenu #retweeted_by_others_tab a,
body#retweeted_of_mine #content .tabMenu #retweeted_of_mine_tab a,
.translator-profile a {
  color: #${user.profileTextColor};
}

#side_base {
  border-left:1px solid #${user.profileSidebarBorderColor};
  background-color: #${user.profileSidebarFillColor};
}
.side_user_link_last,.side_user_link,.side_link
{
	border-color:#${user.profileSidebarFillColor};
}
ul.sidebar-menu li.active a,
ul.sidebar-menu li a:hover,
#side div#custom_search.active,
#side .promotion,
.notify div {
  background-color: #1B201F;
}

.list-header,
.list-controls,
ul.sidebar-list li.active a,
ul.sidebar-list li a:hover {
  background-color: #030807 !important;
}

#side .actions,
#side .promo {
  border: 1px solid #051A17;
}

#side div.section-header h3 {
  border-bottom: 1px solid #051A17;
}

#side hr {
  background: #051A17;
  color: #051A17;
}

ul.sidebar-menu li.loading a {
  background: #1B201F url('http://a1.twimg.com/a/1256237813/images/spinner.gif') no-repeat 171px 0.5em !important;
}

#side .collapsible h2.sidebar-title {
  background: transparent url('http://a2.twimg.com/a/1256237813/images/toggle_up_light.png') no-repeat center right !important;
}

#side .collapsible.collapsed h2.sidebar-title {
  background: transparent url('http://a1.twimg.com/a/1256237813/images/toggle_down_light.png') no-repeat center right !important;
}

#side ul.lists-links li a em {
  background: url('http://a1.twimg.com/a/1256237813/images/arrow_right_light.png') no-repeat left top;
}

#side span.pipe {
  border-left:1px solid #051A17;
}

#list_subscriptions span.view-all,
#list_memberships span.view-all,
#profile span.view-all,
#profile_favorites span.view-all,
#following span.view-all,
#followers span.view-all {
  border-left: 0;
}   
	</style>
	<#else>
	<style type="text/css">
      
        body { background: #C0DEED url(/img/bg-clouds.jpg) repeat-x; }

a,
#content tr.hentry:hover a,
body#profile #content div.hentry:hover a,
#side .stats a:hover span.stats_count,
#side div.user_icon a:hover,
li.verified-profile a:hover,
#side .promotion .definition strong,
p.list-numbers a:hover,
#side div.user_icon a:hover span {
  color: #0099B9;
}

body,
ul#tabMenu li a, #side .section h1,
#side .stat a,
#side .stats a span.stats_count,
#side div.section-header h1,
#side div.user_icon a,
#side div.user_icon a:hover,
#side div.section-header h3.faq-header,
ul.sidebar-menu li.active a,
li.verified-profile a,
#side .promotion a,
body #content .list-header h2,
p.list-numbers a,
.bar h3 label,
body.timeline #content h1,
.list-header h2 a span {
  color: #3C3940;
}

#side_base {
  border-left:1px solid #5ED4DC;
  background-color: #95E8EC;
}

ul.sidebar-menu li.active a,
ul.sidebar-menu li a:hover,
#side div#custom_search.active,
#side .promotion,
.notify div {
  background-color: #A9FCFF;
}

.list-header,
.list-controls,
ul.sidebar-list li.active a,
ul.sidebar-list li a:hover {
  background-color: #95E8EC !important;
}

#side .actions,
#side .promo {
  border: 1px solid #5ED4DC;
}

#side div.section-header h3 {
  border-bottom: 1px solid #5ED4DC;
}

#side hr {
  background: #5ED4DC;
  color: #5ED4DC;
}

#side span.view-all {
  border-left:1px solid #5ED4DC;
}

#list_subscriptions span.view-all,
#list_memberships span.view-all,
#profile span.view-all,
#profile_favorites span.view-all,
#following span.view-all,
#followers span.view-all {
  border-left: 0;
}      
    
	</style>
	</#if>