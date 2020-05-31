<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<!DOCTYPE html>
<!--  No scriptlets!!! 
	  See http://download.oracle.com/javaee/5/tutorial/doc/bnakc.html 
-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="model" class="presentation.web.model.ViewFacilityOccupationModel" scope="request"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/resources/app.css"> 
<title>AulaGes: ver ocupação de instalação</title>
</head>
<body>
<h2>Ver Ocupação de Instalação</h2>
<c:choose>
<c:when test="${model.sessions == null}">
<form action="viewOccupation" method="post">
    <div class="mandatory_field">
    	<label for="name">Nome da instalação:</label> 
    	<input type="text" name="name" size="50" value="${model.name}"/> 
    </div>
    <div class="mandatory_field">
    	<label for="date">Dia a vizualizar (yyyy-mm-dd):</label> 
    	<input type="date" name="date" size="50" value="${model.date}"/> 
    </div>
    <div class="button" align="right">
   		<input type="submit" value="View Occupation">
   	</div>
</form>
</c:when>
<c:otherwise>
<c:forEach var="s" items="${model.sessions}">
	<li>${s}</li>
</c:forEach>
</c:otherwise>
</c:choose>
<form action="/aulasges-web-client" method="get">
<div class="button" align="right">
	<input type="submit" value="Home">
</div>
</form>
<c:if test="${model.hasMessages}">
	<p>Mensagens</p>
	<ul>
	<c:forEach var="mensagem" items="${model.messages}">
		<li>${mensagem} 
	</c:forEach>
	</ul>
</c:if>
</body>
</html>