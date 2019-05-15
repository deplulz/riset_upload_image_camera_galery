<?php
 if($_SERVER['REQUEST_METHOD']=='POST'){
  		include_once("koneksi.php");
  	  	  
        $originalImgName= $_FILES['filename']['name'];
        $tempName= $_FILES['filename']['tmp_name'];
        $folder="img/";
        $url = "http://192.168.100.144/multipart_api/img/".$originalImgName;
        
        if(move_uploaded_file($tempName,$folder.$originalImgName)){
                $results = mysqli_query($connect,"INSERT INTO img (file) VALUES ('$url')");
				if($results){
                	 $query= "SELECT * FROM img WHERE file='$url'";
	                 $result= mysqli_query($connect, $query);
	                 $emparray = array();
	                     if(mysqli_num_rows($result) > 0){  
	                     while ($row = mysqli_fetch_assoc($result)) {
                                     $emparray[] = $row;
                                   }
                                   echo json_encode(array( "status" => "true","message" => "Successfully file added!" , "data" => $emparray) ); 
	                     } else {
	                     		echo json_encode(array( "status" => "false","message" => "Failed!") );
	                     }
			   
                }else{
                	echo json_encode(array( "status" => "false","message" => "Failed!") );
                }
        	//echo "moved to ".$url;
        }else{
        	echo json_encode(array( "status" => "false","message" => "Failed!") );
        }
  }
?>