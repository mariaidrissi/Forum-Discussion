<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Utilisateur" %>
<html>
    <head>
        <title>Gérer utilisateur</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="style.css" rel="stylesheet">
    </head>
    <body>
		<div style="display:flex;">
			<h1>Gérer les utilisateurs</h1>
			<form action="Deconnexion" method="get">
				<input class="buttonDeconnexion" type="submit" value="Se deconnecter">
			</form>
			<form action="menu.jsp">
				<input class="buttonStyle" type="submit" value="Retour">
			</form>
		</div>
		<div style="width:100%; margin-top:2%; overflow:auto;">
			<div style="width:60%;height:100%; float:left; left:2%;">
			 	<h3>Utilisateurs existants : </h3>
			   	<%= Utilisateur.FindAlltoString() %>
			   	
				<h3>Créer un nouvel utilisateur : </h3>
				<form action="AjouterUtilisateur" method="post">
				    <label> Prenom : </label>
				    <input type="text" id="frname" name="User first name"/>
				    <br>
				    <label> Nom de famille : </label>
				    <input type="text" id="faname" name="User familly name"/>
				    <br>
				    <label> Login : </label>
				    <input type="text" id="login" name="User login"/>
				    <br>
				    
				    <label> Mot de passe : </label>
				    <input type="password" id="psw" name="User password"/>
				    <br>
				    <label> Homme </label>
				    <input type="radio" id="male" name="gender" value="male" checked/>
				    <br>
				    <label> Femme </label>
				    <input type="radio" id="female" name="gender" value="female"/>
				    <br>
				     
				    <label> Admin </label>
				    <input type="checkbox" name="admin" value="Admin" />
				    
				    <input class="buttonStyle" type="submit" value="Ajouter"> 
				</form>
			</div>
			     
			<div style="height:100%; width:40%; float:right; right:2%;">
			    <form action="SupprimerUtilisateur" method="post">
					<input type="text" placeholder="id de l'utilisateur" name="idU">
					<input class="buttonStyle" type="submit" value="Supprimer un utilisateur">
				</form>
			</div>
		</div>
	</body>
</html>

