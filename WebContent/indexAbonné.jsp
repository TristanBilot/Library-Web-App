<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mes services</title>
<link rel="stylesheet" href = "https://bootswatch.com/4/sandstone/bootstrap.min.css"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>

<div style = "margin:50px">
	<h3 >Nom de compte: ${ sessionScope.login }</h3><br>
	
	<div class = "jumbotron" style = "max-width:500px;">
		<h1>Emprunter un document</h1><br>
		<form id = "connexionForm" action="/PersistentLibraryProject/Emprunt" method="post">
			<input id = "numDoc1" name = "numDoc1" type = "text" placeholder = "numéro de document" style = "padding:5px;" required="required"/>
			<button type = "submit" class = "btn btn-success" value = "Emprunter" >Emprunter</button>
		</form>
	</div>
	
	<div class = "jumbotron" style = "max-width:500px;">
		<h1>Retourner un document</h1><br>
		<form id = "connexionForm" action="/PersistentLibraryProject/Retour" method="post">			
			<input id = "numDoc2" name = "numDoc2" type = "text" placeholder = "numéro de document" style = "padding:5px;" required="required"/>
			<button type = "submit" class = "btn btn-success" value = "Retourner" >Retourner</button>
		</form>
	</div>
	
	<br>	
	
	<p style = "font-size:18px" class = "text-success">${ sessionScope.message }</p>
</div>
	
	<%-- <% session.getAttribute("message"); %>
	
	<% if (session.getAttribute("fail") != null) { %>
		<p class = "text-danger"> <% session.getAttribute("message"); %> </p>
	<% } 
	else {%>
		<p class = "text-success"> <% session.getAttribute("message"); %> </p>
	<% } %> --%>

	<%-- <div style = "min-height: 300px;">
		<h1>Mes documents</h1>
		
		<!-- <div><% request.getAttribute("user"); %> : Abonné</div> -->
			
		<% if (request.getAttribute("cds") != null) { %>
			<div class = "documentList">
				<h2>Mes CD</h2>
				<ul class="ulDocs">
					<c:forEach items="${cds}" var="item"> <!-- Boucle sur tous les cd -->
					    <li id = "cd${item}" class="cd">${item}</li>
					</c:forEach>
				</ul>
			</div>
		<% } %>	
		
		<% if (request.getAttribute("dvds") != null) { %>
			<div class = "documentList">
				<h2>Mes DVD</h2>
				<ul class="ulDocs">
					<c:forEach items="${dvds}" var="item">
					    <li id = "dvd${item}" class="dvd">${item}</li>
					</c:forEach>
				</ul>
			</div>
		<% } %>
			
		<% if (request.getAttribute("livres") != null) { %>
			<div class = "documentList">
				<h2>Mes livres</h2>
				<ul class="ulDocs">
					<c:forEach items="${livres}" var="item">
					    <li id = "livre${item}" class="livre">${item}</li>
					</c:forEach>
				</ul>
			</div>
		<% } %>
	</div>

	<div>
		<h1>Emprunter un livre</h1>
		
		<% if (request.getAttribute("docs") != null) { %>
			<div class = "documentList">
				<h2>Tous les documents disponible</h2>
				<ul class="ulDocs">
					<c:forEach items="${docs}" var="nom">
						<li>${nom}</li>
					</c:forEach>				
				</ul>
				<ul class="ulDocs">
					<c:forEach items="${docsTypes}" var="type">
						<li class="type">${type}</li>
					</c:forEach>
				</ul>
				<ul class="ulBtns">
					<c:forEach var="i" begin = "1" end = "${nbDocs}" step = "1">
						<li><button id = "empruntBtn${ i }" type = "submit" class = "btn btn-success" value = "Emprunter">Emprunter</button></li>
					</c:forEach>
				</ul>
			</div>
		<% } %>
	</div> --%>
</body>
<style>
	.documentList {
		float:left;
		text-align: center;
		float: left;
		background: rgb(220, 220, 220);
		width: auto;
		height: auto;
		border:1px solid rgb(70, 70, 70);		
	}
	
	.ulDocs li {
		padding:10px;
		border: 1px solid rgb(70, 70, 70);;
		list-style: none;
		margin-right: 40px;
		margin-bottom:10px;
	}
	
	.ulBtns li {
		list-style: none;
		margin-bottom:17px;
		margin-right: 40px;
	}
	
	.documentList h2 {
		margin:0px;
		padding-top:20px;
		padding-bottom:20px;
		background: #455A64;
		color: #fff;
		margin:5px;
		margin-bottom: 20px;
	}
	
	.ulDocs, .ulBtns {
		display: inline-block;
	}
	
	.cd {
		background: #F8BBD0;
	}
	.dvd {
		background: #B2DFDB;
	}
	.livre {
		background: #BBDEFB;
	}
	
</style>

<script>

	$(function() {
		$( ".type" ).each(function( i ) {
			if ($(this).html() == "livre") 
				$(this).css("background", "#BBDEFB")
			if ($(this).html() == "cd") 
				$(this).css("background", "#F8BBD0")
			if ($(this).html() == "dvd") 
				$(this).css("background", "#B2DFDB")			
		});
	});

</script>
</html>