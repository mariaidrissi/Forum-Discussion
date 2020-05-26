<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Utilisateur" %>
<%@ page import="model.Forum" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Gerer Forums</title>
<link href="style.css" rel="stylesheet">
</head>
<body>
	<div style="display:flex;margin-bottom:2%;">
		<h1>Gérer les forums</h1>
		<form action="Deconnexion" method="get">
			<input class="buttonDeconnexion" type="submit" value="Se deconnecter">
		</form>
		<form action="menu.jsp">
			<input class="buttonStyle" type="submit" value="Retour">
		</form>
	</div>
	
	<div style="width:100%; margin-top:2%; overflow:auto;">
		<div style="width:60%;height:100%; float:left; left:2%;">
			<h3>Créer un forum : </h3>
			<form action="AjouterForum" method="post" id="addF">
				<label> Titre : </label> <input type="text" id="login" name="titre"/> <br>
				<label> Description : </label> <textarea rows="5" cols="40" name="description" form="addF"></textarea> <br>
				<input class="buttonStyle" type="submit" value="Créer">
			</form>
		</div>
		<div style="height:100%; width:40%; float:right; right:2%;">
			<h3>Gérez les forums : </h3>
			<%= Forum.getForumsForAdmin() %>
		</div>
	</div>
	
</body>
</html>