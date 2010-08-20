$(document).ready(function() {
			window.setInterval(function() {
						updateCount();
					}, 300);
		});

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
	$("form#msg_update_form").slideDown("normal");
	onreplymsg(id);
	return false;
}

function onfo(id)
{
	//alert(id);
	screenname = $("li#follow_" + id).find("a.screen-name").text();
	function callback(param1, param2) {
		param1.text("取消关注");
		param1.attr("href", "/following?action=unfo&u=" + param2);
		param1.attr("onclick","");
		param1.unbind("click");
		param1.bind("click", {func:onunfo, param: param2}, forbind);
	};
	follow(screenname, callback, $("li#follow_" + id).find("a.a_fo"), id);
	return false;
};

function onunfo(id)
{
	//alert(id);
	screenname = $("li#follow_" + id).find("a.screen-name").text();
	function callback(param1, param2) {
		param1.text("关注");
		param1.attr("href", "/following?action=fo&u=" + param2);
		param1.attr("onclick","");
		param1.unbind("click");
		param1.bind("click", {func:onfo, param: param2}, forbind);
	};
	unfollow(screenname, callback, $("li#follow_" + id).find("a.a_fo"), id);
	return false;
};

function onb(id)
{
	//alert(id);
	screenname = $("li#follow_" + id).find("a.screen-name").text();
	function callback(param1, param2) {
		param1.text("取消屏蔽");
		param1.attr("href", "/blocking?action=unb&u=" + param2);
		param1.attr("onclick","");
		param1.unbind("click");
		param1.bind("click", {func:onunb, param: param2}, forbind);
	};
	block(screenname, callback, $("li#follow_" + id).find("a.a_b"), id);
	return false;
};

function onunb(id)
{
	//alert(id);
	screenname = $("li#follow_" + id).find("a.screen-name").text();
	function callback(param1, param2) {
		param1.text("屏蔽");
		param1.attr("href", "/blocking?action=b&u=" + param2);
		param1.attr("onclick","");
		param1.unbind("click");
		param1.bind("click", {func:onb, param: param2}, forbind);;
	};
	unblock(screenname, callback, $("li#follow_" + id).find("a.a_b"), id);
	return false;
};




