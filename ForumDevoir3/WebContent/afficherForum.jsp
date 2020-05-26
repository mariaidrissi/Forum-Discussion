<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Utilisateur" %>
<%@ page import="model.Forum" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="refresh" content="60">
<link href="style.css" rel="stylesheet">
<title>Forum</title>
</head>
<body>

<div style="width:100%; overflow:auto; margin-top:2%;">
	<div style="width:60%;height:100%; float:left; left:2%;">
		<%= ((Forum)session.getAttribute("forum")).afficherForum(((Utilisateur)session.getAttribute("utilisateur")).getId(),
																((String)session.getAttribute("option")),
																((String)session.getAttribute("arg1")),
																((java.sql.Date)session.getAttribute("arg2"))) %>
		
		<div id="editer" style="display:none;border:1px solid black;">
			<form action="EditerMessage" method="post" id="editerM">
				<input type="hidden" name="messageId" id="idM">
				<label> Réecrivez votre message : </label> <textarea rows="10" cols="40" name="contenu" form="editerM"></textarea> <br>
				<input class="buttonStyle" type="submit" value="Editer">
			</form>
		</div>
		
		<div style="border:1px solid black;">
			<form action="EcrireMessage" method="post" id="addM">
				<label> Ecrivez votre message : </label> <textarea rows="10" cols="40" name="contenu" form="addM"></textarea> <br>
				<input class="buttonStyle" type="submit" value="Envoyer">
			</form>
		</div>
	</div>
	
	<div style="height:100%; width:40%; float:right; right:2%;">
		<div style="display:ruby-base;">
			<form action="Deconnexion" method="get">
				<input class="buttonDeconnexion" type="submit" value="Se deconnecter">
			</form>
			<form action="RetourMenu" method="get">
				<input class="buttonStyle" type="submit" value="Retour">
			</form>
		</div>
		<div style="margin-top:10%;">
			<h3>Filtres : </h3>
			<form action="AfficherForum" method="post">
				<input name="forum" type="hidden" value="<%= ((Forum)session.getAttribute("forum")).getId()%>">
				<input name="option" type="hidden" value="nom">
				<input name="arg1" type="text" placeholder="nom de famille">
				<input class="buttonStyle" type="submit" value="afficher">
			</form>
			<form action="AfficherForum" method="post">
				<input name="forum" type="hidden" value="<%= ((Forum)session.getAttribute("forum")).getId()%>">
				<input name="option" type="hidden" value="date">
				<input name="arg2" type="text" placeholder="YYYY-MM-DD">
				<input class="buttonStyle" type="submit" value="afficher">
			</form>
			<form action="AfficherForum" method="post">
				<input name="option" type="hidden" value="all">
				<input name="forum" type="hidden" value="<%= ((Forum)session.getAttribute("forum")).getId()%>">
				<input class="buttonStyle" type="submit" value="show all">
			</form>
		</div>
	</div>
</div>
<script>
function editerMessage(idM) {
	
	var divE = document.getElementById("editer");
	if(divE.style.display == "block")
		divE.style.display="none";
	else {
		divE.style.display="block";
		document.getElementById("idM").setAttribute('value',idM);
	}
}
</script>

</body>
</html>