lock = false;
more_lock = false;
reload_lock = false;
unread_count = 0;
o_title = document.title;
//下面两个变量初始值不设置为0，是为了解决在operamini上第一次发推会提示空推。
status_length = 1;
msg_length = 1;
pagenum = 1;
t = true;

maxid = $("li.hentry:first").find("div.hentry_id").attr("id");
minid = $("li.hentry:last").find("div.hentry_id").attr("id");

function markallread() {
	unread_count = $("li.unread").length;
	if (unread_count > 0) {
		$("li.unread").removeClass("unread");
		unread_count = 0;
	}
};

function updateCount() {
	if($("textarea#status").size() > 0)
	{
		status_length = $("textarea#status").val().length;
		$("span#status-field-char-counter").text(140 - status_length);
		$("span#status-field-char-counter").toggleClass("red", status_length >= 140);
		$("span#status-field-char-counter").toggleClass("green", status_length < 140);
	}
	if($("textarea#msg").size() > 0)
	{
		msg_length = $("textarea#msg").val().length;
		$("span#msg-field-char-counter").text(140 - msg_length);
		$("span#msg-field-char-counter").toggleClass("red", msg_length >= 140);
		$("span#msg-field-char-counter").toggleClass("green", msg_length < 140);
	}
};

function updateStatus(type){
	if(lock)
	{
		return;
	}
	lock = true;
	sinceid = $("li.hentry:first").find("div.hentry_id").attr("id");
	$.get("/update", {
				type : type,
				since : sinceid,
				timestamp : (new Date()).getTime()
			}, function(data) {
				$("ol#timeline").prepend(data);
				$("li.newcome").slideDown("normal");
				$("li.newcome").removeClass("newcome");
				lock = false;
				unreadalert();
			});
};

function postStatus(reply_id, callback, param1, param2) {
	status_length = $("textarea#status").val().length;
	if (status_length > 0) {
		var postdata= {
				type : "poststatus",
				t : $("textarea#status").val(),
				id : reply_id};
		$("input#update-submit").attr("disabled", true); 
		$("span#ajax_msg_status").text("消息发送中...");
		$.ajax({
			   		type : "POST",
					url : "/action",
					dataType : "json",
					data : postdata,
					success : function(json) {
						//alert(json.success);
						$("input#update-submit").attr("disabled", false); 
						if (json.success) {
							$("span#ajax_msg_status").text("消息发送成功");
							$("textarea#status").val("");
							$("input#in_reply_to_status_id").attr("value","");
							if (callback)
								callback(param1, param2);
						} else {
							$("span#ajax_msg_status").text("消息发送失败");
							alert("出错啦！错误代码：" + json.info);
						}
					}
				});
	} else {
		alert("请勿发送空消息！");
	}
};


function rtstatus(id, callback, param1, param2) {
	msg = "确实要RT ID为：" + id + "的Tweet吗？";
	if (confirm(msg)) {
		postdata = {
			type : "rtstatus",
			id : id
		};
		$("span#ajax_msg_status").text("RT中...");
		$.ajax({
			   		type : "POST",
					url : "/action",
					dataType : "json",
					data : postdata,
					success : function(json) {
						if (json.success) {
							// alert("del ok");
							$("span#ajax_msg_status").text("RT成功");
							if (callback)
								callback(param1, param2);
						} else {
							$("span#ajax_msg_status").text("RT失败");
							alert("出错啦！错误代码：" + json.info);
						}
					}
				});
	}
};

function delstatus(id, callback, param1, param2) {
	msg = "确实要删除ID为：" + id + "的Tweet吗？";
	if (confirm(msg)) {
		postdata = {
			type : "delstatus",
			id : id
		};
		$.ajax({
			   		type : "POST",
					url : "/action",
					dataType : "json",
					data : postdata,
					success : function(json) {
						if (json.success) {
							// alert("del ok");
							if (callback)
								callback(param1, param2);
						} else {
							alert("出错啦！错误代码：" + json.info);
						}
					}
				});
	}
};

