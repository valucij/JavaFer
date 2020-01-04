<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true" %>

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

<a href="setcolor?col=White" >WHITE</a><br>
<a href="setcolor?col=Blue" >BLUE</a><br>
<a href="setcolor?col=Red" >RED</a><br>
<a href="setcolor?col=Green" >GREEN</a><br>
<a href="setcolor?col=Cyan" >CYAN</a>

</body>
</html>