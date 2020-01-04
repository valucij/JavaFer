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

<table border="1">

<tr>
<th>Values</th>
<th>Sin</th>
<th>Cos</th>
</tr>
	
	
<c:forEach var="i" begin="0" end="${values.size()-1}">
<tr><td>${values.get(i)}</td><td>${sinus.get(i)}</td><td>${cosinus.get(i) }</td></tr>
</c:forEach>
	
	
</table>
</body>
</html>