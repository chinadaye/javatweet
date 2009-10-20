var send_id = "";

$(document).ready(
		function()
		{
			window.setInterval
			(
				function()
				{
					updateCount();
				},
				300
			);
			window.setInterval
			(
				function()
				{
					updateRate();
				},
				6000
			);
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

$("a.follow_action_msg").live(
		"click",
		function()
		{
			send_id = $(this).parents("div.follow_content").children("span.follow_name").text();
			$("div.msg_form").slideDown("normal");
			$("#tweet_msg").val("d " + send_id + " ");
			$("#tweet_msg").focus();
			//alert(send_id);
		}
);