var CollatzViewer = (function(){
	return function(){
		var _elementId = '#collatz';
		var _provider = new DataProvider.mock();
		
		this.on = function(elementId){
			_elementId = elementId;
			return this;
		};
		
		this.from = function(provider){
			_provider = provider;
			return this;
		};
	};
})();