Laboratory.controller('resultController', ['$scope', '$http','$routeParams', '$location', function($scope,$http,$routeParams,$location){

	$scope.simulation={
			id:$routeParams.reportId,
			name:"Resultados",
			Report:[]
	};

	var reloadResult = function(){
		$http.get("/api/reports",{params:{id:$routeParams.resultId}}).success(function(data,status){
			$scope.cleanObjectFromDatabase(data);
			$scope.result = data;
			console.log($scope.result);
		});
	};


	reloadResult();
	
	/**
	 * Simple highcharts-ng maps example.
	 */
	Highcharts.chart('map', {

	    xAxis: {
	        categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 
	            'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
	    },

	    series: [{
	        data: [29.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4]
	    }]
	});

}]);
