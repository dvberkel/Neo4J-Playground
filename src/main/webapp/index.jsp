<html>
	<head>
		<title>Effrafax &raquo; Collatz</title>
		<script src="js/jquery/jquery-1.7.1.min.js"></script>
		<script>
			var CollatzViewer = (function(){
				var Tagger = function(){
					this.idFor = function(name){
						return '#' + this.nameFor(name);
					};
					
					this.nameFor = function(name){
						return name;
					};
				};
				var _tagger = new Tagger();
				
				var Model = function(){
					var _observers = [];
					
					this.add = function(observer){
						_observers.push(observer);
						return this;
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
						var pathElement = $(_tagger.idFor('path')).empty();
						$(path).each(function(index, element){
							pathElement.append("<li>" + element + "</li>");
						});						
					}
					
					this.representation = function() {
						return "<div class='collatzPath'><ol id='" + _tagger.nameFor('path') + "'></ol></div>";
					}
				};
				
				var Controller = function() {
					var _model;
					
					this.of = function(model){
						_model = model;
						return this;
					};
					
					this.update = function(){
						var start = $(_tagger.idFor('start')).val(); start = start ? start : "1";
						_model.pathOf(start);						
					};
					
					this.representation = function(){
						return "<div class='collatzForm'>" + 
								"<input id='" + _tagger.nameFor('start') + "' value='37'/>" + 
								"<button id='" + _tagger.nameFor('showPath') + "'>ShowPath</button>" + 
							"</div>";
					};
				}
				
				return function(){
					var _element;
					
					this.on = function(id) {
						_element = $(id);
						return this;
					};
					
					this.create = function() {
						var view = new View();
						var controller = new Controller().of(new Model().add(view));
						_element.empty();
						_element.append(controller.representation());
						_element.append(view.representation());
						$("#showPath").click(function(){controller.update();});
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