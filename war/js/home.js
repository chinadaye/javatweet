var reply_id = 0;
$(document).ready(
		function()
		{
			checkHome();
//			window.setTimeout
//			(
//					function(){
//						checkHome();
//					},
//					30000
//			);
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
	showIncomeStatuses();
	refreshTitle();
	if(income_statuses_count>=20){
		checkHome();
	}
	return false;
});

$("button#tweet_submit").live(
		"click",
		function()
		{
			function callback()
			{
				reply_id = 0;
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
		}
);

$("a.tweet_action_rt").live(
		"click",
		function()
		{
			reply_id = $(this).parents("div.tweet_content").children("span.tweet_id").text();
			$("#tweet_msg").val("RT @" + $(this).parents("div.tweet_content").children("span.tweet_user").text() + " " + $(this).parents("div.tweet_content").find("div.twittertext").text() + "");
			$("#tweet_msg").focus();
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
