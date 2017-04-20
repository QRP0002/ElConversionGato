 <?php
  require('config.inc.php');
 /* Connect to MySQL and select the database. */
  $connection = mysqli_connect(DB_SERVER, DB_USERNAME, DB_PASSWORD, DB_DATABASE);

  if (mysqli_connect_errno()) echo "Failed to connect to MySQL: " . mysqli_connect_error();

  $dwatabase = mysqli_select_db($connection, DB_DATABASE);

  if(!empty($_POST)) {
    getTableData($connection, $_POST['action']);
  } else {
    ?>
      <form action="Test.php" method="post">
          Username: <br>
          <input type="text" name="action"/>
          <br><br>
          <input type="submit" value="Submit" />
      </form>
    <?php
  }

  function getTableData($connection, $action) {
  header("Content-Type: application/json;charset=utf-8");

  if($action === 'getConvertData' || $action === 'countConvert') {
    $query = "SELECT * FROM conversion_information";
  } else {
    $query = "SELECT * FROM conversion_types";
  }

  if($result = mysqli_query($connection, $query)) {
    if($action === 'getConvertData' || $action === 'countConvert') {
      if($action === 'countConvert') {
        $response = array(
          'data' => array (
            'success' => 1,
            'count'   => $result->num_rows
          )
	);
       } else {
            $posts = array();
          while ($row = $result->fetch_row()) {
            $post = array();
            $post["convert_id"]      = $row[0];
            $post["convert_type"]    = $row[1];
            $post["convert_from"]    = $row[2];
            $post["convert_to"]      = $row[3];
            $post["convert_formula"] = $row[4];
            $post["convert_value"]   = $row[5];
            array_push($posts, $post);
          }
          $response = array(
            'data' => array (
              'success' => 1,
              'items' => $posts
            )
          );
       }
    } elseif ($action === 'getTypeData' || $action === 'countType') {
      if($action === 'countType') {
        $response = array(
          'data' => array (
            'success' => 1,
            'count'   => $result->num_rows
          )
	);
       } else {
          $posts = array();
          while ($row = $result->fetch_row()) {
            $post = array();
            $post["conversion_type_id"] = $row[0];
            $post["conversion_name"]    = $row[1];
            array_push($posts, $post);
          }
          $response = array(
            'data' => array (
              'success' => 1,
              'items' => $posts
            )
          );
       }
    }
  }

  $result->close();
  $connection->close();
  echo die(json_encode($response));
}