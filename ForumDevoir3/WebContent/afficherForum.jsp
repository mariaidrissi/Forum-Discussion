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
<header>
	<%= ((Forum)session.getAttribute("forum")).afficherForumTitre() %>
	<nav class="menu" style="text-align:right!important;">
		<form style="margin-top:1%;margin-bottom:1%;display:inline-block;" action="RetourMenu">
			<input class="buttonStyle" type="submit" value="Retour">
		</form>
		<form style="margin-top:1%;margin-bottom:1%;display:inline-block;" action="Deconnexion" method="get">
			<input class="buttonDeconnexion" type="submit" value="Se deconnecter">
		</form>
	</nav>
</header>

<div class="row">
	<div id="upleftmenu">
		<%= ((Forum)session.getAttribute("forum")).afficherForum(((Utilisateur)session.getAttribute("utilisateur")).getId(),
																((String)session.getAttribute("option")),
																((String)session.getAttribute("arg1")),
																((java.sql.Date)session.getAttribute("arg2"))) %>
		
		<div id="editer" style="display:none;border:1px solid black;">
			<form action="EditerMessage" method="post" id="editerM">
				<input type="hidden" name="messageId" id="idM">
				<label> Réecrivez votre message : </label> <textarea rows="10" cols="40" name="contenu" form="editerM" required></textarea> <br>
				<input class="buttonOk" type="submit" value="Editer">
			</form>
			<button class="buttonDeconnexion" onclick="editerMessage(0)">Fermer</button>
		</div>
		<div id="boutonEcrire">
			<button class="buttonStyle" onclick="ecrireMessage()">Ecrire</button>
		</div>
		
		<div id="ecrire" style="border:1px solid black; display:none;">
			<form action="EcrireMessage" method="post" id="addM">
				<label> Ecrivez votre message : </label> 
				<textarea rows="10" cols="30" name="contenu" form="addM" required></textarea> <br>
				<input class="buttonOk" type="submit" value="Envoyer">
			</form>
			<button class="buttonDeconnexion" onclick="ecrireMessage()">Fermer</button>
		</div>
	</div>
	
	<div id="uprightmenu">
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

function ecrireMessage() {
	var divE = document.getElementById("ecrire");
	var divB = document.getElementById("boutonEcrire");
	if(divE.style.display == "block")
		divE.style.display="none";
	else {
		divE.style.display="block";
	}
}
</script>
<div class="break"></div>
<footer >
<p>SR03 application forum 2020</p>
<p>Lise Jolicoeur & Maria Idrissi<p>
</footer>
</body>
</html>