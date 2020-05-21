<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Utilisateur" %>
<%@ page import="model.Forum" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="refresh" content="60">
<title>Forum</title>
</head>
<body>

<div style="width:60%;height:100%; position:fixed;left:2%;overflow-y:scroll;">
	<%= ((Forum)session.getAttribute("forum")).afficherForum(((Utilisateur)session.getAttribute("utilisateur")).getId(),
															((String)session.getAttribute("option")),
															((String)session.getAttribute("arg1")),
															((java.sql.Date)session.getAttribute("arg2"))) %>
	
	<div id="editer" style="display:none;border:1px solid black;">
		<form action="EditerMessage" method="post" id="editerM">
			<input type="hidden" name="messageId" id="idM">
			<label> Réecrivez votre message : </label> <textarea rows="10" cols="40" name="contenu" form="editerM"></textarea> <br>
			<input type="submit" value="Editer">
		</form>
	</div>
	
	<div style="border:1px solid black;">
		<form action="EcrireMessage" method="post" id="addM">
			<label> Ecrivez votre message : </label> <textarea rows="10" cols="40" name="contenu" form="addM"></textarea> <br>
			<input type="submit" value="Envoyer">
		</form>
	</div>
</div>

<div style=" height:100%; width:30%;position:fixed;right:2%;overflow-y:scroll;">
	<div style="display:ruby-base;">
		<form action="Deconnexion" method="get">
			<input type="submit" value="Se deconnecter">
		</form>
		<form action="RetourMenu" method="get">
			<input type="submit" value="Retour">
		</form>
	</div>
	<div style="margin-top:10%;">
		<h3>Filtres : </h3>
		<form action="AfficherForum" method="post">
			<input name="forum" type="hidden" value="<%= ((Forum)session.getAttribute("forum")).getId()%>">
			<input name="option" type="hidden" value="nom">
			<input name="arg1" type="text" placeholder="nom de famille">
			<input type="submit" value="afficher">
		</form>
		<form action="AfficherForum" method="post">
			<input name="forum" type="hidden" value="<%= ((Forum)session.getAttribute("forum")).getId()%>">
			<input name="option" type="hidden" value="date">
			<input name="arg2" type="text" placeholder="YYYY-MM-DD">
			<input type="submit" value="afficher">
		</form>
		<form action="AfficherForum" method="post">
			<input name="option" type="hidden" value="all">
			<input name="forum" type="hidden" value="<%= ((Forum)session.getAttribute("forum")).getId()%>">
			<input type="submit" value="show all">
		</form>
	</div>
</div>

<script>
function editerMessage(idM) {
	
	var divE = document.getElementById("editer");
	divE.style.display="block";
	document.getElementById("idM").setAttribute('value',idM);
}
</script>

</body>
</html>