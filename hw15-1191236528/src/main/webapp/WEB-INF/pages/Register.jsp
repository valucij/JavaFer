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

<form action="register" method="post">

Ime:<input type="text" name="firstName">
<div class="error">
<c:if test="${errors.get('firstName')!=null }">
<br>
<c:out value="${errors.get('firstName')}"></c:out>
</c:if>
</div>
<br>
Prezime:<input type="text" name="lastName">
<div class="error">
<c:if test="${errors.get('lastName')!=null }">
<br>
<c:out value="${errors.get('lastName')}"></c:out>
</c:if>
</div>
<br>
E-mail:<input type="email" name="email">
<div class="error">
<c:if test="${errors.get('email')!=null }">
<br>
<c:out value="${errors.get('email')}"></c:out>
</c:if>
</div>
<br>
Username:<input type="text" name="username">
<div class="error">
<c:if test="${errors.get('username')!=null }">
<br>
<c:out value="${errors.get('username')}"></c:out>
</c:if>
</div>
<br>

Lozinka:<input type="password" name="password">
<div class="error">
<c:if test="${errors.get('password')!=null }">
<br>
<c:out value="${errors.get('password')}"></c:out>
</c:if>
</div>
<br>

<input type="submit" value="Registriraj me!">
</form>
<br>
<form action="main" method="post">
<input type="submit" value="Vrati se na početnu!">
</form>
</body>
</html>