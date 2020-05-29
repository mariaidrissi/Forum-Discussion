<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Utilisateur" %>
<%@ page import="model.Forum" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Forums</title>
<link href="style.css" rel="stylesheet">
</head>
<body>
<header>
	<h1>Gérer les forums</h1>
	<nav class="menu" style="text-align:right!important;">
		<form style="margin-top:1%;margin-bottom:1%;display:inline-block;" action="menu.jsp">
			<input class="buttonStyle" type="submit" value="Retour">
		</form>
		<form style="margin-top:1%;margin-bottom:1%;display:inline-block;" action="Deconnexion" method="get">
			<input class="buttonDeconnexion" type="submit" value="Se deconnecter">
		</form>
	</nav>
</header>

<div class="row">
	<div id="upleft">
		<h3>Créer un forum : </h3>
		<form action="AjouterForum" method="post" id="addF">
			<label> Titre : </label> <input type="text" id="login" name="titre" required/> <br>
			<label> Description : </label> <textarea rows="5" cols="40" name="description" form="addF" required></textarea> <br>
			<input class="buttonOk" type="submit" value="Créer">
		</form>
	</div>
	<div id="upright">
		<h3>Gérez les forums : </h3>
		<%= Forum.getForumsForAdmin() %>
	</div>
</div>

<div class="break"></div>
<footer >
<p>SR03 application forum 2020</p>
<p>Lise Jolicoeur & Maria Idrissi<p>
</footer>
</body>
</html>