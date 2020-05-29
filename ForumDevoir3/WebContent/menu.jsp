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

<header>
<h1>Bonjour <%= session.getAttribute("login") %></h1>

<nav class="menu">
	<c:set var="role" value='<%=(String)session.getAttribute("role") %>'/>
	<c:if test="${role eq 'Admin'}">
		<form style="margin-top:1%;margin-bottom:1%;display:inline-block;" action="AjouterUtilisateur" method="get">
			<input class="buttonStyle" type="submit" value="Gérer les utilisateurs">
		</form>

		<form style="margin-top:1%;margin-bottom:1%;display:inline-block;" action="GererForums" method="get">
			<input class="buttonStyle" type="submit" value="Gérer les forums">
		</form>
		<form action="Deconnexion" method="get" style="display:inline-block;float:right;">
			<input class="buttonDeconnexion" type="submit" value="Se déconnecter">
		</form>
	</c:if>
</nav>
</header>

<div class="row">
	<aside id="upleft">
		<h3>Informations :</h3>
		<p>Nom : <%=session.getAttribute("nom")%> <%=session.getAttribute("prenom")%> </p>
		<p>Gender : <%= session.getAttribute("gender")%></p>
	</aside>
	<main id="upright">
		<h3>Forums : </h3>
		<div style="height:100%; float:left;padding:10px;">
			<h3>Forums auxquels vous êtes abonnés </h3>
			<%= ((Utilisateur)session.getAttribute("utilisateur")).listerForumsAbonne() %>
		</div>
		<div style="height:100%; float:right; padding:10px;">
			<h3>S'abonner à d'autres forums </h3>
			<%= ((Utilisateur)session.getAttribute("utilisateur")).listerForumsNonAbonne() %>
		</div>
	</main>
</div>

<div class="break"></div>
<footer >
<p>SR03 application forum 2020</p>
<p>Lise Jolicoeur & Maria Idrissi<p>
</footer>
</body>
</html>