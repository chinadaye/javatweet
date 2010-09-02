$(document).ready(function() {
			window.setInterval(function() {
						updateCount();
					}, 300);
			window.setInterval(function() {
						reloadprofile();
					}, 60000);
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
	sendto = $("input#send_to").attr("value");
	if(sendto.length > 0)
	{
		sendMsg(sendto);
	}
	else
	{
		alert("请指定消息接收者");
	}
	return false;
};

function ondelmsg(id)
{
	//alert(id);
	function callback(param1, param2) {
		param1.slideUp("normal");
	};
	delmsg(id, callback, $("li#msg_" + id));
	return false;
};

function ongetmoremsg(u)
{
	type = "";
	if(u == "/inbox")
	{
		type = "moreinbox"
	}
	else if(u == "/outbox")
	{
		type = "moreoutbox";
	}
	getmorestaus(type);
};

