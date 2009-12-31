/**
 * update:2009-12-31
 */


tweet_length = 0;
unread_count = 0;
title = document.title;
t = false;
var short_url_services = "1u.ro,1url.com,2pl.us,3.ly,a2a.me,abe5.com,awe.sm,bkite.com,blippr.com,blippr.com,bt.io,burnurl.com,c.shamekh.ws,cd4.me,clickthru.ca,cuturl.com,df9.net,eezurl.com,fa.by,fav.me,flic.kr,fuseurl.com,go2.me,golmao.com,gri.ms,gurl.es,hellotxt.com,icio.us,ito.mx,j.mp,linkbee.com,loopt.us,lt.tl,miniurl.com,minurl.fr,ncane.com,ofl.me,oxyz.info,p8g.tw,pic.gd,poll.fm,pop.ly,posted.at,readthis.ca,relyt.us,retwt.me,rly.cc,rsmonkey.com,rurl.org,shortna.me,shrinkster.com,shrtl.com,simurl.net,simurl.org,simurl.us,stickurl.com,sturly.com,su.pr,takemyfile.com,thrdl.es,tinyarro.ws,tinytw.it,tnw.to,tr.my,trcb.me,tumblr.com,tw0.us,tw1.us,tw2.us,tw5.us,tw6.us,tw8.us,tw9.us,twi.gy,twit.ac,twitthis.com,twitzap.com,twtr.us,url.inc-x.eu,url4.eu,uservoice.com,ustre.am,vl.am,wa9.la,wkrg.com,wp.me,x.hypem.com,xeeurl.com,xr.com,zi.pe";
var longurl_services = "0rz.tw,2tu.us,307.to,6url.com,a.gg,a.nf,a2n.eu,ad.vu,adf.ly,adjix.com,alturl.com,atu.ca,azqq.com,b23.ru,b65.com,bacn.me,bit.ly,bloat.me,budurl.com,buk.me,canurl.com,chilp.it,clck.ru,cli.gs,cliccami.info,clipurl.us,clop.in,cort.as,cuturls.com,decenturl.com,digg.com,doiop.com,dwarfurl.com,easyurl.net,eepurl.com,ewerl.com,ff.im,fff.to,fhurl.com,flingk.com,flq.us,fly2.ws,fwd4.me,fwdurl.net,g8l.us,gl.am,go.9nl.com,goshrink.com,hex.io,href.in,htxt.it,hugeurl.com,hurl.ws,icanhaz.com,idek.net,is.gd,jijr.com,kissa.be,kl.am,klck.me,korta.nu,l9k.net,liip.to,liltext.com,lin.cr,linkgap.com,liurl.cn,ln-s.net,ln-s.ru,lnkurl.com,lru.jp,lu.to,lurl.no,memurl.com,merky.de,migre.me,minilien.com,moourl.com,myurl.in,nanoref.com,nanourl.se,netnet.me,ni.to,nn.nf,notlong.com,nutshellurl.com,o-x.fr,offur.com,omf.gd,onsaas.info,ow.ly,parv.us,peaurl.com,ping.fm,piurl.com,plumurl.com,plurl.me,pnt.me,poprl.com,post.ly,ptiturl.com,qlnk.net,qurlyq.com,r.im,rb6.me,rde.me,reallytinyurl.com,redir.ec,redirects.ca,redirx.com,ri.ms,rickroll.it,rubyurl.com,s3nt.com,s7y.us,shink.de,short.ie,short.to,shortenurl.com,shorterlink.com,shortlinks.co.uk,shoturl.us,shredurl.com,shrinkify.com,shrinkr.com,shrinkurl.us,shrtnd.com,shurl.net,shw.me,smallr.com,smurl.com,sn.im,sn.vc,snadr.it,snipr.com,snipurl.com,snurl.com,sp2.ro,spedr.com,srnk.net,srs.li,starturl.com,surl.co.uk,ta.gd,tcrn.ch,tgr.me,tighturl.com,tiny.cc,tiny.pl,tinylink.com,tinyurl.com,to.ly,togoto.us,tr.im,tra.kz,trunc.it,tubeurl.com,twitclicks.com,twitterurl.net,twiturl.de,twurl.cc,twurl.nl,u.mavrev.com,u.nu,u76.org,ub0.cc,ulu.lu,updating.me,ur1.ca,url.az,url.co.uk,url.ie,urlborg.com,urlbrief.com,urlcut.com,urlcutter.com,urlhawk.com,urlkiss.com,urlpire.com,urlvi.be,urlx.ie,virl.com,wapurl.co.uk,wipi.es,x.se,xil.in,xrl.in,xrl.us,xurl.jp,xzb.cc,yatuc.com,yep.it,yfrog.com,zi.ma,zurl.ws,zz.gd,zzang.kr,›.ws,✩.ws,✿.ws,❥.ws,➔.ws,➞.ws,➡.ws,➨.ws,➯.ws,➹.ws,➽.ws";
var img_small_loader = '<img class="small_loader" src="/img/ajax-loader.gif" />';
var income_statuses = "";
var income_statuses_count= 0;
var check_timeout = null;
var last_status_id = 0;

