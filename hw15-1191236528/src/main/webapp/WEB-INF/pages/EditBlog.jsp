<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>


<c:choose>
<c:when test="${currentUserId == null }">
Anonymous
</c:when>
<c:otherwise>
${currentUserName }
</c:otherwise>
</c:choose>

</title>
</head>
<body>

<form action="edit?blogID=${blogEntry.getId() }" method="post">

Naslov:<input type="text" name="title" value="${blogEntry.getTitle() }"><br>
Sadržaj:<textarea name="textAll" >
${blogEntry.getText()}
</textarea>
<br>
<input type="submit" value="Spremi!">
</form>

</body>
</html>