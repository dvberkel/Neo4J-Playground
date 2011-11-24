<html>
	<head>
		<title>Effrafax &raquo; Collatz</title>
		<script src="js/jquery/jquery-1.7.1.min.js"></script>
		<script>
			var CollatzViewer = (function(){
				var Model = function(){
					this.pathOf = function(start) {
						var url = "collatz?pathOf=" + start;
						var request = new XMLHttpRequest();
						request.open("GET", url, false);
						request.send();
						var json = $.parseJSON(request.responseText);
						return json.path;
					}
				};
				
				var View = function() {
					this.update = function(path){
						var pathElement = $("#path").empty();
						$(path).each(function(index, element){
							pathElement.append("<li>" + element + "</li>");
						});						
					}
				};
				
				return function(){
					var _model = new Model();
					var _view = new View();
					var _element;
					
					this.on = function(id) {
						_element = $(id);
						return this;
					};
					
					this.create = function() {
						_element.empty();
						_element.append("<div class='collatzForm'><input id='start' value='37'/><button id='showPath'>ShowPath</button></div>");
						_element.append("<div class='collatzPath'><ol id='path'></ol></div>");
						initialize();
					};
					
					var initialize = function(){
						$("#showPath").click(function(){
							var start = $("#start").val(); start = start ? start : "1";
							var path = _model.pathOf(start);
							_view.update(path);
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