$(document).ready(function(){
	$("#ajax_loader").hide();
	if($("#uploadimg").length>0){
		new AjaxUpload('#uploadimg', {
			name:'media',
			action: '/imgly',
			responseType:'json',
			onSubmit : function(file , ext){
				if (ext && /^(jpg|png|jpeg|gif)$/.test(ext)){
					$('#uploadimg').text('请稍候..');	
				}
			},
			onComplete : function(file, response){
				console.log(response);
				if(response&&response.imgurl){
					$("#tweet_msg").val($("#tweet_msg").val()+" "+response.imgurl);
				}else{
					alert('上传出错,请稍候重试'+(response.error?respon.error:""));
				}
				$('#uploadimg').text('上传图片');				
			}		
		});
	}
});
$("#link_logout").click(function(){
	$.cookie('up',  { expires: -1 });
	setTimeout(function(){window.location.href='/'},25);
	return false;
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

$("div.tweets,div.msgs").live(
		"mouseover",
		function()
		{
			$(this).find("span.tweet_action,span.msg_action").show();
		}
).live(
		"mouseout",
		function()
		{
			$(this).find("span.tweet_action,span.msg_action").hide();
		}
);
$("a.mayshort").live(
		"click",
		function(){
			return revertShortUrl(this);
		}
);
$("p.search").live(
						"mouseover",
						function()
						{
							$(this).find("a.del_saved_search").show();
						}
				).live(
						"mouseout",
						function()
						{
							$(this).find("a.del_saved_search").hide();
						}
);
$("a.del_saved_search").live(
		"click",
		function(){
			var id=$(this).attr('rel');
			if(id.match(/\d+/)!=null&&confirm('确定要删除么？')){
				$(this).hide().after(img_small_loader);
				var postdata = {type: "delquery", id:id};
				$.ajax({
					url: "/action",
					type: "POST",
					dataType: "json",
					data: postdata,
					success: function(json)
					{
						if(json.id==id){
							$("#saved_search_"+id).slideUp();
						}else{
							alert("发生了错误,请刷新页面后重试");
						}
					}
				});
			}
		});
$("#btn_shorturl").click(function(){
	var matches = $("#tweet_msg").val().match(/[A-Za-z]+:\/\/[A-Za-z0-9-,_]+\.[A-Za-z0-9-_,:%&\?\/.#=\+]+/);
	if(matches!=null){
		$("span.tweet_count_info").prepend(img_small_loader);
		$.ajax({
			url: "http://to.ly/api.php",
			type: "GET",
			dataType: "jsonp",
			data: {json:1,longurl:matches[0]},
			error:function(){
				$("span.tweet_count_info img.small_loader").remove();
			},
			success: function(json)
			{
				if(json.shorturl)
				{
					$("#tweet_msg").val($("#tweet_msg").val().replace(matches[0],json.shorturl));
				}
				$("span.tweet_count_info img.small_loader").remove();
			}
		});
	}
});
$("#reflesh_profile").click(function(){
	$(this).hide().after(img_small_loader);
	$.ajax({
		url: "/update",
		type: "GET",
		dataType: "json",
		data: {type:'profilecount'},
		success: function(json)
		{
			if(json.code==1)
			{
				$("#side_count_friends").text(json.friends);
				$("#side_count_followers").text(json.followers);
				$("#side_count_statuses").text(json.statuses);
			}
			$("#reflesh_profile").show().next('img.small_loader').remove();
		}
	});
});
$("#show_add_search").click(function(){
	$("#add_search_form").toggle();
	if($("#add_search_form:visible").length>0){
		$("#add_search_query").focus();
		}
	});
if (window.navigator.userAgent.indexOf("MSIE 6.0")>=1){
	$("body").addClass("ie ie6");
}else{
    if (window.navigator.userAgent.indexOf("MSIE 7.0")>=1){
    	$("body").addClass("ie ie7");
    }else{
    	$("body").addClass("firefox-windows");
    }
};



function showIncomeStatuses(){
	if(income_statuses_count>0&&current_at=='home_1'){
		$("#tweet_warp").children('div.newcome').removeClass("newcome");
		income_statuses_count = 0;
		income_statuses = "";
	}
	refreshCreateAt();
	refreshTitle();
};

/**
 * 检查是否有新消息
 * @return
 */
function checkHome(){
	if(last_status_id==0){
		last_status_id =$("div#tweet_warp div.tweets:first-child").children("div.tweet_content").children("span.tweet_id").text();
	}
	if($("div#tweet_warp div.tweets").length>50&&$("#tweet_msg").val().match(/\S+/)==null&&$("a#income_alert:visible").length!=1){
		window.location.reload();
		return ;
	}
	$.ajax({
		url:'/update?type=home&since='+last_status_id,
		cache:false,
		dataType:'json',
		success:function(data,textStatus){
			if(data&&data.code==1&&data.count>0){
				$("a#income_alert").css("visibility","visible");
				last_status_id  = data.last_id;
				income_statuses_count += data.count;
				$("#tweet_warp").prepend(data.data)
			}
			refreshTitle();
		},
		error:function(){
			refreshTitle();
		}
		});
	
}
function refreshTitle(){
	if(income_statuses_count<15){
		if(check_timeout!=null){
		clearTimeout(check_timeout);
		}
		check_timeout = window.setTimeout
		(
				function(){
					checkHome();
				},
				120000
		);
	}
	if(income_statuses_count>0){
		$("a#income_alert:hidden").slideDown();
		document.title = "("+income_statuses_count+")"+title;
	}else{
		$("a#income_alert").hide();
		document.title = title;
	}
}
function refreshCreateAt(){
	var times = $("a.status_create_at");
	var count = times.length;
	var currenttime = new Date().getTime();
	for(var i=0;i<count;i++){
		var longtime = $(times[i]).attr('rel');
		var secs = (currenttime - longtime)/1000;
		var timetext = '';
		if(secs < 60) {
			timetext = '刚刚';
		}else if(secs < 3600) {
			timetext =  Math.round(secs/60) + " 分钟前";
		}else if(secs < 86400) {
			timetext = Math.round(secs/3600) + " 小时前";
		}else if(secs<86400*2){
			timetext = '昨天';
		}else{
			timetext = "2天之前";
		}
		$(times[i]).text(timetext);
	}
}
function onAddSearch(form){
	var query = $("#add_search_query").val();
	if(query.match(/\S+/)!=null){
		$("#btn_add_search").hide().after(img_small_loader);
		var postdata = {type: "addquery", query:query};
		$.ajax({
			url: "/action",
			type: "POST",
			dataType: "json",
			data: postdata,
			success: function(json)
			{
				if(json.query&&json.id){
					$("#add_search_query").val('');
					$("#saved_searches").append('<p id="saved_search_'+json.id+'" class="search"><a href="/search?s='+json.query+'">#'+json.query+'</a><a href="javascript:;" class="del_saved_search" style="display:none;" rel="'+json.id+'">X</a><p>')
					$("#btn_add_search").show().next('img.small_loader').remove();
					$(form).hide();
				}else{
					alert("发生了错误,请刷新页面后重试");
				}
			}
		});
	}else{
		alert('内容不能为空');
	}
	return false;
}
/**
 * 还原单个连接
 */
function revertShortUrl(link){
	$(link).after(img_small_loader);
	var url = $(link).attr('href');
	var matches = url.match(/.*\/\/([A-Za-z0-9-_.]+)\/.*/);
	if(matches!=null&&short_url_services.match(".*,?"+matches[1]+",?.*")){
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
	}else{
		$(link).next('img.small_loader').remove();
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
	if(tweet_length>0){
		$("#tweet_submit").attr("disabled",'');
	}else{
		$("#tweet_submit").attr("disabled",'disabled');
	}
	$("span#tweet_count").text(140 - tweet_length);
	$("span#tweet_count").toggleClass("tweet_count_red", tweet_length >= 140);
};

function onPostStatus(reply_id, callback, param)
{
	if(tweet_length > 0)
	{
		$("span.tweet_count_info").prepend(img_small_loader);
		var postdata;
		if(check_timeout!=null){
			clearTimeout(check_timeout);
			check_timeout = null;
		}
		
		if(reply_id == 0) postdata = {
				type: "post",
				tweet_msg: $("#tweet_msg").val(),
				id: reply_id,
				last_id:last_status_id
				}; 
		else postdata = {
				type: "post",
				tweet_msg: $("#tweet_msg").val(),
				id: reply_id,
				last_id:last_status_id
		};
		
		$.ajax({
			url: "/action",
			type: "POST",
			dataType: "json",
			data: postdata,
			error:function(){
				$("span.tweet_count_info img.small_loader").remove();
				refreshTitle();
			},
			success: function(json)
			{
				if(json.result == "ok")
				{
					if(json.new_data){
						$("#tweet_warp").prepend(json.new_data);
						income_statuses_count += json.new_count;
					}
					if(json.data){
						
						if(json.id>last_status_id&&current_at=='home_1'){
							last_status_id = json.id;
						}
						$("#tweet_warp").prepend($(json.data).removeClass("newcome"));
					}
					$("#tweet_msg").val("");
						
					if(callback) callback(param);
				}
				else
				{
					alert("出错啦！错误代码：" + json.info);
				}
				$("span.tweet_count_info img.small_loader").remove();
				window.setTimeout(refreshTitle,2000);
			}
		});
	}
	
};

function onDelete(id, callback, param)
{
	msg = "确实要删除这条Tweet吗？";
	if(confirm(msg))
	{
		param.after(img_small_loader);
		postdata = {type: "delete", id: id};
		$.ajax({
			url: "/action",
			type: "POST",
			dataType: "json",
			data: postdata,
			error:function(){
				param.next('img.small_loader').remove();
			},
			success: function(json)
			{
				param.next('img.small_loader').remove();
				if(json.result == "ok")
				{
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
	param.after(img_small_loader);
	$.ajax({
		url: "/action",
		type: "POST",
		dataType: "json",
		data: postdata,
		error:function(){
			param.next('img.small_loader').remove();
		},
		success: function(json)
		{
			param.next('img.small_loader').remove();
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
		param.after(img_small_loader);
		postdata = {type: "unfavor", id: id};
		$.ajax({
			url: "/action",
			type: "POST",
			dataType: "json",
			data: postdata,
			error:function(){
				param.next('img.small_loader').remove();
			},
			success: function(json)
			{
				param.next('img.small_loader').remove();
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
	param.after(img_small_loader);
	$.ajax({
		url: "/action",
		type: "POST",
		dataType: "json",
		data: postdata,
		error:function(){
			param.next('img.small_loader').remove();
		},
		success: function(json)
		{
			param.next('img.small_loader').remove();
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
		param.after(img_small_loader);
		postdata = {type: "unfollow", id: id};
		$.ajax({
			url: "/action",
			type: "POST",
			dataType: "json",
			data: postdata,
			error:function(){
			param.next('img.small_loader').remove();
			},
			success: function(json)
			{
				param.next('img.small_loader').remove();
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
		param.after(img_small_loader);
		postdata = {type: "block", id: id};
		$.ajax({
			url: "/action",
			type: "POST",
			dataType: "json",
			data: postdata,
			error:function(){
				param.next('img.small_loader').remove();
			},
			success: function(json)
			{
				param.next('img.small_loader').remove();
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
		param.after(img_small_loader);
		postdata = {type: "unblock", id: id};
		$.ajax({
			url: "/action",
			type: "POST",
			dataType: "json",
			data: postdata,
			error:function(){
				param.next('img.small_loader').remove();
			},
			success: function(json)
			{
				param.next('img.small_loader').remove();
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
		param.after(img_small_loader);
		postdata = {type: "delmsg", id: id};
		$.ajax({
			url: "/action",
			type: "POST",
			dataType: "json",
			data: postdata,
			error:function(){
				param.next('img.small_loader').remove();
			},
			success: function(json)
			{
				param.next('img.small_loader').remove();
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