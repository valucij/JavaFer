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
<h1>Blogovi:</h1>
<c:choose>
<c:when test="${blogEntries == null }">
Korisnik nema napisanih blogova! 
</c:when>

<c:otherwise>

<ol>
<c:forEach var="i" begin="0" end="${blogEntries.size()-1 }">
<li><a href="${blogEntries.get(i).getBlogUser().getNick() }/${blogEntries.get(i).getId()}" > ${blogEntries.get(i).getTitle()}</a></li>
</c:forEach>
</ol>

</c:otherwise>
</c:choose>
<br>
<form action="main" method="post">
<input type="submit" value="Vrati se na početnu!">
</form>
<br>
<c:if test="${addNew !=null }">
<form action="${currentUserNick}/new" method="get">
<input type="submit" value="Dodaj novi blog"/>
</form>
</c:if>
<c:if test="${currentUserId !=null }">
<br><br>
<form action="logout" method="post">
<input type="submit" name="logout" value="Log out">
</form>
</c:if>

</body>
</html>