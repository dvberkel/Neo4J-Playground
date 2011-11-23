describe("DataProvider", function(){
	it("should exist", function(){
		expect(DataProvider).toBeDefined();
	});
	
	describe("mock",function(){
		it("should exist", function(){
			expect(DataProvider.mock).toBeDefined();
		});
		
		it("should be instantiatable()", function(){
			var provider = new DataProvider.mock();
			expect(provider).toBeDefined();
		});

		describe("methods", function(){
			var provider = new DataProvider.mock();
			describe("pathOf", function(){
				it("should exist", function(){
					expect(provider.pathOf).toBeDefined();
				});
				
				it("should be callable", function() {
					expect(provider.pathOf()).toEqual([]);
				});
				
				it("should return a list of decreasing positive integers from n", function(){
					var range = function(n) {
						var result = [];
						for (var i = n; i > 0; i--) {
							result.push(i);
						}
						return result;
					}
					for (var n = -1; n < 5; n++) {
						expect(provider.pathOf(n)).toEqual(range(n));
					}
				}); 
			});
		});
	});
});