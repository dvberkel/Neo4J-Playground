describe("CollatzViewer", function(){
	it("should exist", function(){
		expect(CollatzViewer).toBeDefined();
	});
	
	describe("instantiation", function(){
		it("should be possible", function(){
			var viewer = new CollatzViewer();
			expect(viewer).toBeDefined();
		});
		
		describe("customization", function(){
			it("should accept an id", function(){
				var viewer = new CollatzViewer().on('#collatz');
				expect(viewer).toBeDefined();
			})
			
			it("should accept a DataProvider", function(){
				var viewer = new CollatzViewer().from(new DataProvider.mock());
				expect(viewer).toBeDefined();
			});
		});
	});
});