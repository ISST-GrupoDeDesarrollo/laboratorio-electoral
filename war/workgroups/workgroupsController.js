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
			});
	};

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
