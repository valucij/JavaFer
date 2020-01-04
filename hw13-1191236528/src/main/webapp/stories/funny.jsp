<%@ page import="java.util.ArrayList,java.util.List,java.util.Random, java.util.Arrays" language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
    
<%! 
private List<String> list = Arrays.asList("black", "Chocolate", "Magenta", "cyan", "GreenYellow", "goldenrod", "lightgray");
private int min = 0;
private int max = 6;
private Random rn = new Random();

private String getRandomColor() {
	return list.get(rn.nextInt(max - min + 1) + min);
}
%>    
    
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

<h1 style="color:<%= getRandomColor()%>;">How to Leave the Planet</h1>
<p style="color:<%= getRandomColor()%>;">1. Phone NASA. Their phone number is (713) 483-3111. Explain that it's very
important that you get away as soon as possible.<br>2. If they do not cooperate, phone any friend you may have in the White House - (202) 456-1414 - to have 
a word on your behalf with the guys in NASA.<br>3. If you don't have any friends in the White House, phone Kremlin (ask the overseas operator for
 0107-095-295-9051). They don't have any friends either(at least, none to speak of), but they do seem to have a little influence, so you may as well try.<br>
 4. If that also fails, phone the Pope for guidance. His telephone number is 011-39-6-6982, and I gather his switchboard is infallible.<br>
 5. If all these attempts fail, flag down a passing flying saucer and explain that it's vitally important you get away before phone bill arrives.</p>
</body>
<footer><p style="color:<%= getRandomColor()%>;">Douglas Adams</p></footer>
</html>