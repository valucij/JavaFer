<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<%  Object string = session.getAttribute("pickedBgColor");
	String color;
	if(string == null){
		color = "white";
	}else{
		color = String.valueOf(string);
	}

%>
<body bgcolor="<%= color %>">
<h1>Glasanje za omiljeni bend:</h1>
<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link
kako biste glasali!</p>
<ol>
<c:forEach var="i" begin="0" end="${id.size()-1}">
<li><a href="glasanje-glasaj?id=${id.get(i)}">${bandNames.get(i)}</a></li>
</c:forEach>
</ol>
</body>
</html>