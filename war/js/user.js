$(document).ready(function() {
			window.setInterval(function() {
						updateCount();
					}, 300);
		});

if($("textarea#status").length > 0)
{
	$("textarea#status").keypress(function(e) {
			if (e.ctrlKey && e.which == 13 || e.which == 10) {
				onupdate_submit();
				document.body.focus();
			} else if (e.shiftKey && e.which == 13 || e.which == 10) {
				onupsend_submit();
				document.body.focus();
			}
		});
}
if($("textarea#msg").length > 0)
{
	$("textarea#msg").keypress(function(e) {
			if (e.ctrlKey && e.which == 13 || e.which == 10) {
				onsend_submit();
				document.body.focus();
			} else if (e.shiftKey && e.which == 13 || e.which == 10) {
				onsend_submit();
				document.body.focus();
			}
		});
}

function onupdate_submit()
{
	reply_id = $("input#in_reply_to_status_id").attr("value");
	function callback(param1, param2)
	{
		param1.slideUp("normal");
	};
	postStatus(reply_id, callback, $("form#status_update_form"));
	return false;
};

function onreply(id)
{
	$("form#msg_update_form").slideUp("normal");
	$("form#status_update_form").slideDown("normal");
	onreplystatus(id);
	return false;
}

function onfav(id)
{
	//alert(id);
	function callback(param1, param2) {
		param1.text("取消收藏");
		param1.attr("href", "/favorites?action=unfav&id=" + param2);
		param1.attr("onclick","");
		param1.unbind("click");
		param1.bind("click", {func:onunfav, param: param2}, forbind);
	};
	favstatus(id, callback, $("li#status_" + id).find("a.a_fav"), id);
	return false;
};

function onunfav(id)
{
	//alert(id);
	function callback(param1, param2) {
		param1.text("收藏");
		param1.attr("href", "/favorites?action=fav&id=" + param2);
		param1.attr("onclick","");
		param1.unbind("click");
		param1.bind("click", {func:onfav, param: param2}, forbind);
	};
	unfavstatus(id, callback, $("li#status_" + id).find("a.a_fav"), id);
	return false;
};

function ondel(id)
{
	//alert(id);
	function callback(param1, param2) {
		param1.slideUp("normal");
	};
	delstatus(id, callback, $("li#status_" + id));
	return false;
};

function onrt(id)
{
	//alert(id);
	rtstatus(id);
	return false;
};

function onsend_submit()
{
	sendto =$("input#send_to").attr("value");
	if(sendto.length > 0)
	{
		function callback(param1, param2)
		{
			param1.slideUp("normal");
		};
		sendMsg(sendto, callback, $("form#msg_update_form"));
	}
	else
	{
		alert("请指定消息接收者");
	}
	return false;
};

function onsendmsg(id)
{
	$("form#status_update_form").slideUp("normal");
	$("form#msg_update_form").slideDown("normal");
	onreplymsg(id);
	return false;
}

function onfo(id)
{
	//alert(id);
	function callback(param1, param2) {
		param1.text("取消关注");
		param1.attr("href", "/following?action=unfo&u=" + param2);
		param1.attr("onclick","");
		param1.unbind("click");
		param1.bind("click", {func:onunfo, param: param2}, forbind);
	};
	follow(id, callback, $("ul#user_action").find("a.a_fo"), id);
	return false;
};

function onunfo(id)
{
	//alert(id);
	function callback(param1, param2) {
		param1.text("关注");
		param1.attr("href", "/following?action=fo&u=" + param2);
		param1.attr("onclick","");
		param1.unbind("click");
		param1.bind("click", {func:onfo, param: param2}, forbind);
	};
	unfollow(id, callback, $("ul#user_action").find("a.a_fo"), id);
	return false;
};

function onb(id)
{
	//alert(id);
	function callback(param1, param2) {
		param1.text("取消屏蔽");
		param1.attr("href", "/blocking?action=unb&u=" + param2);
		param1.attr("onclick","");
		param1.unbind("click");
		param1.bind("click", {func:onunb, param: param2}, forbind);
	};
	block(id, callback, $("ul#user_action").find("a.a_b"), id);
	return false;
};

function onunb(id)
{
	//alert(id);
	function callback(param1, param2) {
		param1.text("屏蔽");
		param1.attr("href", "/blocking?action=b&u=" + param2);
		param1.attr("onclick","");
		param1.unbind("click");
		param1.bind("click", {func:onb, param: param2}, forbind);
	};
	unblock(id, callback, $("ul#user_action").find("a.a_b"), id);
	return false;
};

function ongetmoreusertimeline(id)
{
	if(more_lock)
	{
		return;
	}
	more_lock = true;
	//alert(maxid);
	//alert(pagenum);
	$("a#more").find("h3").text("Loading...");
	$.get("/update", {
				type : "moreusertimeline",
				u : id,
				maxid : maxid,
				page: pagenum + 1,
				timestamp : (new Date()).getTime()
			}, function(data) {
				$("ol#timeline").append(data);
				$("a#more").find("h3").text("more");
				pagenum += 1;
				more_lock = false;
			}, "html");
};


function ongetmoreuserfav(id)
{
	if(more_lock)
	{
		return;
	}
	more_lock = true;
	//alert(maxid);
	//alert(pagenum);
	$("a#more").find("h3").text("Loading...");
	$.get("/update", {
				type : "morefav",
				u : id,
				page: pagenum + 1,
				timestamp : (new Date()).getTime()
			}, function(data) {
				$("ol#timeline").append(data);
				$("a#more").find("h3").text("more");
				pagenum += 1;
				more_lock = false;
			}, "html");
};