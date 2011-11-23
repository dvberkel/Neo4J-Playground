describe("DataProvider", function(){
	it("should exist", function(){
		expect(DataProvider).toBeDefined();
	});
	
	describe("factory",function(){
		describe("mock", function(){
			it("should exist", function(){
				expect(DataProvider.mock).toBeDefined();
			});
			
			it("should be instantiatable()", function(){
				var provider = new DataProvider.mock();
				expect(provider).toBeDefined();
			});
		});
	});
	
	describe("methods of", function(){
		var provider = new DataProvider.mock();
		describe("mock", function(){
			describe("pathOf", function(){
				it("should exist", function(){
					expect(provider.pathOf).toBeDefined();
				});
			});
		});
	});
});