<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<title>
		Jtweet,Twitter API proxy in Java on GAE.
		</title>
	</head>
	<body>
		<h1>Jtweet,Twitter API proxy in Java on GAE.</h1>
		<p>This is a Twitter API proxy,and is not intend to be viewed in a browser.
		<br />
		Please use <b>http://<% out.print(request.getServerName()); %>/api</b> as a Twitter API URI in your Twitter Client.
	</body>
</html>