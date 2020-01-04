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
<h1>OS usage</h1>
<p>Here are the results of OS usage in survey that we completed.</p>
<img src="reportImage">
</body>
</html>