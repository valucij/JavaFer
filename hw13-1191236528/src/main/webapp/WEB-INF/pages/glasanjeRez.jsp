<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
table.rez td {text-align: center;}
</style>
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
<h1>Rezultati glasanja</h1>
<p>Ovo su rezultati glasanja.</p>
<table border="1" class="rez">
<thead><tr><th>Bend</th><th>Broj glasova</th></tr></thead>
<tbody>

<c:forEach var="i" items="${mapRezultati}">
<tr><td>${mapBandInformations.get(i.key)}</td><td>${i.value}</td></tr>
</c:forEach>

</tbody>
</table>
<br>
<h2>Grafički prikaz rezultata</h2>
<img alt="Pie-Chart" src="glasanje-grafika" width="400" height="400" />
<br>

<h2>Rezultati u XLS formatu</h2>
<p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a></p>


<h2>Razno</h2>
<p>Primjeri pjesama pobjedničkih bendova:</p>
<ul>
<c:forEach var="i" begin="0" end="${winners.size()-1 }">
<li><a href="${winners.get(i) }">${mapBandInformations.get(winnerIds.get(i))}</a></li>
</c:forEach>
</ul>
</body>
</html>