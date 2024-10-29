<?php

if (!isset($_COOKIE['userid'])) {
	header("Location: membership.php");
} 
else {
	print($_COOKIE['userid']." has logged in.");
}

?>

<!DOCTYPE html>
<!-- Web Programming Project
	 Description: This page can only be accessed by a member. 
	 Allows members to submit games they want to be highlighted in the
	 near future.-->
<html>
	<head>
		<script src="./jquery/jquery-3.6.3.min.js"></script>
        <link rel="stylesheet" href="./foundation/css/foundation.css">
        <title>Membership Access Page</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<style>
			body {
				font-family: Helvetica;
			}

			#navigation {
				font-size: 35px;
				padding: 10px;
				border: 1px solid black;
			}

			#header {
				border: 1px solid black;
				font-size: 25px;
				padding: 15px;
				background-color: #0c679b;
				text-align: center;
				color: white;
				height: 150px;
			}

			#body {
				font-size: 20px;
				padding: 5px;
				border: 1px solid black;
			}

			.footer {
				border: 1px solid black;
				background-color: dimgray;
				color: white;
			}

		</style>

		<script>
		$(document).ready(function(){
			$("#alert_link").on("click", createAlert);

			function createAlert() {
					alert("Returning to the Homepage!");
					return(true);
			}

		});
		</script>

	</head>
	<body>
		<div class="grid-x">
			<div id=navigation class="cell small-12 medium-12 large-12 text-right">
				<!-- Removed link from statement. -->
				<a id=alert_link href="">Homepage</a>
			</div>
		</div>

		<div class="grid-x">
			<div id=header class="cell small-12 medium-12 large-12">
				<p>Membership Access | Suggest a Game</p>
			</div>
		</div>

		<div class="grid-x">
			<div id=body class="cell small-12 medium-12 large-12 text-left">
				<p>Welcome to the membership page! Because you are a member, you have the exclusive ability
				to suggest and recommend a game you think I should highlight! These games will be put on a 
				priority list for me to research to see if they would fit at as a good game coming out in 2023.</p>

				<form onsubmit="return(InsertGame())">
					Video Game Title: <input type="text" id="title"><br>
					Developer Name: <input type="text" id="developer"><br>
					<input type="submit" value="Submit"><br>
				</form>

				<div id=showgame></div>
				<script>
					function InsertGame() {
						game_title_value = $("#title").val();
						game_developer = $("#developer").val();
						$.get("./membership_access_ajax.php", {"cmd": "create", "title" : game_title_value, "developer" : game_developer}, function(data) {
							$("#showgame").html(data);
						});
						return(false);
					}
					function DisplayGame() {
						$.get("./membership_access_ajax.php", {"cmd": ""}, function(data) {
							$("#showgame").html(data);
						});
						return(false);
					}
					DisplayGame();

				</script>
			</div>
		</div>

		<div class="grid-x footer">
			<div id=footer class="cell small-12 medium-12 large-12 text-left">
				<p>&copy; James Bui, All Rights Reserved</p>
			</div>
		</div>
	</body>
</html>