function favstatus(id, callback, param1, param2) {
	postdata = {
		type : "fav",
		id : id
	};
	$.ajax({
		   		type : "POST",
				url : "/action",
				dataType : "json",
				data : postdata,
				success : function(json) {
					if (json.success) {
						// alert("favor ok");
						if (callback)
							callback(param1, param2);
					} else {
						alert("出错啦！错误代码：" + json.info);
					}
				}
			});
};

function unfavstatus(id, callback, param1, param2) {
	msg = "确实要删除对ID为：" + id + "的Tweet的收藏吗？";
	if (confirm(msg)) {
		postdata = {
			type : "unfav",
			id : id
		};
		$.ajax({
			   		type : "POST",
					url : "/action",
					dataType : "json",
					data : postdata,
					success : function(json) {
						if (json.success) {
							// alert("unfavor ok");
							if (callback)
								callback(param1, param2);
						} else {
							alert("出错啦！错误代码：" + json.info);
						}
					}
				});
	}
};

function follow(id, callback, param1, param2) {
	postdata = {
		type : "fo",
		id : id
	};

	$.ajax({
		   		type : "POST",
				url : "/action",
				dataType : "json",
				data : postdata,
				success : function(json) {
					if (json.success) {
						// alert("follow ok");
						if (callback)
							callback(param1, param2);
					} else {
						alert("出错啦！错误代码：" + json.info);
					}
				}
			});
};

function unfollow(id, callback, param1, param2) {
	msg = "确实要取消对" + id + "的跟踪吗？";

	if (confirm(msg)) {
		postdata = {
			type : "unfo",
			id : id
		};
		$.ajax({
			   		type : "POST",
					url : "/action",
					dataType : "json",
					data : postdata,
					success : function(json) {
						if (json.success) {
							// alert("unfollow ok");
							if (callback)
								callback(param1, param2);
						} else {
							alert("出错啦！错误代码：" + json.info);
						}
					}
				});
	}
};

function block(id, callback, param1, param2) {
	msg = "确实要屏蔽" + id + "吗？";

	if (confirm(msg)) {
		postdata = {
			type : "b",
			id : id
		};
		$.ajax({
			   		type : "POST",
					url : "/action",
					dataType : "json",
					data : postdata,
					success : function(json) {
						if (json.success) {
							// alert("block ok");
							if (callback)
								callback(param1, param2);
						} else {
							alert("出错啦！错误代码：" + json.info);
						}
					}
				});
	}
};

function unblock(id, callback, param1, param2) {
	msg = "确实要取消对" + id + "的屏蔽吗？";

	if (confirm(msg)) {
		postdata = {
			type : "unb",
			id : id
		};
		$.ajax({
			   		type : "POST",
					url : "/action",
					dataType : "json",
					data : postdata,
					success : function(json) {
						if (json.success) {
							// alert("unblock ok");
							if (callback)
								callback(param1, param2);
						} else {
							alert("出错啦！错误代码：" + json.info);
						}
					}
				});
	}
};

function sendMsg(sendto, callback, param1, param2) {
	msg_length = $("textarea#msg").val().length;
	if (msg_length > 0) {
		var postdata= {
				type : "sendmsg",
				t : $("textarea#msg").val(),
				id : sendto};
		$("input#send-submit").attr("disabled", true); 
		$("span#ajax_msg_msg").text("消息发送中...");
		$.ajax({
			   		type : "POST",
					url : "/action",
					dataType : "json",
					data : postdata,
					success : function(json) {
						//alert(json.success);
						$("input#send-submit").attr("disabled", false); 
						if (json.success) {
							$("span#ajax_msg_msg").text("消息发送成功");
							$("textarea#msg").val("");
							$("input#send_to").attr("value","");
							$("span#send_to_span").text("");
							if (callback)
								callback(param1, param2);
						} else {
							$("span#ajax_msg_msg").text("消息发送失败");
							alert("出错啦！错误代码：" + json.info);
						}
					}
				});
	} else {
		alert("请勿发送空消息！");
	}
};

