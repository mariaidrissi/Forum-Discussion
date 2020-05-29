<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Utilisateur" %>
<html>
<head>
    <title>Utilisateur</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="style.css" rel="stylesheet">
</head>
<body>
<header>
	<h1>Gérer les utilisateurs</h1>
	<nav class="menu" style="text-align:right!important;">
		<form style="margin-top:1%;margin-bottom:1%;display:inline-block;" action="menu.jsp">
			<input class="buttonStyle" type="submit" value="Retour">
		</form>
		<form style="margin-top:1%;margin-bottom:1%;display:inline-block;" action="Deconnexion" method="get">
			<input class="buttonDeconnexion" type="submit" value="Se deconnecter">
		</form>
	</nav>
</header>
<!-- <div style="width:100%; margin-top:2%; overflow:auto;"> -->
<div class="row">
	<!--<div style="height:100%; width:40%; float:right; right:2%;">  -->
	<div id="upleft">
	    <form action="SupprimerUtilisateur" method="post">
			<input type="text" placeholder="id de l'utilisateur" name="idU">
			<input class="buttonStyle" type="submit" value="Supprimer un utilisateur">
		</form>
	</div>
	
	<!-- <div style="width:60%;height:100%; float:left; left:2%;"> -->
	<div id="upright">
	 	<h3>Utilisateurs existants : </h3>
	   	<%= Utilisateur.FindAlltoString() %>
	   	
		<h3>Créer un nouvel utilisateur : </h3>
		<form action="AjouterUtilisateur" method="post">
		    <label> Prenom : </label>
		    <input type="text" id="frname" name="User first name" required/>
		    <br>
		    <label> Nom de famille : </label>
		    <input type="text" id="faname" name="User familly name" required/>
		    <br>
		    <label> Login : </label>
		    <input type="text" id="login" name="User login" required/>
		    <br>
		    
		    <label> Mot de passe : </label>
		    <input type="password" id="psw" name="User password" required/>
		    <br>
		    <label> Homme </label>
		    <input type="radio" id="male" name="gender" value="male" checked/>
		    <br>
		    <label> Femme </label>
		    <input type="radio" id="female" name="gender" value="female"/>
		    <br>
		     
		    <label> Admin </label>
		    <input type="checkbox" name="admin" value="Admin"/>
		    
		    <input class="buttonOk" type="submit" value="Ajouter"> 
		</form>
	</div>
</div>
<div class="break"></div>
<footer >
<p>SR03 application forum 2020</p>
<p>Lise Jolicoeur<p>
</footer>
	</body>
</html>

