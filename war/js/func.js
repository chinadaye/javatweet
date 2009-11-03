tweet_length = 0;
unread_count = 0;
title = document.title;
t = false;
var is_income = false;
var short_url_services = "1u.ro,1url.com,2pl.us,2tu.us,3.ly,a.gg,a.nf,a2a.me,abe5.com,adjix.com,alturl.com,atu.ca,awe.sm,b23.ru,bacn.me,bit.ly,bkite.com,blippr.com,blippr.com,bloat.me,bt.io,budurl.com,buk.me,burnurl.com,c.shamekh.ws,cd4.me,chilp.it,clck.ru,cli.gs,clickthru.ca,cort.as,cuturl.com,decenturl.com,df9.net,doiop.com,dwarfurl.com,easyurl.net,eepurl.com,eezurl.com,ewerl.com,fa.by,fav.me,ff.im,fff.to,fhurl.com,flic.kr,flq.us,fly2.ws,fuseurl.com,fwd4.me,gl.am,go.9nl.com,go2.me,golmao.com,goshrink.com,gri.ms,gurl.es,hellotxt.com,hex.io,href.in,htxt.it,hugeurl.com,hurl.ws,icanhaz.com,icio.us,idek.net,is.gd,ito.mx,j.mp,jijr.com,kissa.be,kl.am,korta.nu,l9k.net,liip.to,liltext.com,lin.cr,linkbee.com,liurl.cn,ln-s.net,ln-s.ru,lnkurl.com,loopt.us,lru.jp,lt.tl,lurl.no,memurl.com,migre.me,minilien.com,miniurl.com,minurl.fr,moourl.com,myurl.in,ncane.com,netnet.me,nn.nf,o-x.fr,ofl.me,omf.gd,ow.ly,oxyz.info,p8g.tw,parv.us,pic.gd,ping.fm,piurl.com,plurl.me,pnt.me,poll.fm,pop.ly,poprl.com,post.ly,posted.at,ptiturl.com,qurlyq.com,rb6.me,readthis.ca,redirects.ca,redirx.com,relyt.us,retwt.me,ri.ms,rickroll.it,rly.cc,rsmonkey.com,rubyurl.com,rurl.org,s3nt.com,s7y.us,short.ie,short.to,shortna.me,shoturl.us,shrinkster.com,shrinkurl.us,shrtl.com,shw.me,simurl.net,simurl.org,simurl.us,sn.im,sn.vc,snipr.com,snipurl.com,snurl.com,sp2.ro,spedr.com,starturl.com,stickurl.com,sturly.com,su.pr,takemyfile.com,tcrn.ch,thrdl.es,tighturl.com,tiny.cc,tiny.pl,tinyarro.ws,tinytw.it,tinyurl.com,tnw.to,to.ly,togoto.us,tr.im,tr.my,trcb.me,tumblr.com,tw0.us,tw1.us,tw2.us,tw5.us,tw6.us,tw8.us,tw9.us,twi.gy,twit.ac,twitthis.com,twiturl.de,twitzap.com,twtr.us,twurl.nl,u.mavrev.com,u.nu,ub0.cc,updating.me,ur1.ca,url.co.uk,url.ie,url.inc-x.eu,url4.eu,urlborg.com,urlbrief.com,urlcut.com,urlhawk.com,urlkiss.com,urlpire.com,urlvi.be,urlx.ie,uservoice.com,ustre.am,virl.com,vl.am,wa9.la,wapurl.co.uk,wipi.es,wkrg.com,wp.me,x.hypem.com,x.se,xeeurl.com,xr.com,xrl.in,xrl.us,xurl.jp,xzb.cc,yatuc.com,yep.it,yfrog.com,zi.pe,zz.gd";
var img_small_loader = '<img class="small_loader" src="/img/ajax-loader.gif" />';
$(document).ready(function(){
	//$(document).ajaxStart(function(){$("#ajax_loader").show();}).ajaxStop(function(){$("#ajax_loader").hide();});
	$("#ajax_loader").hide();
});

