Laboratory.controller('navigationController', ['$scope', '$http', '$routeParams', '$location', function($scope,$http,$routeParams,$location){

	$scope.$watch("appUser",function(){
		if(	$scope.appUser){ 
			$scope.loggedIn=true;
			$scope.loggedOut = false; 
		}else{
			$scope.loggedIn=false; 
			$scope.loggedOut = true; 
		}
	});

		$scope.logout=function(){
			$http.delete("/api/login",{}).success(function(data,status){
				$location.path("/");
				$scope.$emit("loginChanged");
			});
		}

	}]);
