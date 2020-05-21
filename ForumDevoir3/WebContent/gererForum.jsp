<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Utilisateur" %>
<%@ page import="model.Forum" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Gerer Forums</title>
</head>
<body>
	<h1>Gérer les forums : </h1>
	<div style="width:40%;height:100%; position:fixed; left:2%;overflow-y:scroll;">
		<div style="display:ruby-base;">
			<form action="Deconnexion" method="get">
				<input type="submit" value="Se deconnecter">
			</form>
			<form action="menu.jsp" method="get">
				<input type="submit" value="Retour">
			</form>
		</div>
		<h3>Créer un forum : </h3>
		<form action="AjouterForum" method="post" id="addF">
			<label> Titre : </label> <input type="text" id="login" name="titre"/> <br>
			<label> Description : </label> <textarea rows="5" cols="40" name="description" form="addF"></textarea> <br>
			<input type="submit" value="Créer">
		</form>
	</div>
	
	<div style=" height:100%; width:50%; position:fixed;right:2%;overflow-y:scroll;">
		<h3>Gérez les forums : </h3>
		<%= Forum.getForumsForAdmin() %>
	</div>
	
</body>
</html>