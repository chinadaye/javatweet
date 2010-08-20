$(document).ready(function() {
			window.setInterval(function() {
						updateCount();
					}, 300);
			window.setInterval(function() {
						updateStatus("home");
						reloadprofile();
					}, 60000);
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

function onupdate_submit()
{
	reply_id = $("input#in_reply_to_status_id").attr("value");
	postStatus(reply_id);
	return false;
};

function onreply(id)
{
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

function ongetmore(u)
{
	type = "";
	if(u == "/public")
	{
		getmorepub();
		return;
	}
	else if(u == "/replies")
	{
		type = "morereplise";
	}
	else if(u == "/favorites")
	{
		type = "morefav";
	}
	else if(u == "/retweets_by_me")
	{
		type = "morertbyme";
	}
	else if(u == "/retweets_to_me")
	{
		type = "morerttome";
	}
	getmorestaus(type);
};
