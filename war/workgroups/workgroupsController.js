Laboratory.controller('workgroupsController', ['$scope', '$http', '$routeParams', '$location', '$uibModal', function($scope,$http,$routeParams,$location,$uibModal){
		
	$scope.$on("checkAuth",function(){
		if(!$scope.appUser){
			$location.path("/");
		}
	});
	
	var reloadWorkgroups = function(){
			$http.get("/api/workgroups").success(function(data){
				$scope.cleanObjectFromDatabase(data);
				for (var i = data.length - 1; i >= 0; i--) {
					if($scope.selectedWorkgroup && $scope.selectedWorkgroup.id===data[i].id){
						$scope.selectedWorkgroup = data[i];
					}
					if(data[i].isPersonal){
						data.splice(i,1);
					}
				}
				$scope.workgroups = data;
				if(!$scope.selectedWorkgroup && data.length>0){
					$scope.selectedWorkgroup = data[0];
				}
				console.log(data);
				reloadMembersPermissions();
			});
	};
	
	
	
	$scope.permissionsOptions = ["Create simulation","Delete other member simulation",
	                             "Add message","Delete other member messages","Add new member","Delete member"];
	
	var reloadMembersPermissions = function(){
		$scope.permissionsSelected = {};
		$scope.permissionsMembers = [];
		
		var workgroup = $scope.selectedWorkgroup;
		if($scope.selectedWorkgroup != null)
			$scope.permissionsMembers = workgroup.permissions;
		
		for (i in workgroup.members){
			var usernameMember = workgroup.members[i].username;
			$scope.permissionsSelected[usernameMember] = [];
				
				if ($scope.permissionsMembers[usernameMember].createSimulation){
					$scope.permissionsSelected[usernameMember].push($scope.permissionsOptions[0]);
				}
				if ($scope.permissionsMembers[usernameMember].deleteSimulations){
					$scope.permissionsSelected[usernameMember].push($scope.permissionsOptions[1]);
				}
				if ($scope.permissionsMembers[usernameMember].addMessage){
					$scope.permissionsSelected[usernameMember].push($scope.permissionsOptions[2]);
				}
				if ($scope.permissionsMembers[usernameMember].deleteMessage){
					$scope.permissionsSelected[usernameMember].push($scope.permissionsOptions[3]);
				}
				if ($scope.permissionsMembers[usernameMember].addMember){
					$scope.permissionsSelected[usernameMember].push($scope.permissionsOptions[4]);
				}
				if ($scope.permissionsMembers[usernameMember].deleteMember){
					$scope.permissionsSelected[usernameMember].push($scope.permissionsOptions[5]);
				}
		} 
	}
	
	$scope.newPermission = function(member){
		$scope.permissionsUpdated = {
				createSimulation : false,
				deleteSimulation : false,
				addMessage : false,
				deleteMessege : false,
				addMember : false,
				deleteMember : false
		}; 
		
			if ($scope.permissionsSelected[member.username].indexOf("Create simulation")>(-1)){
				$scope.permissionsUpdated.createSimulation = true;
			}
			if ($scope.permissionsSelected[member.username].indexOf("Delete other member simulation")>(-1)){
				$scope.permissionsUpdated.deleteSimulations = true;
			}
			if ($scope.permissionsSelected[member.username].indexOf("Add message")>(-1)){
				$scope.permissionsUpdated.addMessage = true;
			}
			if ($scope.permissionsSelected[member.username].indexOf("Delete other member messages")>(-1)){
				$scope.permissionsUpdated.deleteMessage = true;
			}
			if ($scope.permissionsSelected[member.username].indexOf("Add new member")>(-1)){
				$scope.permissionsUpdated.addMember = true;
			}
			if ($scope.permissionsSelected[member.username].indexOf("Delete member")>(-1)){
				$scope.permissionsUpdated.deleteMember = true;
			}
		
		$scope.selectedWorkgroup.permissions[member.username] = $scope.permissionsUpdated;
		var workgroup = $scope.selectedWorkgroup;
		$http({
			method: 'PUT',
			url: '/api/workgroups',
			data: workgroup,
			params: {newPermission:member.username} 
		}).success(function(data){
			console.log("exito al guardar permisos");
			reloadWorkgroups();
		}).error(function(){
			console.log("error al guardar permisos");
		});
	}
	
	
	$scope.addWorkgroup = function(){
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: 'createWorkgroupModal.html',
			controller: 'createWorkgroupController'
		});
		
		modalInstance.result.then(
			function(){
				reloadWorkgroups()
			}, function(){
				console.log("cancelado");
			}
		);
	}
	
	$scope.addMember = function(workgroup){
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: 'addMemberModal.html',
			controller: 'addMemberController',
			resolve:{
				workgroup:function(){return workgroup;}
			}
		});
		
		modalInstance.result.then(
			function(){
				reloadWorkgroups()
			}, function(){
				console.log("cancelado");
			}
		);
	}

	$scope.removeUser = function(workgroup,user){
		$http.delete("/api/workgroups",{params:{"removeUser":user.username,"workgroup":workgroup.id}}).success(function(data,status){
			reloadWorkgroups();
		});
	}

	reloadWorkgroups();

}]);


Laboratory.controller('createWorkgroupController',['$scope', '$http','$routeParams', '$location', '$uibModal', '$uibModalInstance',
                                         function($scope,$http,$routeParams,$location,$uibModal, $uibModalInstance){
	
	$scope.ok = function () {
		$http.post("/api/workgroups",$scope.workgroup).success(function(data){		
			$uibModalInstance.close();
		});
			
	};

	$scope.cancel = function () {
	   $uibModalInstance.dismiss("Canceled");
	};
	
}]);

Laboratory.controller('addMemberController',['$scope', '$http','$routeParams', '$location', '$uibModal', '$uibModalInstance','workgroup',
                                                   function($scope,$http,$routeParams,$location,$uibModal, $uibModalInstance,workgroup){
	$scope.workgroup = workgroup;
  	$scope.ok = function () {
  		$http.put("/api/workgroups",$scope.workgroup,{params:{addUser:$scope.username}}).success(function(data){		
  			$uibModalInstance.close();
  		}).error(function(data,status){
  			switch(status){
  			case 400:
  				alert("The user doesn't exist");
  				break;
  			}
  		});
  	};

  	$scope.cancel = function () {
  	   $uibModalInstance.dismiss("Canceled");
  	};
  	
  }]);
