Laboratory.controller('projectsController', ['$scope', '$http','$routeParams', '$location', function($scope,$http,$routeParams,$location){
	$scope.showModal = false;
	$scope.toggleModal = function(){
		$scope.showModal = !$scope.showModal;
	};
	
	
	
}]);


Laboratory.directive('modal', function(){
	return{
		template: '/proyects/createProyectModal.html',
		restrict: 'E',
		transclude: true,
		replace: true,
		scope: true,
		link: function postLink(scope, element, attrs){
			$scope.title = attrs.title;
			
			$scope.$watch(attrs.visible, function(value){
				if(value == true){
					$(element).modal(show);
				}else{
					$(element).modal(hide);
				}
			});
			
			$(element).on('shown.bs.modal', function(){
				scope.$apply(function(){
					scope.$parent[attrs.visible] = true;
				});
			});
			
			$(element).on('hidden.bs.modal', function(){		
				scope.$apply(function(){
					scope.$parent[attrs.visible] = false;
				});
			});
		}
	}
});
