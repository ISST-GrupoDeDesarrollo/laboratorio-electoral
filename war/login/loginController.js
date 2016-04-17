Laboratory.controller('loginController', ['$scope', '$http','$routeParams', '$location', function($scope,$http,$routeParams,$location){
	var user = $scope.usuario;
	var contr = $scope.contrasena;
	
	/*  ADD WHEN LOGIN-BACKEND BE FINISHED
	 var direccion = $http.get("/api/login/geturl").success(function(data,status){
			$scope.registerUrl = data.url;
		});
		
	*/
	
	$scope.sendLogin = function(){
    console.log("send");
    return $http({
      method: 'GET',
      url: '',
      data: {
        username: user,
        password: contr
      }/*,
      transformRequest: function(data, headersGetter){
        var formData = new FormData();
        angular.forEach(data, function(value, key){
          formData.append(key,value);
        });
        var headers = headersGetter();
        delete headers['Content-Type'];
        return formData;
      }*/
    })
    .success(function(response){
   	
    })
    .error(function(response, status){

    });


  };
}]);