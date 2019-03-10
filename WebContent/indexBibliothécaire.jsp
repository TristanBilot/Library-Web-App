<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <!-- pour JSTL -->
<!DOCTYPE html> 
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href = "https://bootswatch.com/4/sandstone/bootstrap.min.css"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<title>Gestion de la médiathèque</title>
</head>
<body>

	<div style = "margin:50px;display:inline-grid;">
		<h3>Nom de compte: ${ sessionScope.login }</h3><br>
		
		<div class = "jumbotron" style = "max-width:550px;">
			<h1>Ajouter un document</h1><br>
			<form id = "connexionForm" action="/PersistentLibraryProject/Ajout" method="post">
			<SELECT id = "type" name="type" size="1" style = "margin-right:15px">
				<OPTION>Livre
				<OPTION>CD
				<OPTION>DVD
			</SELECT>
				<input id = "nom" name = "nom" type = "text" placeholder = "nom du document" style = "padding:5px;margin-right:15px" required="required" />
				<input id = "auteur" name = "auteur" type = "text" placeholder = "auteur" style = "padding:5px;margin-right:15px" required="required"/><br><br>
				<button type = "submit" class = "btn btn-success" value = "Ajouter">Ajouter</button>
			</form>
		</div>
		
		<br>	
		
		<p style = "font-size:18px" class = "text-success">${ sessionScope.message }</p>
	</div>

</body>
</html>