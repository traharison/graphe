<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML>
<html>
  <head>
    <style>
      body {
        margin: 0px;
        padding: 0px;
      }
    </style>
    <title>Graphe</title>
    <link href="<c:url value="/res/css/bootstrap.css"/>" rel="stylesheet">
  </head>
  <body>
	<div class="container">
		<div class="row" style="margin-left:150px;">
			<canvas id="myCanvas" width="750" height="400" style="background-color : grey;"></canvas>
		</div>
		<div class="row" >	
			<div class="col-md-2 col-md-offset-1" style="margin-left:150px;">
				<h4>Tracer: </h4>
				<form class="form-inline">
					<div class="form-group">
						<input type="radio" class="form-control" name="action" id="noeud" value="noeud" checked> Noeuds
						<input type="radio" class="form-control" name="action" id="arcs" value="arcs"> Arcs
					</div>
					<!-- <input type="file" id="FileUpload" onchange="selectFolder(event);" webkitdirectory mozdirectory msdirectory odirectory directory/> -->
				</form>
			</div>
			<div class="col-md-7" >
				<h4>Supprimer:</h4>
				<form class="form-inline">
					<div class="form-group"><input type="text" class="form-control" id="arctodel" value="0"><a id="delarc" class="btn btn-default">Supprimer Arc</a></div>
					<div class="form-group"><input type="text" class="form-control" id="noeudtodel" value="0"><a id="delnoeud" class="btn btn-default">Supprimer Noeud</a></div>
				</form>
				
			</div>
			
		</div>
		<div class="row" >
			<div class="col-md-7 " >
				<h4>Chemin min entre :</h4>
				<form class="form-inline">
					<div class="form-group">
						<input type="text" class="form-control" id="originway" value="0">
						<input type="text" class="form-control" id="destinationway" value="0">
						<a id="minway" class="btn btn-default">Chemin min</a></div>
				</form>
			</div>
		</div>
		<div class="row" >
			<div class="col-md-10 col-md-offset-1" >
			<hr style="width: 100%; color: grey; height: 1px; background-color:black;" />
				<div class="col-md-2">
					<a id="adjacence" class="btn btn-default ">Mat d'adjacence</a>
				</div>
				<div class="col-md-2">
					<a id="incidence" class="btn btn-default ">Mat d'incidence</a>
				</div>
				<div class="col-md-2">
					<a id="cout" class="btn btn-default ">Mat de cout</a>
				</div>
				<div class="col-md-2">
					<a id="coloration" class="btn btn-default ">Coloration</a>
				</div>
				<div class="col-md-2">
					<a class="btn btn-default " onclick="refreshGraphe();" >Rafraichir le graphe</a>
				</div>
				<div class="col-md-2">
					<a href="<c:url value="clear.html"/>" class="btn btn-default ">Effacer le graphe</a>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-8" style="margin-left:150px;">
				<h4 id="lib"></h4>
				<table id="table" class="table">
			  	</table>
			</div>
		</div>
		
		
	</div>
	<script src="<c:url value="/res/js/jquery-2.1.0.min.js"/>" ></script>
	<script src="<c:url value="/res/js/bootstrap.min.js"/>" type="text/javascript"></script>
	<script type="text/javascript">
	/*function selectFolder(e) {
		var theFiles = e.target.files;
		console.log(theFiles);
		var relativePath = theFiles[0].webkitRelativePath;
		console.log(relativePath);
		var folder = relativePath.split("/");
		console.log(folder[0]);
	}*/
	</script>
    <script>
    
    var elem = document.getElementById('myCanvas'),
    elemLeft = elem.offsetLeft,
    elemTop = elem.offsetTop,
    context = elem.getContext('2d'),
    elements = [],
	radius = 30,
	nb = 1,
	nba = 1,
	arcs = [],
	temp = [];
	$(document).ready(function() {
		refreshGraphe();
	});
    
    function refreshNoeud()
    {
    	$.ajax({
    		type: "GET",
    		async : false,
    		url:  '<c:url value="/rest/noeud"/>',
    		success : function(data){
				elements = data;
				//nb = (elements.length == 0 ) ? 1 : elements[elements.length-1].number+1;
				if(elements.length == 0){
					nb = 1;
				}else{
					var temp_max = 0;
					for(var i=0;i<elements.length;i++){
						if(elements[i].number > temp_max){
							temp_max = elements[i].number;
						}
					}
					nb = temp_max+1;
				}
				//console.log("length "+elements.length+" nb "+nb);
				elements.forEach(function(element) {
					context.beginPath();
					context.arc(element.x, element.y, radius, 0, 2 * Math.PI, false);
					context.fillStyle = 'white';
					context.fill();
					context.lineWidth = 2;
					context.strokeStyle = 'black';
					context.font = '20pt Calibri';
					context.fillStyle = 'black';
					context.textAlign = 'center';
					context.fillText(element.number, element.x, element.y+4);
					context.stroke();
				});
    		}
    	});
    };
    function refreshGraphe()
	{
    	context.clearRect ( 0 , 0 , 750, 400 );
    	refreshNoeud();
		$.ajax({
    		type: "GET",
    		async : false,
    		url:  '<c:url value="/rest/arc"/>',
    		success : function(data){
    			arcs = data;
    			//nba = (arcs.length == 0 ) ? 1 : arcs[arcs.length-1].number+1;
    			if(arcs.length == 0){
					nba = 1;
				}else{
					var temp_max = 0;
					for(var i=0;i<arcs.length;i++){
						if(arcs[i].number > temp_max){
							temp_max = arcs[i].number;
						}
					}
					nba = temp_max+1;
				}
    			arcs.forEach(function(arc) {
    				drawLineArrow(arc.originex, arc.originey,arc.destinationx, arc.destinationy,arc.couleur,arc.capacite,arc.number);
    			});
    		}
    	});
	};
	
	var arrow = [
		[ 2, 0 ],
		[ -10, -4 ],
		[ -10, 4]
	];
	function rotateShape(shape,ang) {
		var rv = [];
		for(p in shape)
			rv.push(rotatePoint(ang,shape[p][0],shape[p][1]));
		return rv;
	};
	function rotatePoint(ang,x,y) {
		return [
			(x * Math.cos(ang)) - (y * Math.sin(ang)),
			(x * Math.sin(ang)) + (y * Math.cos(ang))
		];
	};
	function translateShape(shape,x,y) {
		var rv = [];
		for(p in shape)
			rv.push([ shape[p][0] + x, shape[p][1] + y ]);
		return rv;
	};
	
	function drawFilledPolygon(shape) {
		var canvas = document.getElementById('myCanvas');
		var ctx = canvas.getContext('2d');
		ctx.beginPath();
		ctx.moveTo(shape[0][0],shape[0][1]);

		for(p in shape)
			if (p > 0) {
				ctx.lineTo(shape[p][0],shape[p][1]);
				ctx.fillStyle = 'black';
				ctx.fill();
			}

		ctx.lineTo(shape[0][0],shape[0][1]);	
		ctx.strokeStyle = 'black';
		ctx.stroke();
	};
	
	function drawLineArrow(x1,y1,x2,y2,couleur,capacite,number) {
		var canvas = document.getElementById('myCanvas');
		var ctx = canvas.getContext('2d');
		ctx.beginPath();
		ctx.moveTo(x1,y1);
		ctx.lineTo(x2,y2);
		ctx.strokeStyle = couleur;
		/*ctx.fillStyle = "black";
		context.font = '12pt Calibri';
		ctx.textAlign = "right";
		ctx.fillText("("+number+") "+capacite, ((x1+x2)/2)-3,((y1+y2)/2)-8);*/
		
		ctx.stroke();
		var ang = Math.atan2(y2-y1,x2-x1);
		drawFilledPolygon(translateShape(rotateShape(arrow,ang),x2,y2));
		/* Capacite */
		ctx.arc(((x1+x2)/2), ((y1+y2)/2), 17, 0, 2 * Math.PI, false);
		context.fillStyle = 'grey';
		ctx.fill();
		ctx.lineWidth = 2;
		ctx.strokeStyle = 'grey';
		ctx.font = '12pt Calibri';
		ctx.fillStyle = 'black';
		ctx.textAlign = 'center';
		ctx.fillText("("+number+") "+capacite, ((x1+x2)/2), ((y1+y2)/2)+4);
		/* End capacite */
	};
	
	function getAction()
	{
		if(document.getElementById('noeud').checked)
		{
			return "noeud";
		}
		else if(document.getElementById('arcs').checked){
			return "arc";
		}
	}
	
	
	elem.addEventListener('click', function(event) {
		var action = getAction();
		
		var test = false;
		var x = event.pageX - elemLeft;
        y = event.pageY - elemTop;
		if(action == "noeud")
		{
			temp = [];
			// Collision detection between clicked offset and element.
			var collision = new Array();
			elements.forEach(function(element) {
				var dx = x - element.x;
				var dy = y - element.y;
				var distance = Math.sqrt(dx * dx + dy * dy);
				if( distance < radius + radius)
				{
					collision.push(element.number);
					test = true;
				}
			});
			if(test)
			{
				alert('Emplacement non autoriser, cause collision avec le(s) noeud(s) :'+collision);
			}
			
			if(nb<=15 && test==false)
			{
				//var canvas = document.getElementById('myCanvas');
				//var context = canvas.getContext('2d');
				var centerX = x;//canvas.width / 2;
				var centerY = y;//canvas.height / 2;
				var couleur = 'white';
				$.ajax({
		    		type: "POST",
		    		async : false,
		    		url:  '<c:url value="/rest/noeud/add"/>',
		    		data:  {'number' : nb,'x':centerX,'y':centerY,'couleur':couleur},
		    		success : function(data){
		    			//console.log(data);
		    			context.beginPath();
						context.arc(centerX, centerY, radius, 0, 2 * Math.PI, false);
						context.fillStyle = 'white';
						context.fill();
						context.lineWidth = 2;
						context.strokeStyle = 'black';
						context.font = '20pt Calibri';
						context.fillStyle = 'black';
						context.textAlign = 'center';
						context.fillText(nb, centerX, centerY+4);
						context.stroke();
						elements = data;
						nb++;
		    		}
		    	});
			}
			
		}
		else if(action =="arc")
		{
			var collision = new Array();
			elements.forEach(function(element) {
				var dx = x - element.x;
				var dy = y - element.y;
				var distance = Math.sqrt(dx * dx + dy * dy);
				if( distance < radius + radius)
				{
					collision.push(element.number);
					test = true;
				}
			});
			if(test==false)
					alert('Aucun noeud a cet emplacement');
			if(collision.length == 1)
			{
				temp.push(elements[collision[0]-1]);
			}
			if(temp.length == 2)
			{
				var couleurarc = 'black';
				var originX = 0;
				var originY = 0;
				var destinationX = 0;
				var destinationY = 0;
				var deltaY = temp[1].y - temp[0].y;
				var deltaX = temp[1].x - temp[0].x;
				var distance = Math.sqrt((deltaX*deltaX)+(deltaY*deltaY));
				var vectX = deltaX / distance;
				var vectY = deltaY / distance;
				originX = temp[0].x + radius*vectX;
				originY = temp[0].y + radius*vectY;
				destinationX = temp[1].x - radius*vectX;
				destinationY = temp[1].y - radius*vectY;
				$.ajax({
		    		type: "POST",
		    		async : false,
		    		url:  '<c:url value="/rest/arc/add"/>',
		    		data:  {'number' : nba,'origine':temp[0].number,'destination':temp[1].number,'originex':originX,'originey':originY,'destinationx':destinationX,'destinationy':destinationY,'couleur':couleurarc},
		    		success : function(data){
		    			//console.log(data);
		    			drawLineArrow(originX, originY,destinationX, destinationY,couleurarc,0,nba);
						temp = [];
						arcs = data;
						nba++;
		    		}
		    	});
				
			}
		}

		

	}, false);
	
	$( "#adjacence" ).click(function() {
		$("#table").children().remove();
		$('#lib').html("Matrice d'adjacence");
		$.ajax({
    		type: "GET",
    		async : false,
    		url:  '<c:url value="/rest/matrice/adjacence"/>',
    		success : function(data){
    			console.log(data.length);
    			var thead = "<thead><tr><th>#</th>";
    			for(var i=0;i<data.length;i++)
   				{
    				thead += "<th>"+(i+1)+"</th>";
   				}
    			var theadfin = "</tr></thead>";
    			thead +=theadfin;
    			console.log(thead);
    			var tbody = "<tbody>";
    			var tr = "";
    			for(var i=0;i<data.length;i++)
   				{
    				tr = "<tr><th>"+(i+1)+"</th>";
    				for(var j=0;j<data.length;j++)
    				{
    					tr += "<td>"+data[i][j]+"</td>";
   					}
    				tr += "</tr>";
    				tbody +=tr;
   				}
    			tbody +="</tbody>";
    			var trHTML = thead+tbody;
    			console.log(trHTML);
    			$('#table').append(trHTML);
    		}
    	});
	});
	
	$( "#incidence" ).click(function() {
		$("#table").children().remove();
		$('#lib').html("Matrice d'incidence");
		$.ajax({
    		type: "GET",
    		async : false,
    		url:  '<c:url value="/rest/matrice/incidence"/>',
    		success : function(data){
    			console.log(data.length);
    			var thead = "<thead><tr><th>#</th>";
    			for(var i=0;i<data.length;i++)
   				{
    				thead += "<th>"+(i+1)+"</th>";
   				}
    			var theadfin = "</tr></thead>";
    			thead +=theadfin;
    			console.log(thead);
    			var tbody = "<tbody>";
    			var tr = "";
    			for(var i=0;i<data.length;i++)
   				{
    				tr = "<tr><th>"+(i+1)+"</th>";
    				for(var j=0;j<data.length;j++)
    				{
    					tr += "<td>"+data[i][j]+"</td>";
   					}
    				tr += "</tr>";
    				tbody +=tr;
   				}
    			tbody +="</tbody>";
    			var trHTML = thead+tbody;
    			console.log(trHTML);
    			$('#table').append(trHTML);
    		}
    	});
	});
	
	function changeCout(val,origin,destination)
	{
		console.log(val.value);
		console.log(origin);
		console.log(destination);
		$.ajax({
    		type: "GET",
    		async : false,
    		url:  '<c:url value="/rest/matrice/cout/change"/>',
    		data:  {'cap' : val.value,'origine':origin,'destination':destination},
    		success : function(data){
    			if(data == "ok")
   				{
    				refreshGraphe();
    				$( "#cout" ).click();
   				}
    		}
		});
		
	};
	
	$( "#cout" ).click(function() {
		$("#table").children().remove();
		$('#lib').html("Matrice de cout");
		$.ajax({
    		type: "GET",
    		async : false,
    		url:  '<c:url value="/rest/matrice/cout"/>',
    		success : function(data){
    			var thead = "<thead><tr><th>#</th>";
    			for(var i=0;i<data.length;i++)
   				{
    				thead += "<th>"+(i+1)+"</th>";
   				}
    			var theadfin = "</tr></thead>";
    			thead +=theadfin;
    			var tbody = "<tbody>";
    			var tr = "";
    			for(var i=0;i<data.length;i++)
   				{
    				tr = "<tr><th>"+(i+1)+"</th>";
    				for(var j=0;j<data.length;j++)
    				{
    					if(data[i][j]=="Infinity" || i==j){
    						tr += "<td>"+data[i][j]+"</td>";
    					}
    					else
    						tr += "<td> <input type='text' value='"+data[i][j]+"' name='cout' onchange='changeCout(this,"+(i+1)+","+(j+1)+");' title='Modifier la valeur et cliquer hors du champ.' /></td>";
   					}
    				tr += "</tr>";
    				tbody +=tr;
   				}
    			tbody +="</tbody>";
    			var trHTML = thead+tbody;
    			$('#table').append(trHTML);
    		}
    	});
	});
	
	$( "#coloration" ).click(function() {
		$.ajax({
    		type: "GET",
    		async : false,
    		url:  '<c:url value="/rest/coloration"/>',
    		success : function(data){
    			if(data != "")
    			{
	    			elements = data;
					nb = (elements.length == 0 ) ? 1 : elements[elements.length-1].number+1;
					context.clearRect ( 0 , 0 , 750, 400 );
					elements.forEach(function(element) {
						context.beginPath();
						context.arc(element.x, element.y, radius, 0, 2 * Math.PI, false);
						context.fillStyle = element.couleur;
						context.fill();
						context.lineWidth = 2;
						context.strokeStyle = 'black';
						context.font = '20pt Calibri';
						context.fillStyle = 'black';
						context.textAlign = 'center';
						context.fillText(element.number, element.x, element.y+4);
						context.stroke();
					});
					$.ajax({
			    		type: "GET",
			    		async : false,
			    		url:  '<c:url value="/rest/arc"/>',
			    		success : function(data){
			    			arcs = data;
			    			nba = (arcs.length == 0 ) ? 1 : arcs[arcs.length-1].number+1;
			    			arcs.forEach(function(arc) {
			    				drawLineArrow(arc.originex, arc.originey,arc.destinationx, arc.destinationy,arc.couleur,arc.capacite,arc.number);
			    			});
			    		}
			    	});
    			}
    		}
		});
		
	});
	
	$( "#delarc" ).click(function() {
		var num = $( "#arctodel" ).val();
		$( "#arctodel" ).val("0");
		$.ajax({
    		type: "DELETE",
    		async : false,
    		url:  '<c:url value="/rest/arc/del/"/>'+num,
    		success : function(data){
    			if(data == "ok")
   				{
    				refreshGraphe();
    				$( "#cout" ).click();
   				}
    			else{
    				alert("Arc non existant");
    			}
    		}
		});
	});
	
	$( "#delnoeud" ).click(function() {
		var num = $( "#noeudtodel" ).val();
		$( "#noeudtodel" ).val("0");
		$.ajax({
    		type: "DELETE",
    		async : false,
    		url:  '<c:url value="/rest/noeud/del/"/>'+num,
    		success : function(data){
    			if(data == "ok")
   				{
    				refreshGraphe();
    				$( "#cout" ).click();
   				}
    			else{
    				alert("Noeud non existant");
    			}
    		}
		});
	});
	
	$( "#minway" ).click(function() {
		var origine = $( "#originway" ).val();
		var destination = $( "#destinationway" ).val();
		$( "#originway" ).val("0");
		$( "#destinationway" ).val("0");
		$.ajax({
    		type: "POST",
    		async : false,
    		url:  '<c:url value="/rest/chemin/min/"/>',
    		data:  {'origine':origine,'destination':destination},
    		success : function(data){
    			if(data != "")
   				{
    				var color = false;
    				context.clearRect ( 0 , 0 , 750, 400 );
    				data.forEach(function(arc) {
    					if(arc.couleur == 'white'){
    						color = true;	
    					}
	    				drawLineArrow(arc.originex, arc.originey,arc.destinationx, arc.destinationy,arc.couleur,arc.capacite,arc.number);
	    			});
    				refreshNoeud();
    				if(color == false){
    					alert("Aucun chemin possible");
   					}
   				}
    			else{
    				alert("Aucun chemin possible");
    			}
    		}
		});
	});
	
	
	
    </script>
  </body>
</html> 