$("textarea#tweet_msg").keypress(function(e){
    if(e.ctrlKey && e.which == 13 || e.which == 10) { 
            $("button#tweet_submit").click();
            document.body.focus();
    } else if (e.shiftKey && e.which==13 || e.which == 10) {
            $("button#tweet_submit").click();
            document.body.focus();
    }          
});

$("div.unread").live(
		"mouseover",
		function()
		{
			$(this).removeClass("unread");
			unread_count = unread_count - 1;
		}
);
$("div.tweets").live(
		"mouseover",
		function()
		{
			$(this).find("span.tweet_action,span.msg_action").show();
			var mayshorts = $(this).find("a.mayshort");
			for(var i=0;i<mayshorts.length;i++){
				revertShortUrl(mayshorts[i]);
			}
		}
);
$("div.tweets").live(
		"mouseout",
		function()
		{
			$(this).find("span.tweet_action,span.msg_action").hide();
		}
);
if (window.navigator.userAgent.indexOf("MSIE 6.0")>=1){
	$("body").addClass("ie ie6");
}else{
    if (window.navigator.userAgent.indexOf("MSIE 7.0")>=1){
    	$("body").addClass("ie ie7");
    }else{
    	$("body").addClass("firefox-windows");
    }
};


function updateUnread()
{
	unread_count = $("div.unread").length;
};

/**
 * 调整UI
 */
function markupUI(){
	//高亮当前页面的tab链接
	var href = window.location.href;
	var matches = href.match(/http\:\/\/[0-9a-z\.]*\/([0-9a-z\?=&]+)/i);
	if(matches!=null){
		var act  = matches[1];
		$(".side_link_content a.side_link[href='/"+act+"']").addClass("side_link_current");
	}
	//$("#ajax_loader").css("visibility","hidden");
};


function flash_title()
{
	if(is_income)
	{
		if(t)
		{
			document.title = "您有新消息.. " + title;
			t = false;
		}
		else
		{
			document.title = title;
			t = true;
		}
	}
	else
	{
		document.title = title;
	}
	setTimeout(flash_title,1000);
};

/**
 * 检查是否有新消息
 * @return
 */
function checkHome(){
	sinceid = $("div#tweet_warp div.tweets:first-child").children("div.tweet_content").children("span.tweet_id").text();
	$.getJSON('/check',
			{
			type: "home",
			since: sinceid
			},
			function(respon){
				if(respon&&respon.code==1&&respon.count>0){
					$("a#income_alert").css("visibility","visible");
					is_income  = true;
				}else{
					window.setTimeout
					(
							function(){
								checkHome();
							},
							15000
					);
				}
			});
}

/**
 * 还原单个连接
 */
function revertShortUrl(link){
	var url = $(link).attr('href');
	var matches = url.match(/.*\/\/([A-Za-z0-9-_.]+)\/.*/);
	if(matches!=null&&short_url_services.match(".*,?"+matches[1]+",?.*")){
		$(link).after(img_small_loader);
		$.getJSON('/untinyme',
				{url:matches[0]},
				function(data){
					if(data.org_url){
						$(link).attr('href',data.org_url);
						$(link).text(data.org_url);
					}
					$(link).next('img.small_loader').remove();
				});
	}
	$(link).removeClass('mayshort');
}

/**
 * 还原所有连接
 */
function retrieveShortUrl(){
	var short_urls = $("a.mayshort");
	var short_urls_length = short_urls.length;
	for(var i=0;i<short_urls_length;i++){
		var url = $(short_urls[i]).attr('href');
		var matches = url.match(/.*\/\/([A-Za-z0-9-_.]+)\/.*/);
		if(matches!=null&&short_url_services.match(".*,?"+matches[1]+",?.*")){
			$.getJSON('/untinyme',
					{url:matches[0]},
					function(data){
						if(data.org_url){
							$(short_urls[i]).attr('href',data.org_url);
							$(short_urls[i]).text(data.org_url);
						}
					});
			if(short_urls_length>1){
				window.setTimeout(function(){retrieveShortUrl();},1000);
			}
			$(short_urls[i]).removeClass('mayshort');
			return;
		}else{
			$(short_urls[i]).removeClass('mayshort');
		}
	}
}