function delmsg(id, callback, param1, param2) {
	msg = "确实要删除ID为：" + id + "的Msg吗？";
	if (confirm(msg)) {
		postdata = {
			type : "delmsg",
			id : id
		};
		$.ajax({
			   		type : "POST",
					url : "/action",
					dataType : "json",
					data : postdata,
					success : function(json) {
						if (json.success) {
							// alert("del ok");
							if (callback)
								callback(param1, param2);
						} else {
							alert("出错啦！错误代码：" + json.info);
						}
					}
				});
	}
};

function onupdate_reset()
{
	$("input#in_reply_to_status_id").attr("value","");
	$("textarea#status").val("");
	return false;
};

function onsend_reset()
{
	$("input#send_to").attr("value","");
	$("span#send_to_span").text("");
	$("textarea#msg").val("");
	return false;
}

function onreplystatus(id)
{
	o_status = $("textarea#status").val();
	$("input#in_reply_to_status_id").attr("value",id);
	$("textarea#status").val("@" + $("li#status_" + id).find("a.screen-name").text() + " " + o_status);
	$("textarea#status").focus()
	return false;
};

function onrt_t(id)
{
	o_status = $("li#status_" + id).find("span.entry-content").text();
	$("input#in_reply_to_status_id").attr("value",id);
	$("textarea#status").val("RT @" + $("li#status_" + id).find("a.screen-name").text() + " " + o_status);
	$("textarea#status").focus()
	return false;
};

function onreplymsg(id)
{
	$("input#send_to").attr("value",id);
	$("span#send_to_span").text(id);
	$("textarea#msg").focus()
	return false;
};

function getmorestaus(type) {
	if(more_lock)
	{
		return;
	}
	more_lock = true;
	//alert(maxid);
	//alert(pagenum);
	$("a#more").find("h3").text("Loading...");
	$.get("/update", {
				type : type,
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

function getmorepub() {
	if(more_lock)
	{
		return;
	}
	more_lock = true;
	$("a#more").find("h3").text("Loading...");
	$.get("/update", {
				type : "morepub",
				timestamp : (new Date()).getTime()
			}, function(data) {
				$("ol#timeline").append(data);
				$("a#more").find("h3").text("more");
				more_lock = false;
			}, "html");
};

function getmoresearch(s) {
	if(more_lock)
	{
		return;
	}
	more_lock = true;
	//alert(pagenum);
	$("a#more").find("h3").text("Loading...");
	$.get("/update", {
				type : "moresearch",
				s : s,
				page: pagenum + 1,
				timestamp : (new Date()).getTime()
			}, function(data) {
				$("ol#timeline").append(data);
				$("a#more").find("h3").text("more");
				pagenum += 1;
				more_lock = false;
			}, "html");
};

function forbind(e)
{
	e.data.func(e.data.param);
	e.preventDefault();
}

function playSound() {
	var ring = $.cookie("setting_ring");
	if (ring == null || ring == "true") {
		var ringswf = document.getElementById("MsgRing");
		ringswf.Rewind();
		ringswf.Play();
	}
};

function unreadalert() {
	unread_count = $("li.unread").length;
	if (unread_count > 0)
		playSound();
};

function flash_title() {
	var flash = $.cookie("setting_flash");
	unread_count = $("li.unread").length;
	if ((flash  == null || flash  == "true") && unread_count > 0){
		if (t) {
			document.title = "【" + unread_count + "未读】 " + o_title;
			t = false;
		} else {
			document.title = o_title;
			t = true;
		}
	} else {
		document.title = o_title;
	}
};

function reloadprofile()
{
	if(reload_lock)
	{
		return;
	}
	reload_lock = true;
	$.get("/action", {
			type : "reloadprofile",
			timestamp : (new Date()).getTime()
		}, function(json) {
			if (json.success) {
				$("span#update_count").text(json.t_count);
				$("div#following_count").text(json.foing_count);
				$("div#follower_count").text(json.foer_count);
				$("div#favs_count").text(json.favs_count);
			} else {
				alert("出错啦！错误代码：" + json.info);
			}
			reload_lock = false;
		}, "json");
};
