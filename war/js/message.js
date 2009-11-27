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
		}
); 


$("a.msg_action_reply").live(
		"click",
		function()
		{
			send_id = $(this).parents("div.msg_content").children("span.msg_user").text();
			//$("div.msg_form").slideDown("normal");
			$("#user_followers").val(send_id);
			$("#tweet_msg").focus();
			//$("#tweet_msg").val("d " + send_id + " ");
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
