<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
	<style>
		div.container{
			width:80%;
			margin:auto;
			text-align:center;
		}
	</style>
</head>
<body>

<div class="container">
<h1>
	Hello world!  
</h1>


<P>  The time on the server is ${serverTime}. </P>
<a href="memo"><h3>한줄 메모장</h3></a>

<a href="melon/chart"><h3>Melon 차트</h3></a>
</div>
</body>
</html>
