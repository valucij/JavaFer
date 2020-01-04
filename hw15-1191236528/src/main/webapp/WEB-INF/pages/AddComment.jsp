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

<form action="comment?blogID=${blogID }" method="post">
Sadržaj:
<textarea name="message" rows="20" cols="50"></textarea>
<div class="error">
<c:if test="${errors.get('messageError') != null }">
<c:out value="${errors.get('messageEmailError')} "></c:out>
</c:if>
</div>
<br>

<c:if test="${currentUserId == null }">
E-mail:
<input type="email" name="usersEmail"/>
<div class="error">
<c:if test="${errors.get('usersEmailError') != null }">
<c:out value="${errors.get('usersEmailError')} "></c:out>
</c:if>
</div>
</c:if>


<br>
<input type="submit" value="Dodaj komentar!">
</form>


</body>
</html>