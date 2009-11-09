tweet_length = 0;
unread_count = 0;
title = document.title;
t = false;
var is_income = false;
var short_url_services = "1u.ro,1url.com,2pl.us,3.ly,a2a.me,abe5.com,awe.sm,bkite.com,blippr.com,blippr.com,bt.io,burnurl.com,c.shamekh.ws,cd4.me,clickthru.ca,cuturl.com,df9.net,eezurl.com,fa.by,fav.me,flic.kr,fuseurl.com,go2.me,golmao.com,gri.ms,gurl.es,hellotxt.com,icio.us,ito.mx,j.mp,linkbee.com,loopt.us,lt.tl,miniurl.com,minurl.fr,ncane.com,ofl.me,oxyz.info,p8g.tw,pic.gd,poll.fm,pop.ly,posted.at,readthis.ca,relyt.us,retwt.me,rly.cc,rsmonkey.com,rurl.org,shortna.me,shrinkster.com,shrtl.com,simurl.net,simurl.org,simurl.us,stickurl.com,sturly.com,su.pr,takemyfile.com,thrdl.es,tinyarro.ws,tinytw.it,tnw.to,tr.my,trcb.me,tumblr.com,tw0.us,tw1.us,tw2.us,tw5.us,tw6.us,tw8.us,tw9.us,twi.gy,twit.ac,twitthis.com,twitzap.com,twtr.us,url.inc-x.eu,url4.eu,uservoice.com,ustre.am,vl.am,wa9.la,wkrg.com,wp.me,x.hypem.com,xeeurl.com,xr.com,zi.pe";
var longurl_services = "0rz.tw,2tu.us,307.to,6url.com,a.gg,a.nf,a2n.eu,ad.vu,adf.ly,adjix.com,alturl.com,atu.ca,azqq.com,b23.ru,b65.com,bacn.me,bit.ly,bloat.me,budurl.com,buk.me,canurl.com,chilp.it,clck.ru,cli.gs,cliccami.info,clipurl.us,clop.in,cort.as,cuturls.com,decenturl.com,digg.com,doiop.com,dwarfurl.com,easyurl.net,eepurl.com,ewerl.com,ff.im,fff.to,fhurl.com,flingk.com,flq.us,fly2.ws,fwd4.me,fwdurl.net,g8l.us,gl.am,go.9nl.com,goshrink.com,hex.io,href.in,htxt.it,hugeurl.com,hurl.ws,icanhaz.com,idek.net,is.gd,jijr.com,kissa.be,kl.am,klck.me,korta.nu,l9k.net,liip.to,liltext.com,lin.cr,linkgap.com,liurl.cn,ln-s.net,ln-s.ru,lnkurl.com,lru.jp,lu.to,lurl.no,memurl.com,merky.de,migre.me,minilien.com,moourl.com,myurl.in,nanoref.com,nanourl.se,netnet.me,ni.to,nn.nf,notlong.com,nutshellurl.com,o-x.fr,offur.com,omf.gd,onsaas.info,ow.ly,parv.us,peaurl.com,ping.fm,piurl.com,plumurl.com,plurl.me,pnt.me,poprl.com,post.ly,ptiturl.com,qlnk.net,qurlyq.com,r.im,rb6.me,rde.me,reallytinyurl.com,redir.ec,redirects.ca,redirx.com,ri.ms,rickroll.it,rubyurl.com,s3nt.com,s7y.us,shink.de,short.ie,short.to,shortenurl.com,shorterlink.com,shortlinks.co.uk,shoturl.us,shredurl.com,shrinkify.com,shrinkr.com,shrinkurl.us,shrtnd.com,shurl.net,shw.me,smallr.com,smurl.com,sn.im,sn.vc,snadr.it,snipr.com,snipurl.com,snurl.com,sp2.ro,spedr.com,srnk.net,srs.li,starturl.com,surl.co.uk,ta.gd,tcrn.ch,tgr.me,tighturl.com,tiny.cc,tiny.pl,tinylink.com,tinyurl.com,to.ly,togoto.us,tr.im,tra.kz,trunc.it,tubeurl.com,twitclicks.com,twitterurl.net,twiturl.de,twurl.cc,twurl.nl,u.mavrev.com,u.nu,u76.org,ub0.cc,ulu.lu,updating.me,ur1.ca,url.az,url.co.uk,url.ie,urlborg.com,urlbrief.com,urlcut.com,urlcutter.com,urlhawk.com,urlkiss.com,urlpire.com,urlvi.be,urlx.ie,virl.com,wapurl.co.uk,wipi.es,x.se,xil.in,xrl.in,xrl.us,xurl.jp,xzb.cc,yatuc.com,yep.it,yfrog.com,zi.ma,zurl.ws,zz.gd,zzang.kr,›.ws,✩.ws,✿.ws,❥.ws,➔.ws,➞.ws,➡.ws,➨.ws,➯.ws,➹.ws,➽.ws";
var img_small_loader = '<img class="small_loader" src="/img/ajax-loader.gif" />';
$(document).ready(function(){
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

$("div.tweets").live(
		"mouseover",
		function()
		{
			$(this).find("span.tweet_action,span.msg_action").show();
		}
);
$("a.mayshort").live(
		"click",
		function(){
			return revertShortUrl(this);
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
			cache:false,
			{
			type: "home",
			since: sinceid
			},
			function(respon){
				if(respon&&respon.code==1&&respon.count>0){
					$("a#income_alert").css("visibility","visible");
					is_income  = true;
				}else{
					$("a#income_alert").css("visibility","hidden");
					window.setTimeout
					(
							function(){
								checkHome();
							},
							30000
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
		$.ajax({
			url:'/untinyme?url='+matches[0],
			dataType:'json',
			success:function(data,textStatus){
				if(data.org_url){
					$(link).attr('href',data.org_url);
					$(link).text(data.org_url);
				}
				$(link).next('img.small_loader').remove();
			},
			error:function(){
				$(link).next('img.small_loader').remove();
			}
			});
		$(link).removeClass('mayshort');
		return false;
	}else if(matches!=null&&longurl_services.match(".*,?"+matches[1]+",?.*")){//longurl_services
		$(link).after(img_small_loader);
		$.ajax({
			url:'http://api.longurl.org/v2/expand?format=json&user-agent=jteet&url='+matches[0],
			dataType:'jsonp',
			success:function(data,textStatus){
				if(data['long-url']){
					$(link).attr('href',data['long-url']);
					$(link).text(data['long-url']);
				}
				$(link).next('img.small_loader').remove();
			},
			error:function(){
				$(link).next('img.small_loader').remove();
			}
			});
		$(link).removeClass('mayshort');
		return false;
	}
	
	return true;
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
				$("a#income_alert").css("visibility","hidden");
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