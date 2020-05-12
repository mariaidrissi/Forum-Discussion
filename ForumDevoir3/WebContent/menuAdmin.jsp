<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Utilisateur" %>
<%@ page import="model.Forum" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Menu</title>
</head>
<body>

<div style="display:flex;">
<h1>Bonjour <%= session.getAttribute("login") %></h1>
<form action="Deconnexion" method="get">
<input type="submit" value="Se deconnecter">
</form>
</div>
<form action="AjouterUtilisateur" method="get">
<input type="submit" value="Ajouter un utilisateur">
</form>

<div style="width:40%; position:fixed; top:25%; left:2%;overflow-y:scroll;">
<h3>Creer un forum : </h3>
<form action="AjouterForum" method="post" id="addF">
<label> Titre : </label> <input type="text" id="login" name="titre"/> <br>
<label> Description : </label> <textarea rows="5" cols="40" name="description" form="addF"></textarea> <br>
<input type="submit" value="Creer">
</form>
</div>

<div style=" height:100%; width:50%; position:fixed;top:2%;right:2%;overflow-y:scroll;">
<div>
<h3>Forums auxquels vous etes abonne : </h3>
<%= ((Utilisateur)session.getAttribute("utilisateur")).listerForumToString() %>
</div>
<div>
<h3>S'abonner a d'autres forums : </h3>
<%= ((Utilisateur)session.getAttribute("utilisateur")).listerForumsNonAbonne() %>
</div>
</div>

</body>
</html>