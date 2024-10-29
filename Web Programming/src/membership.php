<?php

// Removed login credentials from statements.
define( 'DB_NAME', '');
define( 'DB_USER', '');
define( 'DB_PASSWORD', '');
define( 'DB_HOST', '');

function CheckLogin($username, $password) {

	$conn = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
	// Check connection
	if (!$conn) {
	  die("Connection failed: " . mysqli_connect_error());
	}

	$sql = "SELECT username, password FROM Members WHERE username='$username' and password='$password'";
	$result = mysqli_query($conn, $sql);

	if (mysqli_num_rows($result) > 0) {
		setcookie('userid', "$username", time() + (86400 * 30), "/");
		header("Location: membership_access.php");
	}
	else {
		setcookie("userid", "", time() - 3600, '/');
		print("Error, the username and password are not correct.");
	}

	mysqli_close($conn);

}

?>

<!DOCTYPE html>
<!-- Web Programming Project
	 Description: This page acts as a landing page for members of the
	 Project website. It will require users to login using a cookie.-->
<html>
	<head>
		<script src="./jquery/jquery-3.6.3.min.js"></script>
        <link rel="stylesheet" href="./foundation/css/foundation.css">
        <title>Membership Page</title>
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
				<p>Membership</p>
			</div>
		</div>

		<div class="grid-x">
			<div id=body class="cell small-12 medium-12 large-12 text-left">
				<p>Welcome to the Membership page! To proceed, please login with the correct username and password.</p>
				<form method="post">
					Insert Username: <input type="text" name="username"><br>
					Insert Password: <input type="password" name="password"><br>
					<input type="submit" value="Submit">
				</form>
			</div>
		</div>

		<div class="grid-x footer">
			<div id=footer class="cell small-12 medium-12 large-12 text-left">
				<p>&copy; James Bui, All Rights Reserved</p>
			</div>
		</div>

	</body>
</html>

<?php

if ($_POST['username'] != '' && $_POST['password'] != '') {
	CheckLogin($_POST['username'], $_POST['password']);
}

?>
