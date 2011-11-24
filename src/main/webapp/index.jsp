<html>
	<head>
		<title>Effrafax &raquo; Collatz</title>
		<script src="js/jquery/jquery-1.7.1.min.js"></script>
		<script>
			var CollatzViewer = (function(){
				var Model = function(){
					var _observers = [];
					
					this.add = function(observer){
						_observers.push(observer);
					};
					
					this.pathOf = function(start) {
						var url = "collatz?pathOf=" + start;
						var request = new XMLHttpRequest();
						request.open("GET", url, false);
						request.send();
						var json = $.parseJSON(request.responseText);
						notify(json.path);
					};
					
					var notify = function(path) {
						for (var index = 0; index < _observers.length; index++) {
							_observers[index].update(path);
						}
					};
				};
				
				var View = function() {
					this.update = function(path){
						var pathElement = $("#path").empty();
						$(path).each(function(index, element){
							pathElement.append("<li>" + element + "</li>");
						});						
					}
				};
				
				var Controller = function() {
					var _model;
					
					this.of = function(model){
						_model = model;
						return this;
					};
					
					this.update = function(){
						var start = $("#start").val(); start = start ? start : "1";
						_model.pathOf(start);						
					};
				}
				
				return function(){
					var _model = new Model();
					var _view = new View();
					var _controller = new Controller();
					var _element;
					
					this.on = function(id) {
						_element = $(id);
						return this;
					};
					
					this.create = function() {
						_model.add(_view);
						_controller.of(_model);
						_element.empty();
						_element.append("<div class='collatzForm'><input id='start' value='37'/><button id='showPath'>ShowPath</button></div>");
						_element.append("<div class='collatzPath'><ol id='path'></ol></div>");
						initialize();
					};
					
					var initialize = function(){
						$("#showPath").click(function(){
							_controller.update();
						});
					};
				}
			})();
		</script>
		<script>
			$(function(){
				var viewer = new CollatzViewer().on("#collatzViewer");
				viewer.create();
			});
		</script>
	</head>
	<body>
		<h1>Collatz Webservice</h1>
		<div id="collatzViewer"></div>
	</body>
</html>