function updateCount()
{
	tweet_length = $("textarea#tweet_msg").val().length;
	$("span#tweet_count").text(140 - tweet_length);
	$("span#tweet_count").toggleClass("tweet_count_red", tweet_length >= 140);
};

function updateHome()
{
	sinceid = $("div#tweet_warp div.tweets:first-child").children("div.tweet_content").children("span.tweet_id").text();
	$("#ajax_loader").show();
	$.get(
			"/update",
			{
				type: "home",
				since: sinceid,
				timestamp: (new Date()).getTime()
			},
			function(data)
			{
				$("#tweet_warp").prepend(data);
				$("div.newcome").slideDown("normal");
				$("div.newcome").removeClass("newcome");
				updateUnread();
				$("#ajax_loader").hide();
			}
		);
};


function updatePublic()
{
	sinceid = $("div#tweet_warp div.tweets:first-child").children("div.tweet_content").children("span.tweet_id").text();
	$("#ajax_loader").show();
	$.get(
			"/update",
			{
				type: "public",
				since: sinceid,
				timestamp: (new Date()).getTime()
			},
			function(data)
			{
				$("#tweet_warp").prepend(data);
				$("div.newcome").slideDown("normal");
				$("div.newcome").removeClass("newcome");
				updateUnread();
				$("#ajax_loader").show();
			}
		);
};

function updateReply()
{
	sinceid = $("div#tweet_warp div.tweets:first-child").children("div.tweet_content").children("span.tweet_id").text();
	//alert(sinceid);
	$.get(
			"/update",
			{
				type: "reply",
				since: sinceid,
				timestamp: (new Date()).getTime()
			},
			function(data)
			{
				$("#tweet_warp").prepend(data);
				$("div.newcome").slideDown("normal");
				$("div.newcome").removeClass("newcome");
				updateUnread();
			}
		);
};

function updateMessage()
{
	sinceid = $("div#msg_warp div.msgs:first-child").children("div.msg_content").children("span.msg_id").text();
	$.get(
			"/update",
			{
				type: "message",
				since: sinceid,
				timestamp: (new Date()).getTime()
			},
			function(data)
			{
				$("#msg_warp").prepend(data);
				$("div.newcome").slideDown("normal");
				$("div.newcome").removeClass("newcome");
				updateUnread();
			}
		);
};

function onPostStatus(reply_id, callback, param)
{
	if(tweet_length > 0)
	{
		var postdata;
		if(reply_id == 0) postdata = {
				type: "post",
				tweet_msg: $("#tweet_msg").val()
				}; 
		else postdata = {
				type: "post",
				tweet_msg: $("#tweet_msg").val(),
				id: reply_id
		};
		$.ajax({
			url: "/action",
			type: "POST",
			dataType: "json",
			data: postdata,
			success: function(json)
			{
				if(json.result == "ok")
				{
					//alert("post ok");
					$("#tweet_msg").val("");
					if(callback) callback(param);
				}
				else
				{
					alert("出错啦！错误代码：" + json.info);
				}
			}
		});
	}
	else
	{
		alert("请勿发送空消息！");
	}
};

function onDelete(id, callback, param)
{
	msg = "确实要删除这条Tweet吗？";
	if(confirm(msg))
	{
		postdata = {type: "delete", id: id};
		$.ajax({
			url: "/action",
			type: "POST",
			dataType: "json",
			data: postdata,
			success: function(json)
			{
				if(json.result == "ok")
				{
					//alert("del ok");
					if(callback) callback(param);
				}
				else
				{
					alert("出错啦！错误代码：" + json.info);
				}
			}
		});
	}
};

function onFavor(id, callback, param)
{
	postdata = {type: "favor", id: id};
	$.ajax({
		url: "/action",
		type: "POST",
		dataType: "json",
		data: postdata,
		success: function(json)
		{
			if(json.result == "ok")
			{
				//alert("favor ok");
				if(callback) callback(param);
			}
			else
			{
				alert("出错啦！错误代码：" + json.info);
			}
		}
	});
};

