<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Forum</title>
</head>
<body>
<%=session.getAttribute("forum").toString()%>
<form action="Deconnexion" method="get">
<input type="submit" value="Se deconnecter">
</form>

<form action="RetourMenu" method="get">
<input type="submit" value="Retour">
</form>

<div style="overflow-y:scroll;">
<form action="EcrireMessage" method="post" id="addM">
<label> Ecrivez votre message : </label> <textarea rows="10" cols="40" name="contenu" form="addM"></textarea> <br>
<input type="submit" value="Envoyer">
</form>
</div>

</body>
</html>