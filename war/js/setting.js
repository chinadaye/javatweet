reload_lock = false;
$(document).ready(function() {
			var ring_radio = $(":radio[name='ring']");
			var flash_radio = $(":radio[name='flash']");
			var ring = $.cookie("setting_ring");
			var flash = $.cookie("setting_flash");
			// alert(ring);
			if (ring == null || ring == "true")
				ring_radio[0].checked = "true";
			else
				ring_radio[1].checked = "true";
			if (flash == null || flash == "true")
				flash_radio[0].checked = "true";
			else
				flash_radio[1].checked = "true";
			window.setInterval(function() {
						reloadprofile();
					}, 60000);
		});

function onCookieSetting() {
	var ring = $(":radio:checked[name='ring']").val();
	var flash = $(":radio:checked[name='flash']").val();
	$.cookie("setting_ring", ring, {
				expires : 14
			});
	$.cookie("setting_flash", flash, {
				expires : 14
			});
	alert("设置保存成功");
};

function checkext(fname) {
	if (fname.length == 0) {
		alert("请选择要上传的文件");
		return false;
	}
	var accept = false;
	var exts = new Array(".gif", ".jpg", ".png", ".jpeg");
	var pos = fname.lastIndexOf(".");
	var ext = fname.substring(pos, fname.length);
	for (var i = 0; i < exts.length; i++) {
		if (ext.toLowerCase() == exts[i]) {
			accept = true;
			break;
		}
	}
	if (!accept) {
		alert("只允许上传gif，jpg，png格式的图片");
		return false;
	} else
		return true;
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
