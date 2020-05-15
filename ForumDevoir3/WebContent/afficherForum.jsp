<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Utilisateur" %>
<%@ page import="model.Forum" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Forum</title>
</head>
<body>
<div style="float:right;">
<form action="Deconnexion" method="get">
<input type="submit" value="Se deconnecter">
</form>
<form action="RetourMenu" method="get">
<input type="submit" value="Retour">
</form>
</div>

<%= ((Forum)session.getAttribute("forum")).afficherForum(((Utilisateur)session.getAttribute("utilisateur")).getId(),((String)session.getAttribute("option")),((String)session.getAttribute("arg1")),((java.sql.Date)session.getAttribute("arg2"))) %>

<div style="width:60%; position:fixed; top:25%; left:2%;overflow-y:scroll;">

<div id="editer" style="display:none;">
<form action="EditerMessage" method="post" id="editerM">
<input type="hidden" name="messageId" id="idM">
<label> Reecrivez votre message : </label> <textarea rows="10" cols="40" name="contenu" form="editerM"></textarea> <br>
<input type="submit" value="Editer">
<input>
</form>
</div>

<div style="overflow-y:scroll;">
<form action="EcrireMessage" method="post" id="addM">
<label> Ecrivez votre message : </label> <textarea rows="10" cols="40" name="contenu" form="addM"></textarea> <br>
<input type="submit" value="Envoyer">
</form>
</div>

</div>

<div style=" height:100%; width:30%; position:fixed;top:2%;right:2%;overflow-y:scroll;">

<form action="AfficherForumByNom" method="post">
<input name="nom" type="text" placeholder="nom">
<input type="submit" value="afficher">
</form>
<form action="AfficherForumByDate" method="post">
<input name="nom" type="text" placeholder="YYYY-MM-DD">
<input type="submit" value="afficher">
</form>
<form action="AfficherForum"><input type="submit" value="all"></form>
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