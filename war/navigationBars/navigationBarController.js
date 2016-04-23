Laboratory.controller('navigationController', ['$scope', '$http', '$routeParams', '$location', function($scope,$http,$routeParams,$location){

	      $http.get('/checkLoginServlet').success(function(data) {
	        
	        $scope.cleanObjectFromDatabase(data);
	 		console.log(data);
	 		console.log("a");
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
	        });
	        
	    }]);
	