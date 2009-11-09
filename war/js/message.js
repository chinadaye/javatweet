var send_id = "";

$(document).ready(
		function()
		{
			window.setInterval
			(
				function()
				{
					//alert("update");
					if(autofresh) updateMessage();
				},
				60000
			);
			window.setInterval
			(
				function()
				{
					updateCount();
				},
				300
			);
//			flash_title();
//			markupUI();
			//retrieveShortUrl();
		}
); 

$("button#tweet_submit").live(
		"click",
		function()
		{
			function callback(param)
			{
				$("div.msg_form").slideUp("normal");
				send_id = "";
			}
			onSendMessage(send_id, callback);
		}
);

$("a.msg_action_reply").live(
		"click",
		function()
		{
			send_id = $(this).parents("div.msg_content").children("span.msg_user").text();
			$("div.msg_form").slideDown("normal");
			$("#tweet_msg").val("d " + send_id + " ");
			//alert(send_id);
		}
);

$("a.msg_action_del").live(
		"click",
		function()
		{
			del_id = $(this).parents("div.msg_content").children("span.msg_id").text();
			function callback(param)
			{
				param.parents("div.msgs").slideUp("normal");
				//alert(del_id);
			};
			onDeleteMessage(del_id, callback, $(this));
		}
);
$("#btn_shorturl").click(function(){
	var matches = $("#tweet_msg").val().match(/[A-Za-z]+:\/\/[A-Za-z0-9-,_]+\.[A-Za-z0-9-_,:%&\?\/.#=\+]+/);
	if(matches!=null){
		$.ajax({
			url: "/short",
			type: "POST",
			dataType: "json",
			data: {url:matches[0]},
			success: function(json)
			{
				if(json.short)
				{
					$("#tweet_msg").val($("#tweet_msg").val().replace(matches[0],json.short))
				}
			}
		});
	}
});
