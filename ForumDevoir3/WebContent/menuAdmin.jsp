<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Menu</title>
</head>
<body>
<div>
<h1>Bonjour <%= session.getAttribute("login") %></h1>
<form action="/Forum/Deconnexion" method="get">
<input type="submit" value="Se deconnecter">
</form>
</div>

<div style="width:40%; position:fixed; top:25%; left:2%;overflow-y:scroll;">
<h3>Creer un nouvel utilisateur : </h3>
<form action="/Forum/AjouterUtilisateur" method="post">
<label> Login : </label> <input type="text" id="login" name="login"/> <br>
<label> Password : </label> <input type="password" id="psw" name="password"/><br>
<p>Statut : </p>
<label> admin </label> <input type="radio" name="statut" value="admin"/><br>
<label> user </label> <input type="radio" name="statut" value="user" checked/> <br>
<input type="submit" value="Creer">
</form>

<h3>Creer un forum : </h3>
<form action="/Forum/AjouterForum" method="post" id="addF">
<label> Titre : </label> <input type="text" id="login" name="titre"/> <br>
<label> Description : </label> <textarea rows="5" cols="40" name="description" form="addF"></textarea> <br>
<input type="submit" value="Creer">
</form>

</div>

<div style=" height:100%; width:50%; position:fixed;top:2%;right:2%;overflow-y:scroll;">

<h3>Forums : </h3>
TODO
</div>

</body>
</html>