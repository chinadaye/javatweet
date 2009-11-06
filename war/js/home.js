var reply_id = 0;
$(document).ready(
		function()
		{
			window.setTimeout
			(
					function(){
						checkHome();
					},
					15000
			);
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
$("a#income_alert").click(function(){
	$(this).css("visibility","hidden")
	updateHome();
	is_income = false;
	window.setTimeout
	(
			function(){
				checkHome();
			},
			60000
	);
	return false;
});

$("button#tweet_submit").live(
		"click",
		function()
		{
			function callback()
			{
				reply_id = 0;
				if(autofresh) updateHome();
			}
			onPostStatus(reply_id, callback);
		}
);

$("a.tweet_action_reply").live(
		"click",
		function()
		{
			reply_id = $(this).parents("div.tweet_content").children("span.tweet_id").text();
			oringal_msg = $("#tweet_msg").val();
			$("#tweet_msg").val("@" + $(this).parents("div.tweet_content").children("span.tweet_user").text() + " " + oringal_msg);
			$("#tweet_msg").focus();
			//alert(reply_id);
		}
);

$("a.tweet_action_rt").live(
		"click",
		function()
		{
			reply_id = $(this).parents("div.tweet_content").children("span.tweet_id").text();
			$("#tweet_msg").val("RT @" + $(this).parents("div.tweet_content").children("span.tweet_user").text() + " " + $(this).parents("div.tweet_content").children("span.twittertext").text() + "");
			$("#tweet_msg").focus();
			//alert(reply_id);
		}
);

$("a.tweet_action_del").live(
		"click",
		function()
		{
			del_id = $(this).parents("div.tweet_content").children("span.tweet_id").text();
			function callback(param)
			{
				param.parents("div.tweets").slideUp("normal");
				//alert(del_id);
			};
			onDelete(del_id, callback, $(this));
		}
);

$("a.tweet_action_favor").live(
		"click",
		function()
		{
			favor_id = $(this).parents("div.tweet_content").children("span.tweet_id").text();
			function callback(param)
			{
				param.text("取消收藏");
				param.attr("class", "tweet_action_unfavor");
				//alert(favor_id);
			};
			onFavor(favor_id, callback, $(this));
		}
);

$("a.tweet_action_unfavor").live(
		"click",
		function()
		{
			favor_id = $(this).parents("div.tweet_content").children("span.tweet_id").text();
			function callback(param)
			{
				param.text("收藏");
				param.attr("class", "tweet_action_favor");
				//alert(favor_id);
			};
			onUnFavor(favor_id, callback, $(this));
		}
);
