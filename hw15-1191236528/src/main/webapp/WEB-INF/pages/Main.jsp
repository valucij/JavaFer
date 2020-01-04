<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style type="text/css">
		.error {
		   font-family: fantasy;
		   font-weight: bold;
		   font-size: 0.9em;
		   color: #FF0000;
		   padding-left: 110px;
		}
</style>
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

<c:choose>

<c:when test="${currentUserId != null}">
<h1><c:out value="${currentUserName}"/></h1>
</c:when>


<c:otherwise>
<h3>Login</h3>
<form action="main" method="post">
<div>
Username:<input type="text" name="user" value="${usernameForm }"/>

</div>


<br>
<div>
Password:<input type="password" name="pass"/>

</div>

<input type="submit" value="Login"/>
</form>
<div class="error">
<c:if test="${loginError !=null}">
<br>
<c:out value="${loginError }"></c:out>
</c:if>
</div>


</c:otherwise>				
</c:choose>

<br><br>
<form action="register" method="get">
<input type="submit" value="Registriraj novog korisnika!"/><br>
</form>
<br>

<h4>Lista registriranih korisnika i linkovi koji vode na popis blogova istih:</h4>

<c:choose>
<c:when test="${users == null}">
<c:out value="Nema registriranih korisnika!"></c:out>
</c:when>
<c:otherwise>
<ol>
<c:forEach var="i" items="${users}">
<li><a href="author/${i.key}">${users.get(i.key)}</a></li>
</c:forEach>
</ol>
</c:otherwise>

</c:choose>


<br>
<c:if test="${currentUserId != null }">
<br><br>
<form action="logout" method="post">
<input type="submit" name="logout" value="Log out">
</form>
</c:if>

</body>
</html>