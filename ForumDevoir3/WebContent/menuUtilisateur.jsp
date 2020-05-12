<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Menu</title>
</head>
<body>
<h1>Bonjour <%= session.getAttribute("login") %></h1>
<div style="height:100%;left:5%; top:20%; overflow-y:scroll;">
<!-- <%= request.getAttribute("listeForums") %> -->TODO
</div>
</body>

<div style="top : 5%; right:5%">
<form action="/Forum/Deconnexion" method="get">
<input type="submit" value="Se deconnecter">
</form>
</div>

</html>