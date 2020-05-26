<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.Utilisateur" %>
<%@ page import="model.Forum" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Menu</title>
	<link href="style.css" rel="stylesheet">
</head>
<body>

<div>
	<div style="display: ruby-base;">
		<h1>Bonjour <%= session.getAttribute("login") %></h1>
		<form action="Deconnexion" method="get">
			<input class="buttonDeconnexion" type="submit" value="Se déconnecter">
		</form>
	</div>
	<div>
		
	<c:set var="role" value='<%=(String)session.getAttribute("role") %>'/>
	<c:if test="${role eq 'Admin'}">
		<form style="margin-top:1%;margin-bottom:1%;" action="AjouterUtilisateur" method="get">
			<input class="buttonStyle" type="submit" value="Gérer les utilisateurs">
		</form>
		<form style="margin-top:1%;margin-bottom:1%;" action="GererForums" method="get">
			<input class="buttonStyle" type="submit" value="Gérer les forums">
		</form>
	</c:if>
	<div style="width:100%; margin-top:2%; overflow:auto;">
		<div style="width:60%;height:100%; float:left; left:2%;">
			<h3>Forums auxquels vous êtes abonnés : </h3>
			<%= ((Utilisateur)session.getAttribute("utilisateur")).listerForumsAbonne() %>
		</div>
		<div style="height:100%; width:40%; float:right; right:2%;">
			<h3>S'abonner à d'autres forums : </h3>
			<%= ((Utilisateur)session.getAttribute("utilisateur")).listerForumsNonAbonne() %>
		</div>
		</div>
	</div>
</div>
</body>
</html>