function onUnFavor(id, callback, param)
{
	msg = "确实要删除对ID为：" + id + "的Tweet的收藏吗？";
	if(confirm(msg))
	{
		postdata = {type: "unfavor", id: id};
		$.ajax({
			url: "/action",
			type: "POST",
			dataType: "json",
			data: postdata,
			success: function(json)
			{
				if(json.result == "ok")
				{
					//alert("unfavor ok");
					if(callback) callback(param);
				}
				else
				{
					alert("出错啦！错误代码：" + json.info);
				}
			}
		});
	}
};

function onFollow(id, callback, param)
{
	postdata = {type: "follow", id: id};
	
	$.ajax({
		url: "/action",
		type: "POST",
		dataType: "json",
		data: postdata,
		success: function(json)
		{
			if(json.result == "ok")
			{
				//alert("follow ok");
				if(callback) callback(param);
			}
			else
			{
				alert("出错啦！错误代码：" + json.info);
			}
		}
	});
};

function onUnFollow(id, callback, param)
{
	msg = "确实要取消对" + id + "的跟踪吗？";

	if(confirm(msg))
	{
		postdata = {type: "unfollow", id: id};
		$.ajax({
			url: "/action",
			type: "POST",
			dataType: "json",
			data: postdata,
			success: function(json)
			{
				if(json.result == "ok")
				{
					//alert("unfollow ok");
					if(callback) callback(param);
				}
				else
				{
					alert("出错啦！错误代码：" + json.info);
				}
			}
		});
	}
};

function onBlock(id, callback, param)
{
	msg = "确实要屏蔽" + id + "吗？";

	if(confirm(msg))
	{
		postdata = {type: "block", id: id};
		$.ajax({
			url: "/action",
			type: "POST",
			dataType: "json",
			data: postdata,
			success: function(json)
			{
				if(json.result == "ok")
				{
					//alert("block ok");
					if(callback) callback(param);
				}
				else
				{
					alert("出错啦！错误代码：" + json.info);
				}
			}
		});
	}
};

function onUnBlock(id, callback, param)
{
	msg = "确实要取消对" + id + "的屏蔽吗？";

	if(confirm(msg))
	{
		postdata = {type: "unblock", id: id};
		$.ajax({
			url: "/action",
			type: "POST",
			dataType: "json",
			data: postdata,
			success: function(json)
			{
				if(json.result == "ok")
				{
					//alert("unblock ok");
					if(callback) callback(param);
				}
				else
				{
					alert("出错啦！错误代码：" + json.info);
				}
			}
		});
	}
};

function onSendMessage(id, callback, param)
{
	if(tweet_length > 0)
	{
		var postdata;
		if(send_id != "") 
		{
			postdata = {
				type: "msg",
				tweet_msg: $("#tweet_msg").val(),
				id: send_id
			};
			$.ajax({
				url: "/action",
				type: "POST",
				dataType: "json",
				data: postdata,
				success: function(json)
				{
					if(json.result == "ok")
					{
						//alert("post ok");
						$("#tweet_msg").val("");
						if(callback) callback(param);
					}
					else
					{
						alert("出错啦！错误代码：" + json.info);
					}
				}
			});
		}
	}
	else
	{
		alert("请勿发送空消息");
	}
};

function onDeleteMessage(id, callback, param)
{
	msg = "确实要删除ID为：" + id + "的消息吗？";
	if(confirm(msg))
	{
		postdata = {type: "delmsg", id: id};
		$.ajax({
			url: "/action",
			type: "POST",
			dataType: "json",
			data: postdata,
			success: function(json)
			{
				if(json.result == "ok")
				{
					//alert("del ok");
					if(callback) callback(param);
				}
				else
				{
					alert("出错啦！错误代码：" + json.info);
				}
			}
		});
	}
};

function markallread()
{
	if(unread_count > 0)
	{
//		if(confirm("确定要将全部标记为已读吗？"))
//		{
			$("div.unread").removeClass("unread");
			unread_count = 0;
//		}
	}
};