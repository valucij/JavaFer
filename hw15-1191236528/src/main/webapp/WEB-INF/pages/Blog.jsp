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

<h1> ${blogEntry.getTitle()}</h1>
<p>
${blogEntry.getText() }
</p>

<c:if test="${edit!=null }">
<form action="edit?blogID=${blogEntry.getId()}" method="post">
<input type="submit" value="Uredi"/>
</form>
</c:if>
<br><br>
<h3>Komentari:</h3>
<c:choose>
<c:when test="${comments == null }">
<c:out value="Nema komentara"></c:out>
</c:when>
<c:otherwise>
<c:forEach var="i" begin="0" end="${ comments.size()-1}">
<h5>${comments.get(i).getUsersEMail() }</h5>
<p>${comments.get(i).getMessage() }</p>
<p>--------------------------------------</p><br>
</c:forEach>
</c:otherwise>
</c:choose>


<br>
<form action="main" method="post">
<input type="submit" value="Vrati se na početnu!">
</form>
<br>

<form action="comment?blogID=${blogEntry.getId()}" method="post">
<input type="submit" value="Dodaj novi komentar!">
</form>

<c:if test="${currentUserId!=null }">
<br><br>
<form action="logout" method="post">
<input type="submit" name="logout" value="Log out">
</form>
</c:if>




</body>
</html>