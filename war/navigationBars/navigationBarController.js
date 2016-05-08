Laboratory.controller('navigationController', ['$scope', '$http', '$routeParams', '$location', function($scope,$http,$routeParams,$location){

	var reload = function(){
	      $http.get('/checkLoginServlet').success(function(data) {
	        
	        $scope.cleanObjectFromDatabase(data);
	 		
	          if (data == true){
	            $scope.loggedIn = true;
	            $scope.loggedOut = false;}
	          
	          else{
	            $scope.loggedIn = false;
	            $scope.loggedOut = true;
	            }
	          
	        })
	        .error(function(data) {
	          console.log('error: ' + data);
	        });}
	        
	        $scope.logout=function(){
	        	$http.delete("/api/login",{}).success(function(data,status){
	        		$location.path("/");
	        		   reload();
	        	});
	        }
	        
	        reload();

	    }]);
	