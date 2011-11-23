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
		var _url = null;
		
		this.from = function(url) {
			_url = url;
			return this;
		}
		
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
		
		var path = function(n){
			var url = _url + "?pathOf=" + n;
			var request = new XMLHttpRequest();
			request.open("GET", url, false);
			request.setRequestHeader('Accept', 'application/json');
			request.send();
			console.log(url);
			console.log(request.responseText);
			
		};
		
		this.pathOf = function(n){
			if (_url === null) {
				return localPath(n);
			} else {
				return path(n);
			}
		};
	};
	
	return {
		mock : Mock,
		service : Service
	};
})();