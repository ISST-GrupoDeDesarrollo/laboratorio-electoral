Laboratory.controller('profileController', ['$scope', '$http', '$routeParams', '$location', '$uibModal', function($scope,$http,$routeParams,$location,$uibModal){
	
	$scope.enabled = true;
	$scope.changes = false;
	
	$scope.$on("checkAuth",function(){
		if(!$scope.appUser){
			$location.path("/");
		}
	});
	
	var getProfile = function(){
			$http.get("/api/profile").success(function(data){
				$scope.cleanObjectFromDatabase(data);
				$scope.user = data;
				console.log(data);

			$http.get("/api/workgroups").success(function(data,status){
				
				$scope.cleanObjectFromDatabase(data);
				$scope.numberWorkgroups = 0;
				$scope.numberProjects = 0;
				$scope.numberWorkgroupsOwn = 0;
				$scope.numberProjectsOwn = 0;
				console.log(data);
				
				if(data != null){
					
					$scope.workgroups = data;
					
					for (ind in $scope.workgroups ){
						$scope.numberWorkgroups = $scope.numberWorkgroups + 1;
						
						if($scope.workgroups[ind].creator == $scope.user.username){
							$scope.numberWorkgroupsOwn = $scope.numberWorkgroupsOwn + 1;
						}
						
						if($scope.workgroups[ind].projects != null){
							for (ind2 in $scope.workgroups[ind].projects ){
								$scope.numberProjects = $scope.numberProjects + 1;
								
								/*if($scope.workgroups[ind].projects[ind2] == $scope.user.username){
									$scope.numberWorkgroupsOwn = $scope.numberWorkgroupsOwn + 1;
								}*/	
							}
							}
					}
					
	               
				}
				
				$http.get("/api/messages").success(function(data,status){
					
					$scope.cleanObjectFromDatabase(data);
					$scope.numberMessages = 0;
					console.log(data);
					
					if(data != null){
				
						$scope.messages = data;
						
						for (ind in $scope.messages ){
							if($scope.messages[ind].creatorUsername == $scope.user.username){
									$scope.numberMessages = $scope.numberMessages + 1;

								}
								
							}
								}
					
					$http.get("/api/reports").success(function(data,status){
						
						$scope.cleanObjectFromDatabase(data);
						$scope.numberReports = 0;
						console.log(data);
						
						if(data != null){
							
							$scope.reports = data;
							
							for (ind in $scope.reports ){
									
										$scope.numberReports = $scope.numberReports + 1;
										
								}
									}
						
						$http.get("/api/simulations").success(function(data,status){
							
							$scope.cleanObjectFromDatabase(data);
							$scope.numberSimulations= 0;
							console.log(data);
							
							if(data != null){
								
								$scope.simulations = data;
							
								for (ind in $scope.simulations ){
									$scope.numberSimulations = $scope.numberSimulations + 1;
		
									}
								}		
						});
					});
				});
			});
			});
			};
			
	
	$scope.animationsEnabled = true;
	$scope.changeEmail = function(){
		
		var modalInstance = $uibModal.open({
			animation: $scope.animationsEnabled,
			templateUrl: 'emailModal.html',
			controller: 'emailModalController'
		});
		
		modalInstance.result.then(
				function(){
					$location.path("/projects");
				}
			);
	}
	
	
	$scope.changePass = function(){
		
		var modalInstance = $uibModal.open({
			animation: $scope.animationsEnabled,
			templateUrl: 'passModal.html',
			controller: 'passModalController'
		});
		
		modalInstance.result.then(
				function(){
					$location.path("/projects");
				}
			);
	}
	
	
	$scope.changeRol = function(){
		
		var modalInstance = $uibModal.open({
			animation: $scope.animationsEnabled,
			templateUrl: 'rolModal.html',
			controller: 'rolModalController'
		});
		
		modalInstance.result.then(
				function(){
					$location.path("/projects");
				}
			);
	}
	
	
	$scope.changeImage = function(input){
		if (input.files && input.files[0]) {
        	var reader = new FileReader();
        	reader.onload = function (e) {
            	$('#fot').attr('src', e.target.result);
        	}

        reader.readAsDataURL(input.files[0]);
        
        $scope.enabled = false;

    		}
		
		
 	};
	

	$scope.changeImg = function(){
		
	
		$http.get("/api/profile/geturl").success(function(data,status){
	            
			var formData = new FormData();
			
	        formData.append('img',$scope.files[0]);
	        formData.append('change',"img");
	        
	        $http.post(data.url,formData, {
	        	transformRequest: angular.identity,
	        	headers: {'Content-Type': undefined}
	        }).success(function(dataReturned){
	 			$scope.cleanObjectFromDatabase(dataReturned);
				alert("Image changed succesfully")
				$location.path("/projects");
	 		}).error(function(data, status){
	 			if(status == 401){
	 				alert("Denied access without session");
	 			}else if(status == 400){
	 				alert("Complete all the fields");
	 			}
	 		});
			
		});
	}
	
	
	$scope.change = function(){
		$scope.changes = !$scope.changes;
	}
	
	
	getProfile();

}]);




