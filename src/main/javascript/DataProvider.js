var DataProvider = (function(){
	var Mock = function() {
		var range = function(n) {
			var result = [];
			for (var i = n; i > 0; i--) {
				result.push(i);
			}
			return result;
		}

		this.pathOf = function(n){
			return range(n);
		};
	}
	
	return {
		mock : Mock
	};
})();