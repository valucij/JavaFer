<%@ page  import="java.util.concurrent.TimeUnit" language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
<%! 
private String time() {
	long mili =(long) getServletContext().getAttribute("time");
	long currentMili = System.currentTimeMillis();
	
	mili = currentMili - mili;
	
	long days = TimeUnit.MILLISECONDS.toDays(mili);
	mili -= TimeUnit.DAYS.toMillis(days);
	long hours = TimeUnit.MILLISECONDS.toHours(mili);
	mili -= TimeUnit.HOURS.toMillis(hours);
	long min = TimeUnit.MILLISECONDS.toMinutes(mili);
	mili -= TimeUnit.MINUTES.toMillis(min);
	long sec = TimeUnit.MILLISECONDS.toSeconds(mili);
	mili -= TimeUnit.SECONDS.toMillis(sec);
		
	return days + " days, " + hours + " hours, " + min + " minutes, " + 
	sec + " seconds and " + mili + " miliseconds";
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
<h2>How long was since this app was started?</h2>
<p><%= time() %></p>
</body>
</html>