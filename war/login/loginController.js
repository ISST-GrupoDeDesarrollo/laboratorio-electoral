angular.module('Laboratory', []).controller('loginController', ['$scope', '$http', function($scope,$http){
  console.log("1");

  $scope.sendLogin = ['$scope', "$http" , function($scope, $http){
    console.log("2");

    $http({
      method: "GET",
      url: "adress",
      data: {
        username: $scope.usuario,
        password: $scope.contraseña
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


  }];
}]);