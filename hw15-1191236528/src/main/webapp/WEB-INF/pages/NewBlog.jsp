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

<form action="author/${currentUserNick }/new" method="post">

Naslov:<input type="text" name="title">
<div class="error">
<c:if test="${errors.get('titleError') !=null }">
${errors.get('titleError')}
</c:if>
</div>
<br>

Sadržaj:<textarea rows="4" cols="50" name="textAll"> 
</textarea>
<div class="error">
<c:if test="${errors.get('textError') !=null }">
${errors.get('textError')}
</c:if>
</div>
<br>

<input type="submit" value="Objavi!"/>
</form>


</body>
</html>