Laboratory.controller('emailModalController',['$scope', '$http','$routeParams', '$location', '$uibModal', '$uibModalInstance',
                                         function($scope,$http,$routeParams,$location,$uibModal, $uibModalInstance){

	
	$scope.ok = function (simulation) {
		$http({
			method: 'POST',
			url: '/api/profile',
			data:{email:$scope.newEmail,change:"email"},
			headers: {'Content-Type': 'application/json'}
		}).success(function(dataReturned){
			$scope.cleanObjectFromDatabase(dataReturned);
			$uibModalInstance.close(dataReturned);
			alert("Email changed succesfully")
		}).error(function(data, status){
			if(status === 401){
				alert("Denied access without session");
			}else if(status === 400){
				alert("Complete all the fields");
			}
		});
	};

	$scope.cancel = function () {
	   $uibModalInstance.dismiss("Canceled");
	};
}]);


Laboratory.controller('passModalController',['$scope', '$http','$routeParams', '$location', '$uibModal', '$uibModalInstance',
                                              function($scope,$http,$routeParams,$location,$uibModal, $uibModalInstance){

     	
     	$scope.ok = function (simulation) {
     		$http({
     			method: 'POST',
     			url: '/api/profile',
     			data:{oldPass:$scope.oldPass,newPass1:$scope.newPass1,newPass2:$scope.newPass2,change:"pass"},
     			headers: {'Content-Type': 'application/json'}
     		}).success(function(dataReturned){
     			$scope.cleanObjectFromDatabase(dataReturned);
     			$uibModalInstance.close(dataReturned);
     			alert("Password changed succesfully");
     		}).error(function(data, status){
     			if(status == 400){
     				alert("Complete all the fields");
     			}else if(status == 450){
     				alert("New passwords not equals");
     			}else if(status == 410){
     				alert("Old password wrong");
     			}
     		});
     	};

     	$scope.cancel = function () {
     	   $uibModalInstance.dismiss("Canceled");
     	};
     }]);

Laboratory.controller('rolModalController',['$scope', '$http','$routeParams', '$location', '$uibModal', '$uibModalInstance',
                                              function($scope,$http,$routeParams,$location,$uibModal, $uibModalInstance){

     	
     	$scope.ok = function (simulation) {
     		$http({
     			method: 'POST',
     			url: '/api/profile',
     			data:{rol:$scope.newRol,change:"rol"},
     			headers: {'Content-Type': 'application/json'}
     		}).success(function(dataReturned){
     			$scope.cleanObjectFromDatabase(dataReturned);
     			$uibModalInstance.close(dataReturned);
     			alert("Rol changed succesfully");
     		}).error(function(data, status){
     			if(status == 401){
     				alert("Denied access without session");
     			}else if(status == 400){
     				alert("Complete all the fields");
     			}
     		});
     	};

     	$scope.cancel = function () {
     	   $uibModalInstance.dismiss("Canceled");
     	};
     }]);