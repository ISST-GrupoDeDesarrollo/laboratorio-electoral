Laboratory.controller('registerController', ['$scope', '$http','$routeParams', '$location', function($scope,$http,$routeParams,$location){
	//register controller code goes here
	
	$http.get("/api/register/geturl").success(function(data,status){
		$scope.registerUrl = data.url;
	});
}]);
