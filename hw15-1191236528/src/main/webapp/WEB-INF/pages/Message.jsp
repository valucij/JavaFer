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

<h2> ${message}</h2>
<form action="main" method="post">
<input type="submit" value="Vrati se na početnu!">
</form>

</body>
</html>