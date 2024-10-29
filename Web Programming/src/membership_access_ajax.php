<?php

// Removed login credentials from statements.
define( 'DB_NAME', '');
define( 'DB_USER', '');
define( 'DB_PASSWORD', '');
define( 'DB_HOST', '');

$conn = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
// Check connection
if (!$conn) {
  die("Connection failed: " . mysqli_connect_error());
}

function InsertGameEntry($title, $developer) {
  global $conn;

  $insert = "INSERT INTO Game (title, developer) VALUES ('$title', '$developer') ";
  $result = $conn->query($insert);
}

function ShowGame() {
  global $conn;

  $sql = "SELECT id, title, developer FROM Game";
  $result = mysqli_query($conn, $sql);

  if (mysqli_num_rows($result) > 0) {
    // output data of each row
    while($row = mysqli_fetch_assoc($result)) {
      $id = $row["id"];
      echo "Video Game Title: " . $row["title"]. " - Developer: " . $row["developer"]. " <br> ";
    }
  } else {
    echo "0 results";
  }

}

$cmd = $_GET['cmd'];

if ($cmd == 'create') {
  InsertGameEntry($_GET['title'], $_GET['developer']);
}

ShowGame();

mysqli_close($conn);

?>
