Laboratory.controller('loginController', ['$scope', '$http','$routeParams', '$location', function($scope,$http,$routeParams,$location){
	$scope.sendLogin = function($scope, $http){
    console.log("send");
    $http({
      method: "GET",
      url: "www.google.com",
      data: {
        username: $scope.usuario,
        password: $scope.contrasena
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
    .success(function(data){

    })
    .error(function(data, status){

    });


  };
}]);