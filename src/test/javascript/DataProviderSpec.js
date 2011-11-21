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
});