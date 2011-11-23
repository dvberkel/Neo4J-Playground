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

	describe("service",function(){
		it("should exist", function(){
			expect(DataProvider.service).toBeDefined();
		});
		
		it("should be instantiatable", function(){
			var provider = new DataProvider.service();
			expect(provider).toBeDefined();
		});
		
		it("should Be customizable", function(){
			var provider = new DataProvider.service().from('http://localhost:8080/collatz/collatz');
			expect(provider).toBeDefined();
		});

		describe("methods", function(){
			var provider = new DataProvider.service();
			describe("pathOf", function(){
				it("should exist", function(){
					expect(provider.pathOf).toBeDefined();
				});
				
				it("should return a list of the collatz path from n", function(){
					var expected = [[1, [1]], [2, [2, 1]], [3, [3, 10, 5, 16, 8, 4, 2, 1]], [4, [4, 2, 1]]];
					for (var index = 0; index < expected.length; index++) {
						var start = expected[index][0];
						var path = expected[index][1];
						expect(provider.pathOf(start)).toEqual(path);
					}
				}); 
			});
		});
	});
});