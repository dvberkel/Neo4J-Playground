var DataProvider = (function(){
	var Mock = function() {
		var range = function(n) {
			var result = [];
			for (var i = n; i > 0; i--) {
				result.push(i);
			}
			return result;
		};

		this.pathOf = function(n){
			return range(n);
		};
	}
	
	var Service = function(){
		var collatz = function(n) {
			if (n % 2 == 0) {
				return n / 2;
			} else {
				return 3*n + 1;
			}
		};
		
		var localPath = function(n){
			var result = [n];
			while (n != 1) {
				n = collatz(n);
				result.push(n);
			}
			return result;
		};
		
		this.pathOf = function(n){
			return localPath(n);
		};
	};
	
	return {
		mock : Mock,
		service : Service
	};
})();