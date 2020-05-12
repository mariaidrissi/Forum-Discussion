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

<h1>Bonjour <%= session.getAttribute("login") %></h1>

<div style="top : 5%; right:5%">
<form action="Deconnexion" method="get">
<input type="submit" value="Se deconnecter">
</form>
</div>

<div>
<h3>Forums auxquels vous etes abonne : </h3>
<%= ((Utilisateur)session.getAttribute("utilisateur")).listerForumToString() %>
</div>
<div>
<h3>S'abonner a d'autres forums : </h3>
<%= ((Utilisateur)session.getAttribute("utilisateur")).listerForumsNonAbonne() %>
</div>
</body>
</html>