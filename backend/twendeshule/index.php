<?php
	$conn = mysqli_connect('localhost', 'twendeshule', 'tw3nd35hul3') or die("Could not establish database connection!\n");
	mysqli_select_db($conn, 'twendeshule');
	
	//Routing Parameter
	$fx = $_POST["fx"];
	
	//Routing Logic
	if($fx == "login"){
	
		$parent_phone = mysqli_real_escape_string($conn, $_POST["parent_phone"]);
		$parent_password = mysqli_real_escape_string($conn, $_POST["parent_password"]);	
		
		$data = array(
			"parent_phone" => $parent_phone,
			"parent_password" => $parent_password
		);
	 
		login($data, $conn);
	} else if($fx == "registerParent"){
	
		$parent_name = mysqli_real_escape_string($conn, $_POST["parent_name"]);
		$parent_email = mysqli_real_escape_string($conn, $_POST["parent_email"]);
		$parent_residence = mysqli_real_escape_string($conn, $_POST["parent_residence"]);
		$parent_phone = mysqli_real_escape_string($conn, $_POST["parent_phone"]);
		$parent_password = mysqli_real_escape_string($conn, $_POST["parent_password"]);
		
		$data = array(
			"parent_name" => $parent_name,
			"parent_email" => $parent_email,
			"parent_residence" => $parent_residence,
			"parent_phone" => $parent_phone,
			"parent_password" => $parent_password
		);

		registerParent($data, $conn);
	} else if ($fx == "registerCar"){
		$parent_id = mysqli_real_escape_string($conn, $_POST["parent_id"]);
		$car_model = mysqli_real_escape_string($conn, $_POST["car_model"]);
		$car_reg = mysqli_real_escape_string($conn, $_POST["car_reg"]);
		$car_capacity = mysqli_real_escape_string($conn, $_POST["car_capacity"]);
		
		$data = array(
			"owner" => $parent_id,
			"model" => $car_model,
			"reg" => $car_reg,
			"cap" => $car_capacity
		);
		
		registerCar($data, $conn);
	} else if($fx == "registerKid"){
	
		$kid_name = mysqli_real_escape_string($conn, $_POST["kid_name"]);
		$kid_gender = mysqli_real_escape_string($conn, $_POST["kid_gender"]);
		$kid_age = mysqli_real_escape_string($conn, $_POST["kid_age"]);
		$kid_sch = mysqli_real_escape_string($conn, $_POST["kid_sch"]);
		$kid_level = mysqli_real_escape_string($conn, $_POST["kid_level"]);
		
		$parent_id = mysqli_real_escape_string($conn, $_POST["parent_id"]);
		
		$data = array(
			"kid_name" => $kid_name,
			"kid_gender" => $kid_gender,
			"kid_age" => $kid_age,
			"kid_sch" => $kid_sch,
			"kid_level" => $kid_level,
			"parent_id" => $parent_id
		);
		
		registerKid($data, $conn);
		
	} else if($fx == "getSchools"){
	
		getSchools($conn);
		
	} else if($fx == "getGroups"){
		
		$group_type = mysqli_real_escape_string($conn, $_POST["group_type"]);
		$parent_id = mysqli_real_escape_string($conn, $_POST["parent_id"]);
		
		$data = array(
		    "group_type" => $group_type,
		    "parent_id" => $parent_id
		);
		
		getgroups($conn, $data);

	} else if($fx == "addGroup"){
	
		$grp_name = mysqli_real_escape_string($conn, $_POST["grp_name"]);
		$grp_desc = mysqli_real_escape_string($conn, $_POST["grp_desc"]);
		$grp_leader = mysqli_real_escape_string($conn, $_POST["grp_leader"]);
		
		
		$data = array(
			"grp_name" => $grp_name,
			"grp_desc" => $grp_desc,
			"grp_leader" => $grp_leader
		);

		addGroup($data, $conn);
	} else if($fx == "addEvent"){
	    $grp_id = mysqli_real_escape_string($conn, $_POST["grp_id"]);
	    $parent_id = mysqli_real_escape_string($conn, $_POST["parent_id"]);
	    $start_time = mysqli_real_escape_string($conn, $_POST["start_time"]);
	    $reminder = mysqli_real_escape_string($conn, $_POST["reminder"]);
		$num_days = mysqli_real_escape_string($conn, $_POST["num_days"]);
		$event_id = mysqli_real_escape_string($conn, $_POST["event_id"]);
		
		$data = array(
			"grp_id" => $grp_id,
			"parent_id" => $parent_id,
			"start_time" => $start_time,
			"reminder" => $reminder,
			"num_days" => $num_days,
			"event_id" => $event_id
		);
		
		addEvent($data, $conn);
		
	}//Functions 
	function login($data, $conn){
		
		$phone = $data["parent_phone"];
		$pass = $data["parent_password"];
		
		$query = "SELECT * FROM parent WHERE parent_phone = '{$phone}' AND parent_password = '{$pass}' LIMIT 1;";

		$result = mysqli_query($conn, $query);

		$num_rows = mysqli_num_rows($result);

		$success = false;

		$array = mysqli_fetch_assoc($result);

		if($num_rows > 0){
		
			$success = true;
			
		} else {
		
			$array = array();
			
		}

		$res = array(
			"success"=> $success,
			"data"=> $array
		);
		
		header('Content-Type: application/json');
		echo json_encode($res);
	} function registerParent($data, $conn){
		
		$parent_email = $data["parent_email"];
		$parent_phone = $data["parent_phone"];
		$parent_name = $data["parent_name"];
		$parent_residence = $data["parent_residence"];
		$parent_password = $data["parent_password"];
		
		
		$query = "SELECT * FROM parent WHERE parent_email = '{$parent_email}' OR parent_phone = '{$parent_phone}'";
		
		$result = mysqli_query($conn, $query);

		$num_rows = mysqli_num_rows($result);

		$success = false;

		$array = mysqli_fetch_assoc($result);
		
		$msg = "";
	
		if($num_rows > 0){
		
			$success = false;
			$msg = "Email OR phone number already in use.";
			
		} else {
		
			$query = "INSERT INTO parent(parent_name, parent_email, parent_password, parent_phone, parent_residence) VALUES('{$parent_name}', '{$parent_email}', '{$parent_password}', '{$parent_phone}', '{$parent_residence}')";
			
			$status = mysqli_query($conn, $query);
			
			if($status == true){
				$success = true;
				$msg = "Registration Successful.";
			} else {
				$msg = "Registration Failed. Please contact the administrator!";
				$success = false;
			}
		}
		
		$res = array(
			"success"=> $success,
			"parent_id"=> mysqli_insert_id($conn)
		);
		header('Content-Type: application/json');
		echo json_encode($res);
	} function registerCar($data, $conn){
		$model = $data["model"];
		$reg = $data["reg"];
		$cap = $data["cap"];
		$owner = $data["owner"];
		
		$query = "SELECT * FROM car WHERE car_reg = '{$reg}' AND car_owner = {$owner};";
		
		$result = mysqli_query($conn, $query);
		
		$num_rows = mysqli_num_rows($result);
		
		$success = false;
		
		if($num_rows == 0){
			//Prevent double registration
			$query = "INSERT INTO car(car_owner, car_model, car_reg, car_capacity) VALUES($owner, '{$model}', '{$reg}', $cap)";
			
			$status = mysqli_query($conn, $query);
			
			$success = false;
			$msg = "";
			
			if($status == true){
				$success = true;
				$msg = "Car Registration Successful.";
			} else {
				$msg = "Car Registration Failed. Please contact the administrator!";
				$success = false;
			}
		} else {
			$success = false;
			$msg = "That car is already registered with Twende Shule.";
		}
		$res = array(
			"success" => $success,
			"msg" => $msg
		);
		header('Content-Type: application/json');
		echo json_encode($res);
	} function registerKid($data, $conn){
		
		$kid_name = $data["kid_name"];
		$kid_gender = $data["kid_gender"];
		$kid_age = $data["kid_age"];
		$kid_sch = $data["kid_sch"];
		$kid_parent = $data["parent_id"];
		
		$sch_query = "SELECT * FROM school WHERE school_id = {$kid_sch} LIMIT 1;";
		$parent_query = "SELECT * FROM parent WHERE parent_id = {$kid_parent} LIMIT 1;";
		
		$school = $result = mysqli_query($conn, $sch_query);
		$parent = $result = mysqli_query($conn, $parent_query);
		
		if(mysqli_num_rows($school) > 0 AND mysqli_num_rows($parent) > 0){
			
			$query = "INSERT INTO kid(kid_name, kid_gender, kid_age, kid_sch, kid_parent) VALUES('{$kid_name}', '{$kid_gender}', {$kid_age}, {$kid_sch}, {$kid_parent});";
		
			$status = mysqli_query($conn, $query);
			
			if($status){
				
				$school_array = mysqli_fetch_assoc($school);
				
				$school_name = $school_array["school_name"];
				
				$res = array(
					"success" => true,
					"msg" => "Thank you for using Twende Shule.\n Your data has been submitted to Light {$school_name} for verification.\n Once they get back to us, we will let you know."
				);
				
				header('Content-Type: application/json');
				echo json_encode($res);
				
			} else {
				
				$res = array(
					"success" => false,
					"msg" => "The Kid record could not be created. Please try again!",
					"query" => $query
				);
				
				header('Content-Type: application/json');
				echo json_encode($res);
			}
			
		} else {
		
			$msg = "";
			if(mysqli_num_rows($school) < 1){
			
				$msg = "Its seems the school {$kid_name} attends has not authorised the use of Twende Shule.\n We will talk with them and let you know";
				
			} else if(mysqli_num_rows($school) < 1){
			
				$msg = "The record for {$kid_name}\'s parent could not be found. Please repeat registration. We are sorry for any inconvenience.";
				
			}
			
			$res = array(
				"success" => false,
				"msg" => $msg
			);
			
			header('Content-Type: application/json');
			echo json_encode($res);
			
		}
		
	} function getSchools($conn) {
		$query = "SELECT * FROM school;";
		
		$result = mysqli_query($conn, $query);
		
		$num_rows = mysqli_num_rows($result);
		
		if($num_rows > 0){
		
			$schools = array();
			
			while($row = mysqli_fetch_assoc($result)){
			  $schools[] = $row;
			}
			
			$res = array(
				"success" => true,
				"data" => $schools
			);
			
			header('Content-Type: application/json');
			echo json_encode($res);
			
		} else {
		
			$res = array(
				"success" => false,
				"data" => array()
			);
			
			header('Content-Type: application/json');
			echo json_encode($res);
		}
	} function addGroup($data, $conn){
		$grp_name = $data["grp_name"];
		$grp_desc = $data["grp_desc"];
		$grp_leader = $data["grp_leader"];

		$query = "SELECT * FROM grp WHERE grp_name = '{$grp_name}' AND grp_leader = {$grp_leader};";

		$result = mysqli_query($conn, $query);

		$num_rows = mysqli_num_rows($result);

		if($num_rows > 0){
			
			$res = array(
				"success" => false,
				"data" => array()
			);
			header('Content-Type: application/json');
			echo json_encode($res);
		} else {
			
			$query = "INSERT INTO grp(grp_name, grp_leader, grp_desc) VALUES('{$grp_name}', {$grp_leader}, '{$grp_desc}');";
			
			$status = mysqli_query($conn, $query);

			if($status){
				
				$query = "SELECT * FROM grp WHERE grp_name = '{$grp_name}' AND grp_leader = {$grp_leader} LIMIT 1;";
				$result = mysqli_query($conn, $query);
								
				$row = mysqli_fetch_assoc($result);

				$grp_id = $row["grp_id"];

				$query = "INSERT INTO members(grp_id, parent_id) VALUES({$grp_id}, {$grp_leader})";

				$status = mysqli_query($conn, $query);

				$res = array(
					"success" => true,
					"data" => $row
				);

				header('Content-Type: application/json');
				echo json_encode($res);

			} else {

				$res = array(
					"success" => false,
					"data" => array()
				);
				header('Content-Type: application/json');
				echo json_encode($res);
			}

		}
	} function getGroups($conn, $data){
	    $group_type = $data["group_type"];
	    $parent_id = $data["parent_id"];
	    
	    if($group_type = "all"){
	        $sql = "SELECT * FROM grp WHERE grp_leader = {$parent_id}";
	        
	        $result = mysqli_query($conn, $sql);
	        
	        $num_rows = mysqli_num_rows($result);
		
		    if($num_rows > 0){
		
			    $groups = array();
			
			    while($row = mysqli_fetch_assoc($result)){
			      $groups[] = $row;
			    }
			
			    $res = array(
				    "success" => true,
				    "data" => $groups
			    );
			
			    header('Content-Type: application/json');
			    echo json_encode($res);
			
		    } else {
		
			    $res = array(
				    "success" => false,
				    "data" => array()
			    );
			
			    header('Content-Type: application/json');
			    echo json_encode($res);
		    }
        }
	    
	    
	} function addEvent($data, $conn){
	    
	    $grp_id = $data["grp_id"];
	    $parent_id = $data["parent_id"];
	    $tart_time = $data["start_time"];
	    $reminder = $data["reminder"];
	    $num_days = $data["num_days"];
	    $event_id = $data["event_id"];
	    
	    $query = "INSERT INTO schedule (grp_id, parent_id, start_time, reminder, num_days, event_id) VALUES({$grp_id}, {$parent_id}, '{$start_time}', {$reminder}, {$num_days}, {$event_id});";
        
        $status = mysqli_queri($conn, $query);
        
        if($status){
            
            $query = "SELECT * FROM schedule WHERE event_id = {$event_id} AND parent_id = {$parent_id} AND grp_id = {$grp_id} LIMIT 1";
            
            $result = mysqli_qeuery($conn, $query);
            
            $num_rows = mysqli_num_rows($result);
		
		    if($num_rows > 0){
		        
		        $row = mysqli_fetch_assoc($result);
		        
		        $res = array(
				    "success" => true,
				    "data" => $row
			    );
			
			    header('Content-Type: application/json');
			    echo json_encode($res);

		    } else {

		    	$res = array(
		    		'success' => false,
		    		'msg' => "Something went wrong while trying to add your event to the remote database. Our engineers are already on site fixing the system."
		    	 );

		    	header('Content-Type: application/json');

		    	echo json_encode($res);
		    }
            
        } else {

        	$res = array(
	    		'success' => false,
	    		'msg' => "Something went wrong while trying to add your event to the remote database. Our engineers are already on site fixing the system."
		   	);
		    	
		    header('Content-Type: application/json');

		    echo json_encode($res);
        }
        	    
	}
?>
