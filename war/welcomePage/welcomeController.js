Laboratory.controller('welcomeController', ['$scope', '$http','$routeParams', '$location', function($scope,$http,$routeParams,$location){
	
	$scope.getStarted=function(){
		$location.path($scope.appUser?"/projects":"/login");
	}
}]);
