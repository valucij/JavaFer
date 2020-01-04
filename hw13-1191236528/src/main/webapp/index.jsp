<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
    
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
Links:<br>
<a href="color.jsp" >Background color chooser</a><br>
<a href="stories/funny.jsp" >Funny story</a><br>
<a href="trigonometric?a=0&b=90">See angles!</a><br>
<a href="powers?a=1&b=100&n=3" >Create excel table!</a><br>
<a href="appinfo.jsp">See how long was this app running!</a><br><br>

<form action="trigonometric" method="get">
Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
<input type="submit" value="Tabeliraj"><input type="reset" value="Reset">
</form>
</body>
</html>